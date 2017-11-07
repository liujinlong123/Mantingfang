package com.android.mantingfang.topic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.topic.DropdownListView.OnRefreshListenerHeader;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ChatF extends Activity implements OnRefreshListenerHeader {

	private EditText input;
	private Button send;
	private DropdownListView mListView;
	private ChatLVAdapter mLvAdapter;
	private LinkedList<ChatInfo> infos = new LinkedList<ChatInfo>();
	private SimpleDateFormat sd;
	private String reply = "";// 模拟回复
	
	private ImageView imgBg;
	private LinearLayout linearBack;
	private String prePoetryId = "-1"; //上一条的poetry_id
	private int countPoem = 0;	//诗词对决条目
	private String gradeCount = "1"; //积分

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_gamef);
		
		imgBg = (ImageView)findViewById(R.id.img_chat_gamef_bg);
		linearBack = (LinearLayout)findViewById(R.id.topic_gameF_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//积分更改 排名更改
				finish();
			}
		});
		getPicture(UserId.getInstance(this).getUserId(), "月");
	}

	@SuppressLint("SimpleDateFormat")
	private void initViews(String myHead, String robotHead) {
		mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
		sd = new SimpleDateFormat("MM-dd HH:mm");
		// 模拟收到信息
		infos.add(getChatInfoFrom("你好啊！"));
		infos.add(getChatInfoFrom("欢迎来到飞花令"));
		mLvAdapter = new ChatLVAdapter(this, infos, myHead, robotHead);
		mListView.setAdapter(mLvAdapter);

		// 输入框
		input = (EditText) findViewById(R.id.input_sms);
		// input.setOnClickListener(this);
		send = (Button) findViewById(R.id.send_sms);
		// 发送
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reply = input.getText().toString();
				if (!TextUtils.isEmpty(reply)) {
					infos.add(getChatInfoTo(reply));
					mLvAdapter.setList(infos);
					mLvAdapter.notifyDataSetChanged();
					mListView.setSelection(infos.size() - 1);
					input.setText("");
					//countPoem++;
					if (countPoem < 20) {
						gradeCount = "1";
					} else if (countPoem < 40) {
						gradeCount = "2";
					} else if (countPoem < 60) {
						gradeCount = "3";
					} else if (countPoem < 80) {
						gradeCount = "4";
					} else if (countPoem < 100) {
						gradeCount = "5";
					} else if (countPoem < 120) {
						gradeCount = "6";
					} else {
						gradeCount = "7";
					}
					sendData(UserId.getInstance(ChatF.this).getUserId(), prePoetryId, reply, "月");
				}
			}
		});

		mListView.setOnRefreshListenerHead(this);
	}

	/**
	 * 接收的信息
	 * 
	 * @param message
	 * @return
	 */
	private ChatInfo getChatInfoFrom(String message) {
		ChatInfo info = new ChatInfo();
		info.content = message;
		info.fromOrTo = 0;
		info.time = sd.format(new Date());
		return info;
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mLvAdapter.setList(infos);
				mLvAdapter.notifyDataSetChanged();
				mListView.onRefreshCompleteHeader();
				break;
			}
		}
	};

	/**
	 * 发送的信息
	 * 
	 * @param message
	 * @return
	 */
	private ChatInfo getChatInfoTo(String message) {
		ChatInfo info = new ChatInfo();
		info.content = message;
		info.fromOrTo = 1;
		info.time = sd.format(new Date());
		return info;
	}

	@Override
	public void onRefresh() {
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
					Message msg = mHandler.obtainMessage(0);
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void sendData(final String userId, final String poetryId, final String msg, final String topic) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				//发送msg
				return MyClient.getInstance().http_postSendFei(userId, poetryId, msg, topic, gradeCount);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("user's printing is error")) {
					//对result处理
					try {
						JSONObject jo = new JSONObject(result);
						prePoetryId = jo.getString("poetry_id");
						String content = jo.getString("poetry_sentence") + "\n《" + jo.optString("poetry_name")
								+ "》——[" + jo.optString("poetry_dynasty") + "]" + jo.optString("poetry_writer");
						infos.add(getChatInfoFrom(content));
						mLvAdapter.setList(infos);
						mLvAdapter.notifyDataSetChanged();
						mListView.setSelection(infos.size() - 1);
						
						//有效
						countPoem++;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//没有接收到诗句或者答的不对或者词库不包含这首诗
					String content = "你打错了哦";
					infos.add(getChatInfoFrom(content));
					mLvAdapter.setList(infos);
					mLvAdapter.notifyDataSetChanged();
					mListView.setSelection(infos.size() - 1);
					
					//EditText不可编辑状态
					input.setFocusable(false);
					input.setFocusableInTouchMode(false);
				}
			}

		};

		task.execute();
	}
	
	/**
	 * 设置背景图片
	 * @param userId
	 */
	private void getPicture(final String userId, final String key) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				//修改
				return MyClient.getInstance().http_postTopicGameFPics(userId, key);
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("TEST", result + "---");
				if (result != null && !result.equals("")) {
					String[] tokens = result.split("[#]");
					//PictureLoad.getInstance().loadImage(tokens[0], imgBg);
					Log.v("TEST", tokens[0] + "---" + tokens[2] + "---" + tokens[1]);
					imgBg.setImageResource(R.drawable.wel);
					initViews(tokens[1], tokens[2]);
				}
			}

		};

		task.execute();
	}
	
	private void addCount(final String userId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				//修改
				return MyClient.getInstance().http_postSendFeiCount (userId);
			}

			@Override
			protected void onPostExecute(String result) {
				
			}

		};

		task.execute();
	}
	
	@Override
	protected void onDestroy() {
		if (countPoem >=2 ) {
			addCount(UserId.getInstance(ChatF.this).getUserId());
		}
		super.onDestroy();
	}
}
