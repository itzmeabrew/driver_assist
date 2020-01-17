package com.cipher.driver_assist;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback
{
    private Context context;

    FingerprintHandler(Context mContext)
    {
        context = mContext;
    }

    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject)
    {
        CancellationSignal cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override

    public void onAuthenticationError(int errMsgId, CharSequence errString)
    {
        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override

    public void onAuthenticationFailed()
    {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString)
    {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result)
    {
        Toast.makeText(context, "Success!-Login", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context,dash.class));
    }

}