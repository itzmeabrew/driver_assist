package com.cipher.driver_assist;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class app extends Application
{
    public static final String CHANNEL_ID = "drive_assist";


    private void notification_channel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel Channel = new NotificationChannel(CHANNEL_ID,"da", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(Channel);
            Channel.setDescription("This is the channel");
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        notification_channel();
    }

}
