package com.bruce.android.knowledges.costomviews.TweenAnimation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.*;
import android.widget.ImageView;
import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.scanAnimation.AnimationSurfaceView;
import com.bruce.android.knowledges.costomviews.scanAnimation.IAnimationStrategy;
import com.bruce.android.knowledges.costomviews.scanAnimation.ScanAnimaitonStrategy;

public class TestTweenAnimationActivity extends Activity implements View.OnClickListener {

    private ImageView yanZhiIv = null;
    private ImageView baoBeiErIv = null;
    private ImageView dogIv = null;
    private ImageView girlIv = null;
    private ImageView noMedicineIv = null;

    private AnimationSurfaceView scanAnimationSurfaceView;
    private IAnimationStrategy iAnimationStrategy;

    //Alpha动画 - 渐变透明度
    private Animation alphaAnimation = null;

    //Sacle动画 - 渐变尺寸缩放
    private Animation scaleAnimation = null;

    //Translate动画 - 位置移动
    private Animation translateAnimation = null;

    //Rotate动画 - 画面旋转
    private Animation rotateAnimation = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_tween_animation);
        initViews();

    }

    private void initViews() {
        yanZhiIv = (ImageView) findViewById(R.id.welcome_main_yanzhi_iv);
        baoBeiErIv = (ImageView) findViewById(R.id.welcome_main_baobeier_iv);
        dogIv = (ImageView) findViewById(R.id.welcome_main_dog_iv);
        girlIv = (ImageView) findViewById(R.id.welcome_main_girl_iv);
        noMedicineIv = (ImageView) findViewById(R.id.welcome_main_no_medicine_iv);
        scanAnimationSurfaceView = (AnimationSurfaceView) findViewById(R.id.welcome_main_scan_iv);
        Bitmap scanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_scan);
        scanAnimationSurfaceView.setIcon(scanBitmap);
        iAnimationStrategy = new ScanAnimaitonStrategy(scanAnimationSurfaceView, 195, 1000);
        scanAnimationSurfaceView.setStrategy(iAnimationStrategy);
        scanAnimationSurfaceView.setMarginLeft((((WindowManager)(getApplicationContext().getSystemService(WINDOW_SERVICE))).getDefaultDisplay().getWidth()-scanBitmap.getWidth())/2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translateAnimation:
                Log.e("Tween", "onKeyDown - KEYCODE_DPAD_RIGHT");
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 300.0f);
                translateAnimation.setDuration(1000);
                translateAnimation.setRepeatCount(1000);
                translateAnimation.setRepeatMode(Animation.REVERSE);
//                translateAnimation.setFillEnabled(true);
//                translateAnimation.setFillAfter(true);
                findViewById(R.id.scaleAnimation).startAnimation(translateAnimation);
                break;

            case R.id.rotateAnimation:
                Log.e("Tween", "onKeyDown - KEYCODE_DPAD_DOWN");
                rotateAnimation = new RotateAnimation(0f, 360f);
                rotateAnimation.setDuration(1000);
//                imageView.startAnimation(rotateAnimation);
                break;

            case R.id.scaleAnimation:
                Log.e("Tween", "onKeyDown - KEYCODE_DPAD_LEFT");
                AnimationSet baoBeiErAnimationSet = getAnimationSet(0.9f, 0.9f, 1.05f, 0.95f);
//                baoBeiErIv.setVisibility(View.VISIBLE);
                baoBeiErIv.startAnimation(baoBeiErAnimationSet);
                AnimationSet dogAnimationSet = getAnimationSet(0.4f, 0.2f, 1.1f, 0.9f);
//                dogIv.setVisibility(View.VISIBLE);
                dogAnimationSet.setStartOffset(300);
                dogIv.startAnimation(dogAnimationSet);
                AnimationSet girlAnimationSet = getAnimationSet(0.1f, 0.9f, 1.05f, 0.95f);
//                girlIv.setVisibility(View.VISIBLE);
                girlAnimationSet.setStartOffset(150);
                girlIv.startAnimation(girlAnimationSet);
                AnimationSet noMedicineAnimationSet = getAnimationSet(0.5f, 0.9f, 1.125f, 0.9f);
//                noMedicineIv.setVisibility(View.VISIBLE);
                noMedicineAnimationSet.setStartOffset(200);
                noMedicineIv.startAnimation(noMedicineAnimationSet);
                ScaleAnimation zoomIn = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                zoomIn.setDuration(200);
                zoomIn.setStartOffset(200);
                zoomIn.setFillEnabled(true);
                zoomIn.setFillAfter(true);
                yanZhiIv.startAnimation(zoomIn);

                if (scanAnimationSurfaceView.isShowAnimation()) {
                    scanAnimationSurfaceView.endAnimation();
                    scanAnimationSurfaceView.startAnimation();
                } else {
                    scanAnimationSurfaceView.startAnimation();
                }
                break;

            case R.id.alphaAnimation:
                Log.e("Tween", "onKeyDown - KEYCODE_DPAD_UP");
                alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                //设置动画时间
                alphaAnimation.setDuration(3000);
//                imageView.startAnimation(alphaAnimation);
                break;
            case R.id.animation_set:
                Log.e("Tween", "onKeyDown - KEYCODE_DPAD_CENTER");
                //初始化 Translate动画
                translateAnimation = new TranslateAnimation(0.1f, 100.0f, 0.1f, 100.0f);
                //初始化 Alpha动画
                alphaAnimation = new AlphaAnimation(0.1f, 1.0f);

                //动画集
                AnimationSet set = new AnimationSet(true);
                set.addAnimation(translateAnimation);
                set.addAnimation(alphaAnimation);

                //设置动画时间 (作用到每个动画)
                set.setDuration(1000);
//                imageView.startAnimation(set);
                break;
        }
    }

    private AnimationSet getAnimationSet(float relativeX, float relativeY, float maxZoom, float minZoom) {
        ScaleAnimation zoomIn, zoomOut, zoomRepeat;
        zoomIn = new ScaleAnimation(0.0f, maxZoom, 0.0f, maxZoom, Animation.RELATIVE_TO_SELF, relativeX,
                Animation.RELATIVE_TO_SELF, relativeY);
        zoomOut = new ScaleAnimation(maxZoom, minZoom, maxZoom, minZoom, Animation.RELATIVE_TO_SELF, relativeX,
                Animation.RELATIVE_TO_SELF, relativeY);
        zoomRepeat = new ScaleAnimation(minZoom, 1.0f, minZoom, 1.0f, Animation.RELATIVE_TO_SELF, relativeX,
                Animation.RELATIVE_TO_SELF, relativeY);

        zoomIn.setDuration(300);
        zoomOut.setDuration(200);
        zoomOut.setStartOffset(300);
        zoomRepeat.setDuration(100);
        zoomRepeat.setStartOffset(500);
        AnimationSet tabAnimSet = new AnimationSet(true);
        tabAnimSet.addAnimation(zoomIn);
        tabAnimSet.addAnimation(zoomOut);
//        tabAnimSet.addAnimation(zoomRepeat);
        tabAnimSet.setFillEnabled(true);
        tabAnimSet.setFillAfter(true);
        return tabAnimSet;
    }
}