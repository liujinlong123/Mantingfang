package com.android.mantingfang.fourth;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FourthDianzan extends FragmentActivity {

	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private RadioGroup rgpTitle;
	private RadioButton left;
	private RadioButton right;
	private ViewPager viewPager;
	private Fragment dianzanL;
	private Fragment dianzanR;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fourth_dianzan);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setVisibility(View.GONE);
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("我");
		
		rgpTitle = (RadioGroup)findViewById(R.id.topbar_radgp_select);
		rgpTitle.setVisibility(View.VISIBLE);
		
		initViewPager();
	}
	
	@SuppressWarnings("deprecation")
	private void initViewPager() {
		viewPager = (ViewPager)findViewById(R.id.fourth_dianzan_view_pager);
		left = (RadioButton)findViewById(R.id.topbar_rbtn_selectL);
		right = (RadioButton)findViewById(R.id.topbar_rbtn_selectR);
		left.setOnClickListener(new MyOnClickListener(0));
		right.setOnClickListener(new MyOnClickListener(1));
		
		dianzanL = new DianzanL();
		dianzanR = new DianzanR();
		
		fragmentList.add(dianzanL);
		fragmentList.add(dianzanR);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragmentList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
		left.setChecked(true);
	}
	
	class HomePageAdapter extends android.support.v4.app.FragmentStatePagerAdapter {// FragmentPagerAdapter

		// private FragmentManager fm;
		private List<Fragment> fragments = null;
		@SuppressWarnings("unused")
		private FragmentManager fm;

		public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return fragments.size();// hotIssuesList.size();
		}
	}
	
	public class MyOnClickListener implements View.OnClickListener {

		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	@SuppressLint("ResourceAsColor")
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				left.setChecked(true);
				break;
			case 1:
				right.setChecked(true);
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}
