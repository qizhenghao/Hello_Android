package com.bruce.android.knowledges.costomviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LineView extends View {

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayoutWidth(int width){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = width;
        setLayoutParams(params);
    }

    public void setLayoutWidthAndHeight(int width){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = width;
        params.height = width;
        setLayoutParams(params);
    }
}