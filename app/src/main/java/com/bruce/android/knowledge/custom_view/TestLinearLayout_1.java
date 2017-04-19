package com.bruce.android.knowledge.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by qizhenghao on 17/4/19.
 */
public class TestLinearLayout_1 extends FrameLayout{

    public TestLinearLayout_1(Context context) {
        super(context);
    }

    public TestLinearLayout_1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLinearLayout_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Bruce", "TestLinearLayout_: onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("Bruce", "TestLinearLayout_: onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Bruce", "TestLinearLayout_: onDraw");
    }
}
