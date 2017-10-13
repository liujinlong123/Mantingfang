package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserPager extends Activity {
	
	private View viewHead;
	
	private CustomListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;
	private TextView tvName;
	
	private CircleImageView headImg;
	private ImageView imgBack;
	private TextView tvCollect;
	private TextView tvInfo;
	private ImageView imgCare;
	
	private String userId;
	private String headPath;
	private String nickName;
	private String care;
	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_pager);
		
		viewHead = LayoutInflater.from(UserPager.this).inflate(R.layout.user_head, null);
		initViews();
	}
	
	private void initViews() {
		listview = (CustomListView)findViewById(R.id.user_pager_listview);
		headImg = (CircleImageView)viewHead.findViewById(R.id.user_pager_headphoto);
		imgBack = (ImageView)viewHead.findViewById(R.id.user_pager_img_back);
		tvCollect = (TextView)viewHead.findViewById(R.id.user_pager_collect);
		tvInfo = (TextView)viewHead.findViewById(R.id.user_pager_info);
		tvName = (TextView)viewHead.findViewById(R.id.user_pager_user_name);
		imgCare = (ImageView)viewHead.findViewById(R.id.user_pager_care);
		
		Bundle bundle = getIntent().getExtras();
		userId = bundle.getString("userId");
		headPath = bundle.getString("headPath");
		nickName = bundle.getString("nickName");
		tvName.setText(nickName);
		
		PictureLoad.getInstance().loadImage(headPath, headImg);
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		tvCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserPager.this, UserCollect.class);
				intent.putExtra("userId", userId);
				startActivity(intent);
			}
		});
		
		tvInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserPager.this, UserInfo.class);
				intent.putExtra("userId", userId);
				startActivity(intent);
			}
		});
		getData(userId, nickName);
	}
	
	private void getData(final String user_id, final String nickName) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(UserPager.this,
						"加载中", SuccinctProgress.THEME_LINE, false, true);
			}
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postUserTwo(user_id);
			}
			
			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				if (result != null && !result.equals("")) {
					list = new ArrayList<>();
					try {
						list = TopicList.parseUser(StringUtils.toJSONArray(result),user_id, headPath, nickName).getUserList();
						//PictureLoad.getInstance().loadImage(headPath, imageView);
						adapter = new UserTwoAdapter(UserPager.this, list, headPath, true);
						listview.setAdapter(adapter);
						listview.addHeaderView(viewHead);
						getCare(userId);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(UserPager.this, "返回数据失败", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
			
		};
		
		task.execute();
	}
	
	private void getCare (final String user_id) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().Http_getCare(UserId.getInstance(UserPager.this).getUserId(), user_id);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					care = result;
				} else {
					care = "0";
				}
				
				if (care.equals("1")) {
					imgCare.setImageResource(R.drawable.care_on);
				} else {
					imgCare.setImageResource(R.drawable.care_off);
				}
				
				imgCare.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (Integer.parseInt(UserId.getInstance(UserPager.this).getUserId()) < 0) {
							Intent intent = new Intent(UserPager.this, LogOn.class);
							startActivity(intent);
						} else {
							if (care.equals("0")) {		//没关注-->关注
								imgCare.setImageResource(R.drawable.care_on);
								sendCare(userId, "1");
							} else if (care.equals("1")) { //关注-->没关注
								imgCare.setImageResource(R.drawable.care_off);
								sendCare(userId, "0");
							}
						}
					}
				});
			}
			
		};
		
		task.execute();
	}
	
	private void sendCare(final String userId, final String careT) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_sendCare(UserId.getInstance(UserPager.this).getUserId(), userId, careT);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (careT.equals("0")) {
					care = "0";
				} else if (careT.equals("1")) {
					care = "1";
				}
			}
			
		};
		
		task.execute();
	}
	
}
