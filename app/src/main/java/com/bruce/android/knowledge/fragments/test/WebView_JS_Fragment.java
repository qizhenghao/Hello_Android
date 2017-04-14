package com.bruce.android.knowledge.fragments.test;

import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.fragments.BaseFragment;

import butterknife.BindView;

/**
 * Created by qizhenghao on 17/4/13.
 */
public class WebView_JS_Fragment extends BaseFragment {

    @BindView(R.id.native2H5_btn)
    public Button mButton;
    @BindView(R.id.fragment_webview_js_wb)
    public WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview_js_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mWebView.loadUrl("file:///android_asset/html/webview2js.html");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.addJavascriptInterface(this, "toast");
    }

    @android.webkit.JavascriptInterface
    public void showToast(String s) {
        Toast.makeText(getActivity(), Html.fromHtml(s), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initListener() {
        mButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("javascript:actionFromNative(" + "1" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + "'来自Native的内容'" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + "mWebView" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + mWebView + ")");
            }
        });
    }

    @Override
    public void refresh() {

    }

}
