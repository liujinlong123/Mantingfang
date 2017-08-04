package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Phone extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;

	private EditText editorPhone;
	private EditText editorVer;
	private TextView getVerCode;
	private TextView next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone);

		linearBack = (LinearLayout) findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		title = (TextView) findViewById(R.id.topbar_tv_theme);
		title.setText("注册");
		backTitle = (TextView) findViewById(R.id.topbar_tv_back);
		backTitle.setText("登录");

		editorPhone = (EditText) findViewById(R.id.phoneNum);
		editorVer = (EditText) findViewById(R.id.verCode);
		getVerCode = (TextView) findViewById(R.id.btn_getNum);
		next = (TextView) findViewById(R.id.register_next);

		getVerCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editorPhone.getText().toString() != null) {
					if (editorPhone.getText().toString().length() == 11) {
						getVerCode(editorPhone.getText().toString());
					} else {
						Toast.makeText(Phone.this, "您输入的手机格式不正确", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(Phone.this, "请输入手机号", Toast.LENGTH_SHORT).show();
				}
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editorPhone.getText().toString() != null) {
					if (editorPhone.getText().toString().length() == 11) {
						if (editorVer.getText().toString() != null) {
							if (editorVer.getText().toString().length() == 6) {
								sendVerCode(editorPhone.getText().toString(), editorVer.getText().toString());
								Log.v("TESTPhone", editorPhone.getText().toString() + "---" + editorVer.getText().toString());
							} else {
								Toast.makeText(Phone.this, "请输入六位验证码", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(Phone.this, "请输入六位验证码", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(Phone.this, "您输入的手机格式不正确", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(Phone.this, "请输入手机号", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void getVerCode(final String phoneNum) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().Http_postPhone(phoneNum);
			}

			@Override
			protected void onPostExecute(String result) {
				// Log.v("TESTREG", result + "---");
			}

		};

		task.execute();
	}

	private void sendVerCode(final String phoneNum, final String verCode) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().Http_postPhoneVerCode(phoneNum, verCode);
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("TESTREG", result + "---");
				if (result != null && !result.equals("") && result.equals("validate succeed")) {
					RegisterAll.getInstance().addActivity(Phone.this);
					Intent intent = new Intent(Phone.this, RegisterUser.class);
					intent.putExtra("phoneNum", phoneNum);
					startActivity(intent);
				} else {
					Toast.makeText(Phone.this, "注册失败", Toast.LENGTH_SHORT).show();
				}
			}

		};

		task.execute();
	}
}
