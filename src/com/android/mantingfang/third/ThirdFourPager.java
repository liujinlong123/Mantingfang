package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.ImageLoad;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdFourPager extends Fragment {
	private View view;
	private CustomListView thirdFourListView;
	private ThirdFourAdapter adapterFour;
	private List<UserTwoContent> listFour;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_four, null);

			initViews();
			getData();
			return view;
		}

		return view;
	}

	// ��ʼ��ThirdFour
	private void initViews() {
		thirdFourListView = (CustomListView)view.findViewById(R.id.third_pager_four_listview);
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne("4", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				listFour = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("")) {
						listFour = TopicList.parseFour(StringUtils.toJSONArray(result)).getTopicFour();
						getImage();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	/**
	 * 获取图片
	 */
	private void getImage() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				for (int i = 0; i < listFour.size(); i++) {
					Map<String, String> param = new HashMap<>();
					param.put("path", listFour.get(i).getHeadPath());
					listFour.get(i).setHeadPhoto(ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param));
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				adapterFour = new ThirdFourAdapter(getActivity(), listFour);
				thirdFourListView.setAdapter(adapterFour);
			}
			
		};
		
		task.execute();
	}
}
