package com.bruce.android.knowledge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.bruce.android.knowledge.adapter.BaseFramentPagerAdapter;
import com.bruce.android.knowledge.custom_view.desktop.DesktopTabHost;
import com.bruce.android.knowledge.fragments.BaseFragment;
import com.bruce.android.knowledge.fragments.FirstTabFragment;
import com.bruce.android.knowledge.fragments.SecondTabFragment;
import com.bruce.android.knowledge.fragments.ThirdTabFragment;
import com.bruce.android.knowledge.listeners.OnRefreshFragmentListener;
import com.bruce.android.knowledge.listeners.OnTabItemClickListener;

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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        Log.d("Bruce", displayMetrics.density + ", " + displayMetrics.densityDpi);
        Log.d("Bruce", display.getWidth() + ", " + display.getHeight());

        int id = R.drawable.ic_launcher;
        BitmapFactory.Options options = new BitmapFactory.Options();
//        Bitmap result1 = BitmapFactory.decodeResource(getResources(), id, options);
//        Log.d("Bruce", result1.getWidth() + ", " + result1.getHeight());
//
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), id, options);
//        Log.d("Bruce", options.outWidth + ", " + options.outHeight);

        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
//        options.inDensity = DisplayMetrics.DENSITY_XHIGH;
//        options.inTargetDensity = DisplayMetrics.DENSITY_XHIGH;
        Resources resources = getResources();
        Bitmap result = BitmapFactory.decodeResource(resources, id, options);
        Log.d("Bruce", result.getWidth() + ", " + result.getHeight());
    }


    private void initData() {
        mContext = DeskTopActivity.this;
        firstTabFragment = new FirstTabFragment();
        secondTabFragment = new SecondTabFragment();
        thirdTabFragment = new ThirdTabFragment();
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(secondTabFragment);
        fragmentList.add(firstTabFragment);
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


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Bruce", "deskTop: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Bruce", "deskTop: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Bruce", "deskTop: onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("Bruce", "deskTop: onSaveInstanceState");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        log("desktop: level = " + level);
        switch (level) {
            case TRIM_MEMORY_RUNNING_MODERATE:
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:

                break;
            case TRIM_MEMORY_UI_HIDDEN:
                break;
            case TRIM_MEMORY_BACKGROUND:

                break;
            case TRIM_MEMORY_MODERATE:

                break;
            case TRIM_MEMORY_COMPLETE:

                break;
        }
    }

    private void log(String s) {
        Log.d("Bruce", s);
    }
}
