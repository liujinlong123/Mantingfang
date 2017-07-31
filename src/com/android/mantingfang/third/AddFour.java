package com.android.mantingfang.third;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mantingfanggsc.FileUploader;
import com.android.mantingfanggsc.FileUploader.FileUploadListener;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SearchTwo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddFour extends Activity implements OnRequestPermissionsResultCallback{

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private String userId;
	private String actionUrl = "http://1696824u8f.51mypc.cn:12755//picturewander.php";

	private static final String LOG_TAG = "AudioRecordTest";
	
	private TextView poemName;
	private TextView poemWriter;
	private TextView poemContent;
	private TextView stop;
	private ImageView start;
	private TextView sing;
	
	public static final int POETRY = 6;

	// 录音类
	private MediaPlayer mPlayer = null;  
    private MediaRecorder mRecorder = null;
    //语音文件保存路径
    private String FileName = null;
    
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_four);
		
		Accessibility();
	}

	// 需要数据Map<String,String> param
	// 用户ID 时间 内容 帖子标号 图片上传(图片名字， 图片路径) 接口
	private void initViews() {
		imgFinish = (ImageView) findViewById(R.id.add_four_img_finish);
		tvAdd = (TextView) findViewById(R.id.add_four_tv_add);
		linearAdd = (LinearLayout) findViewById(R.id.add_four_linear_add);

		poemName = (TextView) findViewById(R.id.add_four_poem_name);
		poemWriter = (TextView) findViewById(R.id.add_four_poem_writer);
		poemContent = (TextView) findViewById(R.id.add_four_poem_content);
		stop = (TextView) findViewById(R.id.add_four_stop);
		start = (ImageView) findViewById(R.id.add_four_start);
		sing = (TextView) findViewById(R.id.add_four_sing);

		
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		userId = pref.getString("userId", "-1");

		// 不保存
		imgFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 添加诗词
		linearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddFour.this, SearchTwo.class);
				startActivityForResult(intent, POETRY);
			}
		});

		// 录音播放
		stop.setOnClickListener(new stopRecordListener());		//停止录音
		start.setOnClickListener(new startRecordListener());	//开始录音
		sing.setOnClickListener(new startPlayListener());		//开始播放
		
		//设置sdcard的路径  
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();  
        FileName += "/audiorecordtest.3gp";

		// 上传
		tvAdd.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// 用户Id

				// 帖子标号
				String typeNum = "4";

				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d); // 当前时间

				Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("datatime", dateNowStr);
				param.put("type_num", typeNum);
				if (FileName != null && !FileName.equals("")) {
					getData(param);
				} else {
					Toast.makeText(AddFour.this, "没有音频文件", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	//开始录音 
    class startRecordListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
             mRecorder = new MediaRecorder();  
             mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
             mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
             mRecorder.setOutputFile(FileName);  
             mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
             try {  
                 mRecorder.prepare();
                 Toast.makeText(AddFour.this, "开始录音", Toast.LENGTH_SHORT).show();
             } catch (IOException e) {  
                 Log.e(LOG_TAG, "prepare() failed");  
             }  
             mRecorder.start();  
        }  
          
    }  
    //停止录音 
    class stopRecordListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
             mRecorder.stop();  
             mRecorder.release();  
             mRecorder = null;  
        }  
          
    }  
    //播放录音
    class startPlayListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            mPlayer = new MediaPlayer();  
            Toast.makeText(AddFour.this, "播放录音", Toast.LENGTH_SHORT).show();
            try{  
                mPlayer.setDataSource(FileName);  
                mPlayer.prepare();  
                mPlayer.start();  
            }catch(IOException e){  
                Log.e(LOG_TAG,"播放失败");  
            }  
        }  
    }  
    //停止播放录音  
    class stopPlayListener implements OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            mPlayer.release();  
            mPlayer = null; 
            Toast.makeText(AddFour.this, "停止录音", Toast.LENGTH_SHORT).show();
        }     
    }  

	private void getData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				return FileUploader.upload(actionUrl, new File(FileName), param, new FileUploadListener() {

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
	
	@SuppressLint("InlinedApi")
	public void Accessibility() {
		if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
                    		Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else
        {
        	initViews();
        }

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] arg1, int[] grantResults) {
		if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                initViews();
            } else
            {
                // Permission Denied
                Toast.makeText(AddFour.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
        }

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case POETRY:
			if (resultCode == RESULT_OK) {
				String poetry_name = data.getStringExtra("poetry_name");
				String poetry_writer = data.getStringExtra("poetry_writer");
				String poetry_content = data.getStringExtra("poetry_content");
				poemName.setText(poetry_name);
				poemWriter.setText(poetry_writer);
				poemContent.setText(poetry_content);
				Log.v("AddFour", poetry_name + " " + poetry_writer + " " + poetry_content);
			}
			break;
			
		default:
			break;
		}
	}
}
