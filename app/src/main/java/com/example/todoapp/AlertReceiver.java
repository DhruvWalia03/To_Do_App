package com.example.todoapp;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

class AlertReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        channel channel= new channel(context);
        NotificationCompat.Builder nb = channel.getChannelNotification();
        channel.getManager().notify(1,nb.build());
    }
}