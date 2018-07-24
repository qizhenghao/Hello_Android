package com.bruce.android.knowledge.activities.wifiTest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lcy
 * on 1/9/15.
 * <p>
 * Add more set/get types if need.
 */
public class SimpleSharedPref {

    private Context context;
    private String id;

    public SimpleSharedPref(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    public String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public boolean getBool(String key, boolean defaultVal) {
        return getSharedPreferences().getBoolean(key, defaultVal);
    }

    public void setBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(id, Context.MODE_PRIVATE);
    }

}
