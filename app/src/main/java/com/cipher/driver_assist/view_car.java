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
    private TextView odo,cap,miles,reg,mdl,eng,hp,name,time,fuel,age;
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
                            odo.setText(document.getDouble("odo")+" KM");
                            cap.setText(document.getDouble("cap")+"L");
                            miles.setText(String.valueOf(document.getDouble("milage")));
                            mdl.setText(document.getString("model_name"));
                            eng.setText(document.getString("eng_type"));
                            hp.setText(document.getDouble("hp")+" KW");
                            time.setText("4:30");
                            fuel.setText(document.getString("fuel_type"));
                            age.setText(String.valueOf(document.getDouble("age")));

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
        miles=findViewById(R.id.vmilage);
        reg=findViewById(R.id.vreg);
        hp=findViewById(R.id.vpwr);
        eng=findViewById(R.id.veng);
        name=findViewById(R.id.vname);
        mdl=findViewById(R.id.vmdl);
        fuel=findViewById(R.id.vfuel);
        cap=findViewById(R.id.vcap);
        age=findViewById(R.id.vage);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getData();
    }
}
