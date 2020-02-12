package com.cipher.driver_assist;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.cipher.driver_assist.app.CHANNEL_ID;

public class notification_handler extends Service
{
    private void get_emergency_state(final PendingIntent intent)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("int");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Boolean value=dataSnapshot.getValue(Boolean.class);
                send_notification(intent,value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d("db","Firebase error");
            }
        });

    }

    private void send_notification(PendingIntent intent,boolean value)
    {
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Emergency Mode")
                .setContentText("Emergency mode trigger detected")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(value)
        {
            startForeground(1,notification);
        }
        else
        {
            stopForeground(true);
        }


    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Intent notificationIntent=new Intent(this,MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent notifyIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        get_emergency_state(notifyIntent);

        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //Toast.makeText(this,"Service is running",Toast.LENGTH_SHORT).show();
        //send_notification();
    }
}
