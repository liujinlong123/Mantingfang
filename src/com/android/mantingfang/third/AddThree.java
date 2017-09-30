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

import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.picture.Picture;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.FilesUpload;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.PictureUtil;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddThree extends Activity implements OnRequestPermissionsResultCallback {

	public static final int TAKE_PHOTO = 1;

	public static final int CROP_PHOTO = 2;

	public static final int CHOOSE_PHOTO = 3;

	public static final int LIST_PHOTO = 4;
	
	private static final int MY_PERMISSIONS_REQUEST_CAMERA = 6;

	private ImageView imgFinish;
	private TextView tvAdd;
	//private LinearLayout linearAdd;
	private KindGridView grdView;
	private EditText editerTitle;
	private EditText editerContent;
	private String actionUrl = MyClient.actionUrl + "receivecard.php";

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
		setContentView(R.layout.add_three);

		//initViews();
		Accessibility();
	}

	// 需要数据Map<String,String> param
	// 用户ID 时间 内容 帖子标号 图片上传(图片名字， 图片路径) 接口
	private void initViews() {
		imgFinish = (ImageView) findViewById(R.id.add_three_img_finish);
		tvAdd = (TextView) findViewById(R.id.add_three_tv_add);
		grdView = (KindGridView) findViewById(R.id.add_three_grd_photo);
		editerTitle = (EditText) findViewById(R.id.add_three_editer_title);
		editerContent = (EditText)findViewById(R.id.add_three_editer_content);
		
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
		//linearAdd.setVisibility(View.GONE);
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
				String title = editerTitle.getText().toString();
				String contentT = editerContent.getText().toString();
				String content = "";
				if (contentT.contains("\n")) {
					String[] tokens = contentT.split("\n");
					for (String e: tokens) {
						content += (e + "。");
					}
					
				} else {
					content = contentT;
				}
				
				// 帖子标号
				//String typeNum = "3";

				
				if (content != null && !content.equals("")) {
					Date d = new Date();
					d.setHours(d.getHours());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateNowStr = sdf.format(d); // 当前时间
					
					Map<String, String> param = new HashMap<>();
					param.put("user_id", UserId.getInstance(AddThree.this).getUserId());
					param.put("datatime", dateNowStr);
					param.put("content", content);
					param.put("type_num", "3");
					param.put("original_title", title);
					
					saveData(param);
				} else {
					Toast.makeText(AddThree.this, "内容不能为空", Toast.LENGTH_SHORT).show();
				}
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

	private void saveData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(AddThree.this,
						"正在上传", SuccinctProgress.THEME_LINE, false, true);
			}
			
			
			@Override
			protected String doInBackground(String... params) {
				Map<String, File> files = new HashMap<>();
				ArrayList<String> setPathss = new ArrayList<>();
				for (String e: setPath) {
					setPathss.add(PictureUtil.compressImage(e, PictureUtil.desPath, 30));
				}
				
				for (String e: setPathss) {
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
				SuccinctProgress.dismiss();
				if (result != null && !result.equals("")) {
					Intent intent = new Intent();
					intent.putExtra("isSave", true);
					intent.putExtra("user_id", param.get("user_id"));
					intent.putExtra("datatime", param.get("datatime"));
					intent.putExtra("title", param.get("title"));
					intent.putExtra("content", param.get("content"));
					intent.putExtra("postId", result.substring(result.lastIndexOf("}") + 1));
					intent.putStringArrayListExtra("setPath", setPath);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(AddThree.this, "上传失败,请重新发送", Toast.LENGTH_SHORT).show();
				}
			}

		};

		task.execute();
	}

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
	
	@SuppressLint("InlinedApi")
	public void Accessibility() {
		if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    		Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else
        {
        	initViews();
        }

	}

	@SuppressLint("Override")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] arg1, int[] grantResults) {
		if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initViews();
                
            } else
            {
                // Permission Denied
                Toast.makeText(AddThree.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                //userPhoto.setClickable(false);
                //finish();
            }
            return;
        }

	}
}
