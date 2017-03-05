package com.bruce.android.knowledges.costomviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by qizhenghao on 15-10-26.
 */
public class RotationTextView extends TextView {
    public RotationTextView(Context context) {
        super(context);
    }

    public RotationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
