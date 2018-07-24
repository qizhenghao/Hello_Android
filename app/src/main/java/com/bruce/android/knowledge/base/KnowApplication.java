package com.bruce.android.knowledge.base;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.activities.TestFlowLayoutActivity;

/**
 * Created by qizhenghao on 16/10/9.
 */
public class KnowApplication extends Application {

    public static final String TAG = "KnowApplication";

    public static KnowApplication context = null;
    public static boolean sWaitingSpecifiedWifiConnected;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.d(TAG, "currentThread().getId() = " + Thread.currentThread().getId() + "");
//        initNotification();
    }

//    private void initNotification() {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        RemoteViews remoteView = new RemoteViews(getPackageName(),R.layout.remote);
//        remoteView.setTextViewText(R.id.text, "Custom Text");
//        remoteView.setTextViewText(R.id.btn, "Custom Button");
//        remoteView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//
//        Intent notifyIntent = new Intent(this, TestFlowLayoutActivity.class);
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//// Creates the PendingIntent
//// 当设置下面PendingIntent.FLAG_UPDATE_CURRENT这个参数的时候，常常使得点击通知栏没效果，你需要给notification设置一个独一无二的requestCode
//        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent pendIntent = PendingIntent.getActivity(this, requestCode,
//                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteView.setOnClickPendingIntent(R.id.btn, pendIntent);
//
//        mBuilder.setSmallIcon(R.drawable.small);
//        mBuilder.setContent(remoteView);
//        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotifyManager.notify(NOTIFY_ID, mBuilder.build());
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
