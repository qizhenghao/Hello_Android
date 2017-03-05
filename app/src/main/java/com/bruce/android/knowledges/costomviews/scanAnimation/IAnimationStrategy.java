package com.bruce.android.knowledges.costomviews.scanAnimation;

/**
 * Created by qizhenghao on 15-11-9.
 */
public interface IAnimationStrategy {

    public void compute();

    public boolean doing();

    public void start();

    public double getX();

    public double getY();

    public void cancel();
}
