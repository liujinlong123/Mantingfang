package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.ClientPoem;
import com.android.mantingfanggsc.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
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
	private SpeechSynthesizer mTts;
	private ClientPoem poem;
	private List<Poem> poemList;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private int count = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SpeechUtility.createUtility(getBaseContext(), SpeechConstant.APPID + "=595f86ad");
		setContentView(R.layout.first_pager_info);
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		
		initViews();
	}
	
	private void initViews() {
		btnInfo = (Button)findViewById(R.id.first_pager_info);
		imgOn = (ImageView)findViewById(R.id.first_pager_open);
		imgnext = (ImageView)findViewById(R.id.first_pager_next);
		tv1 = (TextView)findViewById(R.id.first_pager_info_tv1);
		tv2 = (TextView)findViewById(R.id.first_pager_info_tv2);
		tv3 = (TextView)findViewById(R.id.first_pager_info_tv3);
		poem = new ClientPoem("fuck you", "0");
		poemList = new ArrayList<>();
		poemList = poem.getPoemList();
		
		
		btnInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		imgnext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				poemList = poem.getPoemList();
				/*String[] token = poemList.get(1).getRhesis().split("[, + . 。，]");
				if (token.length == 2) {
					tv1.setText(token[0]);
					tv2.setText(token[1]);
					tv3.setText(poemList.get(1).getWritername());
				}*/
				
				for (int i = 0; i < poemList.size(); i++) {
					Log.v("list-rhesis", poemList.get(i).getRhesis() + "----------");
					Log.v("list-content", poemList.get(i).getContent() + "----------");
				}
			}
		});
		
		imgOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = "明月几时有，把酒问青天";
				setParam();
				int code = mTts.startSpeaking(text, mTtsListener);
				
				if (code != ErrorCode.SUCCESS) {
					Toast.makeText(FirstPagerInfoP.this, "语音合成实拍， 错误码：" + code, Toast.LENGTH_SHORT).show();
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
		}
		
		@Override
		public void onSpeakPaused() {
			//播放暂停
		}
		
		@Override
		public void onSpeakResumed() {
			//继续播放
			
		}
		
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// 合成进度
			
		}

		@Override
		public void onCompleted(SpeechError error) {
			// 播放完成
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
			//播放进度
			
		}
		
	};
	
	/**
	 * 参数设置
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
		//退出时 释放连接
		mTts.destroy();
	}
}
