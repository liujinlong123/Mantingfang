package com.android.mantingfang.topic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfang.third.BaseFragment;
import com.android.mantingfang.third.PictureLoad;
import com.android.mantingfang.third.TopicGridviewAdapter;
import com.android.mantingfang.third.UserTwoContent;
import com.android.mantingfang.topic.CycleViewPager.ImageCycleViewListener;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.CustomListViewTwo;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTopic extends BaseFragment {

	private View view;
	private LinearLayout linearSearch;
	private TextView linearTvShow;
	private CustomListViewTwo list;
	private MesAdapter adapterList;
	//private ThirdOneAdapter adapterList;
	private List<UserTwoContent> dataList;
	private TopicGridviewAdapter adapter;
	private static final int LOAD_DATA_FINISH = 10;// 上拉刷新
	private static final int REFRESH_DATA_FINISH = 11;// 下拉刷新
	
	/**
	 * 轮播图
	 */
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	
	private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
			"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
			"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.topic, null);
			
			configImageLoader();
			initialize();
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		linearSearch = (LinearLayout)view.findViewById(R.id.topic_linear_search);
		linearTvShow = (TextView)view.findViewById(R.id.topic_linear_tv_search);
		list = (CustomListViewTwo)view.findViewById(R.id.topic_tuijian);
		
		//话题搜索
		linearSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		getData();
	}
	
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postOne(UserId.getInstance(getContext()).getUserId(), "1", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						dataList = (List<UserTwoContent>) (TopicList.parseOne(StringUtils.toJSONArray(result), 1)
								.getTopicList());
						adapterList = new MesAdapter();
						//adapterList = new ThirdOneAdapter(getActivity(), dataList, list);
						list.setAdapter(adapterList);
						//setListViewHeightBasedOnChildren(list);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
		
		task.execute();
	}
	
	
	class MesAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.third_pager_one_itemlist, null);
				
				holder.linearHead = (LinearLayout) convertView.findViewById(R.id.third_pager_one_listhead);
				holder.headPhoto = (CircleImageView) convertView.findViewById(R.id.third_pager_user_photo);
				holder.userName = (TextView) convertView.findViewById(R.id.third_pager_user_name);
				holder.time = (TextView) convertView.findViewById(R.id.third_pager_user_time);
				holder.content = (TextView) convertView.findViewById(R.id.third_pager_user_content);
				holder.linearSound = (LinearLayout) convertView.findViewById(R.id.third_pager_sound);
				holder.imgSound = (ImageView) convertView.findViewById(R.id.third_pager_img_sound);
				holder.grdview = (KindGridView) convertView.findViewById(R.id.third_pager_user_grdphoto);
				holder.singleImage = (ImageView) convertView.findViewById(R.id.third_pager_single_img);
				holder.linearPoem = (LinearLayout) convertView.findViewById(R.id.third_pager_linearPoem);
				holder.poemName = (TextView) convertView.findViewById(R.id.third_pager_tv_poemName);
				holder.poemQuote = (TextView) convertView.findViewById(R.id.third_pager_tv_poem);
				holder.zan = (ImageView) convertView.findViewById(R.id.third_pager_one_zan);
				holder.comment = (ImageView) convertView.findViewById(R.id.third_pager_one_comment);
				holder.share = (ImageView) convertView.findViewById(R.id.third_pager_one_share);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			UserTwoContent content = dataList.get(position);
			initItemViews(content, holder, view);
			
			return convertView;
		}
		
		private void initItemViews(final UserTwoContent content, final ViewHolder holder, View view) {
			//头像
			PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.headPhoto);
			
			//用户昵称
			holder.userName.setText(content.getName());
			Log.v("user--name", content.getName() + "-----");
			
			//发表时间
			holder.time.setText(content.getTime());
			
			//发表内容
			holder.content.setText(content.getContent());
			
			//初始化图片
			if (content.getPicture() == null || content.getPicture().size() == 0) {
				holder.grdview.setVisibility(View.GONE);
			} else if (content.getPicture().size() == 1) {
				holder.singleImage.setVisibility(View.VISIBLE);
				PictureLoad.getInstance().loadImage(content.getPicture().get(0).getPath(), holder.singleImage);
			} else if (content.getPicture().size() > 1) {
				adapter = new TopicGridviewAdapter(getContext(), content.getPicture());
				holder.grdview.setAdapter(adapter);
			}
			
			//相关诗词
			holder.linearPoem.setVisibility(View.GONE);
			
			
			//跳转用户
			holder.linearHead.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					UIHelper.showUserDetail(getContext(), 0, content.getUserId(), content.getHeadPath(), content.getName());
					Log.v("useroneadapter", content.getHeadPath());
				}
			});
			
			//点赞点击事件
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
							sendZan(UserId.getInstance(getContext()).getUserId(), content.getPost_com_pId() + "", "1", content.getPost_com_num() + "");
						} else if (content.getZan().equals("1")){
							holder.zan.setImageResource(R.drawable.a7r);
							content.setZan("0");
							sendZan(UserId.getInstance(getContext()).getUserId(), content.getPost_com_pId() + "", "0", content.getPost_com_num() + "");
						}
					}
				}
			});
			
			//评论点击事件
			holder.comment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			//分享点击事件
			holder.share.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
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
		
		
	}
	
	
	class ViewHolder {
		LinearLayout linearHead;

		CircleImageView headPhoto;

		TextView userName;

		TextView time;

		TextView content;

		LinearLayout linearSound;

		ImageView imgSound;

		KindGridView grdview;
		
		ImageView singleImage;

		LinearLayout linearPoem;

		TextView poemName;

		TextView poemQuote;

		ImageView zan;

		ImageView comment;

		ImageView share;
	}
	
	private void LoadMore() {
		list.setOnItemClickListener((OnItemClickListener) this);
		list.setOnRefreshListener(new CustomListViewTwo.OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 下拉刷新
				loadData(0);
			}
		});
		list.setOnLoadListener(new CustomListViewTwo.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				// TODO 加载更多
				loadData(1);
			}
		});
	}

	/*
	 * 执行完了 上下拉刷新加载数据方法
	 */
	public void loadData(final int type) {
		new Thread() {
			@Override
			public void run() {
				switch (type) {
				case 0:// 这里是下拉刷新
					/*
					 * ye = 1; zhaobiao_xiala();
					 */
					break;
				case 1:
					// 这里是上拉刷新
					/*
					 * ye = ye + 1; zhaobiao_shangla();
					 */
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (type == 0) {// 下拉刷新
					// 通知Handler
					myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
				} else if (type == 1) {// 上拉刷新
					// 通知Handler
					myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
				}
			}
		}.start();
	}

	/*
	 * handle
	 */
	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_DATA_FINISH:
				if (new MesAdapter() != null) {
					new MesAdapter().notifyDataSetChanged();
				}
				list.onRefreshComplete(); // 下拉刷新完成
				break;
			case LOAD_DATA_FINISH:
				if (new MesAdapter() != null) {
					new MesAdapter().notifyDataSetChanged();
				}
				list.onLoadMoreComplete(); // 加载更多完成
				break;
			default:
				break;
			}
		}
	};
	
	
	
	
	//-----------------------------------------------------设置listView------------------------------------------------
	/**
	 * ScrollView -- ListView
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			
			totalHeight += listItem.getMeasuredHeight();
			/*int h = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			listItem.measure(0, h);
			totalHeight += listItem.getMeasuredHeight();*/
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	//-----------------------------------------------------设置listView------------------------------------------------
	
	
	//-----------------------------------------------------轮播图------------------------------------------------------
	@SuppressLint("NewApi")
	private void initialize() {
		
		cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);
		
		for(int i = 0; i < imageUrls.length; i ++){
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i );
			infos.add(info);
		}
		
		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(getContext(), infos.get(infos.size() - 1).getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(getContext(), infos.get(i).getUrl()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(getContext(), infos.get(0).getUrl()));
		
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		//设置轮播
		cycleViewPager.setWheel(true);

	    // 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		//设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(getContext(),
						"position-->" + info.getContent(), Toast.LENGTH_SHORT)
						.show();
			}
			
		}

	};
	
	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);		
	}
	
	//-------------------------------------------------------------轮播图--------------------------------------------------
}
