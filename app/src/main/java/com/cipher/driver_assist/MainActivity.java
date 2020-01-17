package com.cipher.driver_assist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity
{
    private final int time_out = 4000;

    private void get_permssion()
    {
        int perm=0;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Please enable the necessary permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, perm);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "All permissions granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(MainActivity.this, login.class);
                startActivity(home);
                finish();
            }
        }, time_out);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        get_permssion();
        startService(new Intent(this,notification_handler.class));
    }
}