package com.cipher.driver_assist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class image_loader extends AppCompatActivity
{
    private ImageView im;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        im=findViewById(R.id.secam);
        progressBar=findViewById(R.id.progress_bar);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        final String url="www.cozy-buttons.000webhostapp.com/uploads/1.jpg";
        new image_downlader_api(im).execute(url);
    }
}
