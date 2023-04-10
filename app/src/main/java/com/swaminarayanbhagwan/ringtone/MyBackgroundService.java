package com.swaminarayanbhagwan.ringtone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyBackgroundService extends Service {

    private MyCallReceiver myCallReceiver;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        myCallReceiver = new MyCallReceiver();
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(myCallReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification to show that the service is running
//        Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
//                .setContentTitle("My Background Service")
//                .setContentText("Service is running in the background")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .build();
//
//        // Start the service as a foreground service with the notification
//        startForeground(NOTIFICATION_ID, notification);

        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());

        return START_NOT_STICKY;
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("My Service")
                .setContentText("Running in the background")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        return builder.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myCallReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


