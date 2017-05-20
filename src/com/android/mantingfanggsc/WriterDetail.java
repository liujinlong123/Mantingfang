package com.android.mantingfanggsc;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.model.Poem;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WriterDetail extends Activity {

	private ListView listview;
	private List<Poem> plist;
	private WriterDetailListAdapter adapter;
	
	//topbar
	private LinearLayout linearback;
	private TextView tv_back;
	private TextView tv_theme;
	private ImageView img_collect;
	private ImageView img_comment;
	private ImageView img_more;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writerdetail);
		
		initViews();
		initListView();
		
	}
	
	private void initViews() {
		linearback = (LinearLayout)findViewById(R.id.topbar_all_back);
		tv_back = (TextView)findViewById(R.id.topbar_tv_back);
		tv_theme = (TextView)findViewById(R.id.topbar_tv_theme);
		img_collect = (ImageView)findViewById(R.id.topbar_all_collect);
		img_comment = (ImageView)findViewById(R.id.topbar_all_comment);
		img_more = (ImageView)findViewById(R.id.topbar_all_more);
		
		tv_back.setText("·µ»Ø");
		tv_theme.setVisibility(View.INVISIBLE);
		img_collect.setVisibility(View.VISIBLE);
		img_comment.setVisibility(View.VISIBLE);
		img_more.setVisibility(View.VISIBLE);
		
		linearback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initListView() {
		listview = (ListView)findViewById(R.id.writerdetail_listview);
		adapter = new WriterDetailListAdapter(WriterDetail.this, getData());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					
				} else {
					UIHelper.showPoemDetail(WriterDetail.this, 1, 0);
				}
			}
		});
	}
	
	private List<Poem> getData() {
		plist = new ArrayList<Poem>();
		plist = new PoetryDao(WriterDetail.this).getAllPoem();
		Log.v("plist", plist.size() + "");
		return plist;
	}
}
