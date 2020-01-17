package com.cipher.driver_assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class login extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText txt_name,txt_pass;
    private Button btn_login;

    private static final String KEY_NAME = "abrewNeha";
    private Cipher cipher;
    private KeyStore keyStore;

    @TargetApi(Build.VERSION_CODES.M)
    private void get_fingerprint()
    {
        FingerprintManager.CryptoObject cryptoObject;
        FingerprintManager fingerprintManager;
        KeyguardManager keyguardManager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected())
            {
                Toast.makeText(this,"No Biometrics",Toast.LENGTH_SHORT).show();
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Please enable permissions",Toast.LENGTH_SHORT).show();
            }
            if (!fingerprintManager.hasEnrolledFingerprints())
            {
                Toast.makeText(this,"Register atleast one fingerprint",Toast.LENGTH_SHORT).show();
            }

            if (!keyguardManager.isKeyguardSecure())
            {
                Toast.makeText(this,"Biometrics not secure",Toast.LENGTH_SHORT).show();
            }
            else
            {
                try
                {
                    generateKey();
                }
                catch (FingerprintException e)
                {
                    e.printStackTrace();
                }

                if (initCipher())
                {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException
    {
        try
        {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);

            keyGenerator.init(new  KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        }
        catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException
                | InvalidAlgorithmParameterException | CertificateException | IOException exc)
        {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean initCipher()
    {
        try
        {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try
        {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }
        catch (KeyPermanentlyInvalidatedException e)
        {
            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private class FingerprintException extends Exception
    {
        FingerprintException(Exception e)
        {
            super(e);
        }
    }

    private void Sign_In(String email,String pass)
    {
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Log.d("X","Sign on sucess");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent dash=new Intent(login.this, dash.class);
                            startActivity(dash);
                        }
                        else
                        {
                            Toast.makeText(login.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        TextView title;

        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        title=findViewById(R.id.login_title);
        title.setLetterSpacing((float) 0.15);
        txt_name=findViewById(R.id.name);
        txt_pass=findViewById(R.id.pass);
        btn_login=findViewById(R.id.login);

        mAuth=FirebaseAuth.getInstance();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onStart()
    {
        super.onStart();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Sign_In(txt_name.getText().toString(),txt_pass.getText().toString());
            }
        });

        get_fingerprint();
    }
}
