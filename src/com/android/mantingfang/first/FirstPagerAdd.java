package com.android.mantingfang.first;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPagerAdd extends Activity {

	private ImageView imgBack;
	private TextView btnBeijing;
	private EditText editText;
	private EditText editWriter;
	private Button tvSave;
	private String userId;
	private String text;
	private String writer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_pager_add);
		
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		userId = pref.getString("userId", "-1");
		
		initViews();
	}
	
	private void initViews() {
		imgBack = (ImageView)findViewById(R.id.first_pager_img_addBack);
		btnBeijing = (TextView)findViewById(R.id.first_pager_img_beijing);
		editText = (EditText)findViewById(R.id.first_pager_text);
		editWriter = (EditText)findViewById(R.id.first_pager_writer);
		tvSave = (Button)findViewById(R.id.first_pager_save);
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnBeijing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FirstPagerAdd.this, "背景", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text = editText.getText().toString();
				writer = editWriter.getText().toString();
				if (text != null && !text.equals("") && Integer.parseInt(userId) >= 0) {
					saveData();
					finish();
				} else if (text == null || text.equals("")) {
					Toast.makeText(FirstPagerAdd.this, "内容不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void saveData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().Http_postSaveCard(userId, text, writer);
			}
			
			@Override
			protected void onPostExecute(String result) {
				
			}
			
		};
		
		task.execute();
	}
}
