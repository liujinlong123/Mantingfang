package com.android.mantingfang.first;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragViewPager extends Fragment{

	private View view;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private RelativeLayout layout;
	
	private PoemRhesis rhesis;
	
	public FragViewPager() {}
	
	public FragViewPager(PoemRhesis rhesis) {
		this.rhesis = rhesis;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.first_view_item, null);
			tv1 = (TextView)view.findViewById(R.id.first_viewpager_info_tv1);
			tv2 = (TextView)view.findViewById(R.id.first_viewpager_info_tv2);
			tv3 = (TextView)view.findViewById(R.id.first_viewpager_info_tv3);
			layout = (RelativeLayout)view.findViewById(R.id.relative_layout_viewpager);
			if (rhesis.getPoemId() != null && rhesis.getWriter() != null && rhesis.getRhesis() != null
					&& !rhesis.getPoemId().equals("") && !rhesis.getWriter().equals("") && !rhesis.getRhesis().equals("")) {
				String[] tokens = rhesis.getRhesis().split("[，,.。!?！？]");
				tv1.setText(tokens[0]);
				tv2.setText(tokens[1]);
				tv3.setText(rhesis.getWriter());
			}
			
			return view;
		}
		
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FirstPagerInfoP.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("rhesis", rhesis);
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});
		super.onActivityCreated(savedInstanceState);
	}
}
