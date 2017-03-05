package com.bruce.android.knowledges.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by qizhenghao on 15-9-22.
 */
public class NetUtils {

    /**
     * 检查网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return !(info == null || !info.isAvailable());
    }

}
