package com.bruce.android.knowledges.costomviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class DrawableCenterTextView extends TextView {

	public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawableCenterTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DrawableCenterTextView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[0];
			if (drawableLeft != null) {
				float textWidth = getPaint().measureText(getText().toString());
				int drawablePadding = getCompoundDrawablePadding();
				int drawableWidth = drawableLeft.getIntrinsicWidth();
				float bodyWidth = textWidth + drawableWidth + drawablePadding;
				canvas.translate((getWidth() - bodyWidth) / 2, 0);
			}
		}
		super.onDraw(canvas);
	}
}
