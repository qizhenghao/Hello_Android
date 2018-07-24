package com.bruce.android.knowledge.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ScrollView;

import com.bruce.android.knowledge.activities.CanvasActivity;
import com.bruce.android.knowledge.activities.InternetVideoActivity;
import com.bruce.android.knowledge.activities.TestCircleMenuActivity;
import com.bruce.android.knowledge.activities.TestClipActivity;
import com.bruce.android.knowledge.activities.TestCoordinatorActivity;
import com.bruce.android.knowledge.activities.TestCosAnimationActivity;
import com.bruce.android.knowledge.activities.TestCustomViewGroupActivity;
import com.bruce.android.knowledge.activities.TestFlowLayoutActivity;
import com.bruce.android.knowledge.activities.TestImageViewActivity;
import com.bruce.android.knowledge.activities.TestLineViewActivity;
import com.bruce.android.knowledge.activities.TestMultiImageViewActivity;
import com.bruce.android.knowledge.activities.TestRotationTextViewActivity;
import com.bruce.android.knowledge.activities.TestShaderActivity;
import com.bruce.android.knowledge.activities.TestSingleTouchActivity;
import com.bruce.android.knowledge.activities.TestSlideViewPagerActivity;
import com.bruce.android.knowledge.activities.TestTransformMatrixActivity;
import com.bruce.android.knowledge.activities.TextViewLinkActivity;
import com.bruce.android.knowledge.activities.processTest.TestProcessActivity;
import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.activities.wifiTest.TestDialogActivity;
import com.bruce.android.knowledge.animation.property.PropertyAnimationUtils;
import com.bruce.android.knowledge.costomviewdemos.DemoPopupWindow;
import com.bruce.android.knowledge.custom_view.tween_animation.TestTweenAnimationActivity;
import com.bruce.android.knowledge.custom_view.pinnedHeaderListView.TestPinnedHeaderListViewActivity;
import com.bruce.android.knowledge.custom_view.scanAnimation.ParabolaAnimationActivity;
import com.bruce.android.knowledge.custom_view.scanAnimation.ScanAnimationActivity;
import com.bruce.android.knowledge.fragments.test.FilePathFragment;
import com.bruce.android.knowledge.fragments.test.WebView_JS_Fragment;
import com.bruce.android.knowledge.net.demo.TestHttpActivity;
import com.bruce.android.knowledge.receivers.MediaButtonReceiver;
import com.bruce.android.knowledge.services.ServiceTestActivity;
import com.bruce.android.knowledge.test.aidl.TestAidlActivity;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qizhenghao on 17/3/2.
 */
public class FirstTabFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.scrollview)
    public ScrollView scrollView;

    AudioManager mAudioManager;
    ComponentName mComponent;

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void initView() {
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected void initData() {

        mAudioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        //构造一个ComponentName，指向MediaoButtonReceiver类
        mComponent = new ComponentName(mActivity.getPackageName(), MediaButtonReceiver.class.getName());

//注册一个MediaButtonReceiver广播监听
        mAudioManager.registerMediaButtonEventReceiver(mComponent);

        Log.d("bruce", defaultUserAgent());
    }

    private String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : "Dalvik/2.1.0 (Linux; U; Android 5.0; SM-G9006V Build/LRX21T)";
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioManager.unregisterMediaButtonEventReceiver(mComponent);
    }

    @OnClick({R.id.test_dialog_activity, R.id.test_file_path, R.id.test_CoordinatorLayout, R.id.test_aidl, R.id.webView_js, R.id.process_test, R.id.http_demo, R.id.service_demo, R.id.main_open_test_ImageView_btn, R.id.main_open_test_pinned_btn, R.id.main_open_test_clip_btn, R.id.main_open_test_lineview_btn, R.id.main_open_test_VideoView_btn, R.id.main_open_test_muti_iv_btn, R.id.main_open_test_CircleMenu_btn, R.id.main_open_test_slideviewpager_btn, R.id.main_open_test_popupwindow_dialog_btn, R.id.main_open_test_tween_animation_btn, R.id.main_open_test_shader_btn, R.id.main_open_test_canvas_btn, R.id.main_open_test_custom_viewgroup_btn, R.id.main_open_test_flowlayout_btn, R.id.main_open_textviewlink_btn, R.id.main_open_rotation_text_btn, R.id.main_open_transform_matrix_btn, R.id.main_open_single_touch_btn, R.id.main_open_cos_animation_btn, R.id.main_open_scan_animation1_btn, R.id.main_open_scan_animation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_dialog_activity:
                startActivity(new Intent(getContext(), TestDialogActivity.class));
                break;
            case R.id.test_file_path:
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.add(R.id.first_tab_inner_fragment, new FilePathFragment());
                transaction1.commit();
                break;
            case R.id.test_CoordinatorLayout:
                startActivity(new Intent(mContext, TestCoordinatorActivity.class));
                break;
            case R.id.test_aidl:
                startActivity(new Intent(mContext, TestAidlActivity.class));
                break;
            case R.id.webView_js:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.first_tab_inner_fragment, new WebView_JS_Fragment());
//                transaction.addToBackStack("");
                ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
                reentrantReadWriteLock.writeLock().lock();
                transaction.commit();
//                PropertyAnimationUtils.startAnimator(view);
//                PropertyAnimationUtils.startAnimator1(view);
                PropertyAnimationUtils.startAnimator2(view);
                break;
            case R.id.process_test:
                view.startAnimation(AnimationUtils.loadAnimation(
                        mContext, R.anim.vc_0_0_1_newsfeed_loading_rotate));
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
                startActivity(new Intent(mContext, TestPinnedHeaderListViewActivity.class));
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
