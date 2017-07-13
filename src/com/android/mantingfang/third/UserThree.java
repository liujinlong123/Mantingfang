package com.android.mantingfang.third;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserThree extends Fragment {

	//private MyViewPager vp;
	private View view;
	
	/*public UserThree(MyViewPager vp) {
		this.vp = vp;
	}*/
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_three_frag, null);
			
			//vp.setObjectForPosition(view, 2);
			return view;
		}
		//vp.setObjectForPosition(view, 2);
		return view;
	}
}
