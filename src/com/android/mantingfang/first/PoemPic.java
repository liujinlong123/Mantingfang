package com.android.mantingfang.first;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfanggsc.MyClient;
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
import android.widget.Toast;

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
				if (result != null && !result.equals("")) {
					//String content = result;	//待处理
					try {
						JSONObject jo = new JSONObject(result);
						String content = jo.getString("tags");
						JSONArray obj = new JSONArray(content);
						if (obj.length() >= 1) {
							String contentT = ((JSONObject) obj.get(0)).getString("value");
							Toast.makeText(PoemPic.this, contentT, Toast.LENGTH_SHORT).show();
							if (contentT.length() % 2 == 0) {
								getDataTwo(path, contentT);
							} else if (contentT.length() == 1) {
								SuccinctProgress.dismiss();
								Toast.makeText(PoemPic.this, "再拍一次看看", Toast.LENGTH_SHORT).show();
							} else if (contentT.length() > 1){
								getDataTwo(path, contentT.substring(0, 2));
							}
						} else {
							SuccinctProgress.dismiss();
							Toast.makeText(PoemPic.this, result, Toast.LENGTH_LONG).show();
							//finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					SuccinctProgress.dismiss();
					Toast.makeText(PoemPic.this, "再拍一次看看", Toast.LENGTH_SHORT).show();
					//finish();
				}
			}
			
		};
		
		task.execute();
	}
	
	private void getDataTwo(final String path, final String content) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postPoemByCamera(content, "5");
			}
			
			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				if (result != null && !result.equals("")) {
					Bitmap bm = BitmapFactory.decodeFile(path);
					imgBg.setImageBitmap(bm);
					imgPic.setImageBitmap(bm);
					tvPoem.setText(result);
				} else {
					Bitmap bm = BitmapFactory.decodeFile(path);
					imgBg.setImageBitmap(bm);
					imgPic.setImageBitmap(bm);
					Toast.makeText(PoemPic.this, "再拍一次看看", Toast.LENGTH_SHORT).show();
				}
			}
			
		};
		
		task.execute();
	}
}
