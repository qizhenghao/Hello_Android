package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.bruce.android.knowledges.R;

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
