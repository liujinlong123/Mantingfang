package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Setting extends Activity {
	
	private LinearLayout logout;
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_setting);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("设置");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		logout = (LinearLayout)findViewById(R.id.fourth_setting_logout);
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pf = getSharedPreferences("data", MODE_PRIVATE);
				userId = pf.getString("userId", "-1");
				if (userId != null && !userId.equals("")) {
					if (Integer.parseInt(userId) < 0) {
						logout.setClickable(false);
					} else {
						logout.setClickable(true);
						SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
						editor.putString("userId", "-1");
						editor.putString("headPath", "null");
						editor.putString("nickName", "");
						editor.commit();
						Intent intent = new Intent("com.android.mantingfang.fourth.MyBroadcast.LOG_OFF");
						sendBroadcast(intent);
						finish();
					}
				}
			}
		});
		
	}
}
