package com.android.mantingfang.third;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfo extends Activity {

	private LinearLayout linearBack;
	private TextView tvNickName;
	private TextView tvSex;
	private TextView tvAge;
	private TextView tvBirth;
	private TextView tvArea;
	private TextView tvLabel;
	private TextView tvIntro;
	
	private String userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_three_frag);
		
		linearBack = (LinearLayout)findViewById(R.id.user_info_back);
		tvNickName = (TextView)findViewById(R.id.user_info_nickname);
		tvSex = (TextView)findViewById(R.id.user_info_sex);
		tvAge = (TextView)findViewById(R.id.user_info_age);
		tvBirth = (TextView)findViewById(R.id.user_info_birth);
		tvArea = (TextView)findViewById(R.id.user_info_area);
		tvLabel = (TextView)findViewById(R.id.user_info_label);
		tvIntro = (TextView)findViewById(R.id.user_info_intro);
		
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		userId = getIntent().getStringExtra("userId");
		getData(userId);
	}
	
	
	private void getData(final String userId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().Http_postUserInfo(userId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				//Log.v("user--info", result);
				if (result != null && !result.equals("")) {
					String str = result.substring(result.indexOf("[") + 1, result.lastIndexOf("]"));
					try {
						JSONObject jo = new JSONObject(str);
						String nickName = jo.optString("user_nickname");
						String sex = jo.optString("user_sex");
						String birth = jo.optString("user_age");
						String area = jo.optString("user_area");
						String label = jo.optString("user_label");
						String intro = jo.optString("user_introduce");
						
						tvNickName.setText(nickName);
						tvSex.setText(sex);
						tvBirth.setText(birth);
						tvArea.setText(area);
						tvLabel.setText(label);
						tvIntro.setText(intro);
						
						if (birth != null && !birth.equals("")) {
							String a = birth.substring(0, birth.indexOf("-"));
							Calendar d=Calendar.getInstance();
							int year = d.get(Calendar.YEAR);
							tvAge.setText((year - Integer.parseInt(a)) + "");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
		
		task.execute();
	}
}
