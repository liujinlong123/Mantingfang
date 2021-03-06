package com.android.mantingfang.fourth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.third.PictureLoad;
import com.android.mantingfang.third.User;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.FileUploader;
import com.android.mantingfanggsc.FileUploader.FileUploadListener;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.PictureUtil;
import com.android.mantingfanggsc.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("Override")
public class FourthMy extends Activity implements OnRequestPermissionsResultCallback{
	
	public static final int TAKE_PHOTO = 1;

	public static final int CROP_PHOTO = 2;

	public static final int CHOOSE_PHOTO = 3;
	
	public static final int INTRO = 4;
	
	public static final int LOCATION = 5;

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView save;
	
	//昵称 	签名 	性别 	生日 	所在地 	个人介绍
	private TextView sex;
	private TextView birth;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private TextView area;
	private TextView intro;
	
	private CircleImageView userPhoto;
	private Uri imgUri;
	
	private User user = new User();
	private String actionUrl = MyClient.actionUrl + "receiveusermessage.php";
	
	
	private static final int MY_PERMISSIONS_REQUEST_CAMERA = 4;
	
	private EditText editorNickName;
	private EditText editorLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_my);
		//initViews();
		Accessibility();
	}
	
	private void initViews() {
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		 
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("主页");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		save = (TextView)findViewById(R.id.topbar_all_saveOn);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Map<String, String> param = new HashMap<>();
				param.put("user_id", user.getUserId());
				param.put("user_nickname", user.getUserNickname());
				param.put("user_label", user.getUserLabel());
				param.put("user_sex", user.getUserSex());
				param.put("user_age", user.getUserAge());
				param.put("user_area", user.getUserArea());
				param.put("user_introduce", user.getUserIntro());
				saveData(param);
				setResult(RESULT_OK);
				finish();
			}
		});
		sex = (TextView)findViewById(R.id.fourth_my_xingbie);
		birth = (TextView)findViewById(R.id.fourth_my_birth);
		area = (TextView)findViewById(R.id.fourth_my_area);
		intro = (TextView)findViewById(R.id.fourth_my_intro);
		userPhoto = (CircleImageView)findViewById(R.id.fourth_my_userPhoto);
		editorNickName = (EditText)findViewById(R.id.fourth_my_nicheng);
		editorLabel = (EditText)findViewById(R.id.fourth_my_qianming);
		
		
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		user.setUserId(pref.getString("userId", "-1"));
		getData(user.getUserId());
		
		//昵称
		editorNickName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null && !s.toString().equals("")) {
					user.setUserNickname(s.toString());
				}
				
			}
		});
		
		//签名
		editorLabel.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null && !s.toString().equals("")) {
					user.setUserLabel(s.toString());
				}
			}
		});
		
		
		// 性别选择
		sex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(FourthMy.this)
				.setItems(R.array.item_sex, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						switch (whichcountry) {
						case 0:
							sex.setText("男");
							user.setUserSex("男");
							break;
						case 1:
							sex.setText("女");
							user.setUserSex("女");
							break;
						}
						
					}
				}).show();
			}
		});
		
		//生日选择
		birth.setOnClickListener(new OnClickListener() {
			Calendar c = Calendar.getInstance();
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(FourthMy.this, new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						mYear = year;
						mMonth = monthOfYear;
						mDay = dayOfMonth;
						birth.setText(mYear + "-" + ( mMonth + 1) + "-" + mDay);
						user.setUserAge(mYear + "-" + ( mMonth + 1) + "-" + mDay);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		//地区选择
		area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FourthMy.this, Location.class);
				startActivityForResult(intent, LOCATION);
			}
		});
		
		//个人介绍
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FourthMy.this, Intro.class);
				startActivityForResult(intent, INTRO);
			}
		});
		
		//个人头像
		userPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openOptionsMenu();
			}
		});
	}
	
	/**
	 * 保存
	 * @param param
	 */
	private void saveData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@SuppressLint("SdCardPath")
			@Override
			protected String doInBackground(String... params) {
				final String path = PictureUtil.compressImage(user.getUserPhoto(), PictureUtil.desPath, 30);
				//Log.v("Path", path + "----");
				
				File file = new File(path);
				if (file.exists()) {
					return FileUploader.upload(actionUrl, new File(path), param, new FileUploadListener() {

						@Override
						public void onProgress(long pro, double precent) {

						}

						@Override
						public void onFinish(int code, String res, Map<String, List<String>> headers) {
							PictureUtil.deleteFile(path);
							Log.v("result--des", res);
						}
					});
				} else {
					return FileUploader.upload(actionUrl, new File(user.getUserPhoto()), param, new FileUploadListener() {

						@Override
						public void onProgress(long pro, double precent) {

						}

						@Override
						public void onFinish(int code, String res, Map<String, List<String>> headers) {
							Log.v("result--ori", res);
						}
					});
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
				editor.putString("headPath", user.getUserPhoto());
				editor.putString("nickName", user.getUserNickname());
				editor.commit();
			}
			
		};
		
		task.execute();
	}
	
	/**
	 * 获取数据
	 * @param userId
	 */
	private void getData(final String userId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@SuppressLint("SdCardPath")
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postUserInfo(userId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						List<User> list = TopicList.parseUserInfo(StringUtils.toJSONArray(result), userId).getUserInfoList();
						if (list != null && list.size() > 0) {
							user = list.get(0);
							editorNickName.setText(user.getUserNickname());
							editorLabel.setText(user.getUserLabel());
							sex.setText(user.getUserSex());
							birth.setText(user.getUserAge());
							area.setText(user.getUserArea());
							intro.setText(user.getUserIntro());
							//user.setUserPhoto("1.png");
							editorNickName.setSelection(user.getUserNickname().length());
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					PictureLoad.getInstance().loadImage(user.getUserPhoto(), userPhoto);
				}
			}
			
		};
		
		task.execute();
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
				e.printStackTrace();
			}

			imgUri = Uri.fromFile(outputImage);
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
			startActivityForResult(intent, TAKE_PHOTO);
			break;

		case R.id.choose_from_album:
			// 跳转相册
			Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
			intent2.setType("image/*");
			startActivityForResult(intent2, CHOOSE_PHOTO);
			break;
		}

		return true;
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
					userPhoto.setImageBitmap(bitmap);
					//user.setUserPhoto(userPhoto);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		// 图片选择
		case CHOOSE_PHOTO:
			if (resultCode == RESULT_OK) {
				if (Build.VERSION.SDK_INT >= 19) {
					handleImageOnKitKat(data);
				} else {
					handleImageBeforeKitKat(data);
				}
			}
			break;
			
		//个人介绍
		case INTRO:
		if (resultCode == RESULT_OK) {
			String intros = data.getStringExtra("intro");
			if (intros != null) {
				user.setUserIntro(intros);
				intro.setText(intros);
			} else {
				user.setUserIntro("");
			}
		}
			break;
			
		//位置
		case LOCATION:
			if (resultCode == RESULT_OK) {
				String loc = data.getStringExtra("location");
				if (loc != null) {
					user.setUserArea(loc);
					area.setText(loc);
				} else {
					user.setUserArea("");
				}
			}
		default:
			break;
		}
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("NewApi")
	private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(this, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
				String id = docId.split("[:]")[1];
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			} else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			imagePath = getImagePath(uri, null);
		}
		displayImage(imagePath);
	}
	
	private void handleImageBeforeKitKat(Intent data) {
		Uri uri = data.getData();
		String imagePath = getImagePath(uri, null);
		displayImage(imagePath);
	}
	
	private String getImagePath(Uri uri, String selection) {
		String path = null;
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();
		}
		
		return path;
	}
	
	private void displayImage(String imagePath) {
		if (imagePath != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			//Log.v("Path", imagePath);
			userPhoto.setImageBitmap(bitmap);
			user.setUserPhoto(imagePath);
		} else {
			Toast.makeText(FourthMy.this, "failed to get image", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FourthMy.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
        }

	}
}
