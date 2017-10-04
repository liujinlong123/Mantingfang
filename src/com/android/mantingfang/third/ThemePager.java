package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ThemePager extends Activity {
	private CustomListView thirdOneListView;
	private ThemeAdapter adapterOne;
	private List<UserTwoContent> listOne;
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_pager_one);

		thirdOneListView = (CustomListView)findViewById(R.id.third_pager_one_listview);
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("话题");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("");
		
		int type = getIntent().getIntExtra("type", -1);
		if (type == 1) {
			String searchKey = getIntent().getStringExtra("searchKey");
			sendRequestWithHttpClient(searchKey, "search_topic_accordingkeyword.php");
		} else if (type == -1) {
			String searchKey = getIntent().getStringExtra("searchKey");
			sendRequestWithHttpClient(searchKey, "search_topic.php");
		}
		
	}
	
	public void addOne(UserTwoContent item) {
		if (adapterOne == null) {
			listOne = new ArrayList<UserTwoContent>();
			listOne.add(item);
			adapterOne = new ThemeAdapter(ThemePager.this, listOne, thirdOneListView);
			thirdOneListView.setAdapter(adapterOne);
		} else {
			listOne.add(0, item);
			adapterOne.notifyDataSetChanged();
		}
	}
	
	public void refresh(int postId) {
		listOne.get(0).setPost_com_pId(postId);
		adapterOne.notifyDataSetChanged();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("tables");
				 Log.v("reponse--str-----", str);
				if (str != null && !str.equals("")) {
					//Log.v("reponse--str-----", str);
				}
				try {
					if (str != null && !str.equals("") && !str.equals("[") && !str.equals("]") && !str.equals("无返回值")) {
						listOne = (List<UserTwoContent>) (TopicList.parseOne(StringUtils.toJSONArray(str.substring(str.indexOf("["))), 1)
								.getTopicList());

						adapterOne = new ThemeAdapter(ThemePager.this, listOne, thirdOneListView);
						thirdOneListView.setAdapter(adapterOne);
						thirdOneListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

							}
						});
					} else {
						Toast.makeText(ThemePager.this, "换个词搜搜吧", Toast.LENGTH_SHORT).show();
					}

					//Log.v("ListOne", listOne.size() + "----");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private void sendRequestWithHttpClient(final String searchKey, final String address) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = new Message();
				try {
					Log.v("Address", address);
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(MyClient.actionUrl + address);
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("TypeNum", "1"));
					param.add(new BasicNameValuePair("number", "0"));
					param.add(new BasicNameValuePair("user_id", UserId.getInstance(ThemePager.this).getUserId()));
					param.add(new BasicNameValuePair("keyword", searchKey));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
					httpPost.setEntity(entity);
					HttpResponse httpResponse = httpClient.execute(httpPost);

					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity httpEntity = httpResponse.getEntity();
						
						String response = EntityUtils.toString(httpEntity, "utf-8");
						//Log.v("TESTONE", response);
						msg.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("tables", response);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}
	
	/**
	 * 获取图片
	 * @param path
	 */
	/*private void getImage() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				for (int i = 0; i < listOne.size(); i++) {
					Map<String, String> param = new HashMap<>();
					param.put("path", listOne.get(i).getHeadPath());
					listOne.get(i).setHeadPhoto(ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param));
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				adapterOne = new ThirdOneAdapter(getActivity(), listOne);
				thirdOneListView.setAdapter(adapterOne);
			}
			
		};
		
		task.execute();
	}*/
}
