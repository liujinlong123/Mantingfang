package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UserOne extends Fragment {

	private View view;
	/*private MyViewPager vp;
	
	public UserOne(MyViewPager vp) {
		this.vp = vp;
	}*/
	
	private CustomListView listview;
	private UserOneAdapter adapter;
	private List<Integer> list;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_one_frag, null);
			//vp.setObjectForPosition(view, 0);
			
			//initViews();
			Log.v("TESTFRAGMENT", (String)getArguments().get("userId") + "-----One");
			return view;
		}
		//vp.setObjectForPosition(view, 0);
		return view;
	}
	
	private void initViews() {
		listview = (CustomListView)view.findViewById(R.id.user_one_listview);
		adapter = new UserOneAdapter(getActivity(), getData(), false);
		listview.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listview);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UIHelper.showPoemDetail(getActivity(), list.get(position), 0);
			}
		});
	}
	
	private List<Integer> getData() {
		list = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			list.add(i);
		}
		
		return list;
	}
	
	/**
	 * ScrollView嵌套listview
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}
