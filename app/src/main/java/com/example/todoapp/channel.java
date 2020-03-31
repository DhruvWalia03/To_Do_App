package com.example.todoapp;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;

public class channel extends ContextWrapper {
    public static final String Channel_id_1="channel1";
    private NotificationManager manager;
    String name,date,time;

    public channel(Context context) {
        super(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationCreator();
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void notificationCreator() {
            NotificationChannel channel1=new NotificationChannel(Channel_id_1, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(" This is for Important Notifications ");

            getManager().createNotificationChannel(channel1);
        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public NotificationManager getManager() {
        if(manager == null)
            manager=getSystemService(NotificationManager.class);
        return manager;
    }

    /*public void getTitleOfTask(String name,String date, String time)
    {
        this.name=name;
        this.date=date;
        this.time=time;
    }*/

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), Channel_id_1)
                .setContentTitle("Task")
                .setContentText("Complete the work")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(R.mipmap.iclauncher);
    }
}