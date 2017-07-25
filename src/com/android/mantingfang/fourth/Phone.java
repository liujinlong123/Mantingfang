package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Phone extends Activity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private EditText editor;
	private TextView tvNext;
	private boolean pagerTwo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone);
		
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
		backTitle.setText("返回");
		editor = (EditText)findViewById(R.id.phoneNum);
		tvNext = (TextView)findViewById(R.id.btn_getNum);
	}
}
