package com.android.mantingfang.topic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.third.ThemePager;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TopicSearch extends Activity {
	
	private EditText editor;
	private TextView cancel;
	private GridView grd;
	private ListView list;
	private ListAdapter adapter;
	private GridView grdKind;
	
	private ArrayAdapter<String> adapterGrd;
	private ArrayList<String> dataList;
	private ArrayList<String> relatedKeys;
	
	/*private static String[] testString = {"王俊凯 蓝色头发", "林更新", "闫妮 井柏然", "每月给1200元 女...", 
			"圆明园石狮子石...", "吴昕", "张一山", "内蒙古龙飓风 5人...", "参观北大要价一...", "李清照"};*/
	private static String[] kinds = {"情感", "风景", "人物", "运动", "历史", "品质"};
	private static int[] imgs = {R.drawable.search_1, R.drawable.search_2, R.drawable.search_3,
			R.drawable.search_4, R.drawable.search_5, R.drawable.search_6};
	private String searchHotKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_search);
		
		searchHotKey = getIntent().getStringExtra("searchKey");
		initViews();
	}
	
	private void initViews() {
		editor = (EditText)findViewById(R.id.topic_search_editor);
		cancel = (TextView)findViewById(R.id.topic_search_cancel);
		grd = (GridView)findViewById(R.id.topic_search_grv);
		list = (ListView)findViewById(R.id.topic_search_list);
		grdKind = (GridView)findViewById(R.id.grdview_six);
		
		editor.setHint(searchHotKey);
		GrdAdapter grdAdapter = new GrdAdapter();
		grdKind.setNumColumns(3);
		grdKind.setAdapter(grdAdapter);
		grdKind.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(TopicSearch.this, ThemePager.class);
				intent.putExtra("searchKey", kinds[position]);
				intent.putExtra("type", 1);
				startActivity(intent);
			}
		});
		
		//搜索--修改后
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editor.getText().toString().equals("") || editor.getText().toString() == null) {
					Toast.makeText(TopicSearch.this, "内容不能为空", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(TopicSearch.this, ThemePager.class);
					intent.putExtra("searchKey", editor.getText().toString());
					startActivity(intent);
				}
			}
		});
		
		/*editor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals("") || s.toString() == null) {
					list.setVisibility(View.GONE);
				} else {
					//getData(s.toString());
				}
			}
		});
		
		//getHotKey();*/
		}
	
	/**
	 * 返回十条热搜
	 */
	private void getHotKey() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_HotKeys("");
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						dataList = new ArrayList<>();
						JSONArray obj = StringUtils.toJSONArray(result);
						for (int i = 0; i < obj.length(); i++) {
							dataList.add(obj.getJSONObject(i).getString("hotKey"));
						}
						
						String[] tokens = new String[dataList.size()];
						for (int i = 0; i < dataList.size(); i++) {
							tokens[i] = dataList.get(i);
						}
						
						grd.setNumColumns(2);
						grd.setSelector(new ColorDrawable(Color.TRANSPARENT)); 
						adapterGrd = new ArrayAdapter<>(TopicSearch.this, R.layout.topic_search_grd_item, tokens);
						grd.setAdapter(adapterGrd);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					grd.setVisibility(View.GONE);
				}
			}
			
		};
		
		task.execute();
	}
	
	
	/**
	 * 返回相关关键词
	 */
	private void getData(final String key) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_relatedKeys(key);
			}
			
			@Override
			protected void onPostExecute(String result) {
				relatedKeys = new ArrayList<>();
				if (result != null && !result.equals("")) {
					try {
						JSONArray obj = StringUtils.toJSONArray(result);
						for (int i = 0; i < obj.length(); i++) {
							relatedKeys.add(obj.getJSONObject(i).getString("relatedKeys"));
						}
						adapter = new ListAdapter();
						list.setAdapter(adapter);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					
				}
			}
			
		};
		
		task.execute();
	}
	
	class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return relatedKeys.size();
		}

		@Override
		public Object getItem(int position) {
			return relatedKeys.get(position);
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
				convertView = LayoutInflater.from(TopicSearch.this).inflate(R.layout.related_keys_item, null);
				holder.tvShow = (TextView)convertView.findViewById(R.id.related_keys);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tvShow.setText(relatedKeys.get(position));
			
			return convertView;
		}
		
		class ViewHolder {
			TextView tvShow;
		}
		
	}
	
	
	class GrdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return kinds.length;
		}

		@Override
		public Object getItem(int position) {
			return kinds[position];
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
				convertView = LayoutInflater.from(TopicSearch.this).inflate(R.layout.search_topic_grd, null);
				
				holder.img = (ImageView)convertView.findViewById(R.id.search_topic_grd_img);
				holder.tv = (TextView)convertView.findViewById(R.id.search_topic_grd_tv);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.img.setImageResource(imgs[position]);
			holder.tv.setText(kinds[position]);
			
			return convertView;
		}
		
		class ViewHolder {
			ImageView img;
			
			TextView tv;
		}
		
	}
}
