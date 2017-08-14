package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

public class RiShangPager extends BaseFragment{

	private View view;
	private ViewPager viewpager;
	private ImageView imgCollect;
	private ImageView imgPre;
	private ImageView imgPlayer;
	private ImageView imgNext;
	private ImageView imgMore;
	private RadioButton btnPoem;
	private RadioButton btnTrans;
	
	private HomePageAdapter adapter;
	
	private RiShangPoem poemPager;
	private RiShangTrans poemTrans;
	
	private boolean isPlay = false;
	
	//动画
	private AnimationDrawable animationDrawable;
	
	// 语音合成对象
	private SpeechSynthesizer mTts;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	public static Typeface typefaceKT;
	public static Typeface typefaceLS;
	public static Typeface typefaceHWXK;
	
	
	private String poemContent = "为君既不易，为臣良独难。忠信事不显，乃有见疑患。周公佐成王，金縢功不刊。"
			+ "推心辅王室，二叔反流言。待罪居东国，泣涕常流连。皇灵大动变，震雷风且寒。拔树偃秋稼，天威不可干。"
			+ "素服开金縢，感悟求其端。公旦事既显，成王乃哀叹。吾欲竟此曲，此曲悲且长。今日乐相乐，别后莫相忘";
	private String testTrans = "做国君既不容易，做臣下实在更难。当忠信不被理解时，就有被猜疑的祸患。周公辅佐文王、武王，“金縢”功绩不灭永传。一片忠心辅助周王室，管叔、蔡叔反大造谣言。"
			+ "周公待罪避居洛阳地，常常是老泪纵横长流不干。天帝动怒降下大灾难，雷鸣电闪卷地狂风猛又寒。拔起了大树吹倒庄稼，上天的威严不可触犯。"
			+ "成王感悟身穿礼服开金縢，寻求上天震怒降灾的根源。周公忠信大白天下，成王感动伤心悲叹。我真想奏完这支乐曲，可是这首乐曲又悲又长。今日大家一起共欢乐，希望别后不要把它遗忘。";
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_left_one, null);
			typefaceKT = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KT.ttf");
			typefaceLS = Typeface.createFromAsset(getActivity().getAssets(), "fonts/LS.ttf");
			typefaceHWXK = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HWXK.ttf");
			
			initViews();
			return view;
		}
		
		return view;
	}
	
	@SuppressWarnings("deprecation")
	private void initViews() {
		viewpager = (ViewPager)view.findViewById(R.id.rishang_viewPager);
		imgCollect = (ImageView)view.findViewById(R.id.rishang_collect);
		imgPre = (ImageView)view.findViewById(R.id.rishang_pre);
		imgPlayer = (ImageView)view.findViewById(R.id.rishang_play);
		imgNext = (ImageView)view.findViewById(R.id.rishang_next);
		imgMore = (ImageView)view.findViewById(R.id.rishang_more);
		btnPoem = (RadioButton)view.findViewById(R.id.rishang_rbtn2);
		btnTrans = (RadioButton)view.findViewById(R.id.rishang_rbtn3);
		
		// 初始化合成对象
		SpeechUtility.createUtility(getContext(), SpeechConstant.APPID + "=595f86ad");
		mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
		
		
		btnPoem.setOnClickListener(new MyOnClickListener(0));
		btnTrans.setOnClickListener(new MyOnClickListener(1));
		
		poemPager = new RiShangPoem("静夜思", "曹植", poemContent);
		poemTrans = new RiShangTrans(testTrans);
		
		fragmentList.add(poemPager);
		fragmentList.add(poemTrans);
		viewpager.setOffscreenPageLimit(2);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
		adapter = new HomePageAdapter(getChildFragmentManager(), fragmentList);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(0);
		
		imgCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		imgPre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		imgPlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 开始合成
	            // 收到onCompleted 回调时，合成结束、生成合成音频
	            // 合成的音频格式：只支持pcm格式
				//String text = rhesis.getRhesis();
				//设置参数
				/*setParam();
				int code = mTts.startSpeaking("Fuck you", mTtsListener);
				
				if (code != ErrorCode.SUCCESS) {
					Toast.makeText(getContext(), "语音合成失败,错误码:" + code, Toast.LENGTH_SHORT).show();
				}*/
				
				if (!isPlay) {	//开始播放
					imgPlayer.setImageResource(R.drawable.rishang_play);
					isPlay = true;
				} else {		//停止播放
					imgPlayer.setImageResource(R.drawable.rishang_pause);
					isPlay = false;
				}
				animationDrawable = (AnimationDrawable) imgPlayer
						.getDrawable();
				animationDrawable.start();
			}
		});
		
		imgNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		imgMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
				.setItems(R.array.item_fonts, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						switch (whichcountry) {
						case 0:
							setViewPagerAdapter(0);
							break;
						case 1:
							setViewPagerAdapter(1);
							break;
							
						case 2:
							setViewPagerAdapter(2);
							break;
						}
						
					}
				}).show();
			}
		});
	}
	
	
	//-----------------------------------------------------ViewPager---------------------------------------------------------
	
	@SuppressLint("ResourceAsColor")
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				btnPoem.setBackgroundResource(R.drawable.icon_point_pre);
				btnTrans.setBackgroundResource(R.drawable.icon_point);
				
				break;
				
			case 1:
				btnPoem.setBackgroundResource(R.drawable.icon_point);
				btnTrans.setBackgroundResource(R.drawable.icon_point_pre);
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	class HomePageAdapter extends android.support.v4.app.FragmentStatePagerAdapter {// FragmentPagerAdapter

		// private FragmentManager fm;
		private List<Fragment> fragments = null;
		@SuppressWarnings("unused")
		private FragmentManager fm;

		public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return fragments.size();// hotIssuesList.size();
		}
		
		public void setFonts(int type) {
			if (fragments.size() == 2) {
				((RiShangPoem) fragments.get(0)).setFronts(type);
				((RiShangTrans) fragments.get(1)).setFronts(type);
			}
		}
		
		public void setContent(String name, String writer, String content, String trans) {
			if (fragments.size() == 2) {
				((RiShangPoem) fragments.get(0)).setContent(name, writer, content);
				((RiShangTrans) fragments.get(1)).setTrans(trans);
			}
		}
	}

	public class MyOnClickListener implements View.OnClickListener {

		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewpager.setCurrentItem(index);
		}

	}
	
	private void setViewPagerAdapter(int type) {
		@SuppressWarnings("static-access")
		SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE).edit();
		editor.putInt("rishang_fonts_type", type);
		editor.commit();
		adapter.setFonts(type);
	}
	
	//-----------------------------------------------------ViewPager---------------------------------------------------------
	
	
	
	//-----------------------------------------------------数据获取---解析----------------------------------------------------
	private void getPoemUserId() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						JSONObject jo = new JSONObject(result);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	private void getPoemById(final String poemId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_RiShang(poemId);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						JSONObject jo = new JSONObject(result);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	
	//-----------------------------------------------------数据获取---解析----------------------------------------------------
	
	
	
	
	//-----------------------------------------------------语音合成----------------------------------------------------------
	/**
	 * 合成回调监听
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			//imgOn.setImageResource(R.drawable.a8n);
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
               // imgOn.setImageResource(R.drawable.a8p);
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
    public void onDestroy() {
    	super.onDestroy();
        if (mTts != null) {
        	mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }
    
  //-----------------------------------------------------语音合成----------------------------------------------------------
}
