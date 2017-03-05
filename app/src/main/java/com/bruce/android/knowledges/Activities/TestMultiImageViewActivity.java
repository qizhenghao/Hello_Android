package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.os.Bundle;

import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.NewsfeedShareMultiImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizhenghao on 16/2/26.
 */
public class TestMultiImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_multi_imageview_activity);

        NewsfeedShareMultiImageView multiImageView = (NewsfeedShareMultiImageView) findViewById(R.id.test_multi_iv);
        List<String> urls = new ArrayList<String>();
        urls.add("1");
        urls.add("2");
        urls.add("3");
        urls.add("4");
        multiImageView.setImages(urls, null, 4);
    }
}
