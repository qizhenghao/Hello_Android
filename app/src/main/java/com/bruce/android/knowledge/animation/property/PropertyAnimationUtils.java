package com.bruce.android.knowledge.animation.property;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by qizhenghao on 17/4/14.
 */
public class PropertyAnimationUtils {

    public static ObjectAnimator startAnimator(Object object) {
//        ObjectAnimator animator = new ObjectAnimator();
        ObjectAnimator animator = ObjectAnimator.ofFloat(object, "scaleX", 0, 1);
        animator.setDuration(3000);
//        animator.setPropertyName("x");
//        animator.setTarget(object);
//        animator.setIntValues(0, 300);
        animator.setRepeatCount(6);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
//        animator.setEvaluator();
        animator.start();
        return animator;
    }

    //PropertyValuesHolder多属性一起动吧
    public static void startAnimator1(Object object) {
        PropertyValuesHolder a1 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder a2 = PropertyValuesHolder.ofFloat("translationX", 0, 300);
        ObjectAnimator.ofPropertyValuesHolder(object, a1, a2).setDuration(2000).start();
    }

    //Y轴3D旋转动画
    public static void startAnimator2(Object object) {
        ObjectAnimator.ofFloat(object, "rotationY", 0.0f, 360.0f).setDuration(1000).start();
    }

    public static void startAnimatorSet(Object view) {
        ObjectAnimator a1 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(view, "translationY", 0f, 300);
//        ......
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(5000);
        animSet.setInterpolator(new LinearInterpolator());
//animSet.playTogether(a1, a2, ...); //两个动画同时执行
        animSet.play(a1).after(a2); //先后执行
//        ......其他组合方式
        animSet.start();
    }
}
