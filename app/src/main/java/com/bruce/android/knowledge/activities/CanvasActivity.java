package com.bruce.android.knowledge.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.bruce.android.knowledge.R;

public class CanvasActivity extends Activity {
    private DisplayMetrics displayMetrics;

    /**
     * 画一个几何图形
     * hades
     * 蓝色着衣
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);

        displayMetrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
    }

    public class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            // 设置画布的背景颜色
            canvas.drawColor(Color.WHITE);
            /**
             * 定义矩形为空心
             */
            // 定义画笔1
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
//            paint.setStyle(Paint.Style.FILL);
            // 消除锯齿
            paint.setAntiAlias(true);
            // 设置画笔的颜色
            paint.setColor(Color.RED);
            // 设置paint的外框宽度
            paint.setStrokeWidth(4);

            // 画一个圆
            canvas.drawCircle(40, 30, 20, paint);
            // 画一个正放形
            canvas.drawRect(20, 70, 70, 120, paint);
            // 画一个长方形
            canvas.drawRect(20, 170, 90, 230, paint);

            //画渐变弧度
            RectF rectF = new RectF(20f, 250f, 200f, 430f);
            float startAngle = 135f, sweepAngle = 270;
            Paint sweepPaint = new Paint();
            sweepPaint.setAntiAlias(true);
            sweepPaint.setStyle(Paint.Style.STROKE);
            sweepPaint.setStrokeCap(Paint.Cap.ROUND);
            sweepPaint.setStrokeWidth(50);
            sweepPaint.setColor(getResources().getColor(R.color.green));
            sweepPaint.setShader(null);
            canvas.drawArc(rectF, startAngle+120, sweepAngle-120, false, sweepPaint);

            SweepGradient sweepGradient = new SweepGradient(110, 340, new int[] {Color.CYAN,Color.DKGRAY,Color.GRAY,Color.LTGRAY,Color.MAGENTA,
                    Color.GREEN,Color.TRANSPARENT, Color.BLUE }, null);
            sweepPaint.setShader(sweepGradient);
            canvas.drawArc(rectF, startAngle, sweepAngle - 120, false, sweepPaint);

//            int padding = 20;
//            int r = displayMetrics.widthPixels - padding * 2;
//            int left = displayMetrics.widthPixels/2 - 100;
//            int right = displayMetrics.widthPixels/2 + 100;
//            int leftMin = padding;
//            int top = 200;
//            int bottom = r + top + (left-leftMin)/20*20;
//            int everyPadding = 20;
//            while (left > leftMin) {
//                // 画一个椭圆
//                RectF re = new RectF(left, top, right, bottom);
//                canvas.drawOval(re, paint);
//                left -= everyPadding;
//                right += everyPadding;
//                top += everyPadding;
//                bottom -= everyPadding;
//            }

            paint.setColor(Color.BLACK);
            float padding = 100;
            float r = (displayMetrics.widthPixels - padding * 2)/2;
            float left = padding;
            float leftMax = displayMetrics.widthPixels/2 - 100;
            float changed = leftMax - left;
            float top = 720;
            float bottom = 2*r + top + changed;
            top -= changed;
            float leftMin = left;
            left = leftMax;
            float right = displayMetrics.widthPixels/2 + 100;
            float everyPadding = 20;
            while (left > leftMin) {
                paint.setAlpha((int) ((1.1-(left-leftMin)*1.0f/changed)*255));
                RectF re = new RectF(left, top, right, bottom);
                // 画一个椭圆
                canvas.drawOval(re, paint);
                left -= everyPadding;
                right += everyPadding;
                top += everyPadding;
                bottom -= everyPadding;
            }


//            paint.setColor(Color.BLACK);
//            float centerPointLeft = displayMetrics.widthPixels/2;
//            float centerPointTop = displayMetrics.heightPixels/2;
//            float padding = 100;
//            float fixedMin = 330;
//            float r = (displayMetrics.widthPixels - padding * 2)/2;
//            float s = r * r;
//
//            float left = centerPointLeft - fixedMin;
//            float right = centerPointLeft + fixedMin;
//            float a = (right - left) / 2;
//            float b = s / a;
//            float top = centerPointTop - b;
//            float bottom = centerPointTop + b;
//
//            float everyPadding = 10;
//            while (a < b) {
//                paint.setAlpha((int) ((r - (left - padding))/r*255));
//                RectF re = new RectF(left, top, right, bottom);
//                // 画一个椭圆
//                canvas.drawOval(re, paint);
//                left -= everyPadding;
//                right += everyPadding;
//                a = (right - left) / 2;
//                b = s / a;
//                top = centerPointTop - b;
//                bottom = centerPointTop + b;
//            }

//            /**
//             * 定义矩形为实心
//             */
//            // 定义画笔2
//            Paint paint2 = new Paint();
//            paint2.setStyle(Paint.Style.FILL);
//            // 消除锯齿
//            paint2.setAntiAlias(true);
//            // 设置画笔的颜色
//            paint2.setColor(Color.BLUE);
//            // 画一个圆
//            canvas.drawCircle(240, 30, 20, paint2);
//            // 画一个正放形
//            canvas.drawRect(220, 70, 70, 120, paint2);
//            // 画一个长方形
//            canvas.drawRect(220, 170, 90, 230, paint2);
//            // 画一个椭圆
//            RectF re2 = new RectF(220, 250, 100, 400);
//            canvas.drawOval(re2, paint2);
        }
    }
}