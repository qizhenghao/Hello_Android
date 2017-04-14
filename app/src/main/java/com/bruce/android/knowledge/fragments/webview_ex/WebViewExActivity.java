package com.bruce.android.knowledge.fragments.webview_ex;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.bruce.android.knowledge.R;

import butterknife.OnClick;


public class WebViewExActivity extends Activity implements View.OnClickListener{

    WebView mWebView;
    String mUrl = "http://bitkiller.duapp.com/jsobj.html";
    String mUrl2 = "file:///android_asset/html/test.html";
    String mUrl3 = "file:///android_asset/html/js3.html";
    Object mJsObj = new JSInterface();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview_ex_layout);
        
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSInterface(), "jsInterface");
    }

    @OnClick({R.id.action_load, R.id.action_refresh})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_load:
                mWebView.loadUrl(mUrl2);
                break;

            case R.id.action_refresh:
                mWebView.reload();
                break;
        }
    }

    class JSInterface {
        @JavascriptInterface
        public String onButtonClick(String text) {
            final String str = text;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("leehong2", "onButtonClick: text = " + str);
                    Toast.makeText(getApplicationContext(), "onButtonClick: text = " + str, Toast.LENGTH_LONG).show();
                }
            });
            
            return "This text is returned from Java layer.  js text = " + text;
        }
        
        @JavascriptInterface
        public void onImageClick(String url, int width, int height) {
            final String str = "onImageClick: text = " + url + "  width = " + width + "  height = " + height;
            Log.i("leehong2", str);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
