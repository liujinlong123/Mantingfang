package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.third.UserTwoContent;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;

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
	private DianzanAdapter adapter;
	private List<UserTwoContent> dataList;
	
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.dianzan_left, null);
			
			listview = (CustomListView)view.findViewById(R.id.dianzan_left_list);
			getData();
			
			return view;
		}
		
		return view;
	}
	
	/**
	 * 异步获取信息
	 */
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(getContext(),
						"加载中", SuccinctProgress.THEME_LINE, false, true);
			}
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postDianL(UserId.getInstance(getContext()).getUserId());
			}
			
			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				if (result != null && !result.equals("")) {
					dataList = new ArrayList<>();
					try {
						dataList = TopicList.parseDianzanL(StringUtils.toJSONArray(result)).getDianzanLList();
						adapter = new DianzanAdapter(getContext(), dataList);
						listview.setAdapter(adapter);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					//Toast.makeText(getContext(), "返回数据失败", Toast.LENGTH_SHORT).show();
					//finish();
				}
			}
			
		};
		
		task.execute();
	}
}
