package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import com.bruce.android.knowledges.costomviews.SomeShaders;

public class TestShaderActivity extends Activity {
	private SomeShaders mGameView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGameView = new SomeShaders(this);

		setContentView(mGameView);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		super.onKeyUp(keyCode, event);
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mGameView == null) {
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		}
		return mGameView.onKeyDown(keyCode, event);
	}
}