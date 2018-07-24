package com.bruce.android.knowledge.fragments.test;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.fragments.BaseFragment;

import butterknife.BindView;

/**
 * Created by qizhenghao on 17/11/22.
 */

public class FilePathFragment extends BaseFragment {

    @BindView(R.id.test_file_path_tv)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.test_file_path_fragment_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                mActivity.getFilesDir().getAbsolutePath()).append("\n")
                .append(mActivity.getCacheDir().getAbsolutePath()).append("\n")
                .append(mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()).append("\n")
                .append(mActivity.getObbDir().getAbsolutePath()).append("\n")

                .append(Environment.getExternalStorageDirectory().getAbsolutePath()).append("\n")
                .append(Environment.getDownloadCacheDirectory().getAbsolutePath()).append("\n")
                .append(Environment.getRootDirectory().getAbsolutePath()).append("\n")
                .append(Environment.getDataDirectory().getAbsolutePath()).append("\n")
                .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath())
                .append("\n");

        textView.setText(stringBuilder.toString());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {

    }
}
