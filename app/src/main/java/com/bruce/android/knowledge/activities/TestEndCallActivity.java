package com.bruce.android.knowledge.activities;

import java.lang.reflect.Method;
import java.util.Objects;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telecom.TelecomManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.bruce.android.knowledge.R;

public class TestEndCallActivity extends Activity {

    private static final int REQUEST_CODE_END_CALL = 10001;
    private static final int REQUEST_CODE_ACCEPT_CALL = 10002;
    private static final int REQUEST_CODE_START_CALL = 10003;
    Button endCallBtn, acceptCallBtn, startCallBtn;
    TestEndCallActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_endcall_layout);
        mActivity = this;
        acceptCallBtn = findViewById(R.id.btn_phone0);
        endCallBtn = findViewById(R.id.btn_phone1);
        startCallBtn = findViewById(R.id.btn_phone2);

        startCallBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "没有拨号权限", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_START_CALL);
                        return;
                    }
                    // 开始直接拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10010"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(mActivity, "拨打电话！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        endCallBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "没有拨号权限", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_END_CALL);
                        return;
                    }
                    Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
                    IBinder binder = (IBinder) method.invoke(null, new Object[]{"phone"});
                    ITelephony telephony = ITelephony.Stub.asInterface(binder);
                    telephony.endCall();
                    Toast.makeText(mActivity, "挂断电话！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        acceptCallBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= 26) {
                        /*8.0以上增加了ANSWER_PHONE_CALLS权限*/
                        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mActivity, "没有 ANSWER_PHONE_CALLS 权限", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, REQUEST_CODE_ACCEPT_CALL);
                            return;
                        }
                        TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);
                        Method method = Class.forName("android.telecom.TelecomManager").getMethod("acceptRingingCall");
                        method.invoke(telecomManager);
                    } else if (Build.VERSION.SDK_INT >= 19) {
                        /* 模拟耳机插入动作,用于接听电话 */
                        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
                        KeyEvent eventDown = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
                        KeyEvent eventUp = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
                        Objects.requireNonNull(audioManager).dispatchMediaKeyEvent(eventDown);
                        audioManager.dispatchMediaKeyEvent(eventUp);
                    } else {
                        /* Android中通过Runtime.getRuntime().exec来执行底层Linux下的程序或脚本（bat）*/
                        /* adb shell input keyevent “value” 一般用于模拟物理事件, 例如, 调用input keyevent 3 则是模拟点击Home键 */
                        Runtime.getRuntime().exec("input keyevent " + Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                    }
                    Toast.makeText(mActivity, "接通电话！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_START_CALL) {
            startCallBtn.performClick();
        } else if (requestCode == REQUEST_CODE_ACCEPT_CALL) {
            acceptCallBtn.performClick();
        } else if (requestCode == REQUEST_CODE_END_CALL) {
            endCallBtn.performClick();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}