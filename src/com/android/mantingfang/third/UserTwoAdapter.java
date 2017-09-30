package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.Player;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;
import com.android.mantingfanggsc.Player.StartPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserTwoAdapter extends BaseAdapter {
	
	public static final int VAULE_THEME_M = 0;
	public static final int VALUE_THEME_S = 1;
	public static final int VALUE_NOTE = 2;
	public static final int VALUE_ORIGINAL_M = 3;
	public static final int VALUE_ORIGINAL_S = 4;
	public static final int VALUE_AUDIO = 5;

	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;
	private String headPath;
	private Player player;
	
	@SuppressWarnings("unused")
	private MediaPlayer mPlayer = null;
	private AnimationDrawable animationDrawable;
	private Handler handler;
	private boolean showLine = false;

	public UserTwoAdapter(Context context, List<UserTwoContent> list, String headPath) {
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.headPath = headPath;
		player = new Player();
	}
	
	public UserTwoAdapter(Context context, List<UserTwoContent> list, String headPath, boolean showLine) {
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.headPath = headPath;
		player = new Player();
		this.showLine = showLine;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		int type = getItemViewType(position);
		if (convertView == null) {
			view = inflater.inflate(R.layout.third_pager_one_itemlist, null);
			holder = new ViewHolder();
			holder.linearHead = (LinearLayout) view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (CircleImageView) view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView) view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView) view.findViewById(R.id.third_pager_user_time);
			holder.title = (TextView)view.findViewById(R.id.third_pager_user_title);
			holder.content = (TextView) view.findViewById(R.id.third_pager_user_content);
			holder.linearSound = (LinearLayout) view.findViewById(R.id.third_pager_sound);
			holder.imgSound = (ImageView) view.findViewById(R.id.third_pager_img_sound);
			holder.singleImage = (ImageView)view.findViewById(R.id.third_pager_single_img);
			holder.grdview = (KindGridView) view.findViewById(R.id.third_pager_user_grdphoto);
			holder.linearPoem = (LinearLayout) view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView) view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView) view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView) view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView) view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView) view.findViewById(R.id.third_pager_one_share);
			holder.line = (View)view.findViewById(R.id.line_view_user_show);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		UserTwoContent content = list.get(position);
		initViews(content, holder, view, type);
		if (showLine) {
			holder.line.setVisibility(View.VISIBLE);
		}
		
		return view;
	}

	final static class ViewHolder {
		LinearLayout linearHead;

		CircleImageView headPhoto;

		TextView userName;

		TextView time;
		
		TextView title;

		TextView content;

		LinearLayout linearSound;

		ImageView imgSound;
		
		ImageView singleImage;

		KindGridView grdview;

		LinearLayout linearPoem;

		TextView poemName;

		TextView poemQuote;

		ImageView zan;

		ImageView comment;

		ImageView share;
		
		View line;
	}

	@SuppressLint("HandlerLeak")
	private void initViews(final UserTwoContent content, final ViewHolder holder, View view, int type) {
		// 头像路径
		PictureLoad.getInstance().loadImage(headPath, holder.headPhoto);
		// 用户昵称
		holder.userName.setText(content.getName());
		// 时间
		holder.time.setText(content.getTime());

		// 点赞点击事件
		if (content.getZan() != null) {
			if (content.getZan().equals("0")) {
				holder.zan.setImageResource(R.drawable.a7r);
			} else if (content.getZan().equals("1")){
				holder.zan.setImageResource(R.drawable.a7u);
			}
		}
		
		holder.zan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (content.getZan() != null) {
					if (content.getZan().equals("0")) {
						holder.zan.setImageResource(R.drawable.a7u);
						content.setZan("1");
						sendZan(UserId.getInstance(mContext).getUserId(), content.getPost_com_pId() + "", "1", content.getPost_com_num() + "");
					} else if (content.getZan().equals("1")){
						holder.zan.setImageResource(R.drawable.a7r);
						content.setZan("0");
						sendZan(UserId.getInstance(mContext).getUserId(), content.getPost_com_pId() + "", "0", content.getPost_com_num() + "");
					}
				}
			}
		});

		// 评论点击事件
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 分享点击时间
		holder.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		switch (type) {
		case VAULE_THEME_M:
			//内容
			String[] tokens = content.getContent().split("#");
			String contentT = "";
			for (String e: tokens) {
				if (!e.equals("")) {
					contentT += e + "\n";
				}
			}
			holder.content.setText(contentT);
			//初始化图片
			initGridView(content.getPicture(), holder);
			//诗词
			holder.linearPoem.setVisibility(View.GONE);
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
							content.getHeadPath());
				}
			});
			break;
			
		case VALUE_THEME_S:
			//内容
			String[] tokens_ts = content.getContent().split("#");
			String contentT_ts = "";
			for (String e: tokens_ts) {
				if (!e.equals("")) {
					contentT_ts += e + "\n";
				}
			}
			holder.content.setText(contentT_ts);
			//初始化图片
			holder.singleImage.setVisibility(View.VISIBLE);
			PictureLoad.getInstance().loadImage(content.getPicture().get(0).getPath(), holder.singleImage);
			//诗词
			holder.linearPoem.setVisibility(View.GONE);
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
							content.getHeadPath());
				}
			});
			break;
			
		case VALUE_NOTE:
			//内容
			String[] tokens_note = content.getContent().split("#");
			String contentT_note = "";
			for (String e: tokens_note) {
				if (!e.equals("")) {
					contentT_note += e + "\n";
				}
			}
			holder.content.setText(contentT_note);
			//初始化图片
			//initGridView(content.getPicture(), holder);
			//相关诗词
			holder.poemName.setText(content.getPoemName());
			holder.poemQuote.setText(content.getPoemContent());
			holder.linearPoem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getData(content.getPoemId(), holder);
				}
			});
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
							content.getHeadPath());
				}
			});
			break;
			
		case VALUE_ORIGINAL_M:
			//内容
			holder.title.setText(content.getTitle());
			String[] tokens_om = content.getContent().split("。");
			String contentT_om = "";
			for (String e: tokens_om) {
				if (!e.equals("")) {
					contentT_om += e + "\n";
				}
			}
			holder.content.setText(contentT_om);
			//初始化图片
			initGridView(content.getPicture(), holder);
			holder.linearPoem.setVisibility(View.GONE);
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
							content.getHeadPath());
				}
			});
			break;
			
		case VALUE_ORIGINAL_S:
			//内容
			holder.title.setText(content.getTitle());
			String[] tokens_os = content.getContent().split("。");
			String contentT_os = "";
			for (String e: tokens_os) {
				if (!e.equals("")) {
					contentT_os += e + "\n";
				}
			}
			holder.content.setText(contentT_os);
			//初始化图片
			holder.singleImage.setVisibility(View.VISIBLE);
			PictureLoad.getInstance().loadImage(content.getPicture().get(0).getPath(), holder.singleImage);
			holder.linearPoem.setVisibility(View.GONE);
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
							content.getHeadPath());
				}
			});
			break;
			
		case VALUE_AUDIO:
			holder.content.setVisibility(View.GONE);
			
			holder.linearSound.setVisibility(View.VISIBLE);
			
			holder.linearSound.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			//相关诗词
			holder.poemName.setText(content.getPoemName());
			holder.poemQuote.setText(content.getPoemContent());
			holder.linearPoem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getData(content.getPoemId(), holder);
				}
			});
			
			content.getSoundPath().setPlay("1");
			holder.linearSound.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.v("Audio--", "http://1696824u8f.51mypc.cn:12755/receive%20audio/---" + content.getSoundPath());
					//-----此次播放结束---第一次播放
					if (content.getSoundPath().getPlay().equals("1")) {
						content.getSoundPath().setPlay("2");
						holder.imgSound.setImageResource(R.drawable.animation_audio);
						animationDrawable = (AnimationDrawable) holder.imgSound.getDrawable();
						new Thread(new Runnable() {

							@Override
							public void run() {
								player.playUrl(MyClient.actionUrlMT + "receive%20audio/"
										+ content.getSoundPath().getPath(), new StartPlayer() {

											@Override
											public void startAudio() {
												handler.sendEmptyMessage(0);

											}
										});

								player.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

									@Override
									public void onCompletion(MediaPlayer mp) {

										handler.sendEmptyMessage(1);
									}
								});
							}
						}).start();
					} 
					//------播放过程中
					else if (content.getSoundPath().getPlay().equals("2")) {
						animationDrawable.stop();
						content.getSoundPath().setPlay("3");
						player.pause();
					} 
					//------停止播放
					else if (content.getSoundPath().getPlay().equals("3")) {
						animationDrawable.start();
						content.getSoundPath().setPlay("2");
						player.play();
					}
				}
			});

			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 0:
						animationDrawable.start();
						content.getSoundPath().setPlay("2");
						Log.v("StartPlay", "-----start");
						break;

					case 1:
						animationDrawable.stop();
						holder.imgSound.setImageResource(R.drawable.sound_three);
						content.getSoundPath().setPlay("1");
						Log.v("edn", "-----end");
						break;
					}
				}
			};
			break;
		}
	}
	
	private void initGridView(ArrayList<FileImgs> pictures, ViewHolder holder) {
		holder.grdview.setNumColumns(3);
		if (pictures.size() == 0 || pictures == null) {
			holder.grdview.setVisibility(View.GONE);
		} else {
			TopicGridviewAdapter adapter = new TopicGridviewAdapter(mContext, pictures);
			holder.grdview.setAdapter(adapter);
		}
	}

	private void getData(final String poem_id, final ViewHolder holder) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().http_postPoem(poem_id);
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("TEST", result);
				final List<PoemM> poemList;
				try {
					poemList = PoetryList.parsePoem(StringUtils.toJSONArray(result)).getPoemMList();
					if (poemList != null) {

						UIHelper.showPoemMDetail(mContext, poemList.get(0), 0);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		task.execute();
	}
	
	private void sendZan(final String userId, final String topicId, final String zan, final String type) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postDianZan(userId, type, topicId, zan);
			}
			
			@Override
			protected void onPostExecute(String result) {
				
			}
			
		};
		
		task.execute();
	}
	
	
	/**
	 * 根据数据源的position返回需要显示的的layout的type
	 * 
	 * type的值必须从0开始
	 * 
	 * */
	@Override
	public int getItemViewType(int position) {
		UserTwoContent content = list.get(position);
		int type = content.getType();
		return type;
	}
	
	/**
	 * 返回所有的layout的数量
	 * 
	 * */
	@Override
	public int getViewTypeCount() {
		return 6;
	}
}
