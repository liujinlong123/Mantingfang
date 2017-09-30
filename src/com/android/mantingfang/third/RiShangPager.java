package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.first.PoemRhesis;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.iflytek.cloud.ErrorCode;
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
import android.widget.Toast;

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
	
	private List<PoemRhesis> poemCollection;
	private PoemRhesis rhesis;
	private PoemRhesis r;
	
	//动画
	private AnimationDrawable animationDrawable;
	
	// 语音合成对象
	private SpeechSynthesizer mTts;
	private int thePlay = 0;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	public static Typeface typefaceKT;
	public static Typeface typefaceLS;
	public static Typeface typefaceHWXK;
	
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
		
		getTuiJianOne();
		
		imgMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getContext())
				.setItems(R.array.item_kind_choose, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						switch (whichcountry) {
						case 0:
							imgNext.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									getTuiJianTwo();
								}
							});
							
							imgPre.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									getTuiJianTwo();
								}
							});
							if (rhesis != null) {
								poemPager.setContent(rhesis.getPoemName(), rhesis.getWriter(), rhesis.getContent());
								poemTrans.setTrans(rhesis.getTrans());
								if (!rhesis.isCollect()) {
									imgCollect.setImageResource(R.drawable.a8e);
								} else {
									imgCollect.setImageResource(R.drawable.a8g);
								}
							}
							
							imgCollect.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									if (rhesis.isCollect()) {	//收藏-->没收藏
										imgCollect.setImageResource(R.drawable.a8e);
										sendCollection(rhesis.getPoemId(), "0");
									} else if (!rhesis.isCollect()) { 	//没收藏-->收藏
										imgCollect.setImageResource(R.drawable.a8g);
										sendCollection(rhesis.getPoemId(), "1");
									}
								}
							});
							
							imgPlayer.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									switch (thePlay) {
									case 0:
										// 开始合成
							            // 收到onCompleted 回调时，合成结束、生成合成音频
							            // 合成的音频格式：只支持pcm格式
										String text = rhesis.getPoemName() + "， " + rhesis.getWriter() + "， " + rhesis.getContent();
										//设置参数
										setParam();
										int code = mTts.startSpeaking(text, mTtsListener);
										
										if (code != ErrorCode.SUCCESS) {
											Toast.makeText(getContext(), "语音合成失败,错误码:" + code, Toast.LENGTH_SHORT).show();
										}
										break;
										
									case 1:
										mTts.pauseSpeaking();
										imgPlayer.setImageResource(R.drawable.rishang_pause);
										animationDrawable = (AnimationDrawable) imgPlayer
												.getDrawable();
										animationDrawable.start();
										thePlay = 2;
										break;
										
									case 2:
										mTts.resumeSpeaking();
										animationDrawable.stop();
										imgPlayer.setImageResource(R.drawable.rishang_play);
										animationDrawable = (AnimationDrawable) imgPlayer
												.getDrawable();
										animationDrawable.start();
										thePlay = 1;
										break;
									}
								}
							});
							thePlay = 0;
							break;
						case 1:
							thePlay = 0;
							getCollection();
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
	
	private void getTuiJianOne() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_RiShangTuiJian(UserId.getInstance(getContext()).getUserId());
			}

			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					JSONObject jo;
					try {
						jo = new JSONObject(result);
						rhesis = new PoemRhesis(
								jo.getString("poetry_id"),
								jo.getString("poetry_name"),
								jo.optString("poetry_rhesis"),
								jo.getString("writer_name"),
								jo.getString("poetry_content"),
								jo.getString("info_tonow"),
								false);
						
						poemPager = new RiShangPoem(rhesis.getPoemName(), rhesis.getWriter(), rhesis.getContent());
						poemTrans = new RiShangTrans(rhesis.getTrans());
						if (!rhesis.isCollect()) {
							imgCollect.setImageResource(R.drawable.a8e);
						} else {
							imgCollect.setImageResource(R.drawable.a8g);
						}
						
						fragmentList.add(poemPager);
						fragmentList.add(poemTrans);
						viewpager.setOffscreenPageLimit(2);
						viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
						adapter = new HomePageAdapter(getChildFragmentManager(), fragmentList);
						viewpager.setAdapter(adapter);
						viewpager.setCurrentItem(0);
						
						imgNext.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								getTuiJianTwo();
							}
						});
						
						imgPre.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								getTuiJianTwo();
							}
						});
						
						imgCollect.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if (rhesis.isCollect()) {	//收藏-->没收藏
									imgCollect.setImageResource(R.drawable.a8e);
									sendCollection(rhesis.getPoemId(), "0");
								} else if (!rhesis.isCollect()) { 	//没收藏-->收藏
									imgCollect.setImageResource(R.drawable.a8g);
									sendCollection(rhesis.getPoemId(), "1");
								}
							}
						});
						
						imgPlayer.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								switch (thePlay) {
								case 0:
									// 开始合成
						            // 收到onCompleted 回调时，合成结束、生成合成音频
						            // 合成的音频格式：只支持pcm格式
									String text = rhesis.getPoemName() + "， " + rhesis.getWriter() + "， " + rhesis.getContent();
									//设置参数
									setParam();
									int code = mTts.startSpeaking(text, mTtsListener);
									
									if (code != ErrorCode.SUCCESS) {
										Toast.makeText(getContext(), "语音合成失败,错误码:" + code, Toast.LENGTH_SHORT).show();
									}
									break;
									
								case 1:
									mTts.pauseSpeaking();
									imgPlayer.setImageResource(R.drawable.rishang_pause);
									animationDrawable = (AnimationDrawable) imgPlayer
											.getDrawable();
									animationDrawable.start();
									thePlay = 2;
									break;
									
								case 2:
									mTts.resumeSpeaking();
									animationDrawable.stop();
									imgPlayer.setImageResource(R.drawable.rishang_play);
									animationDrawable = (AnimationDrawable) imgPlayer
											.getDrawable();
									animationDrawable.start();
									thePlay = 1;
									break;
								}
							}
						});
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	private void getTuiJianTwo() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_RiShangTuiJian(UserId.getInstance(getContext()).getUserId());
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					JSONObject jo;
					try {
						jo = new JSONObject(result);
						rhesis = new PoemRhesis(
								jo.getString("poetry_id"),
								jo.getString("poetry_name"),
								jo.optString("poetry_rhesis"),
								jo.getString("writer_name"),
								jo.getString("poetry_content"),
								jo.getString("info_tonow"),
								false);
						
						poemPager.setContent(rhesis.getPoemName(), rhesis.getWriter(), rhesis.getContent());
						poemTrans.setTrans(rhesis.getTrans());
						if (!rhesis.isCollect()) {
							imgCollect.setImageResource(R.drawable.a8e);
						} else {
							imgCollect.setImageResource(R.drawable.a8g);
						}
						thePlay = 0;
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	private void getCollection() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_RiShangCollection(UserId.getInstance(getContext()).getUserId());
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("") && !result.equals("[]")) {
					try {
						poemCollection = new ArrayList<>();
						JSONArray obj = StringUtils.toJSONArray(result);
						for (int i = 0; i < obj.length(); i++) {
							JSONObject jo = obj.getJSONObject(i);
							PoemRhesis rhesis = new PoemRhesis(
									jo.getString("poetry_id"),
									jo.getString("poetry_name"),
									jo.optString("poetry_rhesis"),
									jo.getString("writer_name"),
									jo.getString("poetry_content"),
									jo.getString("info_tonow"),
									true);
							poemCollection.add(rhesis);
						}
						
						if (r != null) {
							poemPager.setContent(r.getPoemName(), r.getWriter(), r.getContent());
							poemTrans.setTrans(r.getTrans());
						} else {
							r = poemCollection.get((int)(poemCollection.size() * Math.random()));
							poemPager.setContent(r.getPoemName(), r.getWriter(), r.getContent());
							poemTrans.setTrans(r.getTrans());
						}
						if (!r.isCollect()) {
							imgCollect.setImageResource(R.drawable.a8e);
						} else {
							imgCollect.setImageResource(R.drawable.a8g);
						}
						
						imgNext.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								r = poemCollection.get((int)(poemCollection.size() * Math.random()));
								poemPager.setContent(r.getPoemName(), r.getWriter(), r.getContent());
								poemTrans.setTrans(r.getTrans());
								if (!r.isCollect()) {
									imgCollect.setImageResource(R.drawable.a8e);
								} else {
									imgCollect.setImageResource(R.drawable.a8g);
								}
								thePlay = 0;
							}
						});
						
						imgPre.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								r = poemCollection.get((int)(poemCollection.size() * Math.random()));
								poemPager.setContent(r.getPoemName(), r.getWriter(), r.getContent());
								poemTrans.setTrans(r.getTrans());
								if (!r.isCollect()) {
									imgCollect.setImageResource(R.drawable.a8e);
								} else {
									imgCollect.setImageResource(R.drawable.a8g);
								}
								thePlay = 0;
							}
						});
						
						imgCollect.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if (r.isCollect()) {	//收藏-->没收藏
									imgCollect.setImageResource(R.drawable.a8e);
									sendCollectionR(r.getPoemId(), "0");
								} else if (!r.isCollect()) { 	//没收藏-->收藏
									imgCollect.setImageResource(R.drawable.a8g);
									sendCollectionR(r.getPoemId(), "1");
								}
							}
						});
						
						imgPlayer.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								switch (thePlay) {
								case 0:
									// 开始合成
						            // 收到onCompleted 回调时，合成结束、生成合成音频
						            // 合成的音频格式：只支持pcm格式
									String text = r.getPoemName() + "， " + r.getWriter() + "， " + r.getContent();
									//设置参数
									setParam();
									int code = mTts.startSpeaking(text, mTtsListener);
									
									if (code != ErrorCode.SUCCESS) {
										Toast.makeText(getContext(), "语音合成失败,错误码:" + code, Toast.LENGTH_SHORT).show();
									}
									break;
									
								case 1:
									mTts.pauseSpeaking();
									imgPlayer.setImageResource(R.drawable.rishang_pause);
									animationDrawable = (AnimationDrawable) imgPlayer
											.getDrawable();
									animationDrawable.start();
									thePlay = 2;
									break;
									
								case 2:
									mTts.resumeSpeaking();
									animationDrawable.stop();
									imgPlayer.setImageResource(R.drawable.rishang_play);
									animationDrawable = (AnimationDrawable) imgPlayer
											.getDrawable();
									animationDrawable.start();
									thePlay = 1;
									break;
								}
							}
						});
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	private void sendCollection(final String poemId, final String collection) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postCollection(UserId.getInstance(getContext()).getUserId(), poemId, "1", collection);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (collection.equals("0")) {
					rhesis.setCollect(false);
				} else if (collection.equals("1")) {
					rhesis.setCollect(true);
				}
			}
			
		};
		
		task.execute();
	}
	
	private void sendCollectionR(final String poemId, final String collection) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postCollection(UserId.getInstance(getContext()).getUserId(), poemId, "1", collection);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (collection.equals("0")) {
					r.setCollect(false);
				} else if (collection.equals("1")) {
					r.setCollect(true);
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
			imgPlayer.setImageResource(R.drawable.rishang_play);
			animationDrawable = (AnimationDrawable) imgPlayer
					.getDrawable();
			animationDrawable.start();
			//开始播放
			thePlay = 1;
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
			// 播放完成
			if (error == null) {
				imgPlayer.setImageResource(R.drawable.rishang_pause);
				animationDrawable = (AnimationDrawable) imgPlayer
						.getDrawable();
				animationDrawable.start();
				thePlay = 0;
            } else if (error != null) {
               // Toast.makeText(FirstPagerInfoP.this, "False", Toast.LENGTH_SHORT).show();
            }
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			
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
