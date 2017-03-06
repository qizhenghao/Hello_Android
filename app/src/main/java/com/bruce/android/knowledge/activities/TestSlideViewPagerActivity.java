package com.bruce.android.knowledge.activities;

import android.app.Activity;
import android.os.Bundle;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.custom_view.SlideViewPager;

public class TestSlideViewPagerActivity extends Activity {
	private SlideViewPager mSlideViewPager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_slide_viewpager_activity);
		mSlideViewPager = (SlideViewPager) findViewById(R.id.view_pager);
	}

}