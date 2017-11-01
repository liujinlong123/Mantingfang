package com.android.mantingfang.topic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.third.PictureLoad;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TopicGameF extends Activity {

	private ImageView TopImage;
	private View TopView;
	private CircleImageView imgHead;
	private TextView tvUserName;
	private TextView tvVictoryRate;
	private TextView tvGameNumber;
	private TextView allRank;
	private CustomListView listview;
	private Button btnStart;
	private LinearLayout linearBack;
	private TextView topText;
	private Button imgInfo;
	
	private TopicGameFAdapter adapter;
	private List<TopicGameFContent> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_game);
		
		initViews();
	}
	
	@SuppressLint("InflateParams")
	private void initViews() {
		TopView = LayoutInflater.from(TopicGameF.this).inflate(R.layout.topic_theme, null);
		TopImage = (ImageView)TopView.findViewById(R.id.topic_theme_topImg);
		imgHead = (CircleImageView)TopView.findViewById(R.id.topic_theme_userPhoto);
		tvUserName = (TextView)TopView.findViewById(R.id.topic_theme_name);
		tvVictoryRate = (TextView)TopView.findViewById(R.id.topic_victory_rate);
		tvGameNumber = (TextView)TopView.findViewById(R.id.topic_game_number);
		allRank = (TextView)TopView.findViewById(R.id.all_rank_f);
		listview = (CustomListView)findViewById(R.id.topic_game_rankList);
		btnStart = (Button)findViewById(R.id.start_gameF);
		linearBack = (LinearLayout)findViewById(R.id.topic_gameF_back);
		topText = (TextView)findViewById(R.id.topic_gameF_tv_theme);
		imgInfo = (Button)findViewById(R.id.topic_gameF_info);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		topText.setText("飞花令");
		
		imgInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(TopicGameF.this, "游戏详情", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		Intent intent = getIntent();
		String picPath = intent.getStringExtra("picturepath");
		PictureLoad.getInstance().loadImage(picPath, TopImage);
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(TopicGameF.this, "开始游戏", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(TopicGameF.this, ChatF.class);
				
				startActivity(intent);
			}
		});
		
		getData(UserId.getInstance(TopicGameF.this).getUserId());
	}
	
	private void getData(final String userId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(TopicGameF.this,
						"正在加载", SuccinctProgress.THEME_LINE, false, true);
			}
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postTopicGameFTheme(userId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				Log.v("TESTTopic", result + "---" + userId);
				if (result != null && !result.equals("")) {
					String[] tokens = result.split("[#]");
					if (tokens[0] != null && !tokens[0].equals("")) {
						try {
							JSONArray obj = new JSONArray(tokens[0]);
							//JSONObject jo = new JSONObject(result);
							JSONObject jo = obj.getJSONObject(0);
							TopicGameFContent content = new TopicGameFContent(
									userId,
									jo.optString("user_nickname"),
									jo.optString("user_photo"),
									jo.optString("hororific_appellation"),
									jo.optString("total_filed"),
									null);
							content.setSingleRank(jo.optString("ranking"));
							
							PictureLoad.getInstance().loadImage(content.getHeadPath(), imgHead);
							tvUserName.setText(content.getUserName());
							if (content.getVictoryRate().equals("") || content.getVictoryRate() == null) {
								tvVictoryRate.setText("迷梦");
							} else {
								tvVictoryRate.setText(content.getVictoryRate());
							}
							if (content.getGameNumber().equals("") || content.getGameNumber() == null) {
								tvGameNumber.setText(0 + "");
							} else {
								tvGameNumber.setText(content.getGameNumber());
							}
							
							allRank.setText(content.getSingleRank());
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						//数据为空的处理tokens[0]
					}
					
					if (tokens[1] != null && !tokens[1].equals("")) {
						try {
							JSONArray objT = new JSONArray(tokens[1]);
							dataList = new ArrayList<>();
							for (int i = 0; i < objT.length(); i++) {
								JSONObject joo = (JSONObject) objT.get(i);
								TopicGameFContent content = new TopicGameFContent(
										joo.getString("user_id"),
										joo.optString("user_nickname"),
										joo.optString("user_photo"),
										joo.optString("integral"),
										"",
										joo.optString("care"));
								dataList.add(content);
							}
							adapter = new TopicGameFAdapter(TopicGameF.this, dataList, listview);
							listview.setAdapter(adapter);
							listview.addHeaderView(TopView);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						//数据为空的处理tokens[1]
					}
					
				} else {
					//数据为空的处理result
				}
			}
			
		};
		
		task.execute();
	}
	
	/*private void getDataTwo (final String tokens) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				
			}
			
		};
		
		task.execute();
	}*/
}
