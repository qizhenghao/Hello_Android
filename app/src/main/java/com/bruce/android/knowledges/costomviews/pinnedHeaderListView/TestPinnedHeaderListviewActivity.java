package com.bruce.android.knowledges.costomviews.pinnedHeaderListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce.android.knowledges.R;

public class TestPinnedHeaderListviewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_pinned_header_listview_activity_layout);
        PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout header1 = (LinearLayout) inflator.inflate(R.layout.test_pinned_header_listview_item, null);
        ((TextView) header1.findViewById(R.id.textItem)).setText("HEADER 1");
        LinearLayout header2 = (LinearLayout) inflator.inflate(R.layout.test_pinned_header_listview_item, null);
        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");
        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.test_pinned_header_listview_item, null);
        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");
        listView.addHeaderView(header1);
        listView.addHeaderView(header2);
        listView.addFooterView(footer);
        TestSectionedAdapter sectionedAdapter = new TestSectionedAdapter();
        listView.setAdapter(sectionedAdapter);
    }
}
