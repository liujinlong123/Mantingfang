package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
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
	
	private CircleImageView headImg;
	private ImageView imgBack;
	private TextView tvCollect;
	private TextView tvInfo;
	
	private String userId;
	private String headPath;
	private String nickName;
	
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
		
		Bundle bundle = getIntent().getExtras();
		userId = bundle.getString("userId");
		headPath = bundle.getString("headPath");
		nickName = bundle.getString("nickName");
		
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
				startActivity(intent);
			}
		});
		
		tvInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserPager.this, UserInfo.class);
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
						adapter = new UserTwoAdapter(UserPager.this, list, headPath);
						listview.setAdapter(adapter);
						listview.addHeaderView(viewHead);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
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
}
