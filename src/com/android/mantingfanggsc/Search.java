package com.android.mantingfanggsc;

import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.first.PoemRhesis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Search extends Activity {

	private ListView listviewPome;
	private ListView listviewWriter;
	private ListView listviewContent;
	private List<PoemRhesis> dataListPoem;
	private List<Writer> dataListWriter;
	private List<PoemRhesis> dataListContent;
	private PoemAdapter poemAdapter;
	private WriterAdapter writerAdapter;
	private ContentAdapter contentAdapter;

	private EditText editer;
	
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		initListView();
	}

	private void initListView() {
		inflater = LayoutInflater.from(Search.this);
		editer = (EditText) findViewById(R.id.search_editer_one);
		listviewPome = (ListView) findViewById(R.id.search_list_poem);
		listviewWriter = (ListView) findViewById(R.id.search_list_writer);
		listviewContent = (ListView) findViewById(R.id.search_list_content);

		editer.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() == null || s.toString().equals("")) {
					listviewPome.setVisibility(View.GONE);
					listviewWriter.setVisibility(View.GONE);
					listviewContent.setVisibility(View.GONE);
				} else if (s.toString() != null && !s.toString().equals("")){
					getdataPoem(s.toString());
					getdataWriter(s.toString());
					getdataContent(s.toString());
				}
			}
		});
	}

	private void getdataPoem(final String keyword) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postSearchPoem(keyword);
			}

			@Override
			protected void onPostExecute(String result) {
				try {
					if (result != null && !result.equals("")) {
						dataListPoem = TopicList.parseSearchPoemList(StringUtils.toJSONArray(result)).getSearchPoemList();
						
						poemAdapter = new PoemAdapter();
						listviewPome.setAdapter(poemAdapter);
						setListViewHeightBasedOnChildren(listviewPome);
						listviewPome.setVisibility(View.VISIBLE);
						listviewPome.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								UIHelper.showPoemMDetailTwoById(Search.this, dataListPoem.get(position - 1).getPoemId(), 0);
							}
						});
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		task.execute();
	}
	
	private void getdataWriter(final String keyword) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postSearchWriter(keyword);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						dataListWriter = TopicList.parseSearchWriterList(StringUtils.toJSONArray(result)).getSearchWriterList();
						writerAdapter = new WriterAdapter();
						listviewWriter.setAdapter(writerAdapter);
						setListViewHeightBasedOnChildren(listviewWriter);
						listviewWriter.setVisibility(View.VISIBLE);
						listviewWriter.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								UIHelper.showWriterDetail(Search.this, dataListWriter.get(position - 1), true);
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
	
	private void getdataContent(final String keyword) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postSearchContent(keyword);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					try {
						dataListContent = TopicList.parseSearchContentList(StringUtils.toJSONArray(result)).getSearchContentList();
						contentAdapter = new ContentAdapter();
						listviewContent.setAdapter(contentAdapter);
						setListViewHeightBasedOnChildren(listviewContent);
						listviewContent.setVisibility(View.VISIBLE);
						listviewContent.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								UIHelper.showPoemMDetailTwoById(Search.this, dataListContent.get(position - 1).getPoemId(), 0);
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
	
	@SuppressLint("InflateParams")
	class PoemAdapter extends BaseAdapter {
		private static final int TYPE_ONE = 0;
		private static final int TYPE_TWO = 1;
		/*private List<PoemRhesis> dataListPoem;
		
		public PoemAdapter(List<PoemRhesis> dataList) {
			this.dataListPoem = dataList;
		}*/
		
		@Override
		public int getItemViewType(int position) {
			int p = position;
			if (p == 0) {
				return TYPE_ONE;
			} else {
				return TYPE_TWO;
			}
		}
		
		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getCount() {
			return dataListPoem.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return dataListPoem.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder1 holder1 = null;
			ViewHolder2 holder2 = null;
			int type = getItemViewType(position);
			
			if (convertView == null) {
				switch (type) {
				case TYPE_ONE:
					convertView = inflater.inflate(R.layout.testview_item, null);
					holder1 = new ViewHolder1();
					holder1.tv = (TextView)convertView.findViewById(R.id.textview_item_tv);
					
					convertView.setTag(holder1);
					break;
					
				case TYPE_TWO:
					convertView = inflater.inflate(R.layout.item_listview_wenku_poem, null);
					holder2 = new ViewHolder2();
					holder2.poem_name = (TextView)convertView.findViewById(R.id.poemlist_tv_name);
					holder2.poem_rhesis = (TextView)convertView.findViewById(R.id.poemlist_tv_poemrhesis);
					holder2.poem_writer = (TextView)convertView.findViewById(R.id.poemlist_tv_writername);
					
					convertView.setTag(holder2);
					break;
				}
			} else {
				switch(type) {
				case TYPE_ONE:
					holder1 = (ViewHolder1) convertView.getTag();
					break;
					
				case TYPE_TWO:
					holder2 = (ViewHolder2) convertView.getTag();
					break;
				}
			}
			switch (type) {
			case TYPE_ONE:
				holder1.tv.setText("作品");
				break;
				
			case TYPE_TWO:
				PoemRhesis rhesis = dataListPoem.get(position - 1);
				holder2.poem_name.setText(rhesis.getPoemName());
				holder2.poem_rhesis.setText(rhesis.getRhesis());
				holder2.poem_writer.setText("[" + rhesis.getDynasty() + "]" + rhesis.getWriter());
				break;
			}
			
			return convertView;
		}
		
		class ViewHolder1 {
			TextView tv;
		}
		
		class ViewHolder2 {
			TextView poem_name;
			
			TextView poem_rhesis;
			
			TextView poem_writer;
		}
		
	}
	
	@SuppressLint("InflateParams")
	class WriterAdapter extends BaseAdapter {
		private static final int TYPE_ONE = 0;
		private static final int TYPE_TWO = 1;
		/*private List<Writer> dataListWriter;
		
		public WriterAdapter(List<Writer> dataList) {
			this.dataListWriter = dataList;
		}*/
		
		@Override
		public int getItemViewType(int position) {
			int p = position;
			if (p == 0) {
				return TYPE_ONE;
			} else {
				return TYPE_TWO;
			}
		}
		
		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getCount() {
			return dataListWriter.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return dataListWriter.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder1 holder1 = null;
			ViewHolder2 holder2 = null;
			int type = getItemViewType(position);
			
			if (convertView == null) {
				switch (type) {
				case TYPE_ONE:
					convertView = inflater.inflate(R.layout.testview_item, null);
					holder1 = new ViewHolder1();
					holder1.tv = (TextView)convertView.findViewById(R.id.textview_item_tv);
					
					convertView.setTag(holder1);
					break;
					
				case TYPE_TWO:
					convertView = inflater.inflate(R.layout.item_listview_wenku_poem, null);
					holder2 = new ViewHolder2();
					holder2.writer = (TextView)convertView.findViewById(R.id.poemlist_tv_name);
					holder2.dynasty = (TextView)convertView.findViewById(R.id.poemlist_tv_writername);
					holder2.career = (TextView)convertView.findViewById(R.id.poemlist_tv_poemrhesis);
					
					convertView.setTag(holder2);
					break;
				}
			} else {
				switch(type) {
				case TYPE_ONE:
					holder1 = (ViewHolder1) convertView.getTag();
					break;
					
				case TYPE_TWO:
					holder2 = (ViewHolder2) convertView.getTag();
					break;
				}
			}
			
			switch(type) {
			case TYPE_ONE:
				holder1.tv.setText("诗人");
				break;
				
			case TYPE_TWO:
				Writer writer = dataListWriter.get(position - 1);
				holder2.writer.setText(writer.getWriterName());
				holder2.dynasty.setText(writer.getDynastyName());
				holder2.career.setVisibility(View.GONE);
				break;
			}
			
			return convertView;
		}
		
		class ViewHolder1 {
			TextView tv;
		}
		
		class ViewHolder2 {
			TextView writer;
			
			TextView dynasty;
			
			TextView career;
		}
		
	}
	
	@SuppressLint("InflateParams")
	class ContentAdapter extends BaseAdapter {
		private static final int TYPE_ONE = 0;
		private static final int TYPE_TWO = 1;
		/*private List<PoemRhesis> dataListContent;
		
		public ContentAdapter(List<PoemRhesis> dataList) {
			this.dataListContent = dataList;
		}*/
		@Override
		public int getItemViewType(int position) {
			int p = position;
			if (p == 0) {
				return TYPE_ONE;
			} else {
				return TYPE_TWO;
			}
		}
		
		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getCount() {
			return dataListContent.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return dataListContent.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder1 holder1 = null;
			ViewHolder2 holder2 = null;
			int type = getItemViewType(position);
			
			if (convertView == null) {
				switch(type) {
				case TYPE_ONE:
					convertView = inflater.inflate(R.layout.testview_item, null);
					holder1 = new ViewHolder1();
					holder1.tv = (TextView)convertView.findViewById(R.id.textview_item_tv);
					
					convertView.setTag(holder1);
					break;
					
				case TYPE_TWO:
					convertView = inflater.inflate(R.layout.item_listview_wenku_poem, null);
					holder2 = new ViewHolder2();
					holder2.poem_content = (TextView)convertView.findViewById(R.id.poemlist_tv_name);
					holder2.poem_rhesis = (TextView)convertView.findViewById(R.id.poemlist_tv_poemrhesis);
					holder2.poem_name = (TextView)convertView.findViewById(R.id.poemlist_tv_writername);
					
					convertView.setTag(holder2);
					break;
				}
			} else {
				switch(type) {
				case TYPE_ONE:
					holder1 = (ViewHolder1) convertView.getTag();
					break;
					
				case TYPE_TWO:
					holder2 = (ViewHolder2) convertView.getTag();
					break;
				}
			}
			
			switch(type) {
			case TYPE_ONE:
				holder1.tv.setText("诗句");
				break;
				
			case TYPE_TWO:
				PoemRhesis content = dataListContent.get(position - 1);
				String rhesis = content.getRhesis();
				if (rhesis.length() >= 12) {
					holder2.poem_content.setText(rhesis.substring(0, 12) + "...");
				} else if (rhesis.length() < 12) {
					holder2.poem_content.setText(rhesis);
				}
				holder2.poem_rhesis.setVisibility(View.GONE);
				
				if (content.getPoemName().length() >= 3) {
					holder2.poem_name.setText(content.getPoemName().substring(0, 3) + "..");
				} else if (content.getPoemName().length() < 3) {
					holder2.poem_name.setText(content.getPoemName() + " ...");
				}
				break;
			}
			
			return convertView;
		}
		
		class ViewHolder1 {
			TextView tv;
		}
		
		class ViewHolder2 {
			TextView poem_content;
			
			TextView poem_name;
			
			TextView poem_rhesis;
		}
		
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
}
