package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfanggsc.ImageLoad;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.graphics.Bitmap;
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
	private Bitmap bitmap;
	private String headPath;
	private UserTwoContent content;

	//private EditText editer;
	//private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Bundle bundle = getIntent().getExtras();
		content = (UserTwoContent) bundle.get("commentM");
		headPath = bundle.getString("headPath");
		getImage(headPath, bundle);
	}
	
	private void initViews(UserTwoContent content, String topicId, String typeNum) {
		listview = (ListView)findViewById(R.id.comment_main_content);
		listOne = (ListView)findViewById(R.id.comment_better);
		listTwo = (ListView)findViewById(R.id.comment_new);
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme_bg = (TextView)findViewById(R.id.topbar_tv_theme);
		
		list = new ArrayList<>();
		list.add(content);
		adapter = new UserTwoAdapter(CommentMain.this, list, bitmap);
		listview.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listview);
		
		listTv = (ListView)findViewById(R.id.comment_tv_better);
		dataListTv = new ArrayList<>();
		dataListTv.add("精彩评论");
		adapterTv = new CommentTextAdapter(CommentMain.this, dataListTv);
		listTv.setAdapter(adapterTv);
		listTv.setEnabled(false);
		
		getData(topicId, typeNum, bitmap);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		theme_bg.setText("帖子");
	}
	
	private void getData(final String topicId, final String typeNum, final Bitmap bitmap) {
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
					adapter = new UserTwoAdapter(CommentMain.this, list, bitmap);
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
	 * 获取图片
	 * @param path
	 */
	private void getImage(final String path, final Bundle bundle) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				Map<String, String> param = new HashMap<>();
				param.put("path", path);
				bitmap = ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param);
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				initViews(content, bundle.getString("topicId"), bundle.getString("typeNum"));
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
}
