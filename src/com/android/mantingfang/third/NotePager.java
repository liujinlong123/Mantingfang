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

public class NotePager extends Fragment{
	private View view;
	private CustomListView thirdTwoListView;
	private NoteAdapter adapterTwo;
	private List<UserTwoContent> listTwo;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_two, null);
			
			thirdTwoListView = (CustomListView)view.findViewById(R.id.third_pager_two_listview);
			getData();
			
			return view;
		}
		
		return view;
	}
	
	public void addOne(UserTwoContent item) {
		if (adapterTwo == null) {
			listTwo = new ArrayList<UserTwoContent>();
			listTwo.add(item);
			adapterTwo = new NoteAdapter(getContext(), listTwo, thirdTwoListView);
			thirdTwoListView.setAdapter(adapterTwo);
		} else {
			listTwo.add(0, item);
			adapterTwo.notifyDataSetChanged();
		}
		
	}
	
	public void refresh(int postId) {
		listTwo.get(0).setPost_com_pId(postId);
		adapterTwo.notifyDataSetChanged();
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne(UserId.getInstance(getActivity()).getUserId(), "2", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				listTwo = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("") && !result.equals("]")) {
						listTwo = TopicList.parseTwo(StringUtils.toJSONArray(result)).getTopicTwo();
						adapterTwo = new NoteAdapter(getActivity(), listTwo, thirdTwoListView);
						thirdTwoListView.setAdapter(adapterTwo);
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
				
				for (int i = 0; i < listTwo.size(); i++) {
					Map<String, String> param = new HashMap<>();
					param.put("path", listTwo.get(i).getHeadPath());
					listTwo.get(i).setHeadPhoto(ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param));
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				adapterTwo = new ThirdTwoAdapter(getActivity(), listTwo);
				thirdTwoListView.setAdapter(adapterTwo);
			}
			
		};
		
		task.execute();
	}*/
}
