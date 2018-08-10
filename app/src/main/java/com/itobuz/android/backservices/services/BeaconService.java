package com.itobuz.android.backservices.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.itobuz.android.backservices.MainActivity;
import com.itobuz.android.backservices.R;

public class BeaconService extends Service {


    public static final String TAG = BeaconService.class.getSimpleName();
    private static final String CHANNEL_ID = "beacon_channel_id";
    public static final int NOTIFY_ID = 1;


    public BeaconService(Context applicationContext) {
        super();
        Log.i(TAG, "here I am!");
    }

    public BeaconService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        showNotification();
        return START_STICKY;

//        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


   public void showNotification(){

        Intent notificationIntent = new Intent(getApplicationContext(),MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("service is running")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();

            startForeground(NOTIFY_ID,notification);


        } else {

            Notification builder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("service is running")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();

            startForeground(NOTIFY_ID,builder);

        }
    }



}
