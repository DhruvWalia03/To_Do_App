/* package com.example.todoapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import java.nio.channels.Channel;

public class channel extends ContextWrapper {
    public static final String Channel_id_1="channel1";
    public static final String Channel_id_2="channel2";

    public channel(Context context) {
        super(context);
        notificationCreator();
    }


    private void notificationCreator() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(Channel_id_1, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel2= new NotificationChannel(Channel_id_2,"CHannel 2",NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription(" This is for Important Notifications ");
            channel2.setDescription(" This is for Normal Notification ");

            NotificationManager manager= getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2); 
        }
    }
}
*/