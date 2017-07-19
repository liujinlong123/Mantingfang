package com.android.mantingfang.third;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.FileUploader;
import com.android.mantingfanggsc.FileUploader.FileUploadListener;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddOne extends Activity {

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private KindGridView grdView;	
	private EditText editer;
	private String userId;
	private String actionUrl = "http://1696824u8f.51mypc.cn:12755//picturewander.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_one);
		
		initViews();
	}
	
	//需要数据Map<String,String> param
	//用户ID 时间  内容      帖子标号   图片上传(图片名字， 图片路径)	接口
	private void initViews() {
		imgFinish = (ImageView)findViewById(R.id.add_one_img_finish);
		tvAdd = (TextView)findViewById(R.id.add_one_tv_add);
		linearAdd = (LinearLayout)findViewById(R.id.add_one_linear_add);
		grdView = (KindGridView)findViewById(R.id.add_one_photo);
		editer = (EditText)findViewById(R.id.add_one_editer);
		
		/*SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		userId = pref.getString("userId", "xx");*/
		userId = "1";
		
		
		imgFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		linearAdd.setVisibility(View.GONE);
		
		tvAdd.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				//用户Id
				//内容content
				String content = editer.getText().toString();
				//帖子标号
				String typeNum = "1";
				
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d);		//当前时间
				
				Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("datatime", dateNowStr);
				param.put("content", content);
				param.put("type_num", typeNum);
				
				getData(param);
			}
		});
	}
	
	private void getData(final Map<String,String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			//String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				return FileUploader.upload(actionUrl, new File("路径"), param, new FileUploadListener() {
					
					@Override
					public void onProgress(long pro, double precent) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFinish(int code, String res, Map<String, List<String>> headers) {
						Log.v("result", res);
						
					}
				});
			}
			
			@Override
			protected void onPostExecute(String result) {
				Log.v("result", result + "------");
			}
			
			@Override
			protected void onProgressUpdate(Long... values) {
				//tv.setText(values + "");
			}
			
		};
		
		task.execute();
	}
}