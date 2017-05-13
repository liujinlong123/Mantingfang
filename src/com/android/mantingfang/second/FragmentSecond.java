package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.bean.URLs;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class FragmentSecond extends Fragment {

	private View view;
	
	//文库
	private ListView wenkuListView;
	private SecondWenkuListViewAdapter wenkuAdapter;
	private List<KindContent> wenkuList;
	
	//private String[] singleName = {"诗经全集", "楚辞节选", "道德经", "金刚经", "古诗十九首", "唐诗三百首", "宋词三百首", "给孩子的诗", "乐府诗集", "周易"};
	private int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,};
	
	
	//诗人
	private ListView writerListView;
	private SecondWriterListViewAdapter writerAdapter;
	private List<Poem> writerList;
	private PoetryDao poetryDao;
	
	
	//主界面
	private RadioGroup radgp;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_second_pager, null);
			
			initWriter();
			initWenku();
			
			initMain();
			
			return view;
		}
		
		return view;
	}
	
	
	//初始化文库界面
	private void initWenku() {
		wenkuListView = (ListView)view.findViewById(R.id.frag_second_wenku_list);
		wenkuAdapter = new SecondWenkuListViewAdapter(getActivity(), getWenkuData());
		wenkuListView.setAdapter(wenkuAdapter);
	}
	
	private List<KindContent> getWenkuData() {
		wenkuList = new ArrayList<KindContent>();
		for (int i = 0; i < URLs.typeKind.length; i++) {
			//for (int j = 0; j < URLs.TYPES[i].length; j++) {
				KindContent content = new KindContent(URLs.typeKind[i], URLs.TYPES[i], images);
				wenkuList.add(content);
			//}
		}
		
		return wenkuList;
	}
	
	//初始化诗人界面
	private void initWriter() {
		writerListView = (ListView)view.findViewById(R.id.frag_second_writer_list);
		writerAdapter = new SecondWriterListViewAdapter(getActivity(), getWriterData());
		writerListView.setAdapter(writerAdapter);
		writerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(), "fuck you", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private List<Poem> getWriterData() {
		writerList = new ArrayList<Poem>();
		/*poetryDao = new PoetryDao(getActivity());
		//writerList = poetryDao.getPoemByWid(writerid);
		writerList = poetryDao.getPoemByWid(1);
		Log.v("writerList", writerList.size() + "");*/
		for (int i = 0; i < 10; i++) {
			
		}
		return writerList;
	}
	
	//初始化主页面
	private void initMain() {
		radgp = (RadioGroup)view.findViewById(R.id.topbar_second_radgp);
		
		radgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.topbar_second_rbtnL:
					wenkuListView.setVisibility(View.VISIBLE);
					writerListView.setVisibility(View.GONE);
					break;
					
				case R.id.topbar_second_rbtnR:
					writerListView.setVisibility(View.VISIBLE);
					wenkuListView.setVisibility(View.GONE);
					break;
				}
			}
		});
	}
}
