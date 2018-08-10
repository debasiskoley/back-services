package com.itobuz.android.backservices;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.itobuz.android.backservices.services.BeaconService;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public int counter = 0;
    Intent mServiceIntent;
    private BeaconService mBeaconService;
    Context context;
    public Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        createChanel();

        mBeaconService = new BeaconService(getContext());
        mServiceIntent = new Intent(getContext(), mBeaconService.getClass());
        if(!isBeaconServiceRunning(mBeaconService.getClass())) {
            //Start Background Service
            startService(mServiceIntent);

//            //Start process in background;
//            startTimer();
        }
    }

    private boolean isBeaconServiceRunning(Class<? extends BeaconService> aClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (aClass.getName().equals(service.service.getClassName())) {
                    Log.i ("isBeaconServiceRunning?", true+"");
                    return true;
                }
            }
        }
        Log.i ("isBeaconServiceRunning?", false+"");
        return false;
    }


    private void createChanel(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "beacon_channel_id";
        CharSequence channelName = "Beacon Channel";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_LOW;
        }
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
        }
        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Notification should disappear when activty goes to foreground
        NotificationManager manager =
                (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.cancel(BeaconService.NOTIFY_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mBeaconService.showNotification();
    }

    @Override
    protected void onDestroy() {

//        // Stop Background Service onDestroy;
//        stopService(mServiceIntent);
//        Log.i("MainActivity", "onDestroy!");
//
//        // Stop process in background onDestroy;
//        Log.i("Timer ", "onDestroy");
//        stoptimertask();

        super.onDestroy();
    }


    /**
     *  My custom timer process.
     */

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;


    private void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
