package com.android.mantingfang.third;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SearchTwo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddTwo extends Activity {
	
	public static final int POEM_ID = 5;

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private KindGridView grdView;
	private EditText editer;
	private TextView tvPoemName;
	
	//private String userId;
	//private String actionUrl = "http://1696824u8f.51mypc.cn:12755//receivecard.php";
	
	//private String res;
	private String poemId;
	private String poemName;
	private String poemContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_one);

		initViews();
	}

	// 需要数据Map<String,String> param
	// 用户ID 时间 内容 帖子标号 图片上传(图片名字， 图片路径) 接口
	private void initViews() {
		imgFinish = (ImageView) findViewById(R.id.add_one_img_finish);
		tvAdd = (TextView) findViewById(R.id.add_one_tv_add);
		linearAdd = (LinearLayout) findViewById(R.id.add_one_linear_add);
		grdView = (KindGridView) findViewById(R.id.add_one_grd_photo);
		editer = (EditText) findViewById(R.id.add_one_editer);
		tvPoemName = (TextView)findViewById(R.id.add_one_tv_poemName);

		/*SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		userId = pref.getString("userId", "-1");*/

		// 不保存
		imgFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 添加图片
		//initGridViews(CHOOSE_PHOTO, null);
		grdView.setVisibility(View.GONE);

		// 添加诗词
		// linearAdd.setVisibility(View.GONE);
		linearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddTwo.this, SearchTwo.class);
				startActivityForResult(intent, POEM_ID);
			}
		});

		// 上传
		tvAdd.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// 用户Id
				// 内容content
				String content = editer.getText().toString();
				// 帖子标号
				//String typeNum = "2";

				Date d = new Date();
				d.setHours(d.getHours());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d); // 当前时间

				/*Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("poetry_id", poemId);
				param.put("datatime", dateNowStr);
				param.put("content", content);
				param.put("type_num", typeNum);
				saveData(param);*/
				
				Intent intent = new Intent();
				intent.putExtra("poetry_id", poemId);
				intent.putExtra("poetry_name", poemName);
				intent.putExtra("poetry_content", poemContent);
				intent.putExtra("datatime", dateNowStr);
				intent.putExtra("content", content);
				//intent.putExtra("type_num", "2");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/*private void saveData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				try {
					Log.v("TOPIC--Two", param.toString());
					return FilesUpload.post(actionUrl, param, null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("result", result + "------");
				res = result;
				if (res != null && !res.equals("")) {
					Toast.makeText(AddTwo.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			}

		};

		task.execute();
	}*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 诗词Id
		case POEM_ID:
			if (resultCode == RESULT_OK) {
				poemId = data.getStringExtra("poemId");
				poemName = data.getStringExtra("poetry_name");
				poemContent = data.getStringExtra("poetry_content");
				tvPoemName.setText(data.getStringExtra("poetry_name"));
				Log.v("PoemId and poemName", data.getStringExtra("poemId") + " " + data.getStringExtra("poetry_writer"));
			}
			break;
		default:
			break;
		}
	}
}
