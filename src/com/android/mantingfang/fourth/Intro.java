package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Intro extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView save;
	private EditText editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("个人介绍");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("返回");
		save = (TextView)findViewById(R.id.topbar_all_saveOn);
		editor = (EditText)findViewById(R.id.intro);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("intro", editor.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
