package com.bruce.android.knowledges.Activities;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bruce.android.knowledges.R;
import com.bruce.android.knowledges.costomviews.CircleMenuLayout;

/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class TestCircleMenuActivity extends Activity
{

	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "1", "2", "3",
			"4", "5", "6" , "7 ", "8", "9",
			"10", "11"};
	private int[] mItemImgs = new int[] {R.drawable.menu_1,
			R.drawable.menu_2, R.drawable.menu_3,
			R.drawable.menu_4, R.drawable.menu_5,
			R.drawable.menu_6, R.drawable.menu_7,
			R.drawable.menu_8, R.drawable.menu_9,
			R.drawable.menu_10, R.drawable.menu_11,
			R.drawable.menu_12};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//自已切换布局文件看效果
		setContentView(R.layout.test_circlemenu_activity_layout);
//		setContentView(R.layout.activity_main);
		View popLayout = LayoutInflater.from(TestCircleMenuActivity.this).inflate(R.layout.circle_menu_pupwindow_layout, null);
		final PopupWindow mPopupWindow = new PopupWindow(popLayout, 200 ,200);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable());
		mPopupWindow.setAnimationStyle(R.style.Animations_PopUpMenu_Right);
		final ImageView likeIV = (ImageView) findViewById(R.id.like_iv);
//		mPopupWindow.showAsDropDown(likeIV, -200, -200);
		likeIV.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				mPopupWindow.showAsDropDown(v, -68, -200);
				return false;
			}
		});
		likeIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		mCircleMenuLayout = (CircleMenuLayout) popLayout.findViewById(R.id.id_menulayout);
		popLayout.findViewById(R.id.circle_menu_like_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		popLayout.findViewById(R.id.circle_menu_default_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				Toast.makeText(TestCircleMenuActivity.this, "默认赞", Toast.LENGTH_SHORT).show();
			}
		});
		popLayout.findViewById(R.id.circle_menu_enter_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				Toast.makeText(TestCircleMenuActivity.this, "进入商城", Toast.LENGTH_SHORT).show();
			}
		});
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
				Toast.makeText(TestCircleMenuActivity.this, "设置为赞 " + mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
			
			public void itemCenterClick(View view)
			{
				Toast.makeText(TestCircleMenuActivity.this,
						"you can do something just like ccb  ",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

}
