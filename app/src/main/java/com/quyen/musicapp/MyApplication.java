package com.quyen.musicapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class MyApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static final String ChannelID="channel_service";

    @Override
    public void onCreate() {
        super.onCreate();

        CreateChannelnotification();
    }

    private void CreateChannelnotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(ChannelID,"Channel Service"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null,null);
            NotificationManager Manager=getSystemService(NotificationManager.class);
            if (Manager != null)
            {
                Manager.createNotificationChannel(notificationChannel);
            }
        }
    }
}
