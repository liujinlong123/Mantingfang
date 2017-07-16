package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdTwoPager extends Fragment{
	private View view;
	private CustomListView thirdTwoListView;
	private ThirdTwoAdapter adapterTwo;
	private List<UserTwoContent> listTwo;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_two, null);
			
			initViews();
			getData();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		thirdTwoListView = (CustomListView)view.findViewById(R.id.third_pager_two_listview);
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne("2", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				listTwo = new ArrayList<UserTwoContent>();
				try {
					listTwo = TopicList.parseTwo(StringUtils.toJSONArray(result)).getTopicTwo();
					adapterTwo = new ThirdTwoAdapter(getActivity(), listTwo);
					thirdTwoListView.setAdapter(adapterTwo);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
}
