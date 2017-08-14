package com.android.mantingfang.third;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RiShangTrans extends Fragment {

	private View view;
	private TextView title;
	private TextView poemTrans;
	
	private String trans;
	
	
	public RiShangTrans(String trans) {
		this.trans = trans;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.rishang_trans, null);
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		title = (TextView)view.findViewById(R.id.rishang_title);
		poemTrans = (TextView)view.findViewById(R.id.rishang_trans);
		
		SharedPreferences pref = getActivity().getSharedPreferences("data", FragmentActivity.MODE_PRIVATE);
		int type = pref.getInt("rishang_fonts_type", -1);
		title.setText("译文");
		poemTrans.setText(getTrans(trans));
		if (type > -1 && type < 3) {
			setFronts(type);
		}
	}
	
	private String getTrans(String trans) {
		String str = "";
		String[] tokens = trans.split("[，。]");
		for (String e: tokens) {
			str += (e + "\n");
		}
		
		return str;
	}
	
	public void setTrans(String trans) {
		poemTrans.setText(getTrans(trans));
	}
	
	public void setFronts(int type) {
		switch(type) {
		case 0:
			title.setTypeface(RiShangPager.typefaceKT);
			poemTrans.setTypeface(RiShangPager.typefaceKT);
			break;
			
		case 1:
			title.setTypeface(RiShangPager.typefaceLS);
			poemTrans.setTypeface(RiShangPager.typefaceLS);
			break;
			
		case 2:
			title.setTypeface(RiShangPager.typefaceHWXK);
			poemTrans.setTypeface(RiShangPager.typefaceHWXK);
			break;
		}
	}
}
