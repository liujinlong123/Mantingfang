package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.third.UserTwoAdapter;
import com.android.mantingfang.third.UserTwoContent;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DianzanL extends Fragment {

	private View view;
	private CustomListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> dataList;
	
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.dianzan_left, null);
			
			listview = (CustomListView)view.findViewById(R.id.dianzan_left_list);
			dataList = new ArrayList<>();
			getData();
			
			return view;
		}
		
		return view;
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
					dataList.add(new UserTwoContent());
				}
				
				listview.setAdapter(adapter);
			}
		};
		// 执行任务
		task.execute();
	}
}
