package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OriginalAdapter extends BaseAdapter {
	
	public static final int VALUE_ORIGINAL_M = 0;
	public static final int VALUE_ORIGINAL_S = 1;

	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;
	private CustomListView listview;
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新
	
	private TopicGridviewAdapter adapter;

	public OriginalAdapter(Context context, List<UserTwoContent> list, CustomListView listview) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		int type = getItemViewType(position);
		if (convertView == null) {
			view = inflater.inflate(R.layout.third_pager_one_itemlist, null);
			holder = new ViewHolder();

			holder = new ViewHolder();
			holder.linearHead = (LinearLayout) view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (CircleImageView) view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView) view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView) view.findViewById(R.id.third_pager_user_time);
			holder.title = (TextView)view.findViewById(R.id.third_pager_user_title);
			holder.content = (TextView) view.findViewById(R.id.third_pager_user_content);
			holder.singleImage = (ImageView) view.findViewById(R.id.third_pager_single_img);
			holder.grdview = (KindGridView) view.findViewById(R.id.third_pager_user_grdphoto);
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
		initViews(content, holder, type);

		return view;
	}

	final static class ViewHolder {
		LinearLayout linearHead;

		CircleImageView headPhoto;

		TextView userName;

		TextView time;
		
		TextView title;

		TextView content;

		ImageView singleImage;
		
		KindGridView grdview;

		LinearLayout linearPoem;

		TextView poemName;

		TextView poemQuote;

		ImageView zan;

		ImageView comment;

		ImageView share;
	}

	private void initViews(final UserTwoContent content, final ViewHolder holder, int type) {
		// 头像路径
		PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.headPhoto);

		// 用户昵称
		// Log.v("userName", content.getName());
		holder.userName.setText(content.getName());
		// 发表时间
		holder.time.setText(content.getTime());
		//原创标题
		if (content.getTitle() != null && !content.getTitle().equals("")) {
			holder.title.setVisibility(View.VISIBLE);
			holder.title.setText(content.getTitle());
		}
		
		// 发表内容
		String[] tokens = content.getContent().split("。");
		String contentT = "";
		for (String e: tokens) {
			if (!e.equals("")) {
				contentT += e + "\n";
			}
		}
		holder.content.setText(contentT);
		
		// 初始化图片
		switch (type) {
		case VALUE_ORIGINAL_M:
			adapter = new TopicGridviewAdapter(mContext, content.getPicture());
			holder.grdview.setAdapter(adapter);
			break;
			
		case VALUE_ORIGINAL_S:
			holder.singleImage.setVisibility(View.VISIBLE);
			PictureLoad.getInstance().loadImage(content.getPicture().get(0).getPath(), holder.singleImage);
			break;
		}
		// 相关诗词
		holder.linearPoem.setVisibility(View.GONE);

		// 跳转用户详情
		holder.linearHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (UserId.getInstance(mContext).getUserId().equals(content.getUserId())) {
					UIHelper.showMyDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
				} else {
					UIHelper.showUserDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
				}
			}
		});

		// 跳转内容
		holder.content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
						content.getHeadPath());
			}
		});

		// 点赞
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
				if (Integer.parseInt(UserId.getInstance(mContext).getUserId()) < 0) {
					Intent intent = new Intent(mContext, LogOn.class);
					mContext.startActivity(intent);
				} else {
					if (content.getZan() != null) {
						if (content.getZan().equals("0")) {
							holder.zan.setImageResource(R.drawable.a7u);
							content.setZan("1");
							sendZan(UserId.getInstance(mContext).getUserId(), content.getPost_com_pId() + "", "1");
						} else if (content.getZan().equals("1")){
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
				if (OriginalAdapter.this != null) {
					OriginalAdapter.this.notifyDataSetChanged();
				}
				listview.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				if (OriginalAdapter.this != null) {
					OriginalAdapter.this.notifyDataSetChanged();
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
				
				return MyClient.getInstance().http_postOne(userId, "3", num);
			}
			
			@Override
			protected void onPostExecute(String result) {
				List<UserTwoContent> datalist = new ArrayList<UserTwoContent>();
				try {
					if (result != null && !result.equals("")) {
						datalist = TopicList.parseThree(StringUtils.toJSONArray(result)).getTopicThree();
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
	
	private void sendZan(final String userId, final String topicId, final String zan) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postDianZan(userId, "3", topicId, zan);
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
		return 2;
	}

}
