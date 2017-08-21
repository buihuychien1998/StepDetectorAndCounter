package com.tuananh.stepdetectorandcounter.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.tuananh.stepdetectorandcounter.R;
import com.tuananh.stepdetectorandcounter.step.UpdateUiCallBack;
import com.tuananh.stepdetectorandcounter.view.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FRAMGIA\vu.tuan.anh on 21/08/2017.
 */
public class StepService extends Service implements SensorEventListener {
    private UpdateUiCallBack mCallback;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private String mCurrentDate = "";
    private int mCurrentStep;
    private int mNotifyIdStep = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
        initTodayData();
//        initBroadcastReceiver();
//        new Thread(new Runnable() {
//            public void run() {
//                startStepDetector();
//            }
//        }).start();
//        startTimeCount();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        mCallback = paramICallback;
    }

    public int getStepCount() {
        return mCurrentStep;
    }

    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public PendingIntent getDefaultIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    private void initTodayData() {
        mCurrentDate = getTodayDate();
    }

    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getResources().getString(R.string.app_name))
            .setContentText("The number of steps today: " + mCurrentStep + " step")
            .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
            .setWhen(System.currentTimeMillis())
            .setPriority(Notification.PRIORITY_DEFAULT)
            .setAutoCancel(false)
            .setOngoing(true).setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = mBuilder.build();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startForeground(mNotifyIdStep, notification);
    }

    private void updateNotification() {
        Intent hangIntent = new Intent(this, MainActivity.class);
        PendingIntent hangPendingIntent =
            PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification =
            mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("The number of steps today: " + mCurrentStep + " step")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(hangPendingIntent)
                .build();
        mNotificationManager.notify(mNotifyIdStep, notification);
        if (mCallback != null) {
            mCallback.updateUi(mCurrentStep);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }
}
