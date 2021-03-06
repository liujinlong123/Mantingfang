package com.android.mantingfang.fourth;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogOn extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView register;
	private TextView logIn;
	
	private EditText userName;
	private EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("登录");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("返回");
		register = (TextView)findViewById(R.id.topbar_all_saveOn);
		register.setText("注册");
		register.setVisibility(View.VISIBLE);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RegisterAll.getInstance().addActivity(LogOn.this);
				Intent intent = new Intent(LogOn.this, Phone.class);
				startActivity(intent);
			}
		});
		userName = (EditText)findViewById(R.id.users_phoneNum);
		password = (EditText)findViewById(R.id.users_password);
		logIn = (TextView)findViewById(R.id.login);
		logIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (userName.getText().toString() != null && !userName.getText().toString().equals("")
						&& password.getText().toString() != null && !password.getText().toString().equals("")) {
					getData();
				} else {
					if (userName.getText().toString() != null && !userName.getText().toString().equals("")) {
						Toast.makeText(LogOn.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
					} else if (password.getText().toString() != null && !password.getText().toString().equals("")) {
						Toast.makeText(LogOn.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	/**
	 * 登录
	 */
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().Http_postUser(userName.getText().toString(), password.getText().toString());
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					
					Log.v("userId", result);
					try {
						JSONObject jo = new JSONObject(result);
						String userId = jo.getString("user_id");
						String headPath = jo.getString("user_photo");
						String nickName = jo.getString("user_nickname");
						if (Integer.parseInt(userId) < 0) {
							Toast.makeText(LogOn.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
						} else {
							SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
							editor.putString("userId", userId);
							editor.putString("headPath", headPath);
							editor.putString("nickName", nickName);
							editor.commit();
							Intent intent = new Intent();
							intent.putExtra("userId", userId);
							setResult(RESULT_OK, intent);
							finish();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(LogOn.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
				}
			}
			
		};
		
		task.execute();
	}
}
