package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

public class SecondWenkuPoem extends Activity {

	private ListView listview;
	private SecondWenkuPoemListAdapter madapter;
	private List<Poem> list;
	
	//���˰�ť
	private LinearLayout back;
	
	//kindName �������
	private TextView theme;
	
	//�����ͼƬ
	private ImageView imgTheme;
	
	//��������� singleName
	private TextView tvSingleName;
	
	//singleName������
	private TextView sum;
	
	//��һ����������Ϣ
	private String kindName;
	private String singleName;
	private int imageId;
	private int typeId;
	
	//�����ݿ�Ľӿ�-->�����ݿ��ѯ��ȡ����
	private PoetryDao poetryDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floor2_second_kind);
		
		initViews();
		initListView();
	}
	
	
	@SuppressLint("UseValueOf")
	private void initViews() {
		back = (LinearLayout)findViewById(R.id.topbar_all_back);
		theme = (TextView)findViewById(R.id.topbar_tv_theme);
		imgTheme = (ImageView)findViewById(R.id.floor2_second_img_theme);
		tvSingleName = (TextView)findViewById(R.id.floor2_second_tv_name);
		sum = (TextView)findViewById(R.id.floor2_second_tv_sum);
		poetryDao = new PoetryDao(SecondWenkuPoem.this);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		kindName = bundle.getString("kindName");
		singleName = bundle.getString("singleName");
		imageId = bundle.getInt("imgId");
		typeId = new Integer(bundle.getString("typeId")); 
		
		Log.v("Test", kindName + " " + singleName + "  " + imageId + " " + typeId);
		
		theme.setText(kindName);
		imgTheme.setImageResource(imageId);
		tvSingleName.setText(singleName);
	}
	
	private void initListView() {
		listview = (ListView)findViewById(R.id.floor2_secondlist);
		madapter = new SecondWenkuPoemListAdapter(SecondWenkuPoem.this, getData());
		listview.setAdapter(madapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UIHelper.showPoemDetail(view.getContext(), list.get(position).getPoetryid(), 0);
			}
		});
	}
	
	private List<Poem> getData() {
		list = new ArrayList<Poem>();
		
		//list = poetryDao.getPoemByTid(typeId);
		list = poetryDao.getPoemByTid(1);
		/*for (int i = 0; i < 20; i++) {
			Poem poem = new Poem(i, i, i, i, "xx", "xx", "xx");
			list.add(poem);
		}*/
		return list;
	}
}
