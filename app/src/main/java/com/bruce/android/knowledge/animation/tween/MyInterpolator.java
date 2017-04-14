package com.bruce.android.knowledge.animation.tween;

import android.view.animation.Interpolator;

/**
 * Created by qizhenghao on 17/4/14.
 */
public class MyInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        //AccelerateDecelerateInterpolator的实现
        return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }
}
