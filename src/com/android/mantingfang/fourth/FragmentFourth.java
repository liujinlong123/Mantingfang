package com.android.mantingfang.fourth;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
	private String userId;
	private SharedPreferences pref;
	
	private IntentFilter intentFilter;
	private MyBroadcast myBroadcast;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_fourth_pager, null);
			
			
			initViews();
			intentFilter = new IntentFilter();
			intentFilter.addAction("com.android.mantingfang.fourth.LOG_ON");
			myBroadcast = new MyBroadcast(quit);
			getActivity().registerReceiver(myBroadcast, intentFilter);
			
			return view;
		}
		return view;
	}

	private void initViews() {
		pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
		userId = pref.getString("userId", "-1");
		Log.v("userIdTwo", userId);
		
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
		
		if (Integer.parseInt(userId) < 0) {
			quit.setVisibility(View.GONE);
		} else {
			quit.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		userId = pref.getString("userId", "-1");
		//Log.v("userIdTwo", userId);
		
		int id = v.getId();
		Intent intent = new Intent(getActivity(), LogOn.class);
		switch(id) {
		//0
		case R.id.fourth_linear_my:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent0 = new Intent(getActivity(), FourthMy.class);
				startActivity(intent0);
			}
			break;
			
		//1
		case R.id.fourth_linear_zhuye:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				/*Intent intent0 = new Intent(getActivity(), FourthMy.class);
				startActivity(intent0);*/
			}
			break;
		
		//2
		case R.id.fourth_linear_guanzhu:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent2 = new Intent(getActivity(), FourthGuanzhu.class);
				startActivity(intent2);
			}
			break;
			
		//3
		case R.id.fourth_linear_shoucang:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent3 = new Intent(getActivity(), FourthShoucang.class);
				startActivity(intent3);
			}
			break;
			
		//4
		case R.id.fourth_linear_dianzan:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent4 = new Intent(getActivity(), FourthDianzan.class);
				startActivity(intent4);
			}
			break;
			
		//5
		case R.id.fourth_linear_tuijian:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent5 = new Intent(getActivity(), FourthTuijian.class);
				startActivity(intent5);
			}
			break;
			
		//6
		case R.id.fourth_linear_tongzhi:
			if (Integer.parseInt(userId) < 0) {
				startActivity(intent);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent6 = new Intent(getActivity(), FourthTongzhi.class);
				startActivity(intent6);
			}
			break;
			
		//7
		case R.id.fourth_linear_guanyu:
			
			break;
			
		//8
		case R.id.fourth_tv_logoff:
			if (Integer.parseInt(userId) < 0) {
				
			} else if (Integer.parseInt(userId) > -1) {
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
				editor.putString("userId", "-1");
				editor.commit();
				quit.setVisibility(View.GONE);
			}
			break;
			
		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(myBroadcast);
		super.onDestroy();
	}
}
