package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FourthMy extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView save;
	
	//昵称 	签名 	性别 	生日 	所在地 	个人介绍
	private TextView nickName;
	private TextView label;
	private TextView sex;
	private TextView birth;
	private TextView area;
	private TextView intro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_my);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("主页");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		save = (TextView)findViewById(R.id.topbar_all_saveOn);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
