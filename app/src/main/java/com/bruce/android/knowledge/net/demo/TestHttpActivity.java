package com.bruce.android.knowledge.net.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.android.knowledge.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestHttpActivity extends Activity {
    String resultStr = "";
    @BindView(R.id.textview_show)
    TextView textView;
    @BindView(R.id.imagview_show)
    ImageView imageView;
    @BindView(R.id.parent_view)
    RelativeLayout parentView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_http_layout);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_visit_web, R.id.btn_download_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_visit_web:
                visitBaiduWithHttp();
                break;
            case R.id.btn_download_img:
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                showProgressBar();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488825603874&di=782fce94a94cb20784c02cc03fc3f066&imgtype=0&src=http%3A%2F%2Fnpic7.edushi.com%2Fcn%2Fzixun%2Fzh-chs%2F2016-09%2F19%2F3375132-160919112516.jpg";
                        URL url;
                        Bitmap bitmap = null;
                        try {
                            url = new URL(imgUrl);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        final Bitmap finalBitmap = bitmap;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgressBar();
                                imageView.setImageBitmap(finalBitmap);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    private void visitBaiduWithHttp() {
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null; //连接对象
                InputStream is = null;
                try {
                    URL url = new URL("https://www.baidu.com/"); //URL对象
                    conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
                    conn.setDoInput(true); //允许输入流，即允许下载
                    conn.setDoOutput(true); //允许输出流，即允许上传
                    conn.setUseCaches(false); //不使用缓冲
                    conn.setRequestMethod("GET"); //使用get请求
                    is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader bufferReader = new BufferedReader(isr);
                    String inputLine;
                    while ((inputLine = bufferReader.readLine()) != null) {
                        resultStr += inputLine + "\n";
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(Html.fromHtml(resultStr));
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 在母布局中间显示进度条
     */
    private void showProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setVisibility(View.VISIBLE);
        parentView = (RelativeLayout) findViewById(R.id.parent_view);
        parentView.addView(progressBar, params);
    }

    /**
     * 隐藏进度条
     */
    private void dismissProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            parentView.removeView(progressBar);
            progressBar = null;
        }
    }

}
