package com.android.mantingfang.first;

import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPagerInfoP extends Activity {

	private Button btnInfo;
	private ImageView imgOn;
	private ImageView imgnext;
	// 语音合成对象
	private SpeechSynthesizer mTts;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private PoemRhesis rhesis;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SpeechUtility.createUtility(getBaseContext(), SpeechConstant.APPID + "=595f86ad");
		setContentView(R.layout.first_pager_info);
		// 初始化合成对象
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		
		initViews();
	}
	
	private void initViews() {
		btnInfo = (Button)findViewById(R.id.first_pager_info);
		imgOn = (ImageView)findViewById(R.id.first_pager_open);
		imgnext = (ImageView)findViewById(R.id.first_pager_next);
		tv1 = (TextView)findViewById(R.id.first_pager_info_tv2);
		tv2 = (TextView)findViewById(R.id.first_pager_info_tv1);
		tv3 = (TextView)findViewById(R.id.first_pager_info_tv3);
		
		Bundle bundle = getIntent().getExtras();
		rhesis = (PoemRhesis) bundle.get("rhesis");
		Log.v("TESTRhesis", rhesis.getRhesis());
		if (rhesis != null) {
			String[] tokens = rhesis.getRhesis().split("[,，.。]");
			tv1.setText(tokens[0]);
			tv2.setText(tokens[1]);
			tv3.setText(rhesis.getWriter());
		}
		
		
		btnInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getDataTwo(rhesis.getPoemId());
			}
		});
		
		imgnext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getDataOne();
			}
		});
		
		imgOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 开始合成
	            // 收到onCompleted 回调时，合成结束、生成合成音频
	            // 合成的音频格式：只支持pcm格式
				String text = rhesis.getRhesis();
				//设置参数
				setParam();
				int code = mTts.startSpeaking(text, mTtsListener);
				
				if (code != ErrorCode.SUCCESS) {
					Toast.makeText(FirstPagerInfoP.this, "语音合成失败,错误码:" + code, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * 合成回调监听
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			imgOn.setImageResource(R.drawable.a8n);
			//开始播放
		}
		
		@Override
		public void onSpeakPaused() {
			//暂停播放
		}
		
		@Override
		public void onSpeakResumed() {
			//继续播放
			
		}
		
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			//合成进度
			
		}

		@Override
		public void onCompleted(SpeechError error) {
			// �������
			if (error == null) {
                imgOn.setImageResource(R.drawable.a8p);
            } else if (error != null) {
               // Toast.makeText(FirstPagerInfoP.this, "False", Toast.LENGTH_SHORT).show();
            }
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			//���Ž���
			
		}
		
	};
	
	/**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED,
                "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME,
                "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE,
                "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.stopSpeaking();
        // 退出时释放连接
        mTts.destroy();
    }
    
    private void getDataOne() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				rhesis.setPoemId("1");
				rhesis.setRhesis("我在人民广场吃了，" + (int)(Math.random() * 10)  + "只炸鸡");
				rhesis.setWriter("李白" + (int)(Math.random() * 10));
				//return MyClient.getInstance().Http_postViewPager("", "");
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (rhesis != null) {
					String[] tokens = rhesis.getRhesis().split("[,，]");
					tv1.setText(tokens[0]);
					tv2.setText(tokens[1]);
					tv3.setText(rhesis.getWriter());
				}
			}
			
		};
		
		task.execute();
	}
    
    private void getDataTwo(final String poem_id) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().http_postPoem(poem_id);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					final List<PoemM> poemList;
					try {
						poemList = PoetryList.parsePoem(StringUtils.toJSONArray(result)).getPoemMList();
						if (poemList != null) {
							
							UIHelper.showPoemMDetail(FirstPagerInfoP.this, poemList.get(0), 0);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(FirstPagerInfoP.this, "没有返回数据", Toast.LENGTH_SHORT).show();
				}
			}

		};

		task.execute();
	}
}
