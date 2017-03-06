package com.bruce.android.knowledge.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.bruce.android.knowledge.R;

/**
 * Created by qizhenghao on 16/5/5.
 */
public class TestImageViewActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_imageview_layout);
        context = this;
    }

}
