package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Comment extends Activity {

	private LinearLayout linearBack;
	private TextView theme_bg;
	
	private ListView listTv;
	private CommentTextAdapter adapterTv;
	private List<String> dataListTv;
	
	private ListView listOne;
	private CommentAdapter adapterOne;
	private List<CommentContent> dataListOne;

	private ListView listTwo;
	private CommentAdapter adapterTwo;
	private List<CommentContent> dataListTwo;

	private EditText editer;
	private Button btnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		listOne = (ListView)findViewById(R.id.comment_better);
		dataListOne = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			dataListOne.add(new CommentContent());
		}
		adapterOne = new CommentAdapter(this, dataListOne);
		listOne.setAdapter(adapterOne);
		setListViewHeightBasedOnChildren(listOne);
		
		listTv = (ListView)findViewById(R.id.comment_tv_better);
		dataListTv = new ArrayList<>();
		dataListTv.add("精彩评论");
		adapterTv = new CommentTextAdapter(Comment.this, dataListTv);
		listTv.setAdapter(adapterTv);
		listTv.setEnabled(false);
		
		listTwo = (ListView)findViewById(R.id.comment_new);
		dataListTwo = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			dataListTwo.add(new CommentContent());
		}
		adapterTwo = new CommentAdapter(this, dataListTwo);
		listTwo.setAdapter(adapterTwo);
		setListViewHeightBasedOnChildren(listTwo);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme_bg = (TextView)findViewById(R.id.topbar_tv_theme);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		theme_bg.setText("帖子");
		
		
	}
	
	/**
	 * ScrollView嵌套listview
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}
