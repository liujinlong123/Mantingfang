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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UserTwo extends Fragment {

	//private MyViewPager vp;
	private View view;
	
	/*public UserTwo(MyViewPager vp) {
		this.vp = vp;
	}*/
	
	
	private CustomListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;
	private String userId;
	private String nickName;
	private String headPath;
	private ProgressDialog myDialog = null;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_two_frag, null);
			
			//vp.setObjectForPosition(view, 1);
			
			initViews();
			userId = (String)getArguments().get("userId");
			nickName = (String)getArguments().get("nickName");
			headPath = (String)getArguments().get("headPath");
			Log.v("UserTwo", headPath);
			getData(userId, nickName);
			return view;
		}
		
		
		//vp.setObjectForPosition(view, 1);
		return view;
	}
	
	private void initViews() {
		listview = (CustomListView)view.findViewById(R.id.user_two_listview);
	}
	
	private void getData(final String user_id, final String nickName) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				final CharSequence strDialogBody = "正在加载";

				myDialog = ProgressDialog.show(getActivity(), null, strDialogBody, true);
			}
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postUserTwo(user_id);
			}
			
			@Override
			protected void onPostExecute(String result) {
				myDialog.dismiss();
				list = new ArrayList<>();
				try {
					list = TopicList.parseUser(StringUtils.toJSONArray(result),user_id, headPath, nickName).getUserList();
					//PictureLoad.getInstance().loadImage(headPath, imageView);
					adapter = new UserTwoAdapter(getActivity(), list, headPath);
					listview.setAdapter(adapter);
					setListViewHeightBasedOnChildren(listview);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	/**
	 * 获取头像
	 * @param path
	 */
	/*private void getImage(final String path) {
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
				adapter = new UserTwoAdapter(getActivity(), list, headPath);
				listview.setAdapter(adapter);
				setListViewHeightBasedOnChildren(listview);
			}
			
		};
		
		task.execute();
	}*/
	
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
