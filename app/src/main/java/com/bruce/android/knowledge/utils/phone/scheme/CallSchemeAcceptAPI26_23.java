package com.bruce.android.knowledge.utils.phone.scheme;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telecom.TelecomManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * created by Lin on 2017/12/16
 */

public class CallSchemeAcceptAPI26_23 implements ICallSchemeAccept {
    
    @Override
    public void acceptCall(Context context) throws
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /* Android 8.0 加入了接听呼入电话的权限 */
        /* android.permission.ANSWER_PHONE_CALLS */
        /* 在版本为8.0的情况下,可以直接调用TelecomManager#acceptRingtingCall来接听电话,不用通过AIDL */
        /* 在不修改targetSDK的情况下,这里采用反射去调用 */
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, 111);
            return;
        }
        TelecomManager telecomManager =
                (TelecomManager) context.getSystemService(context.TELECOM_SERVICE);
        Method method = Class.forName("android.telecom.TelecomManager").getMethod("acceptRingingCall");
        method.invoke(telecomManager);
    }
    
}
