package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.third.PictureLoad;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GuanzhuR extends Fragment{

private View view;
private CustomListView listview;
private List<User> dataList;
private GuanzhuRAdapter adapter;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.guanzhu_right, null);
			
			listview = (CustomListView)view.findViewById(R.id.guanzhu_right_list);
			
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
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postGuanR(UserId.getInstance(getContext()).getUserId());
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					dataList = new ArrayList<>();
					try {
						JSONArray obj = new JSONArray(result);
						for (int i = 0; i < obj.length(); i++) {
							JSONObject jo = obj.getJSONObject(i);
							User user = new User(
									jo.getString("user_id"),
									jo.getString("user_nickname"),
									jo.getString("user_photo"),
									jo.optString("user_label"));
							dataList.add(user);
						}
						
						adapter = new GuanzhuRAdapter();
						listview.setAdapter(adapter);
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								UIHelper.showUserDetail(getContext(), 0, dataList.get(position - 1).getUserId(), dataList.get(position - 1).getHeadPath(), dataList.get(position - 1).getName());
							}
						});
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
	
	class User {
		String userId;
		String headPath;
		String name;
		String userLabel;
		
		public User(String userId, String name, String headPath, String userLabel) {
			this.userId = userId;
			this.headPath = headPath;
			this.name = name;
			this.userLabel = userLabel;
		}
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getHeadPath() {
			return headPath;
		}
		public void setHeadPath(String headPath) {
			this.headPath = headPath;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUserLabel() {
			return userLabel;
		}
		public void setUserLabel(String userLabel) {
			this.userLabel = userLabel;
		}
	}
	
	private class GuanzhuRAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
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
			ViewHolder holder;
			View view;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(R.layout.dianzanr_item, null);
				holder = new ViewHolder();
				holder.headPhoto = (CircleImageView)view.findViewById(R.id.dianr_headphoto);
				holder.userLabel = (TextView)view.findViewById(R.id.dianr_label);
				holder.userName = (TextView)view.findViewById(R.id.dianr_username);
				
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder)view.getTag();
			}
			
			User user = dataList.get(position);
			holder.userName.setText(user.getName());
			if (user.getUserLabel() != null && !user.getUserLabel().equals("")) {
				holder.userLabel.setText(user.getUserLabel());
			} else {
				holder.userLabel.setText("该用户还没介绍自己哟");
			}
			PictureLoad.getInstance().loadImage(user.getHeadPath(), holder.headPhoto);
			return view;
		}
		
		class ViewHolder {
			CircleImageView headPhoto;
			
			TextView userName;
			
			TextView userLabel;
		}
		
	}
	
}
