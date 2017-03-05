package com.bruce.android.knowledges.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    public class LocalBinder extends Binder {
        String stringToSend = "I'm the test String";
        MyService getService() {
            Log.i("TAG", "getService ---> " + MyService.this);
            return MyService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.i("TAG", "onBind~~~~~~~~~~~~");
//		IBinder myIBinder = null;
//		if ( null == myIBinder )
//			myIBinder = new LocalBinder() ;
//		return myIBinder;
        return mBinder;		//也可以像上面几个语句那样重新new一个IBinder
        //如果这边不返回一个IBinder的接口实例，那么ServiceConnection中的onServiceConnected就不会被调用
        //那么bind所具有的传递数据的功能也就体现不出来~\(≧▽≦)/~啦啦啦（这个返回值是被作为onServiceConnected中的第二个参数的）
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        Log.i("TAG", "onCreate~~~~~~~~~~");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("TAG", "onDestroy~~~~~~~~~~~");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.i("TAG", "onStart~~~~~~");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.i("TAG", "onStartCommand~~~~~~~~~~~~");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Log.i("TAG", "onUnbind~~~~~~~~~~~~~~~~");
        return super.onUnbind(intent);
    }
}
