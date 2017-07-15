package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdTwoPager extends Fragment{
	private View view;
	private CustomListView thirdTwoListView;
	private ThirdTwoAdapter adapterTwo;
	private List<ThirdOneContent> listTwo;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_two, null);
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		thirdTwoListView = (CustomListView)view.findViewById(R.id.third_pager_two_listview);
		adapterTwo = new ThirdTwoAdapter(getActivity(), getData());
		thirdTwoListView.setAdapter(adapterTwo);
	}
	
	private List<ThirdOneContent> getData() {
		listTwo = new ArrayList<ThirdOneContent>();
		for (int i = 0; i < 10; i++) {
			listTwo.add(new ThirdOneContent());
		}
		
		return listTwo;
	}
}
