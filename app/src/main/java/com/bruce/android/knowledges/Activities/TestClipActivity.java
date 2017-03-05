package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by qizhenghao on 16/5/5.
 */
public class TestClipActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        ClipboardManager cm =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!TextUtils.isEmpty(cm.getText())) {
            Toast.makeText(context, cm.getText(), Toast.LENGTH_SHORT).show();
        }
//将文本数据复制到剪贴板
        cm.setText("齐小政齐小政齐小政齐小政齐小政， https://www.baidu.com");
    }

}
