package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.ImageLoad;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentMain extends Activity {
	
	private LinearLayout linearBack;
	private TextView theme_bg;
	
	private ListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;

	private ListView listOne;
	private CommentAdapter adapterOne;
	private List<CommentContent> dataListOne;
	
	private ListView listTv;
	private CommentTextAdapter adapterTv;
	private List<String> dataListTv;

	private ListView listTwo;
	private CommentAdapter adapterTwo;
	private List<CommentContent> dataListTwo;
	private Bitmap bitmap;
	private String headPath;
	private UserTwoContent content;

	//private EditText editer;
	//private Button btnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Bundle bundle = getIntent().getExtras();
		content = (UserTwoContent) bundle.get("commentM");
		headPath = bundle.getString("headPath");
		getImage(headPath, bundle);
	}
	
	private void initViews(UserTwoContent content, String topicId, String typeNum) {
		listview = (ListView)findViewById(R.id.comment_main_content);
		listOne = (ListView)findViewById(R.id.comment_better);
		listTwo = (ListView)findViewById(R.id.comment_new);
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme_bg = (TextView)findViewById(R.id.topbar_tv_theme);
		
		list = new ArrayList<>();
		list.add(content);
		adapter = new UserTwoAdapter(CommentMain.this, list, bitmap);
		listview.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listview);
		
		listTv = (ListView)findViewById(R.id.comment_tv_better);
		dataListTv = new ArrayList<>();
		dataListTv.add("精彩评论");
		adapterTv = new CommentTextAdapter(CommentMain.this, dataListTv);
		listTv.setAdapter(adapterTv);
		listTv.setEnabled(false);
		
		getData(topicId, typeNum, bitmap);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		theme_bg.setText("帖子");
	}
	
	private void getData(final String topicId, final String typeNum, final Bitmap bitmap) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postComment(typeNum, topicId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				dataListOne = new ArrayList<>();
				try {
					dataListOne = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
					dataListTwo = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
					adapter = new UserTwoAdapter(CommentMain.this, list, bitmap);
					listview.setAdapter(adapter);
					setListViewHeightBasedOnChildren(listview);
					
					
					adapterOne = new CommentAdapter(CommentMain.this, dataListOne);
					listOne.setAdapter(adapterOne);
					setListViewHeightBasedOnChildren(listOne);
					
					adapterTwo = new CommentAdapter(CommentMain.this, dataListTwo);
					listTwo.setAdapter(adapterTwo);
					setListViewHeightBasedOnChildren(listTwo);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	/**
	 * 获取图片
	 * @param path
	 */
	private void getImage(final String path, final Bundle bundle) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				Map<String, String> param = new HashMap<>();
				param.put("path", path);
				bitmap = ImageLoad.upload("http://1696824u8f.51mypc.cn:12755//sendpicture.php", param);
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				initViews(content, bundle.getString("topicId"), bundle.getString("typeNum"));
			}
			
		};
		
		task.execute();
	}

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
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	class UserTwoAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		private List<UserTwoContent> list;
		private Bitmap bitmap;

		public UserTwoAdapter(Context context, List<UserTwoContent> list, Bitmap bitmap) {
			this.mContext = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
			this.bitmap = bitmap;
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

			UserTwoContent content = list.get(position);
			initViews(content, holder, view);

			return view;
		}

		final class ViewHolder {
			LinearLayout linearHead;

			CircleImageView headPhoto;

			TextView userName;

			TextView time;

			TextView content;

			LinearLayout linearSound;

			ImageView imgSound;

			KindGridView grdview;

			LinearLayout linearPoem;

			TextView poemName;

			TextView poemQuote;

			ImageView zan;

			ImageView comment;

			ImageView share;
		}

		private void initViews(final UserTwoContent content, final ViewHolder holder, View view) {
			// 头像路径
			holder.headPhoto.setImageBitmap(bitmap);
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
			
			if (content.getPostComNum() == 1) {
				//内容
				holder.content.setText(content.getContent());
				//初始化图片
				initGridView(content.getPicture(), holder);
				//诗词
				//holder.poemName.setText(content.getPoemName());
				//holder.poemQuote.setText(content.getPoemContent());
				holder.linearPoem.setVisibility(View.GONE);
				holder.content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						/*UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
								content.getHeadPath());*/
					}
				});
			} 
			else if (content.getPostComNum() == 2) {
				//内容
				holder.content.setText(content.getContent());
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
						/*UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
								content.getHeadPath());*/
					}
				});
			} 
			else if (content.getPostComNum() == 3) {
				//内容
				holder.content.setText(content.getContent());
				//初始化图片
				initGridView(content.getPicture(), holder);
				holder.linearPoem.setVisibility(View.GONE);
				holder.content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						/*UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
								content.getHeadPath());*/
					}
				});
			} 
			else if (content.getPostComNum() == 4) {
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
			}
		}
		
		private void initGridView(ArrayList<String> pictures, ViewHolder holder) {
			holder.grdview.setNumColumns(3);
			if (pictures.size() == 0 || pictures == null) {
				holder.grdview.setVisibility(View.GONE);
			} else {
				Log.v("PIcture", pictures.toString());
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
	}
}
