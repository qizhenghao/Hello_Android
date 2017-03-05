package com.bruce.android.knowledges.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bruce.android.knowledges.R;

public class ServiceTestActivity extends Activity {
    private Button startButton, bindButton;
    private Button stopButton, unbindButton; 
    private ServiceConnection sc;
    private MediaPlayer mediaPlayer = null;
    private MyService myService;// 类似于MediaPlayer mPlayer = new 
                                // MediaPlayer();只不过这边的服务是自定义的，不是系统提供好了的 
 
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.service_main);
 
        startButton = (Button) findViewById(R.id.startbutton_id); 
        stopButton = (Button) findViewById(R.id.stopbutton_id); 
        bindButton = (Button) findViewById(R.id.bindbutton_id); 
        unbindButton = (Button) findViewById(R.id.unbindbutton_id); 
 
        sc = new ServiceConnection() { 
            /*
             * 只有在MyService中的onBind方法中返回一个IBinder实例才会在Bind的时候
             * 调用onServiceConnection回调方法
             * 第二个参数service就是MyService中onBind方法return的那个IBinder实例，可以利用这个来传递数据
             */ 
            @Override 
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub 
                myService = ((MyService.LocalBinder) service).getService(); 
                String recStr = ((MyService.LocalBinder) service).stringToSend; 
                //利用IBinder对象传递过来的字符串数据（其他数据也可以啦，哪怕是一个对象也OK~~） 
                Log.i("TAG","The String is : " + recStr);
                Log.i("TAG", "onServiceConnected : myService ---> " + myService); 
            } 
 
            @Override 
            public void onServiceDisconnected(ComponentName name) {
                /* SDK上是这么说的：
                 * This is called when the connection with the service has been unexpectedly disconnected
                 * that is, its process crashed. Because it is running in our same process, we should never see this happen.
                 * 所以说，只有在service因异常而断开连接的时候，这个方法才会用到*/ 
                // TODO Auto-generated method stub 
                sc = null; 
                Log.i("TAG", "onServiceDisconnected : ServiceConnection --->" 
                        + sc); 
            } 
 
        }; 
        startButton.setOnClickListener(new View.OnClickListener() {
 
            @Override 
            public void onClick(View v) {
                // TODO Auto-generated method stub 
                Intent intent = new Intent(ServiceTestActivity.this,
                        MyService.class); 
                startService(intent); 
                Log.i("TAG", "Start button clicked"); 
            } 
        }); 
 
        stopButton.setOnClickListener(new View.OnClickListener() {
 
            @Override 
            public void onClick(View v) {
                // TODO Auto-generated method stub 
 
                /*
                 * Intent intent = new
                 * Intent(LocalServiceTestActivity.this,MyService.class);
                 * stopService(intent); 这种方法也是可以的哈~
                 */ 
 
                Intent intent = new Intent(); 
                intent.setAction("com.test.SERVICE_TEST"); 
                stopService(intent); 
                Log.i("TAG", "Stop Button clicked"); 
            } 
        }); 
 
        bindButton.setOnClickListener(new View.OnClickListener() {
 
            @Override 
            public void onClick(View v) {
                // TODO Auto-generated method stub 
//              Intent intent = new Intent(LocalServiceTestActivity.this, 
//                      MyService.class);//这样也可以的 
                Intent intent = new Intent(); 
                intent.setAction("com.test.SERVICE_TEST"); 
                bindService(intent, sc, Context.BIND_AUTO_CREATE);//bind多次也只会调用一次onBind方法
                Log.i("TAG", "Bind button clicked"); 
            } 
        }); 
 
        unbindButton.setOnClickListener(new View.OnClickListener() {
 
            @Override 
            public void onClick(View v) {
                // TODO Auto-generated method stub 
                unbindService(sc); 
                // 这边如果重复unBind会报错，提示该服务没有注册的错误——IllegalArgumentException: 
                // Service not registered: null 
                // 所以一般会设置一个flag去看这个service 
                // bind后有没有被unBind过，没有unBind过才能调用unBind方法(这边我就不设置了哈~\(≧▽≦)/~啦啦啦) 
                Log.i("TAG", "Unbind Button clicked"); 
            } 
        }); 
    } 
}