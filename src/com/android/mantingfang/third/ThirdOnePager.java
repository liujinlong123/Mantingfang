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

public class ThirdOnePager extends Fragment {
	private View view;
	private ListView thirdOneListView;
	private ThirdOneAdapter adapterOne;
	private List<ThirdOneContent> listOne;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_one, null);
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	//≥ı ºªØThirdOne
	private void initViews() {
		thirdOneListView = (ListView)view.findViewById(R.id.third_pager_one_listview);
		adapterOne = new ThirdOneAdapter(getActivity(), getData());
		thirdOneListView.setAdapter(adapterOne);
	}
	
	private List<ThirdOneContent> getData() {
		listOne = new ArrayList<ThirdOneContent>();
		for (int i = 0; i < 10; i++) {
			listOne.add(new ThirdOneContent());
		}
		
		return listOne;
	}
}
