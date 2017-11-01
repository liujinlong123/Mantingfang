package com.android.mantingfang.topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.third.BaseFragment;
import com.android.mantingfang.third.FileImgs;
import com.android.mantingfang.third.ThemeAdapter;
import com.android.mantingfang.third.UserTwoContent;
import com.android.mantingfang.topic.CycleViewPager.ImageCycleViewListener;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

@SuppressLint("InflateParams")
public class FragmentTopic extends BaseFragment {
	
	public static final int VAULE_THEME_M = 0;
	public static final int VALUE_THEME_S = 1;

	private View view;
	private CustomListView list;
	private List<UserTwoContent> dataList;
	
	private List<String> SearchList;
	private ThemeAdapter themeAdapter;

	private IntentFilter intentFilter;
	private ThemeBroadcastReceiver themeReceiver;

	/**
	 * 轮播图
	 */
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	private View viewLinear;
	private View viewCycle;
	
	//ViewLinear
	private LinearLayout viewLinearLayout;
	private TextView viewLinearTv;
	private Timer t;

	private String[] imageUrls = {
			"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=338960318,3040799804&fm=26&gp=0.jpg",
			"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2650519084,56168659&fm=26&gp=0.jpg",
			"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1203797674,2083454031&fm=26&gp=0.jpg",
			"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3272581681,1253176532&fm=26&gp=0.jpg" };
	
	private String searchKey;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.topic, null);

			initViews();

			intentFilter = new IntentFilter();
			intentFilter.addAction("com.android.mantingfang.topic.ThemeBroadcast.THEMEADD");
			themeReceiver = new ThemeBroadcastReceiver();
			getActivity().registerReceiver(themeReceiver, intentFilter);

			return view;
		}

		return view;
	}

	public void addPagerTheme(UserTwoContent item) {

	}

	@SuppressLint("InflateParams")
	private void initViews() {
		list = (CustomListView) view.findViewById(R.id.topic_tuijian);
		
		viewLinear = LayoutInflater.from(getContext()).inflate(R.layout.topic_viewpager, null);
		viewLinearLayout = (LinearLayout) viewLinear.findViewById(R.id.topic_linear_search);
		viewLinearTv = (TextView) viewLinear.findViewById(R.id.topic_linear_tv_search);
		viewLinearTv.setText("大家都在搜");
		//创建定时器对象
	    t=new Timer();
	        
	    //在3秒后执行MyTask类中的run方法
	    t.schedule(new MyTask(), 0, 60000);
	    
	    
		viewLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), TopicSearch.class);
				intent.putExtra("searchKey", searchKey);
				startActivity(intent);
			}
		});
		
		//轮播图
		viewCycle = LayoutInflater.from(getContext()).inflate(R.layout.cycle_viewpager, null);
		configImageLoader();
		initialize();
		
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
						themeAdapter = new ThemeAdapter(getContext(), dataList, list);
						list.setAdapter(themeAdapter);
						list.addHeaderView(viewLinear);
						list.addHeaderView(viewCycle);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 1:
				Bundle bundle = msg.getData();
				searchKey = bundle.getString("tables");
				if (!searchKey.equals("") && searchKey != null) {
					viewLinearTv.setText("大家都在搜:" + searchKey);
				}
				break;
			}
		}
	};

	class MyTask extends TimerTask{

	    @Override
	    public void run() {
	        //每隔五分钟获取一个热搜词
	    	Message msg = new Message();
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(MyClient.actionUrl + "return_keyword.php");
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("TypeNum", "1"));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
				httpPost.setEntity(entity);
				HttpResponse httpResponse = httpClient.execute(httpPost);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					
					String response = EntityUtils.toString(httpEntity, "utf-8");
					Log.v("TESTONE", response);
					msg.what = 1;
					Bundle bundle = new Bundle();
					bundle.putString("tables", response);
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	

	class LinearAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return SearchList.size();
		}

		@Override
		public Object getItem(int position) {
			return SearchList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolderV holderV = null;
			if (convertView == null) {
				holderV = new ViewHolderV();
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_viewpager, null);
				holderV.linearSearch = (LinearLayout) convertView.findViewById(R.id.topic_linear_search);
				holderV.linearTvSearch = (TextView) convertView.findViewById(R.id.topic_linear_tv_search);

				convertView.setTag(holderV);
			} else {
				holderV = (ViewHolderV) convertView.getTag();
			}

			holderV.linearSearch.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(), TopicSearch.class);
					startActivity(intent);
				}
			});
			
			return convertView;
		}

	}

	class ViewHolderV {
		LinearLayout linearSearch;

		TextView linearTvSearch;
	}

	// -----------------------------------------------------轮播图------------------------------------------------------
	@SuppressLint("NewApi")
	private void initialize() {

		cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);

		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i);
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
		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}

	//跳转不同的游戏界面
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(getContext(), "position-->" + info.getContent(), Toast.LENGTH_SHORT).show();

				//Intent intent = new Intent(getContext(), ShowWebView.class);
				Intent intent = new Intent(getContext(), TopicGameF.class);
				intent.putExtra("picturepath", info.getUrl());
				startActivity(intent);
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

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
				.defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	// -------------------------------------------------------------轮播图--------------------------------------------------

	class ThemeBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
			String user_id = pref.getString("theme_user_id", "");
			String datatime = pref.getString("theme_datatime", "");
			String content = pref.getString("theme_content", "");
			String postId = pref.getString("theme_post_id", "");
			boolean isSave = pref.getBoolean("theme_isSave", false);

			Set<String> setPath = pref.getStringSet("theme_setPath", null);

			ArrayList<FileImgs> imgs = new ArrayList<>();
			for (String e : setPath) {
				imgs.add(new FileImgs("1", e));
			}
			/*if (isSave) {
				UserTwoContent item = new UserTwoContent(user_id, UserId.getInstance(getContext()).getHeadPath(),
						UserId.getInstance(getContext()).getNickName(), datatime, null, content, imgs, null, null, null,
						null, "0", 1, Integer.parseInt(postId), 1);
				if (adapterList == null) {
					dataList = new ArrayList<UserTwoContent>();
					dataList.add(item);
					adapterList = new MesAdapter();
					list.setAdapter(adapter);
				} else {
					dataList.add(0, item);
					adapterList.notifyDataSetChanged();
				}
			}*/
		}

	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(themeReceiver);
		t.cancel();
		super.onDestroy();
	}
}
