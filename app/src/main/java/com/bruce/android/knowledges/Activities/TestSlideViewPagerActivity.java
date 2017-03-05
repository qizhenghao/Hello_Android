package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.SlideViewPager;
import com.bruce.android.knowledges.costomviews.SomeShaders;

public class TestSlideViewPagerActivity extends Activity {
	private SlideViewPager mSlideViewPager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_slide_viewpager_activity);
		mSlideViewPager = (SlideViewPager) findViewById(R.id.view_pager);
	}

}