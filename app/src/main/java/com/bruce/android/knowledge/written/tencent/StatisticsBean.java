package com.bruce.android.knowledge.written.tencent;

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
