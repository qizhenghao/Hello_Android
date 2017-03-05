package com.bruce.android.knowledges.base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by qizhenghao on 16/10/9.
 */
public class KnowApplication extends Application {

    public static final String TAG = "KnowApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "currentThread().getId() = " + Thread.currentThread().getId() + "");
    }

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
