package com.bruce.android.knowledges.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bruce.android.knowledges.Activities.CanvasActivity;
import com.bruce.android.knowledges.Activities.InternetVideoActivity;
import com.bruce.android.knowledges.Activities.TestCircleMenuActivity;
import com.bruce.android.knowledges.Activities.TestClipActivity;
import com.bruce.android.knowledges.Activities.TestCosAnimationActivity;
import com.bruce.android.knowledges.Activities.TestCustomViewGroupActivity;
import com.bruce.android.knowledges.Activities.TestFlowLayoutActivity;
import com.bruce.android.knowledges.Activities.TestImageViewActivity;
import com.bruce.android.knowledges.Activities.TestLineViewActivity;
import com.bruce.android.knowledges.Activities.TestMultiImageViewActivity;
import com.bruce.android.knowledges.Activities.TestRotationTextViewActivity;
import com.bruce.android.knowledges.Activities.TestShaderActivity;
import com.bruce.android.knowledges.Activities.TestSingleTouchActivity;
import com.bruce.android.knowledges.Activities.TestSlideViewPagerActivity;
import com.bruce.android.knowledges.Activities.TestTransformMatrixActivity;
import com.bruce.android.knowledges.Activities.TextViewLinkActivity;
import com.bruce.android.knowledges.Activities.processTest.TestProcessActivity;
import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviewdemos.DemoPopupWindow;
import com.bruce.android.knowledges.costomviews.TweenAnimation.TestTweenAnimationActivity;
import com.bruce.android.knowledges.costomviews.pinnedHeaderListView.TestPinnedHeaderListviewActivity;
import com.bruce.android.knowledges.costomviews.scanAnimation.ParabolaAnimationActivity;
import com.bruce.android.knowledges.costomviews.scanAnimation.ScanAnimationActivity;
import com.bruce.android.knowledges.net.demo.TestHttpActivity;
import com.bruce.android.knowledges.services.ServiceTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qizhenghao on 17/3/2.
 */
public class FirstTabFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.process_test, R.id.http_demo, R.id.service_demo, R.id.main_open_test_ImageView_btn, R.id.main_open_test_pinned_btn, R.id.main_open_test_clip_btn, R.id.main_open_test_lineview_btn, R.id.main_open_test_VideoView_btn, R.id.main_open_test_muti_iv_btn, R.id.main_open_test_CircleMenu_btn, R.id.main_open_test_slideviewpager_btn, R.id.main_open_test_popupwindow_dialog_btn, R.id.main_open_test_tween_animation_btn, R.id.main_open_test_shader_btn, R.id.main_open_test_canvas_btn, R.id.main_open_test_custom_viewgroup_btn, R.id.main_open_test_flowlayout_btn, R.id.main_open_textviewlink_btn, R.id.main_open_rotation_text_btn, R.id.main_open_transform_matrix_btn, R.id.main_open_single_touch_btn, R.id.main_open_cos_animation_btn, R.id.main_open_scan_animation1_btn, R.id.main_open_scan_animation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.process_test:
                startActivity(new Intent(mContext, TestProcessActivity.class));
                break;
            case R.id.http_demo:
                startActivity(new Intent(mContext, TestHttpActivity.class));
                break;
            case R.id.service_demo:
                startActivity(new Intent(mContext, ServiceTestActivity.class));
                break;
            case R.id.main_open_test_ImageView_btn:
                startActivity(new Intent(mContext, TestImageViewActivity.class));
                break;
            case R.id.main_open_test_clip_btn:
                startActivity(new Intent(mContext, TestClipActivity.class));
                break;
            case R.id.main_open_test_pinned_btn:
                startActivity(new Intent(mContext, TestPinnedHeaderListviewActivity.class));
                break;
            case R.id.main_open_test_lineview_btn:
                startActivity(new Intent(mContext, TestLineViewActivity.class));
                break;
            case R.id.main_open_test_VideoView_btn:
                startActivity(new Intent(mContext, InternetVideoActivity.class));
                break;
            case R.id.main_open_test_muti_iv_btn:
                startActivity(new Intent(mContext, TestMultiImageViewActivity.class));
                break;
            case R.id.main_open_test_CircleMenu_btn:
                startActivity(new Intent(mContext, TestCircleMenuActivity.class));
                break;
            case R.id.main_open_test_slideviewpager_btn:
                startActivity(new Intent(mContext, TestSlideViewPagerActivity.class));
                break;
            case R.id.main_open_test_popupwindow_dialog_btn:
                DemoPopupWindow pw = new DemoPopupWindow(getActivity());
                pw.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.main_open_test_tween_animation_btn:
                startActivity(new Intent(mContext, TestTweenAnimationActivity.class));
                break;
            case R.id.main_open_test_shader_btn:
                startActivity(new Intent(mContext, TestShaderActivity.class));
                break;
            case R.id.main_open_test_canvas_btn:
                startActivity(new Intent(mContext, CanvasActivity.class));
                break;
            case R.id.main_open_test_custom_viewgroup_btn:
                startActivity(new Intent(mContext, TestCustomViewGroupActivity.class));
                break;
            case R.id.main_open_test_flowlayout_btn:
                startActivity(new Intent(mContext, TestFlowLayoutActivity.class));
                break;
            case R.id.main_open_textviewlink_btn:
                startActivity(new Intent(mContext, TextViewLinkActivity.class));
                break;
            case R.id.main_open_rotation_text_btn:
                startActivity(new Intent(mContext, TestRotationTextViewActivity.class));
                break;
            case R.id.main_open_transform_matrix_btn:
                startActivity(new Intent(mContext, TestTransformMatrixActivity.class));
                break;
            case R.id.main_open_single_touch_btn:
                startActivity(new Intent(mContext, TestSingleTouchActivity.class));
                break;
            case R.id.main_open_cos_animation_btn:
                startActivity(new Intent(mContext, TestCosAnimationActivity.class));
                break;
            case R.id.main_open_scan_animation1_btn:
                startActivity(new Intent(mContext, ParabolaAnimationActivity.class));
                break;
            case R.id.main_open_scan_animation_btn:
                startActivity(new Intent(mContext, ScanAnimationActivity.class));
                break;

            default:
                break;
        }
    }
}
