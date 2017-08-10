package com.android.mantingfang.third;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.Player;
import com.android.mantingfanggsc.Player.StartPlayer;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class ThirdFourAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;
	private Player player;
	private CustomListView listview;
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新

	private MediaPlayer mPlayer = null;
	private AnimationDrawable animationDrawable;
	private Handler handler;

	public ThirdFourAdapter(Context context, List<UserTwoContent> list, CustomListView listview) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
		player = new Player();
		this.listview = listview;
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

	public void setPlayerPause() {
		this.player.stop();
	}

	public void clearPlayer() {
		this.player.stop();
		this.player = null;
	}

	public boolean isPlayer() {
		return this.player == null ? true : false;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.third_pager_one_itemlist, null);
			holder = new ViewHolder();
			holder.linearHead = (LinearLayout) view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (CircleImageView) view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView) view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView) view.findViewById(R.id.third_pager_user_time);
			holder.content = (TextView) view.findViewById(R.id.third_pager_user_content);
			holder.linearSound = (LinearLayout) view.findViewById(R.id.third_pager_sound);
			holder.imgSound = (ImageView) view.findViewById(R.id.third_pager_img_sound);
			holder.linearPoem = (LinearLayout) view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView) view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView) view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView) view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView) view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView) view.findViewById(R.id.third_pager_one_share);

			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		refresh();
		UserTwoContent content = list.get(position);
		initViews(content, holder);

		return view;
	}

	final static class ViewHolder {
		LinearLayout linearHead;

		CircleImageView headPhoto;

		TextView userName;

		TextView time;

		TextView content;

		LinearLayout linearSound;

		ImageView imgSound;

		LinearLayout linearPoem;

		TextView poemName;

		TextView poemQuote;

		ImageView zan;

		ImageView comment;

		ImageView share;
	}

	@SuppressLint("HandlerLeak")
	private void initViews(final UserTwoContent content, final ViewHolder holder) {
		// 头像路径
		PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.headPhoto);
		// 用户昵称
		holder.userName.setText(content.getName());
		// 发表时间
		holder.time.setText(content.getTime());
		// 发表内容
		holder.content.setVisibility(View.GONE);

		// 播放音频
		holder.linearSound.setVisibility(View.VISIBLE);
		content.getSoundPath().setPlay("1");
		holder.linearSound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("Audio--", "http://1696824u8f.51mypc.cn:12755/receive%20audio/---" + content.getSoundPath());
				//-----此次播放结束---第一次播放
				if (content.getSoundPath().getPlay().equals("1")) {
					content.getSoundPath().setPlay("2");
					if (content.getSoundPath().getType().equals("0")) {
						holder.imgSound.setImageResource(R.drawable.animation_audio);
						animationDrawable = (AnimationDrawable) holder.imgSound.getDrawable();
						new Thread(new Runnable() {

							@Override
							public void run() {
								player.playUrl("http://1696824u8f.51mypc.cn:12755/receive%20audio/"
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
					} else if (content.getSoundPath().getType().equals("1")) {
						content.getSoundPath().setPlay("2");
						startPlayFromFile(content.getSoundPath().getPath(), holder.imgSound);
					}
				} 
				//------播放过程中
				else if (content.getSoundPath().getPlay().equals("2")) {
					animationDrawable.stop();
					content.getSoundPath().setPlay("3");
					if (content.getSoundPath().getType().equals("0")) {
						player.pause();
					} else if (content.getSoundPath().getType().equals("1")) {
						pausePlayFromFile();
					}
				} 
				//------停止播放
				else if (content.getSoundPath().getPlay().equals("3")) {
					animationDrawable.start();
					content.getSoundPath().setPlay("2");
					if (content.getSoundPath().getType().equals("0")) {
						player.play();
					} else if (content.getSoundPath().getType().equals("1")) {
						startPlay();
					}
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

		// 相关诗词
		holder.poemName.setText(content.getPoemName());
		holder.poemQuote.setText(content.getPoemContent());

		// 跳转user
		holder.linearHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showUserDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
			}
		});

		// 跳转评论页
		holder.content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "",
						content.getPostComNum() + "", content.getHeadPath());
			}
		});

		// 跳转诗词详情
		holder.linearPoem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getData(content.getPoemId(), holder);
			}
		});

		// 赞
		if (content.getZan() != null) {
			if (content.getZan().equals("0")) {
				holder.zan.setImageResource(R.drawable.a7r);
			} else if (content.getZan().equals("1")) {
				holder.zan.setImageResource(R.drawable.a7u);
			}
		}
		holder.zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Integer.parseInt(UserId.getInstance(mContext).getUserId()) < 0) {
					Intent intent = new Intent(mContext, LogOn.class);
					mContext.startActivity(intent);
				} else {
					if (content.getZan() != null) {
						if (content.getZan().equals("0")) {
							holder.zan.setImageResource(R.drawable.a7u);
							content.setZan("1");
							sendZan(UserId.getInstance(mContext).getUserId(), content.getPost_com_pId() + "", "1");
						} else if (content.getZan().equals("1")) {
							holder.zan.setImageResource(R.drawable.a7r);
							content.setZan("0");
							sendZan(UserId.getInstance(mContext).getUserId(), content.getPost_com_pId() + "", "0");
						}
					}
				}
			}
		});

		// 评论
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showComment(mContext, 0, content.getPostComPId() + "", content.getPostComNum() + "");
			}
		});

		// 分享
		holder.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
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

	private void refresh() {
		listview.setOnRefreshListener(new CustomListView.OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				loadData(0);
			}
		});

		listview.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO 下拉刷新
				loadData(1);
			}
		});
	}

	/**
	 * 执行完了 上下拉刷新加载数据方法
	 * 
	 * @param i
	 */
	protected void loadData(final int type) {
		new Thread() {
			@Override
			public void run() {
				switch (type) {
				case 0:
					getData(UserId.getInstance(mContext).getUserId(), "0");
					break;

				case 1:
					getData(UserId.getInstance(mContext).getUserId(), list.size() + "");
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (type == 0) { // 下拉刷新
					myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
				} else if (type == 1) { // 上拉刷新
					myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
				}
			};
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_DATA_FINISH:
				if (ThirdFourAdapter.this != null) {
					ThirdFourAdapter.this.notifyDataSetChanged();
				}
				listview.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				if (ThirdFourAdapter.this != null) {
					ThirdFourAdapter.this.notifyDataSetChanged();
				}
				listview.onLoadMoreComplete(); // 加载更多完成
				break;
			default:
				break;
			}
		};
	};

	private void getData(final String userId, final String num) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().http_postOne(userId, "4", num);
			}

			@Override
			protected void onPostExecute(String result) {
				List<UserTwoContent> datalist = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("")) {
						datalist = TopicList.parseFour(StringUtils.toJSONArray(result)).getTopicFour();
						if (Integer.parseInt(num) > 0) {
							for (UserTwoContent e : datalist) {
								list.add(e);
							}
						} else if (Integer.parseInt(num) == 0) {
							list = datalist;
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		};

		task.execute();
	}

	private void sendZan(final String userId, final String topicId, final String zan) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().Http_postDianZan(userId, "4", topicId, zan);
			}

			@Override
			protected void onPostExecute(String result) {

			}

		};

		task.execute();
	}

	private void startPlayFromFile(String fileName, final ImageView imgSound) {
		mPlayer = new MediaPlayer();
		Toast.makeText(mContext, "播放录音", Toast.LENGTH_SHORT).show();
		imgSound.setImageResource(R.drawable.animation_audio);
		animationDrawable = (AnimationDrawable) imgSound.getDrawable();
		try {
			mPlayer.setDataSource(fileName);
			mPlayer.prepare();
			mPlayer.start();
			animationDrawable.start();
			mPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					animationDrawable.stop();
					imgSound.setImageResource(R.drawable.sound_three);
				}
			});
		} catch (IOException e) {
			Log.e("----", "播放失败");
		}
	}

	private void stopPlayFromFile() {
		mPlayer.release();
		mPlayer = null;
		Toast.makeText(mContext, "停止播放", Toast.LENGTH_SHORT).show();
	}
	
	private void pausePlayFromFile() {
		mPlayer.pause();
	}

	private void startPlay() {
		mPlayer.start();
	}
}
