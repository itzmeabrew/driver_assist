package com.cipher.driver_assist;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class view_car extends AppCompatActivity
{
    private TextView odo,time,miles,reg,lat,lng,alt,name;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    private void getData()
    {
        db.collection("car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        try
                        {
                            name.setText(document.getString("owner"));
                            reg.setText(document.getString("reg_no"));
                            Log.d("AX", document.getId() + " => " + document.getData());
                        }
                        catch (NullPointerException e)
                        {
                            Log.d("AX","Ex Error");
                        }
                    }
                }
                else
                {
                    Log.d("AX","Error");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_view_car);

        odo=findViewById(R.id.vodo);
        time=findViewById(R.id.vtime);
        miles=findViewById(R.id.vmiles);
        reg=findViewById(R.id.vreg);
        lat=findViewById(R.id.vlat);
        lng=findViewById(R.id.vlng);
        name=findViewById(R.id.vname);
        alt=findViewById(R.id.valt);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getData();
    }
}
