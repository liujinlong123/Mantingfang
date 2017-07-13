package com.android.mantingfang.third;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserOne extends Fragment {

	private View view;
	/*private MyViewPager vp;
	
	public UserOne(MyViewPager vp) {
		this.vp = vp;
	}*/
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_one_frag, null);
			//vp.setObjectForPosition(view, 0);
			return view;
		}
		//vp.setObjectForPosition(view, 0);
		return view;
	}
}
