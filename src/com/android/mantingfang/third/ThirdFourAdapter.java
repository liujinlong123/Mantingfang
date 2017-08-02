package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.Player;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThirdFourAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;
	private Player player;
	private CustomListView listview;
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新
	
	
	public ThirdFourAdapter(Context context, List<UserTwoContent> list, CustomListView listview) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
		player = new Player();
		this.listview = listview;
	}
	
	@Override
	public int getCount() {
		return list == null? 0 : list.size();
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.third_pager_one_itemlist, null);
			holder = new ViewHolder();
			holder.linearHead = (LinearLayout)view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (CircleImageView)view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView)view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView)view.findViewById(R.id.third_pager_user_time);
			holder.content = (TextView)view.findViewById(R.id.third_pager_user_content);
			holder.linearSound = (LinearLayout)view.findViewById(R.id.third_pager_sound);
			holder.imgSound = (ImageView)view.findViewById(R.id.third_pager_img_sound);
			holder.linearPoem = (LinearLayout)view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView)view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView)view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView)view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView)view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView)view.findViewById(R.id.third_pager_one_share);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
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

	private void initViews(final UserTwoContent content, final ViewHolder holder) {
		//头像路径
		PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.headPhoto);
		//用户昵称
		holder.userName.setText(content.getName());
		//发表时间
		holder.time.setText(content.getTime());
		//发表内容
		holder.content.setVisibility(View.GONE);
		
		//播放音频
		holder.linearSound.setVisibility(View.VISIBLE);
		holder.linearSound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v("Audio--", "http://1696824u8f.51mypc.cn:12755/receive%20audio/---" + content.getSoundPath());
				new Thread(new Runnable() {

					@Override
					public void run() {
						player.playUrl("http://1696824u8f.51mypc.cn:12755/receive%20audio/" + content.getSoundPath());
					}
				}).start();
			}
		});
		
		//相关诗词
		holder.poemName.setText(content.getPoemName());
		holder.poemQuote.setText(content.getPoemContent());
		
		//跳转user
		holder.linearHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showUserDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
			}
		});
		
		//跳转评论页
		holder.content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
						content.getHeadPath());
			}
		});
		
		//跳转诗词详情
		holder.linearPoem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(content.getPoemId(), holder);
			}
		});
		
		//赞
		holder.zan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//评论
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showComment(mContext, 0, content.getPostComPId() + "", content.getPostComNum() + "");
			}
		});
		
		//分享
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
	 * @param i
	 */
	protected void loadData(final int type) {
		new Thread() {
			@Override
			public void run() {
				switch (type) {
				case 0:
					getData("0");
					break;
					
				case 1:
					getData(list.size() + "");
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (type == 0) {	//下拉刷新
					myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
				} else if (type == 1) {	//上拉刷新
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
	
	private void getData(final String num) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne("2", num);
			}
			
			@Override
			protected void onPostExecute(String result) {
				List<UserTwoContent> datalist = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("")) {
						datalist = TopicList.parseFour(StringUtils.toJSONArray(result)).getTopicFour();
						if (Integer.parseInt(num) > 0) {
							for (UserTwoContent e: datalist) {
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
}
