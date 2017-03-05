package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class CanvasActivity extends Activity {
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
            // 画一个椭圆
            RectF re = new RectF(20, 250, 100, 400);
            canvas.drawOval(re, paint);

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