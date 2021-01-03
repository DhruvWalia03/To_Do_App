package com.example.todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    String n,t,d;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        n=intent.getStringExtra("Title");
        t=intent.getStringExtra("Time");
        d=intent.getStringExtra("Date");
        channel channel= new channel(context);
        channel.getTitleOfTask(n,d,t);
        NotificationCompat.Builder nb = channel.getChannelNotification();
        channel.getManager().notify(1,nb.build());
    }
}
