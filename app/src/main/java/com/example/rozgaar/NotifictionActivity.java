package com.example.rozgaar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationManagerCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.logging.ConsoleHandler;

public class NotifictionActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "personal_notifications";
    private final String CHANNEL_NAME = "personal_notification";
    private final String CHANNEL_DESC = "personal notification";


    private final int NOTIFICATION_ID = 001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);



        }
    }


    public void displayNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotifictionActivity.this,CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_sms_notificatio);
        builder.setContentTitle("Rozgaar");
        builder.setContentText("New Job Update");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());





    }

    public void to_intent_sample(View view) {
        Intent intent = new Intent(this, Intent_sample.class);
        startActivity(intent);
    }
}
