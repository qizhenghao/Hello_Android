package com.bruce.android.knowledge.activities.wifiTest;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.base.KnowApplication;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestDialogActivity extends Activity {

    Button btnConnect;
    WifiManager wifiManager;
    WifiConnector wac;
    TextView textView1;
    EditText editPwd;
    EditText editSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_dialog_activity_layout);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        textView1 = (TextView) findViewById(R.id.txtMessage);
        wifiManager = (WifiManager) KnowApplication.context.getSystemService(Context.WIFI_SERVICE);
        wac = new WifiConnector(wifiManager);

        editPwd = (EditText) findViewById(R.id.editPwd);
        editSSID = (EditText) findViewById(R.id.editSSID);

        wac.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 操作界面
                textView1.setText(textView1.getText() + "\n" + msg.obj + "");
                super.handleMessage(msg);
            }
        };
        btnConnect.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (KnowApplication.sWaitingSpecifiedWifiConnected)
                    return;
                WifiConnectExecutor mWiFiExecutor = new WifiConnectExecutor(v.getContext(), new WifiConnectExecutor.Listener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText("连接成功");
                                Toast.makeText(v.getContext(), "连接成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText("连接失败");
                                Toast.makeText(v.getContext(), "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                mWiFiExecutor.connect(editSSID.getText().toString(), editPwd.getText().toString());
//                try {
//                    wac.connect(editSSID.getText().toString(), editPwd.getText().toString(),
//                            editPwd.getText().toString().equals("") ? WifiConnector.WifiCipherType.WIFICIPHER_NOPASS : WifiConnector.WifiCipherType.WIFICIPHER_WPA);
//                } catch (Exception e) {
//                    textView1.setText(e.getMessage());
//                }

            }
        });
    }
}