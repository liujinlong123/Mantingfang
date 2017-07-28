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
import com.android.mantingfanggsc.RecordPlayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddFour extends Activity implements OnClickListener {

	private ImageView imgFinish;
	private TextView tvAdd;
	private LinearLayout linearAdd;
	private String userId;
	private String actionUrl = "http://1696824u8f.51mypc.cn:12755//picturewander.php";

	private TextView poemName;
	private TextView poemWriter;
	private TextView poemContent;
	private TextView stop;
	private ImageView start;
	private TextView sing;

	// 录音类
	private MediaRecorder mediaRecorder;
	// 以文件形式保存
	private File recordFile;

	private RecordPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_four);

		recordFile = new File("/mnt/sdcard", "kk.amr");
		initViews();
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

		// 添加诗词
		linearAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 录音播放
		stop.setOnClickListener(this);
		start.setOnClickListener(this);
		sing.setOnClickListener(this);

		// 上传
		tvAdd.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// 用户Id

				// 帖子标号
				String typeNum = "1";

				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateNowStr = sdf.format(d); // 当前时间

				Map<String, String> param = new HashMap<>();
				param.put("user_id", userId);
				param.put("datatime", dateNowStr);
				param.put("type_num", typeNum);
				getData(param);
			}
		});
	}

	@Override
	public void onClick(View v) {
		player = new RecordPlayer(AddFour.this);
		int Id = v.getId();

		switch (Id) {
		case R.id.add_four_start:
			startRecording();
			break;
		case R.id.add_four_stop:
			stopRecording();
			break;
		case R.id.add_four_sing:
			playRecording();
			break;
		}
	}
	
	private void startRecording() {
		mediaRecorder = new MediaRecorder();
		// 判断，若当前文件已存在，则删除
		if (recordFile.exists()) {
			recordFile.delete();
		}
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

		try {
			// 准备好开始录音
			mediaRecorder.prepare();

			mediaRecorder.start();
			start.setImageResource(R.drawable.record_start);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void stopRecording() {
		if (recordFile != null) {
			start.setImageResource(R.drawable.record_end);
			mediaRecorder.stop();
			mediaRecorder.release();
		}
	}

	private void playRecording() {
		player.playRecordFile(recordFile);
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
}