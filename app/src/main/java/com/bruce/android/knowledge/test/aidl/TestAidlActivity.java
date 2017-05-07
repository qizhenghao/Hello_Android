package com.bruce.android.knowledge.test.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by qizhenghao on 17/5/7.
 */
public class TestAidlActivity extends Activity {

    BookManager mBookManager = null;
    private boolean mBound;
    private List<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout contentView = new LinearLayout(this);
        setContentView(contentView);
        Button button1 = new Button(this);
        Button button2 = new Button(this);
        contentView.addView(button1);
        contentView.addView(button2);

        button1.setText("addBook");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果与服务端的连接处于未连接状态，则尝试连接
                if (!mBound) {
                    attemptToBindService();
                    Toast.makeText(TestAidlActivity.this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBookManager == null) return;

                Book book = new Book();
                book.setName("APP研发录In");
                book.setPrice(30);
                try {
                    mBookManager.addBook(book);
                    Log.d("Bruce", book.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setText("getBooks");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果与服务端的连接处于未连接状态，则尝试连接
                if (!mBound) {
                    attemptToBindService();
                    Toast.makeText(TestAidlActivity.this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBookManager == null) return;

                try {
                    mBooks = mBookManager.getBooks();
                    Log.d("Bruce", mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.bruce.android.knowledge.TEST_AIDL");
        intent.setPackage("com.bruce.android.knowledge.test.aidl");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
//        startService(new Intent(TestAidlActivity.this, TestAidlService.class));
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Bruce", "service connected");
            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.d("Bruce", mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Bruce", "service disconnected");
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
