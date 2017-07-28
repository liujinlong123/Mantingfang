package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FourthTuijian extends Activity {

	private CustomListView listview;
	private List<TuijianContent> dataList;
	private ListAdapter adapter;
	
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_tuijian);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("推荐");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		
		listview = (CustomListView)findViewById(R.id.fourth_tuijian_list);
		adapter = new ListAdapter();
		dataList = new ArrayList<>();
		
		getData();
	}
	
	/**
	 * 异步获取信息
	 */
	private void getData() {

		//final String url = "http://zhaobiao.zhaohuake.com/androidTender?page=1&pageSize=5";

		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			@Override
			protected String doInBackground(String... pp) {
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				for (int i = 1; i < 10; i++) {
					dataList.add(new TuijianContent());
				}
				
				listview.setAdapter(adapter);
			}
		};
		// 执行任务
		task.execute();
	}
	
	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList == null? 0: dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_listview_wenku_poem, null);
			}
			
			TextView tvName = (TextView)convertView.findViewById(R.id.poemlist_tv_name);
			TextView tvRhesis = (TextView)convertView.findViewById(R.id.poemlist_tv_poemrhesis);
			TextView tvWriter = (TextView)convertView.findViewById(R.id.poemlist_tv_writername);
			
			/*tvName.setText(dataList.get(position).getPoemName());
			tvRhesis.setText(dataList.get(position).getPoemRhesis());
			tvWriter.setText("[" + dataList.get(position).getDynasty() + "]" + dataList.get(position).getWriter());*/
			
			return convertView;
		}
		
	}
}

class TuijianContent {
	private String poemId;
	private String poemName;
	private String poemRhesis;
	private String dynasty;
	private String writer;
	
	public TuijianContent() {}
	
	public TuijianContent(String poemId, String poemName, String poemRhesis, String dynasty, String writer) {
		this.poemId = poemId;
		this.poemName = poemName;
		this.poemRhesis = poemRhesis;
		this.dynasty = dynasty;
		this.writer = writer;
	}
	
	public String getPoemId() {
		return poemId;
	}
	
	public String getPoemName() {
		return poemName;
	}
	
	public String getPoemRhesis() {
		return poemRhesis;
	}
	
	public String getDynasty() {
		return dynasty;
	}
	
	public String getWriter() {
		return writer;
	}
}