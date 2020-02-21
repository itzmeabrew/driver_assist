package com.cipher.driver_assist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class camera_loader extends AppCompatActivity
{
    private ImageView im;
    private ProgressBar progressBar;
    private Button btn_save;

    final String url="https://cozy-buttons.000webhostapp.com/uploads/cam.jpg";

    private void set_image()
    {
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(im);
    }

    private void write_to_file(Bitmap bitmap)
    {
        File filepath=Environment.getExternalStorageDirectory();
        File dir=new File(filepath.getAbsolutePath()+"/Drive_Assist/");

        dir.mkdir();

        File file=new File(dir,System.currentTimeMillis()+".jpg");
        try
        {
            FileOutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,output);
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        im=findViewById(R.id.secam);
        progressBar=findViewById(R.id.progress_bar);
        btn_save=findViewById(R.id.ser_save);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        set_image();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Bitmap bm;
                try {
                    URL uri=new URL(url);
                    bm=BitmapFactory.decodeStream(uri.openConnection().getInputStream());
                    write_to_file(bm);
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }
}
