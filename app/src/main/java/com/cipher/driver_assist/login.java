package com.cipher.driver_assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText txt_name,txt_pass;
    private Button btn_login;

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

        mAuth=FirebaseAuth.getInstance();

        txt_name=findViewById(R.id.name);
        txt_pass=findViewById(R.id.pass);
        btn_login=findViewById(R.id.login);
    }

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
    }
}
