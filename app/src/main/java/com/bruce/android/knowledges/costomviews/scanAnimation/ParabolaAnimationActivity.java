package com.bruce.android.knowledges.costomviews.scanAnimation;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.bruce.android.knowledges.R;

import static com.bruce.android.knowledges.costomviews.scanAnimation.AnimationSurfaceView.OnAnimationStausChangedListener;

public class ParabolaAnimationActivity extends Activity implements OnClickListener, OnAnimationStausChangedListener, Callback,
        RotateAnimation.InterpolatedTimeListener {
    public static final int REFRESH_TEXTVIEW = 1;
    private Button btnStartAnimation;
    /**
     * 动画界面。
     */
    private AnimationSurfaceView animationSurfaceView;
    private IAnimationStrategy iAnimationStrategy;
    /**
     * 购物车处显示购物数量的TextView。
     */
    private TextView txtNumber;
    /**
     * 购物车中的数量。
     */
    private int number;
    private Handler handler;
    /**
     * TextNumber是否允许显示最新的数字。
     */
    private boolean enableRefresh;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_scan_animation_main);

        handler = new Handler(this);

        number = 0;
        btnStartAnimation = (Button) findViewById(R.id.btnStartAnim);
        txtNumber = (TextView) findViewById(R.id.txtNumber);
        animationSurfaceView = (AnimationSurfaceView) findViewById(R.id.surfaceView);


        btnStartAnimation.setOnClickListener(this);
        animationSurfaceView.setOnAnimationStausChangedListener(this);

        // 设置起始Y轴高度和终止X轴位移
        iAnimationStrategy = new ParabolaAnimationStrategy(animationSurfaceView, 1000, 800);
        animationSurfaceView.setStrategy(iAnimationStrategy);
        animationSurfaceView.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
    }

    public void onClick(View v) {
        if (v == btnStartAnimation) {
            if (animationSurfaceView.isShowAnimation() == false) {
                number++;
                enableRefresh = true;
                animationSurfaceView.startAnimation();
            }
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REFRESH_TEXTVIEW:

                if (txtNumber.getVisibility() != View.VISIBLE) {
                    txtNumber.setVisibility(View.VISIBLE);
                }
                RotateAnimation anim = new RotateAnimation(txtNumber.getWidth() >> 1, txtNumber.getHeight() >> 1,
                        RotateAnimation.ROTATE_INCREASE);
                anim.setInterpolatedTimeListener(this);
                txtNumber.startAnimation(anim);
                break;
        }
        return false;
    }

    @Override
    public void interpolatedTime(float interpolatedTime) {
        // 监听到翻转进度过半时，更新txtNumber显示内容。
        if (enableRefresh && interpolatedTime > 0.5f) {
            txtNumber.setText(Integer.toString(number));
            // Log.d("ANDROID_LAB", "setNumber:" + number);
            enableRefresh = false;
        }
    }

    @Override
    public void onAnimationStart(AnimationSurfaceView view) {

    }

    @Override
    public void onAnimationEnd(AnimationSurfaceView view) {
        handler.sendEmptyMessage(REFRESH_TEXTVIEW);
    }
}