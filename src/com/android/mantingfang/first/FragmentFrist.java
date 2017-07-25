package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FragmentFrist extends Fragment {
	private View view;
	
	//ViewPager
	private ViewPager viewPager;
	private List<PoemRhesis> dataList;
	private ViewAdapter adapter;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	private Button btnAdd;
	private ImageView imgCollect;
	private ImageView imgMore;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_first_pager, null);
			//initViewPager();
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	
	private void initViews() {
		btnAdd = (Button)view.findViewById(R.id.topbar_first_add);
		imgCollect = (ImageView)view.findViewById(R.id.topbar_first_collect);
		imgMore = (ImageView)view.findViewById(R.id.topbar_first_more);
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FirstPagerAdd.class);
				startActivity(intent);
			}
		});
		
		imgCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imgMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		initViewPager();
	}
	
	private void initViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.frag_first_viewpager);
		getData();
	}
	
	class ViewAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentsList;
		
		public ViewAdapter(FragmentManager fm) {
			super(fm);
		}

		public ViewAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragmentsList = fragments;
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentsList.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			//return super.getItemPosition(object);
			return POSITION_NONE;
		}
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				for (int i = 0; i < 100; i++) {
					fragmentList.add(new FragViewPager(new PoemRhesis("1", "无名氏" + i, "关关雎鸠，在河之洲")));
				}
				//return MyClient.getInstance().Http_postViewPager("", "");
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				viewPager.setOffscreenPageLimit(100);
				adapter = new ViewAdapter(getChildFragmentManager(), fragmentList);
				viewPager.setAdapter(adapter);
				viewPager.setCurrentItem(0);
				
				adapter.notifyDataSetChanged();
			}
			
		};
		
		task.execute();
	}
}
