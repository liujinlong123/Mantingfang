package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentMain extends Activity {
	
	private LinearLayout linearBack;
	private TextView theme_bg;
	
	private ListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;

	private ListView listOne;
	private CommentAdapter adapterOne;
	private List<CommentContent> dataListOne;
	
	private ListView listTv;
	private CommentTextAdapter adapterTv;
	private List<String> dataListTv;

	private ListView listTwo;
	private CommentAdapter adapterTwo;
	private List<CommentContent> dataListTwo;

	//private EditText editer;
	//private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Bundle bundle = getIntent().getExtras();
		UserTwoContent content = (UserTwoContent) bundle.get("commentM");
		initViews(content, bundle.getString("topicId"), bundle.getString("typeNum"));
	}
	
	private void initViews(UserTwoContent content, String topicId, String typeNum) {
		listview = (ListView)findViewById(R.id.comment_main_content);
		listOne = (ListView)findViewById(R.id.comment_better);
		listTwo = (ListView)findViewById(R.id.comment_new);
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme_bg = (TextView)findViewById(R.id.topbar_tv_theme);
		
		list = new ArrayList<>();
		list.add(content);
		adapter = new UserTwoAdapter(CommentMain.this, list);
		listview.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listview);
		
		listTv = (ListView)findViewById(R.id.comment_tv_better);
		dataListTv = new ArrayList<>();
		dataListTv.add("精彩评论");
		adapterTv = new CommentTextAdapter(CommentMain.this, dataListTv);
		listTv.setAdapter(adapterTv);
		listTv.setEnabled(false);
		
		getData(topicId, typeNum);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		theme_bg.setText("帖子");
	}
	
	private void getData(final String topicId, final String typeNum) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postComment(typeNum, topicId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				dataListOne = new ArrayList<>();
				try {
					dataListOne = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
					dataListTwo = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
					adapter = new UserTwoAdapter(CommentMain.this, list);
					listview.setAdapter(adapter);
					setListViewHeightBasedOnChildren(listview);
					
					
					adapterOne = new CommentAdapter(CommentMain.this, dataListOne);
					listOne.setAdapter(adapterOne);
					setListViewHeightBasedOnChildren(listOne);
					
					adapterTwo = new CommentAdapter(CommentMain.this, dataListTwo);
					listTwo.setAdapter(adapterTwo);
					setListViewHeightBasedOnChildren(listTwo);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}

	/**
	 * ScrollViewǶ��listview
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	/**
	 * ����ͼƬǽ
	 * @param picture
	 */
	/*private void initGridView(ArrayList<String> picture) {
		userPics.setNumColumns(3);
		if (picture.size() == 0 || picture == null) {
			userPics.setVisibility(View.GONE);
		} else {
			
		}
	}*/
}
