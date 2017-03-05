package com.bruce.android.knowledges;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import com.bruce.android.knowledges.Activities.*;
import com.bruce.android.knowledges.Activities.processTest.TestProcessActivity;
import com.bruce.android.knowledges.adapter.BaseFramentPagerAdapter;
import com.bruce.android.knowledges.costomviewdemos.DemoPopupWindow;
import com.bruce.android.knowledges.costomviews.TweenAnimation.TestTweenAnimationActivity;
import com.bruce.android.knowledges.costomviews.desktop.DesktopTabHost;
import com.bruce.android.knowledges.costomviews.pinnedHeaderListView.TestPinnedHeaderListviewActivity;
import com.bruce.android.knowledges.costomviews.scanAnimation.ParabolaAnimationActivity;
import com.bruce.android.knowledges.costomviews.scanAnimation.ScanAnimationActivity;
import com.bruce.android.knowledges.fragments.BaseFragment;
import com.bruce.android.knowledges.fragments.FirstTabFragment;
import com.bruce.android.knowledges.fragments.SecondTabFragment;
import com.bruce.android.knowledges.fragments.ThirdTabFragment;
import com.bruce.android.knowledges.listeners.OnRefreshFragmentListener;
import com.bruce.android.knowledges.listeners.OnTabItemClickListener;
import com.bruce.android.knowledges.net.demo.TestHttpActivity;
import com.bruce.android.knowledges.services.ServiceTestActivity;

import java.util.ArrayList;
import java.util.List;

public class DeskTopActivity extends FragmentActivity implements View.OnClickListener, OnRefreshFragmentListener, OnTabItemClickListener {

    private Context mContext = null;
    private ViewPager viewPager;
    private DesktopTabHost desktopTabHost;
    private BaseFramentPagerAdapter viewPagerAdapter;
    private List<BaseFragment> fragmentList;

    private FirstTabFragment firstTabFragment;
    private SecondTabFragment secondTabFragment;
    private ThirdTabFragment thirdTabFragment;
    private long currTime;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        currTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        Log.d("Bruce", "init super cost time: " + (System.currentTimeMillis() - currTime));
        currTime = System.currentTimeMillis();
        setContentView(R.layout.activity_main);
        Log.d("Bruce", "setContentView cost time: " + (System.currentTimeMillis() - currTime));
        currTime = System.currentTimeMillis();
        initViews();
        Log.d("Bruce", "initViews cost time: " + (System.currentTimeMillis() - currTime));
        Log.d("Bruce", "Runtime.getRuntime().availableProcessors(): " + Runtime.getRuntime().availableProcessors());
        currTime = System.currentTimeMillis();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        mContext = DeskTopActivity.this;
        firstTabFragment = new FirstTabFragment();
        secondTabFragment = new SecondTabFragment();
        thirdTabFragment = new ThirdTabFragment();
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(firstTabFragment);
        fragmentList.add(secondTabFragment);
        fragmentList.add(thirdTabFragment);

        viewPagerAdapter = new BaseFramentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        desktopTabHost.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        desktopTabHost.setCurrentItem(0);

    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        desktopTabHost = (DesktopTabHost) findViewById(R.id.tab_page_indicator);
        desktopTabHost.setViewIds(new int[]{R.id.tab_line_layout, R.id.tab_one, R.id.tab_two, R.id.tab_three});
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Bruce", "uptimeMillis = " + SystemClock.uptimeMillis() + "");
                handler.postAtTime(this, SystemClock.uptimeMillis() + 5*1000);
            }
        };
        handler.postAtTime(runnable, SystemClock.uptimeMillis() + 5*1000);
    }


    private AlphaAnimation mHideAnimation = null;
    private AlphaAnimation mShowAnimation = null;

    @Override
    public void onClick(View v) {
    }


    /**
     * View渐隐动画效果
     */
    private void setHideAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != mHideAnimation) {
            mHideAnimation.cancel();
        }
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        view.startAnimation(mHideAnimation);
    }

    /**
     * View渐现动画效果
     */
    private void setShowAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != mShowAnimation) {
            mShowAnimation.cancel();
        }
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        view.startAnimation(mShowAnimation);
    }

    @Override
    public void onRefresh(Class fragmentClass) {

    }

    @Override
    public void onTabItemSelected(int index, Bundle args) {

    }
}
