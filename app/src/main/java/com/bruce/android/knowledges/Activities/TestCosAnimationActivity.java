package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bruce.android.knowledges.R;

public class TestCosAnimationActivity extends Activity {

    int shift = 1000;
    long cyclePeriod = 5000;
    ImageView imageView = null;

    int currentY;
    int currentX;
    int startY;
    int startX;
    long startTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cos_animation);
        imageView = (ImageView) findViewById(R.id.cos_animation_iv);
        initPosition();
        thread.start();
    }

    private void initPosition() {
        int[] position = new int[2];
        imageView.getLocationInWindow(position);
        Log.d("Bruce1", "getLocationInWindow:" + position[0] + "," + position[1]);
        startX = position[0];
        startY = position[1];
        currentX = startX;
        currentY = startY;
        startTime = System.currentTimeMillis();
    }

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            refreshIV();
            super.dispatchMessage(msg);
        }
    };

    private void refreshIV() {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(imageView.getLayoutParams());
        margin.setMargins(margin.leftMargin,currentY, margin.rightMargin, currentY+margin.height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        imageView.setLayoutParams(layoutParams);

    }

    private boolean isMoving;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            isMoving = true;
            while (isMoving) {
                computePosition();
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private void computePosition() {
        long intervalTime = (System.currentTimeMillis() - startTime) % cyclePeriod;
        double angle = Math.toRadians(360 * 1.0d * intervalTime / cyclePeriod);
        int y = (int) (shift / 2 * Math.cos(angle));
        y = Math.abs(y - shift/2);
        currentY = startY + y;
        Log.d("Bruce1", "currentX, currentY, angle, y:" + currentX + "," + currentY + "," + angle + "," + y);
    }

    @Override
    protected void onDestroy() {
        isMoving = false;
        super.onDestroy();
    }
}