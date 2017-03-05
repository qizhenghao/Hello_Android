package com.bruce.android.knowledges.costomviews.pinnedHeaderListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce.android.knowledges.R;

public class TestSectionedAdapter extends SectionedBaseAdapter {

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    @Override
    public int getCountForSection(int section) {
        return 15;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.test_pinned_header_listview_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " Item " + position);
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.test_pinned_header_listview_header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Header for section " + section);
        return layout;
    }

}
