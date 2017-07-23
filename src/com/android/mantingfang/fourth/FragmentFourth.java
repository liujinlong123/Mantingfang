package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentFourth extends Fragment implements OnClickListener{

	private View view;
	private LinearLayout linearMy;
	private LinearLayout linearZhuye;
	private LinearLayout linearGuanzhu;
	private LinearLayout linearShoucang;
	private LinearLayout linearDianzan;
	private LinearLayout linearTuijian;
	private LinearLayout linearTongzhi;
	private LinearLayout linearGuanyu;
	private TextView quit;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_fourth_pager, null);
			
			
			initViews();
			
			return view;
		}
		return view;
	}

	private void initViews() {
		linearMy = (LinearLayout)view.findViewById(R.id.fourth_linear_my);
		linearZhuye = (LinearLayout)view.findViewById(R.id.fourth_linear_zhuye);
		linearGuanzhu = (LinearLayout)view.findViewById(R.id.fourth_linear_guanzhu);
		linearShoucang = (LinearLayout)view.findViewById(R.id.fourth_linear_shoucang);
		linearDianzan = (LinearLayout)view.findViewById(R.id.fourth_linear_dianzan);
		linearTuijian = (LinearLayout)view.findViewById(R.id.fourth_linear_tuijian);
		linearTongzhi = (LinearLayout)view.findViewById(R.id.fourth_linear_tongzhi);
		linearGuanyu = (LinearLayout)view.findViewById(R.id.fourth_linear_guanyu);
		quit = (TextView)view.findViewById(R.id.fourth_tv_logoff);
		
		
		linearMy.setOnClickListener(this);
		linearZhuye.setOnClickListener(this);
		linearGuanzhu.setOnClickListener(this);
		linearShoucang.setOnClickListener(this);
		linearDianzan.setOnClickListener(this);
		linearTuijian.setOnClickListener(this);
		linearTongzhi.setOnClickListener(this);
		linearGuanyu.setOnClickListener(this);
		quit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id) {
		//0
		case R.id.fourth_linear_my:
			Toast.makeText(getActivity(), "My", Toast.LENGTH_SHORT).show();
			break;
			
		//1
		case R.id.fourth_linear_zhuye:
			
			break;
		
		//2
		case R.id.fourth_linear_guanzhu:
			
			break;
			
		//3
		case R.id.fourth_linear_shoucang:
			
			break;
			
		//4
		case R.id.fourth_linear_dianzan:
			
			break;
			
		//5
		case R.id.fourth_linear_tuijian:
			Intent intent5 = new Intent(getActivity(), FourthTuijian.class);
			startActivity(intent5);
			break;
			
		//6
		case R.id.fourth_linear_tongzhi:
			Intent intent6 = new Intent(getActivity(), FourthTongzhi.class);
			startActivity(intent6);
			break;
			
		//7
		case R.id.fourth_linear_guanyu:
			
			break;
			
		//8
		case R.id.fourth_tv_logoff:
			
			break;
			
		default:
			break;
		}
	}
}
