package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUser extends Activity {
	
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	
	private EditText nickname;
	private EditText password;
	private EditText passwordSure;
	private TextView finishR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("注册");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("上一步");
		
		nickname = (EditText)findViewById(R.id.user_register_name);
		password = (EditText)findViewById(R.id.user_register_password);
		passwordSure = (EditText)findViewById(R.id.user_register_sure_password);
		finishR = (TextView)findViewById(R.id.login_finish);
		
		finishR.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (nickname.getText().toString() != null && !nickname.getText().toString().equals("")) {
					if (password.getText().toString() != null && !password.getText().toString().equals("")) {
						if (passwordSure.getText().toString() != null && !passwordSure.getText().toString().equals("")) {
							if (password.getText().toString().equals(passwordSure.getText().toString())) {
								String userId = getIntent().getStringExtra("phoneNum");
								saveUser(userId, nickname.getText().toString(), password.getText().toString());
							} else {
								Toast.makeText(RegisterUser.this, "密码不一致", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(RegisterUser.this, "请确认密码", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(RegisterUser.this, "请输入密码", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(RegisterUser.this, "起个名字吧", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void saveUser(final String phoneNum, final String nickname, final String password) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().Http_postSendUserName(phoneNum, nickname, password);
			}

			@Override
			protected void onPostExecute(String result) {
				// Log.v("TESTREG", result + "---");

				if (result != null && !result.equals("")) {
					SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
					editor.putString("userId", result);
					editor.commit();
					RegisterAll.getInstance().destroyAll();
					finish();
				}
			}

		};

		task.execute();
	}
}
