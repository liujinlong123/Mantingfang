package com.android.mantingfang.topic;

import java.util.List;

import com.android.mantingfang.third.PictureLoad;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.CustomListView.OnLoadMoreListener;
import com.android.mantingfanggsc.CustomListView.OnRefreshListener;
import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicGameFAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater inflater;
	private List<TopicGameFContent> list;
	private CustomListView listview;
	private static final int LOAD_DATA_FINISH = 10; //上拉刷新
	private static final int REFERSH_DATA_FINISH = 11; //下拉刷新
	
	public TopicGameFAdapter(Context context, List<TopicGameFContent> list, CustomListView listview) {
		this.list = list;
		this.mContext = context;
		this.listview = listview;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list == null? 0:list.size();
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
		if (convertView == null) {
			view = inflater.inflate(R.layout.topic_game_item, null);
			holder = new ViewHolder();
			holder.rankNumber = (TextView)view.findViewById(R.id.number_rank);
			holder.imgHead = (CircleImageView)view.findViewById(R.id.topic_game_item_userPhoto);
			holder.userName = (TextView)view.findViewById(R.id.topic_game_item_name);
			holder.victoryRate = (TextView)view.findViewById(R.id.topic_game_item_rate);
			holder.heart = (ImageView)view.findViewById(R.id.topic_game_item_collect);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		refresh();
		TopicGameFContent content = list.get(position);
		initViews(content, holder, position);
		
		return view;
	}
	
	class ViewHolder {
		TextView rankNumber;
		
		CircleImageView imgHead;
		
		TextView userName;
		
		TextView victoryRate;
		
		ImageView heart;
	}

	private void initViews(TopicGameFContent content, ViewHolder holder, int position) {
		//头像
		PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.imgHead);
		
		//序号
		if (position <= 2) {
			holder.rankNumber.setText((position + 1) + "");
		} else {
			holder.rankNumber.setText((position + 1) + "");
			holder.rankNumber.setTextColor(mContext.getResources().getColor(R.color.blue));
			holder.rankNumber.setTextSize(25);
		}
		
		//用户名
		holder.userName.setText(content.getUserName());
		
		//胜率
		holder.victoryRate.setText(content.getVictoryRate());
		
		//收藏
		if (content.getCollect().equals("1")) {
			holder.heart.setImageResource(R.drawable.heart_on);
		} else {
			holder.heart.setImageResource(R.drawable.heart_off);
		}
	}
	
	private void refresh() {
		listview.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// 下拉刷新
				loadData(0);
			}
		});
		
		listview.setOnLoadListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// 上拉刷新
				loadData(1);
			}
		});
	}
	
	protected void loadData(final int type) {
		new Thread() {
			@SuppressLint("HandlerLeak")
			@Override
			public void run() {
				switch(type) {
				case 0:
					
					
					break;
					
				case 1:
					
					break;
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if (type == 0) {
					myHandler.sendEmptyMessage(REFERSH_DATA_FINISH);
				} else if (type == 1) {
					myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
				}
			}
		}.start();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFERSH_DATA_FINISH:
				if (TopicGameFAdapter.this != null) {
					TopicGameFAdapter.this.notifyDataSetChanged();
				}
				listview.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				if (TopicGameFAdapter.this != null) {
					TopicGameFAdapter.this.notifyDataSetChanged();
				}
				listview.onLoadMoreComplete(); // 加载更多完成
				break;
			default:
				break;
			};
		};
	};
}
