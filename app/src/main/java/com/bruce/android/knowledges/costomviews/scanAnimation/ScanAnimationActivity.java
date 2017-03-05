package com.bruce.android.knowledges.costomviews.scanAnimation;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.bruce.android.knowledges.R;

public class ScanAnimationActivity extends Activity implements OnClickListener, AnimationSurfaceView.OnAnimationStausChangedListener {
    private Button btnEndAnimation;
    private Button btnStartAnimation;
    /**
     * 动画界面。
     */
    private AnimationSurfaceView animationSurfaceView;
    private IAnimationStrategy iAnimationStrategy;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_scan_animation_main1);


        btnStartAnimation = (Button) findViewById(R.id.btnStartAnim);
        btnEndAnimation = (Button) findViewById(R.id.btnEndAnim);
        btnStartAnimation.setOnClickListener(this);

        animationSurfaceView = (AnimationSurfaceView) findViewById(R.id.surfaceView);

        initScanAnimation();
    }

    private void initScanAnimation() {
        iAnimationStrategy = new ScanAnimaitonStrategy(animationSurfaceView, 300, 2000);
        animationSurfaceView.setStrategy(iAnimationStrategy);
        animationSurfaceView.setOnAnimationStausChangedListener(this);
        animationSurfaceView.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.scan_icon));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEndAnim:
                if (animationSurfaceView.isShowAnimation()) {
                    animationSurfaceView.endAnimation();
                }
                break;
            case R.id.btnStartAnim:
                if (!animationSurfaceView.isShowAnimation()) {
                    animationSurfaceView.startAnimation();
                }
                break;
        }
    }

    @Override
    public void onAnimationStart(AnimationSurfaceView view) {

    }

    @Override
    public void onAnimationEnd(AnimationSurfaceView view) {

    }
}