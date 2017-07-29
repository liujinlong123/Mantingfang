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

public class ThirdThreePager extends Fragment {

	private View view;
	private CustomListView thirdThreeListView;
	private ThirdThreeAdapter adapterThree;
	private List<UserTwoContent> listThree;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_three, null);

			initViews();
			getData();

			return view;
		}

		return view;
	}

	// ��ʼ��ThirdThree
	private void initViews() {
		thirdThreeListView = (CustomListView)view.findViewById(R.id.third_pager_three_listview);
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne("3", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				listThree = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("")) {
						listThree = TopicList.parseThree(StringUtils.toJSONArray(result)).getTopicThree();
						adapterThree = new ThirdThreeAdapter(getActivity(), listThree);
						thirdThreeListView.setAdapter(adapterThree);
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
	/*private void getImage() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				for (int i = 0; i < listThree.size(); i++) {
					Map<String, String> param = new HashMap<>();
					param.put("path", listThree.get(i).getHeadPath());
					listThree.get(i).setHeadPhoto(ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param));
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				adapterThree = new ThirdThreeAdapter(getActivity(), listThree);
				thirdThreeListView.setAdapter(adapterThree);
			}
			
		};
		
		task.execute();
	}*/
}
