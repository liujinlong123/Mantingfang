package com.android.mantingfang.third;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdPagerOne extends BaseFragment {

	private View view;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_left_two, null);
			
			return view;
		}
		
		return view;
	}
}
