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
				/*String[] token = poemList.get(1).getRhesis().split("[, + . ����]");
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
				String text = "���¼�ʱ�У��Ѿ�������";
				setParam();
				int code = mTts.startSpeaking(text, mTtsListener);
				
				if (code != ErrorCode.SUCCESS) {
					Toast.makeText(FirstPagerInfoP.this, "�����ϳ�ʵ�ģ� �����룺" + code, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * �ϳɻص�����
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			imgOn.setImageResource(R.drawable.a8n);
		}
		
		@Override
		public void onSpeakPaused() {
			//������ͣ
		}
		
		@Override
		public void onSpeakResumed() {
			//��������
			
		}
		
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// �ϳɽ���
			
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
	 * 设置参数
	 */
	private void setParam() {
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED,
                "50");
        mTts.setParameter(SpeechConstant.PITCH, "50");
        mTts.setParameter(SpeechConstant.VOLUME,
                "50");
        mTts.setParameter(SpeechConstant.STREAM_TYPE,
                "3");
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTts.stopSpeaking();
		mTts.destroy();
	}
}
