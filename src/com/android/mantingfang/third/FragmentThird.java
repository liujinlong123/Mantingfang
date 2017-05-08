package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

public class FragmentThird extends Fragment {

	private View view;
	private ThirdShiYouListViewAdapter mAdapter;
	private ListView listview;
	private List<ThirdContent> list;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_third_pager, null);
			
			initListView();
			return view;
		}
		
		return view;
	}
	
	//初始化listView
	private void initListView() {
		listview = (ListView)view.findViewById(R.id.third_listview);
		mAdapter = new ThirdShiYouListViewAdapter(getActivity(), getData());
		listview.setAdapter(mAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(), "点击了  " + position + " 项", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private List<ThirdContent> getData() {
		list = new ArrayList<ThirdContent>();
		for (int i = 0; i < 7; i++) {
			ThirdContent content = new ThirdContent();
			list.add(content);
		}
		return list;
	}
}
