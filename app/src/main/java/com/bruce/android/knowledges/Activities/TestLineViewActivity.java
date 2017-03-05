package com.bruce.android.knowledges.Activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.LineView;

public class TestLineViewActivity extends Activity {

    private Animator animator;
    private Animator animator1;
    private LineView lineView;
    private LineView lineView1;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_view_activity);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimator();
            }
        });
        lineView = (LineView) findViewById(R.id.lineView);
        lineView1 = (LineView) findViewById(R.id.lineView1);
        ViewTreeObserver observer = lineView.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                width = lineView.getMeasuredWidth();
                Log.d("Bruce", "width:" + width);
                return true;
            }
        });
    }

    private void startAnimator(){
        animator = ObjectAnimator.ofInt(lineView, "layoutWidth", width, 0);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator1 = ObjectAnimator.ofInt(lineView1, "layoutWidthAndHeight", width, 0);
        animator1.setDuration(3000);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.start();
    }
}