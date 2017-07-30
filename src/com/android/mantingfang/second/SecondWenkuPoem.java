package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.LabelDao;
import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.NetWork;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondWenkuPoem extends Activity {

	private CustomListView listview;
	private SecondWenkuPoemListAdapter madapter;
	private List<Poem> list;
	
	//返回
	private LinearLayout back;
	
	//kindName
	private TextView theme;
	
	//
	private ImageView imgTheme;
	
	//singleName
	private TextView tvSingleName;
	
	//singleName
	private TextView sum;
	
	//��һ����������Ϣ
	private String kindName;
	private String singleName;
	private int imageId;
	private int label_id;
	
	private PoetryDao poetryDao;
	private LabelDao labelDao;
	private boolean isNetwork;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floor2_second_kind);
		
		initViews();
	}
	
	
	@SuppressLint("UseValueOf")
	private void initViews() {
		back = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme = (TextView)findViewById(R.id.topbar_tv_theme);
		imgTheme = (ImageView)findViewById(R.id.floor2_second_img_theme);
		tvSingleName = (TextView)findViewById(R.id.floor2_second_tv_name);
		sum = (TextView)findViewById(R.id.floor2_second_tv_sum);
		poetryDao = new PoetryDao(SecondWenkuPoem.this);
		labelDao = new LabelDao(SecondWenkuPoem.this);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		kindName = bundle.getString("kindName");
		singleName = bundle.getString("singlename");
		label_id = bundle.getInt("label_id");
		imageId = bundle.getInt("imgId");
		
		theme.setText(kindName);
		imgTheme.setImageResource(imageId);
		tvSingleName.setText(singleName);
		listview = (CustomListView)findViewById(R.id.floor2_secondlist);
		isNetwork = NetWork.isNetworkAvailable(SecondWenkuPoem.this);
		getData(isNetwork, labelDao.getLabelById(label_id));
	}
	
	private void getDataFromSQL() {
		list = new ArrayList<Poem>();
		
		list = poetryDao.getPoemByTid(label_id);
	}
	
	private void getData(final boolean isNetwork, final String labelName) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				if (isNetwork) {
					return MyClient.getInstance().Http_postPoemKind(labelName);
				} else {
					getDataFromSQL();
					return null;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				if (isNetwork) {
					try {
						if (result != null && !result.equals("")) {
							list = TopicList.parseKindPoem(StringUtils.toJSONArray(result)).getKindPoemList();
							madapter = new SecondWenkuPoemListAdapter(SecondWenkuPoem.this, list, true);
							listview.setAdapter(madapter);
							listview.setOnItemClickListener(new OnItemClickListener() {
								
								@Override
								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									UIHelper.showPoemMDetailTwoById(SecondWenkuPoem.this, list.get(position - 1).getPoemId(), 0);
								}
							});
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					madapter = new SecondWenkuPoemListAdapter(SecondWenkuPoem.this, list, false);
					listview.setAdapter(madapter);
					listview.setOnItemClickListener(new OnItemClickListener() {
						
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							UIHelper.showPoemDetail(SecondWenkuPoem.this, list.get(position).getPoetryid(), 0);
						}
					});
				}
				
			}

		};

		task.execute();
	}
}
