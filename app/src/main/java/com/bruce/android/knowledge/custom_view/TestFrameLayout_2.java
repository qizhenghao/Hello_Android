package com.bruce.android.knowledge.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by qizhenghao on 17/4/19.
 */
public class TestFrameLayout_2 extends FrameLayout{

    public TestFrameLayout_2(Context context) {
        super(context);
    }

    public TestFrameLayout_2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestFrameLayout_2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Bruce", "TestFrameLayout_2: onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("Bruce", "TestFrameLayout_2: onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Bruce", "TestFrameLayout_2: onDraw");
    }
}
