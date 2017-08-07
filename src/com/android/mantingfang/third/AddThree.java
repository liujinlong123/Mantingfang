package com.android.mantingfang.third;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.mantingfang.picture.Picture;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

public class AddThree extends Activity {

	public static final int TAKE_PHOTO = 1;

	public static final int CROP_PHOTO = 2;

	public static final int CHOOSE_PHOTO = 3;

	public static final int LIST_PHOTO = 4;

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private KindGridView grdView;
	private EditText editer;
	//private String userId;
	//private String actionUrl = "http://1696824u8f.51mypc.cn:12755//receivecard.php";

	private Uri imgUri;
	private PictureAdapter picAdapter;
	private List<Bitmap> bitList;
	
	/**
	 * 选中图片的路径集合
	 */
	private ArrayList<String> setPath = new ArrayList<>();
	
	private int pos = 0;
	
	//private String res;

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
		initGridViews(CHOOSE_PHOTO, null);
		
		// 添加诗词
		linearAdd.setVisibility(View.GONE);
		/*linearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddThree.this, SearchTwo.class);
				startActivityForResult(intent, POEM_ID);
			}
		});*/
		
		grdView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == pos) {
					openOptionsMenu();
				}
				Log.v("TestPosition", position + "");
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
				//String typeNum = "3";

				Date d = new Date();
				d.setHours(d.getHours());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d); // 当前时间

				/*Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("datatime", dateNowStr);
				param.put("content", content);
				param.put("type_num", typeNum);
				saveData(param);*/
				Intent intent = new Intent();
				intent.putExtra("datetime", dateNowStr);
				intent.putExtra("content", content);
				intent.putStringArrayListExtra("setPath", setPath);
				setResult(RESULT_OK, intent);
				finish();
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
		picAdapter = new PictureAdapter(AddThree.this, bitList);
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
			Intent intent2 = new Intent(AddThree.this, Picture.class);
			intent2.putStringArrayListExtra("photos", setPath);
			Log.v("跳转相册", setPath.toString());
			startActivityForResult(intent2, LIST_PHOTO);
			break;
		}

		return true;
	}

	/*private void saveData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				Map<String, File> files = new HashMap<>();
				for (String e: setPath) {
					File f = new File(e);
					files.put(f.getName(), f);
				}
				try {
					Log.v("TOPIC", param.toString());
					return FilesUpload.post(actionUrl, param, files);
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
					Toast.makeText(AddThree.this, "上传成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			}

		};

		task.execute();
	}*/

	/**
	 * 从上一界面返回结果
	 */
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
