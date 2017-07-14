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
import android.widget.ListView;

public class UserTwo extends Fragment {

	//private MyViewPager vp;
	private View view;
	
	/*public UserTwo(MyViewPager vp) {
		this.vp = vp;
	}*/
	
	
	private ListView listview;
	private UserTwoAdapter adapter;
	private List<UserTwoContent> list;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_two_frag, null);
			
			//vp.setObjectForPosition(view, 1);
			
			initViews();
			return view;
		}
		
		
		//vp.setObjectForPosition(view, 1);
		return view;
	}
	
	private void initViews() {
		listview = (ListView)view.findViewById(R.id.user_two_listview);
		adapter = new UserTwoAdapter(getActivity(), getData());
		listview.setAdapter(adapter);
	}
	
	private List<UserTwoContent> getData() {
		list = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			list.add(new UserTwoContent());
		}
		
		return list;
	}
}
