package com.android.mantingfang.third;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommentMain extends Activity {
	
	private View viewOne;
	private View viewTwo;
	private View viewThree;
	private View viewFour;
	
	private LinearLayout linearBack;
	private TextView theme_bg;
	
	
	private ListView listviewCopy;
	private ListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;

	private TextView tvBetter;
	
	private ListView listOne;
	private CommentAdapter adapterOne;
	private List<CommentContent> dataListOne;
	
	private TextView tvNew;

	private ListView listTwo;
	private CommentAdapter adapterTwo;
	private List<CommentContent> dataListTwo;
	
	
	
	private String headPath;
	private UserTwoContent content;
	private EditText editer;
	private Button btnSend;
	private String bePostId;
	private String bePostContent;
	private String bePostUserId;
	private String bePostUserName;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		Bundle bundle = getIntent().getExtras();
		content = (UserTwoContent) bundle.get("commentM");
		headPath = bundle.getString("headPath");
		viewOne = LayoutInflater.from(CommentMain.this).inflate(R.layout.comment_one, null);
		viewTwo = LayoutInflater.from(CommentMain.this).inflate(R.layout.comment_two, null);
		viewThree = LayoutInflater.from(CommentMain.this).inflate(R.layout.comment_three, null);
		viewFour = LayoutInflater.from(CommentMain.this).inflate(R.layout.comment_four, null);
		
		initViews(content, bundle.getString("topicId"), bundle.getString("typeNum"));
	}
	
	private void initViews(UserTwoContent content, final String topicId, final String typeNum) {
		listview = (ListView)viewOne.findViewById(R.id.comment_main_content);
		tvBetter = (TextView)viewTwo.findViewById(R.id.comment_tv_better);
		listOne = (ListView)viewThree.findViewById(R.id.comment_better);
		tvNew = (TextView)viewFour.findViewById(R.id.comment_tv_new);
		
		
		listviewCopy = (ListView)findViewById(R.id.comment_main_content_one);
		listTwo = (ListView)findViewById(R.id.comment_new);
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme_bg = (TextView)findViewById(R.id.topbar_tv_theme);
		editer = (EditText)findViewById(R.id.post_comment);
		btnSend = (Button)findViewById(R.id.post_comment_btn);
		
		list = new ArrayList<>();
		list.add(content);
		adapter = new UserTwoAdapter(CommentMain.this, list, headPath);
		listview.setAdapter(adapter);
		
		getData(topicId, typeNum, headPath);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		theme_bg.setText("帖子");
		
		//消息发送
		btnSend.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(UserId.getInstance(CommentMain.this).getUserId()) < 0) {
					Intent intent = new Intent(CommentMain.this, LogOn.class);
					startActivity(intent);
				} else {
					if (editer.getText().toString() != null && !editer.getText().toString().equals("")) {
						Date d = new Date();
						d.setHours(d.getHours());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dateNowStr = sdf.format(d); // 当前时间
						
						Map<String, String> params = new HashMap<>();
						params.put("userId", UserId.getInstance(CommentMain.this).getUserId());
						params.put("post_id", topicId);
						params.put("typeNum", typeNum);
						params.put("content", editer.getText().toString());
						params.put("time", dateNowStr);
						if (bePostId != null &&! bePostId.equals("")) {
							params.put("bePostId", bePostId);
							params.put("bePostContent", bePostContent);
							params.put("bePostUserId", bePostUserId);
							params.put("bePostUserName", bePostUserName);
							bePostId = null;
							bePostContent = null;
							bePostUserId = null;
							bePostUserName = null;
						} else {
							params.put("bePostId", "-1");
						}
						sendData(params);
					}
				}
				
			}
		});
	}
	
	private void sendData(final Map<String, String> param) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				MyClient.getInstance().Http_SendComment(param);
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				editer.setText("");
				editer.setHint("");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
				CommentContent item = new CommentContent(param.get("post_id"), param.get("typeNum"), UserId.getInstance(CommentMain.this).getUserId(),
						UserId.getInstance(CommentMain.this).getHeadPath(), UserId.getInstance(CommentMain.this).getNickName(), param.get("time"),
						param.get("content"), "0", "0", param.get("bePostContent"), param.get("bePostUserId"), param.get("bePostUserName"), result, param.get("bePostId"));
				
				//Log.v("bePostedId", param.get("bePostId") + "+++++");
				if (dataListTwo == null) {
					listviewCopy.setVisibility(View.GONE);
					adapter = new UserTwoAdapter(CommentMain.this, list, headPath);
					listview.setAdapter(adapter);
					listTwo.addHeaderView(viewOne);
					listTwo.addHeaderView(viewFour);
					
					tvNew.setVisibility(View.VISIBLE);
					dataListTwo = new ArrayList<>();
					dataListTwo.add(item);
					adapterTwo = new CommentAdapter(CommentMain.this, dataListTwo);
					listTwo.setAdapter(adapterTwo);
					listTwo.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
							new AlertDialog.Builder(CommentMain.this)
							.setItems(R.array.item_huifu, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichcountry) {
									switch (whichcountry) {
									case 0:
										bePostId = dataListTwo.get(position - 2).getPostId();
										bePostContent= dataListTwo.get(position - 2).getContent();
										bePostUserId = dataListTwo.get(position - 2).getUserId();
										bePostUserName = dataListTwo.get(position - 2).getName();
										editer.setHint("@" + dataListTwo.get(position - 2).getName());
										break;
									}
									
								}
							}).show();
						}
					});
				} else {
					dataListTwo.add(0, item);
					Log.v("ITEM---item", item.getBePostId());
					adapterTwo.notifyDataSetChanged();
				}
			}

		};

		task.execute();
	}
	
	private void getData(final String topicId, final String typeNum, final String headPath) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				//最新评论
				return MyClient.getInstance().Http_postComment(UserId.getInstance(CommentMain.this).getUserId(), typeNum, topicId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				try {
					if (result != null && !result.equals("") && !result.equals("[]")) {
						dataListTwo = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
						
						getDataTwo(topicId, typeNum, headPath);
					} else {
						adapter = new UserTwoAdapter(CommentMain.this, list, headPath);
						listviewCopy.setVisibility(View.VISIBLE);
						listviewCopy.setAdapter(adapter);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	
	private void getDataTwo(final String topicId, final String typeNum, final String headPath) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				//精彩评论
				return MyClient.getInstance().Http_postCommentBetter(UserId.getInstance(CommentMain.this).getUserId(), typeNum, topicId);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result == null || result.equals("") || result.equals("[]")) {
					adapter = new UserTwoAdapter(CommentMain.this, list, headPath);
					listview.setAdapter(adapter);
					listTwo.addHeaderView(viewOne);
					listTwo.addHeaderView(viewFour);
					
					tvNew.setVisibility(View.VISIBLE);
					adapterTwo = new CommentAdapter(CommentMain.this, dataListTwo);
					listTwo.setAdapter(adapterTwo);
					
					listTwo.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
							new AlertDialog.Builder(CommentMain.this)
							.setItems(R.array.item_huifu, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichcountry) {
									switch (whichcountry) {
									case 0:
										bePostId = dataListTwo.get(position - 2).getPostId();
										bePostContent= dataListTwo.get(position - 2).getContent();
										bePostUserId = dataListTwo.get(position - 2).getUserId();
										bePostUserName = dataListTwo.get(position - 2).getName();
										editer.setHint("@" + dataListTwo.get(position - 2).getName());
										break;
									}
									
								}
							}).show();
						}
					});
				} else {
					try {
						tvBetter.setVisibility(View.VISIBLE);
						dataListOne = TopicList.parseComment(StringUtils.toJSONArray(result), topicId, typeNum).getCommentList();
						
						adapter = new UserTwoAdapter(CommentMain.this, list, headPath);
						listview.setAdapter(adapter);
						
						listTwo.addHeaderView(viewOne);
						listTwo.addHeaderView(viewTwo);
						listTwo.addHeaderView(viewThree);
						listTwo.addHeaderView(viewFour);
						
						
						adapterOne = new CommentAdapter(CommentMain.this, dataListOne);
						listOne.setAdapter(adapterOne);
						
						tvNew.setVisibility(View.VISIBLE);
						adapterTwo = new CommentAdapter(CommentMain.this, dataListTwo);
						listTwo.setAdapter(adapterTwo);
						
						listOne.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
								new AlertDialog.Builder(CommentMain.this)
								.setItems(R.array.item_huifu, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichcountry) {
										switch (whichcountry) {
										case 0:
											bePostId = dataListOne.get(position).getPostId();
											bePostContent= dataListOne.get(position).getContent();
											bePostUserId = dataListOne.get(position).getUserId();
											bePostUserName = dataListOne.get(position).getName();
											editer.setHint("@" + dataListOne.get(position).getName());
											break;
										}
										
									}
								}).show();
							}
						});
						
						listTwo.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
								new AlertDialog.Builder(CommentMain.this)
								.setItems(R.array.item_huifu, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichcountry) {
										switch (whichcountry) {
										case 0:
											bePostId = dataListTwo.get(position - 4).getPostId();
											bePostContent= dataListTwo.get(position - 4).getContent();
											bePostUserId = dataListTwo.get(position - 4).getUserId();
											bePostUserName = dataListTwo.get(position - 4).getName();
											editer.setHint("@" + dataListTwo.get(position - 4).getName());
											break;
										}
										
									}
								}).show();
							}
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		task.execute();
	}
	
	class UserTwoAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;
		private List<UserTwoContent> list;

		public UserTwoAdapter(Context context, List<UserTwoContent> list, String headPath) {
			this.mContext = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
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
			//holder.headPhoto.setImageBitmap(bitmap);
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
					if (Integer.parseInt(UserId.getInstance(mContext).getUserId()) < 0) {
						Intent intent = new Intent(mContext, LogOn.class);
						startActivity(intent);
					} else {
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
	}
}
