package com.bruce.android.knowledge.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

/**
 * Created by qizhenghao on 17/5/12.
 */
public class TestLoaderActivity extends Activity implements LoaderManager.LoaderCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
//        Special
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
