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

public class FourthTongzhi extends Activity {

	private CustomListView listview;
	private List<TongzhiContent> dataList;
	private ListAdapter adapter;
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_tongzhi);
		
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("通知");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		
		listview = (CustomListView)findViewById(R.id.fourth_tongzhi_list);
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
					dataList.add(new TongzhiContent("通知", "21:00"));
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
			// TODO Auto-generated method stub
			return dataList == null? 0 : dataList.size();
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
				convertView = getLayoutInflater().inflate(R.layout.fourth_tongzhi_item, null);
				
			}
			
			TextView tvContent = (TextView)convertView.findViewById(R.id.tongzhi_item_content);
			TextView tvTime = (TextView)convertView.findViewById(R.id.tongzhi_item_time);
			
			tvContent.setText(dataList.get(position).getContent());
			tvTime.setText(dataList.get(position).getTime());
			
			return convertView;
		}
		
	}
}

class TongzhiContent {
	private String content;
	private String time;
	
	public TongzhiContent(String content, String time) {
		this.content = content;
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTime() {
		return time;
	}
}

