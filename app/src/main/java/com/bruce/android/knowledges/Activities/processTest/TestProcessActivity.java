package com.bruce.android.knowledges.Activities.processTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bruce.android.knowledges.R;

public class TestProcessActivity extends Activity implements View.OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_process_layout);
        findViewById(R.id.process_1).setOnClickListener(this);
        findViewById(R.id.process_2).setOnClickListener(this);
        findViewById(R.id.process_3).setOnClickListener(this);
        findViewById(R.id.process_4).setOnClickListener(this);
        findViewById(R.id.process_5).setOnClickListener(this);
        findViewById(R.id.process_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.process_1:
                startActivity(new Intent(this, Process1Activity.class));
                break;
            case R.id.process_2:
                startActivity(new Intent(this, Process2Activity.class));
                break;
            case R.id.process_3:
                startActivity(new Intent(this, Process3Activity.class));
                break;
            case R.id.process_4:
                startActivity(new Intent(this, Process4Activity.class));
                break;
            case R.id.process_5:
                startActivity(new Intent(this, Process5Activity.class));
                break;
            case R.id.process_6:
                startActivity(new Intent(this, Process6Activity.class));
                break;
        }
    }
}
