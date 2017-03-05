package com.bruce.android.knowledges.Activities.processTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bruce.android.knowledges.R;

/**
 * Created by qizhenghao on 17/1/19.
 */
public class Process5Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_layout);
        ((TextView) findViewById(R.id.tv)).setText(this.getClass().getName());
    }
}
