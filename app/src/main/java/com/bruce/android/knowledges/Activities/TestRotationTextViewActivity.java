package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.DrawTextStudy;

import java.util.ArrayList;
import java.util.List;

public class TestRotationTextViewActivity extends Activity implements View.OnClickListener{

    private Context mContext = null;
    private ListView listView = null;
    TheAdapter adapter = null;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotation_textview);
//        setContentView(new DrawTextStudy(this));

        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    private void initData() {
        mContext = TestRotationTextViewActivity.this;
        List<String> dataList = new ArrayList<String>();
        dataList.add("哈哈");
        dataList.add("是不是很");
        dataList.add("我是无哈");
        dataList.add("哈哈");
        dataList.add("啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
        adapter = new TheAdapter(this, dataList);
        listView.setAdapter(adapter);
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.roatation_textview_lv);
    }

    @Override
    public void onClick(View v) {

    }

    class TheAdapter extends BaseAdapter{
        private List<String> dataList = null;
        private Context context = null;
        public TheAdapter(Context context, List<String> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.rotation_textview_list_item, null);
                holder.textview = (TextView) convertView.findViewById(R.id.rotation_tv_list_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textview.setText(dataList.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView textview;
        }
    }


}
