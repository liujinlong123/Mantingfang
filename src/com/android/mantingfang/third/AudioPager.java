package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.UserId;
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

public class AudioPager extends Fragment {
	private View view;
	private CustomListView thirdFourListView;
	private AudioAdapter adapterFour;
	private List<UserTwoContent> listFour;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_four, null);
			thirdFourListView = (CustomListView)view.findViewById(R.id.third_pager_four_listview);
			
			getData();
			return view;
		}

		return view;
	}

	public void addOne(UserTwoContent item) {
		if (adapterFour == null) {
			listFour = new ArrayList<UserTwoContent>();
			listFour.add(item);
			adapterFour = new AudioAdapter(getContext(), listFour, thirdFourListView);
			thirdFourListView.setAdapter(adapterFour);
		} else {
			listFour.add(0, item);
			adapterFour.notifyDataSetChanged();
		}
	}
	
	public void refresh(int postId) {
		listFour.get(0).setPost_com_pId(postId);
		adapterFour.notifyDataSetChanged();
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne(UserId.getInstance(getActivity()).getUserId(), "4", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				listFour = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("") && !result.equals("]")) {
						listFour = TopicList.parseFour(StringUtils.toJSONArray(result)).getTopicFour();
						adapterFour = new AudioAdapter(getActivity(), listFour, thirdFourListView);
						thirdFourListView.setAdapter(adapterFour);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	public void setPlayerPause() {
		adapterFour.setPlayerPause();
	}
	
	@Override
	public void onDestroy() {
		if (adapterFour != null) {
			if (adapterFour.isPlayer()) {
				adapterFour.clearPlayer();
			}
		}
		super.onDestroy();
	}
}
