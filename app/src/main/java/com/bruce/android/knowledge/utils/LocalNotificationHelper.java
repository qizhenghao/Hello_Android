package com.bruce.android.knowledge.utils;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.bruce.android.knowledge.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

import static android.content.Context.APP_OPS_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by cox
 * on 2/25/15.
 */
public class LocalNotificationHelper {
    public static final String id = "router_channel";
    public static final String name = "router_channel_name";
    public static final String ACTION_LOCAL_NOTIFYCATION = "com.xiaomi.router.localnotification.RECEIVE_MESSAGE";


    public static void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager == null)
            return;
        Notification notification1 = createNotification(context);
        notificationManager.notify(1001, notification1);
    }

    public static Notification createNotification(Context context) {
        Notification result;
        int requestCode = 1002;
        Intent intent = new Intent(ACTION_LOCAL_NOTIFYCATION);
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, flags);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager == null)
            return null;
        //适配Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(context, id)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("测试title")
                    .setContentText("测试content")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            result = builder.build();
            return result;
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setContentTitle("测试title");
            builder.setContentText("测试content");
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            result = builder.build();
            return result;
        }
    }

    /*
     * 判断通知权限是否打开
     */
    public static boolean isNotificationEnable(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            return notificationManagerCompat.areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT < 24 && Build.VERSION.SDK_INT >= 19) {
            return isNotificationEnable19(context);
        } else {
            return false;
        }
    }

    @TargetApi(19)
    private static boolean isNotificationEnable19(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();

        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

        try{
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod  = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (int)opPostNotificationValue.get(Integer.class);
            return ((int)checkOpNoThrowMethod.invoke(mAppOps,value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
