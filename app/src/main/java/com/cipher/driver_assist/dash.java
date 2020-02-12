package com.cipher.driver_assist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dash extends AppCompatActivity
{
    private CardView BviewCar,BEmergency,Bmap,BCamera;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    private void emergency_mode()
    {
        DatabaseReference intr=db.getReference("ath");
        intr.setValue(true);
        Toast.makeText(this,"Emergency mode activated-Anti Theft",Toast.LENGTH_SHORT).show();
    }

    private void show_promt()
    {
        new AlertDialog.Builder(this)
                .setTitle("Emergency Mode")
                .setMessage("Enable Emergency Mode?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1)
                    {
                       emergency_mode();
                    }
                }).create().show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_dash);

        BviewCar=findViewById(R.id.dash_viewcar);
        BEmergency=findViewById(R.id.dash_emer_mode);
        Bmap=findViewById(R.id.dash_maps);
        BCamera=findViewById(R.id.bcam);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        BviewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent carView=new Intent(dash.this,view_car.class);
                startActivity(carView);
            }
        });

        BEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                show_promt();
            }
        });

        Bmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent fcar=new Intent(dash.this,findCar.class);
                startActivity(fcar);
            }
        });

        BCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(dash.this,image_loader.class);
                startActivity(intent);
            }
        });
    }
}
