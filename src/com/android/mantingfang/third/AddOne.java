package com.android.mantingfang.third;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mantingfang.picture.Picture;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.FileUploader;
import com.android.mantingfanggsc.FileUploader.FileUploadListener;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddOne extends Activity {

	public static final int TAKE_PHOTO = 1;

	public static final int CROP_PHOTO = 2;

	public static final int CHOOSE_PHOTO = 3;

	public static final int LIST_PHOTO = 4;

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private KindGridView grdView;
	private EditText editer;
	private String userId;
	private String actionUrl = "http://1696824u8f.51mypc.cn:12755//picturewander.php";

	private Uri imgUri;
	private PictureAdapter picAdapter;
	private List<Bitmap> bitList;
	
	/**
	 * 选中图片的路径集合
	 */
	private ArrayList<String> setPath = new ArrayList<>();
	
	private int pos = 0;

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

		/*
		 * SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		 * userId = pref.getString("userId", "xx");
		 */
		userId = "1";

		// 不保存
		imgFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// 添加图片
		initGridViews(CHOOSE_PHOTO, null);
		
		grdView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == pos) {
					openOptionsMenu();
				}
				Log.v("TestPosition", position + "");
			}
		});

		// 添加诗词
		linearAdd.setVisibility(View.GONE);
		linearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		// 上传
		tvAdd.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// 用户Id
				// 内容content
				String content = editer.getText().toString();
				// 帖子标号
				String typeNum = "1";

				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d); // 当前时间

				Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("datatime", dateNowStr);
				param.put("content", content);
				param.put("type_num", typeNum);
				getData(param);
			}
		});
	}

	@SuppressWarnings("static-access")
	private void initGridViews(int code, Bitmap bmp) {
		grdView.setVisibility(View.VISIBLE);
		grdView.setNumColumns(4);
		bitList = new ArrayList<>();
		if (setPath == null || setPath.size() == 0) {
			if (code == TAKE_PHOTO) {
				bitList.add(bmp);
			}
			Bitmap bm = new BitmapFactory().decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
			bitList.add(bm);
		} else {
			for (int i = setPath.size() - 1; i >= 0; i--) {
				bitList.add(new BitmapFactory().decodeFile(setPath.get(i)));
			}
			
			if (code == TAKE_PHOTO) {
				bitList.add(bmp);
			}
			Bitmap bm = new BitmapFactory().decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
			bitList.add(bm);
		}
		
		pos = bitList.size() - 1;
		picAdapter = new PictureAdapter(AddOne.this, bitList);
		grdView.setAdapter(picAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.maint, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.take_photo:
			// 跳转拍照
			File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");

			try {
				if (outputImage.exists()) {
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			imgUri = Uri.fromFile(outputImage);
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
			startActivityForResult(intent, TAKE_PHOTO);
			break;

		case R.id.choose_from_album:
			// 跳转相册
			Intent intent2 = new Intent(AddOne.this, Picture.class);
			intent2.putStringArrayListExtra("photos", setPath);
			Log.v("跳转相册", setPath.toString());
			startActivityForResult(intent2, LIST_PHOTO);
			break;
		}

		return true;
	}

	private void getData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
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
				// tv.setText(values + "");
			}

		};

		task.execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imgUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		// 拍照处理
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
					initGridViews(TAKE_PHOTO, bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		// 图片返回处理
		case LIST_PHOTO:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				setPath = bundle.getStringArrayList("listPhoto");
				initGridViews(CHOOSE_PHOTO, null);
			}
			break;
		default:
			break;
		}
	}
}
