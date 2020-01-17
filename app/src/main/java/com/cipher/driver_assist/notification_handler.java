package com.cipher.driver_assist;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.BoringLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class notification_handler extends Service
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private void notificationx()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Emergency Mode")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());


    }

    private void get_message()
    {
        DatabaseReference mRef=database.getReference("int");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Boolean value=dataSnapshot.getValue(Boolean.class);
                notificationx();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        notificationx();
        Toast.makeText(this,"Process started",Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this,"Process ended",Toast.LENGTH_SHORT).show();
    }
}