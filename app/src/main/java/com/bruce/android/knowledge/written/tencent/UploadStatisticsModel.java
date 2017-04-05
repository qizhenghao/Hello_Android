package com.bruce.android.knowledge.written.tencent;

import android.os.Handler;
import android.os.Looper;

public class UploadStatisticsModel implements Runnable{
    private static Object lock = new Object();//锁
    private Handler handler = null;
    private static UploadStatisticsModel instance = null;

    private UploadStatisticsModel() {
        new Thread(this).start();
    }

    private static UploadStatisticsModel getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null)
                    instance = new UploadStatisticsModel();
            }
        }
        return instance;
    }

    public void upLoad(StatisticsBean bean) {
        upLoad(bean, 0);
    }

    /**
     * 上传信息
     * @param bean
     * @param delayed
     */
    public void upLoad(StatisticsBean bean, long delayed) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //将上传任务加到HTTP任务队列
            }
        }, delayed);
    }

    @Override
    public void run() {
        Looper.prepare();
        if (handler == null) {
            handler = new Handler(Looper.myLooper());
        }
        Looper.loop();
    }

    public class StatisticsBean {


        public int channel;//频道

        public String title;//标题

        public long id;//文章ID

        public int typeEvent;//事件类型

        public StatisticsBean(int channel, String title, long id, int typeEvent) {
            this.channel = channel;
            this.title = title;
            this.id = id;
            this.typeEvent = typeEvent;
        }

    }
}
