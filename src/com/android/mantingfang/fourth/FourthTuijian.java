package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FourthTuijian extends Activity {
	
	private TextView tvAdd;
	private ImageView imgFinish;
	private EditText editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_tuijian);
		
		imgFinish = (ImageView) findViewById(R.id.fankui_img_finish);
		tvAdd = (TextView) findViewById(R.id.fankui_tv_add);
		editor = (EditText)findViewById(R.id.editor_yijian);
		
		imgFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		tvAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!editor.getText().toString().equals("")) {
					sendData();
				} else {
					Toast.makeText(FourthTuijian.this, "写上你的意见哟", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * 异步获取信息
	 */
	private void sendData() {

		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			@Override
			protected String doInBackground(String... pp) {
				return MyClient.getInstance().http_postFanK(UserId.getInstance(FourthTuijian.this).getUserId(), editor.getText().toString());
			}

			@Override
			protected void onPostExecute(String result) {
				Toast.makeText(FourthTuijian.this, "谢谢你的反馈，我们将继续努力", Toast.LENGTH_SHORT).show();
			}
		};
		// 执行任务
		task.execute();
	}
}
