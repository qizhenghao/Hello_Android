package com.bruce.android.knowledge.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.activities.TestCardActivity;

import butterknife.BindView;

/**
 * Created by qizhenghao on 17/3/2.
 */
public class SecondTabFragment extends BaseFragment {

    @BindView(R.id.btns_layout)
    LinearLayout layout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second_tab;
    }

    @Override
    protected void initView() {


        addEvent("银行卡号识别", TestCardActivity.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }

    private void addEvent(String text, View.OnClickListener onClickListener) {
        getButton(text).setOnClickListener(onClickListener);
    }

    private void addEvent(String text, final Class cls) {
        Button button = getButton(text);
        button.setOnClickListener(v -> startActivity(new Intent(getActivity(), cls)));
    }

    @NonNull
    private Button getButton(String text) {
        Button button = new Button(getActivity());
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        layout.addView(button);
        return button;
    }
}
