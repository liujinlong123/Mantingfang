package com.android.mantingfang.first;

import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PoemPic extends Activity {
	
	private ImageView imgBack;
	private ImageView imgSave;
	private ImageView imgBg;
	private ImageView imgPic;
	private TextView tvPoem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poem_pic);
		
		initViews();
	}

	private void initViews() {
		imgBack = (ImageView)findViewById(R.id.poem_pic_back);
		imgSave = (ImageView)findViewById(R.id.poem_pic_save);
		imgBg = (ImageView)findViewById(R.id.poem_pic);
		imgPic = (ImageView)findViewById(R.id.poem_pic_img);
		tvPoem = (TextView)findViewById(R.id.poem_pic_poem);
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		imgSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 保存
				
			}
		});
		
		String path = getIntent().getStringExtra("path");
		getData(path);
	}
	
	private void getData(final String path) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(PoemPic.this,
						"正在上传", SuccinctProgress.THEME_LINE, false, true);
			}
			
			@Override
			protected String doInBackground(String... params) {
				try {
					return new PictureToPoem(path).getJSONArray();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				Bitmap bm = BitmapFactory.decodeFile(path);
				imgBg.setImageBitmap(bm);
				imgPic.setImageBitmap(bm);
				tvPoem.setText(result + "===========");
			}
			
		};
		
		task.execute();
	}
}
