package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LogOn extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView register;
	
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
				Toast.makeText(LogOn.this, "fff", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
