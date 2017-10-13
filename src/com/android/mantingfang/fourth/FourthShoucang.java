package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfanggsc.CustomSwipeListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;
import com.android.mantingfanggsc.SwipeItemView;
import com.android.mantingfanggsc.UIHelper;
import com.android.mantingfanggsc.SwipeItemView.OnSlideListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class FourthShoucang extends Activity {
	
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private RadioGroup rgpTitle;
	private RadioButton left;
	private RadioButton right;
	private ListAdapter adapter1;
	private ListAdapter adapter2;
	private CustomSwipeListView listview1;
	private CustomSwipeListView listview2;
	private SwipeItemView mLastSlideViewWithStatusOn;
	private List<ShoucangContent> dataListOne = new ArrayList<>();
	private List<ShoucangContent> dataListTwo = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_shoucang);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setVisibility(View.GONE);
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		
		rgpTitle = (RadioGroup)findViewById(R.id.topbar_radgp_select);
		rgpTitle.setVisibility(View.VISIBLE);
		
		initViews();
	}
	
	private void initViews() {
		listview1 = (CustomSwipeListView)findViewById(R.id.shoucang_left_list);
		listview2 = (CustomSwipeListView)findViewById(R.id.shoucang_right_list);
		left = (RadioButton)findViewById(R.id.topbar_rbtn_selectL);
		right = (RadioButton)findViewById(R.id.topbar_rbtn_selectR);
		/*left.setOnClickListener(new MyOnClickListener(0));
		right.setOnClickListener(new MyOnClickListener(1));*/
		left.setText("卡片");
		right.setText("诗单");
		
		getData();
	}
	
	class ShoucangContent {
		private String poemId;
		private String poemName;
		private String poemRhesis;
		private String dynasty;
		private String writer;
		
		public ShoucangContent() {}
		
		public ShoucangContent(String poemId, String poemName, String poemRhesis, String dynasty, String writer) {
			this.poemId = poemId;
			this.poemName = poemName;
			this.poemRhesis = poemRhesis;
			this.dynasty = dynasty;
			this.writer = writer;
		}
		
		public String getPoemId() {
			return poemId;
		}
		
		public String getPoemName() {
			return poemName;
		}
		
		public String getPoemRhesis() {
			return poemRhesis;
		}
		
		public String getDynasty() {
			return dynasty;
		}
		
		public String getWriter() {
			return writer;
		}
	}
	
	class ListAdapter extends BaseAdapter {
		private List<ShoucangContent> dataList;
		
		public ListAdapter(List<ShoucangContent> dataList) {
			this.dataList = dataList;
		}

		@Override
		public int getCount() {
			return dataList == null? 0: dataList.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			SwipeItemView slideView = (SwipeItemView) convertView;
			if (slideView == null) {
				View itemView = LayoutInflater.from(FourthShoucang.this).inflate(R.layout.poem_item, null);
				
				slideView = new SwipeItemView(FourthShoucang.this);
				slideView.setContentView(itemView);
				
				holder = new ViewHolder(slideView);
				slideView.setOnSlideListener(new OnSlideListener() {
					
					@Override
					public void onSlide(View view, int status) {
						 if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
					            mLastSlideViewWithStatusOn.shrink();
					        }

					        if (status == SLIDE_STATUS_ON) {
					            mLastSlideViewWithStatusOn = (SwipeItemView) view;
					        }
					}
				});
	            slideView.setTag(holder);
			} else {
				holder = (ViewHolder)slideView.getTag();
			}
			
			//ShoucangContent content = dataList.get(position);
			if(CustomSwipeListView.mFocusedItemView!=null){
		        CustomSwipeListView.mFocusedItemView.shrink();
		        }
			
			ShoucangContent content = dataList.get(position);
			holder.poemName.setText(content.getPoemName());
			holder.poemRhesis.setText(content.getPoemRhesis());
			
			if (!content.getWriter().equals("") && content.getWriter() != null) {
				holder.writer.setText(content.getWriter());
			} else {
				holder.writer.setText("佚名");
			}
			
			holder.deleteHolder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					sendCollection(dataList.get(position).getPoemId());
					dataList.remove(position);
					notifyDataSetChanged();
					
				}
			});
			
			return slideView;
		}
		
		
	}
	
	private static class ViewHolder {
		public TextView poemName;
		public TextView poemRhesis;
		public TextView writer;
		
		public ViewGroup deleteHolder;
		
		ViewHolder (View view) {
			poemName = (TextView)view.findViewById(R.id.poem_item_name);
			poemRhesis = (TextView)view.findViewById(R.id.poem_item_poemrhesis);
			writer = (TextView)view.findViewById(R.id.poem_item_writer);
			
			deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
		}
	}
	
	
	private void getData() {

		//final String url = "http://zhaobiao.zhaohuake.com/androidTender?page=1&pageSize=5";

		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(FourthShoucang.this,
						"正在加载", SuccinctProgress.THEME_LINE, false, true);
			}
			
			@Override
			protected String doInBackground(String... pp) {
				return MyClient.getInstance().Http_Collection (UserId.getInstance(FourthShoucang.this).getUserId(), "0");
			}

			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				if (result != null && !result.equals("")) {
					try {
						parse(StringUtils.toJSONArray(result));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					adapter1 = new ListAdapter(dataListOne);
					listview1.setAdapter(adapter1);
				}
				
				left.setChecked(true);
				rgpTitle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.topbar_rbtn_selectL:
							listview1.setVisibility(View.VISIBLE);
							listview2.setVisibility(View.GONE);
							break;
							
						case R.id.topbar_rbtn_selectR:
							listview1.setVisibility(View.GONE);
							listview2.setVisibility(View.VISIBLE);
							break;
						}
					}
				});
				
				listview1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						UIHelper.showPoemMDetailTwoById(FourthShoucang.this, dataListOne.get(position - 1).getPoemId(), 0);
					}
				});
				
				getDataTwo();
			}
		};
		// 执行任务
		task.execute();
	}
	
	private void getDataTwo() {

		//final String url = "http://zhaobiao.zhaohuake.com/androidTender?page=1&pageSize=5";

		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... pp) {
				return MyClient.getInstance().Http_Collection (UserId.getInstance(FourthShoucang.this).getUserId(), "1");
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						parseTwo(StringUtils.toJSONArray(result));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					adapter2 = new ListAdapter(dataListTwo);
					listview2.setAdapter(adapter2);
				}
				
				listview2.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						UIHelper.showPoemMDetailTwoById(FourthShoucang.this, dataListTwo.get(position - 1).getPoemId(), 0);
					}
				});
			}
		};
		// 执行任务
		task.execute();
	}
	
	private void parse(JSONArray obj) throws JSONException {
		if (obj != null) {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				ShoucangContent content = new ShoucangContent(
						jo.getString("poetry_id"),
						jo.optString("poetry_name"),
						jo.optString("poetry_rhesis"),
						jo.optString("dynasty_name"),
						jo.optString("writer_name"));
				
				dataListOne.add(content);
			}
		}
	}
	
	private void parseTwo(JSONArray obj) throws JSONException {
		if (obj != null) {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				ShoucangContent content = new ShoucangContent(
						jo.getString("poetry_id"),
						jo.optString("poetry_name"),
						jo.optString("poetry_rhesis"),
						jo.optString("dynasty_name"),
						jo.optString("writer_name"));
				
				dataListTwo.add(content);
			}
		}
	}
	
	private void sendCollection(final String poemId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postCollection(UserId.getInstance(FourthShoucang.this).getUserId(), poemId, "1", "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				
			}
			
		};
		
		task.execute();
	}
}
