package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class UserPager extends FragmentActivity {
	
	private TextView userCare;
	private RadioGroup userRgp;
	private MyViewPager mViewPager;
	private LinearLayout linearHeight;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private Fragment userOne;
	private Fragment userTwo;
	private Fragment userThree;
	private VerticalScrollView scrollview;
	
	private LinearLayout linearHead;
	private RadioGroup userRgp1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_pager);
		
		initViews();
	}
	
	@SuppressLint("ClickableViewAccessibility")
	private void initViews() {
		userCare = (TextView)findViewById(R.id.user_pager_care);
		userRgp = (RadioGroup)findViewById(R.id.user_pager_rgp);
		mViewPager = (MyViewPager)findViewById(R.id.user_pager_view_pager);
		scrollview = (VerticalScrollView)findViewById(R.id.user_pager_scrollView);
		linearHeight = (LinearLayout)findViewById(R.id.user_linear_height);
		
		linearHead = (LinearLayout)findViewById(R.id.user_head_linear);
		userRgp1 = (RadioGroup)findViewById(R.id.user_pager_rgp1);
		
		userOne = new UserOne();
		userTwo = new UserTwo();
		userThree = new UserThree();
		
		fragmentList.add(userOne);
		fragmentList.add(userTwo);
		fragmentList.add(userThree);
		
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//mViewPager.resetHeight(1);
		mViewPager.setCurrentItem(1);
		
		userCare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		userRgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.user_pager_rbtn_one:
					mViewPager.setCurrentItem(0);
					Toast.makeText(getBaseContext(), "1", Toast.LENGTH_SHORT).show();
					mViewPager.setCurrentItem(0);
					//mViewPager.resetHeight(0);
					break;
					
				case R.id.user_pager_rbtn_two:
					mViewPager.setCurrentItem(1);
					//mViewPager.resetHeight(1);
					break;
					
				case R.id.user_pager_rbtn_three:
					mViewPager.setCurrentItem(2);
					//mViewPager.resetHeight(2);
					break;
				}
			}
		});
		
		userRgp1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.user_pager_rbtn_one1:
					mViewPager.setCurrentItem(0);
					Toast.makeText(getBaseContext(), "1", Toast.LENGTH_SHORT).show();
					mViewPager.setCurrentItem(0);
					//mViewPager.resetHeight(0);
					break;
					
				case R.id.user_pager_rbtn_two1:
					mViewPager.setCurrentItem(1);
					//mViewPager.resetHeight(1);
					break;
					
				case R.id.user_pager_rbtn_three1:
					mViewPager.setCurrentItem(2);
					//mViewPager.resetHeight(2);
					break;
				}
			}
		});
		
		
		scrollview.setScrollViewListener(new ScrollViewListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public void onScrollChanged(VerticalScrollView myscrollView, int x, int y, int oldx, int oldy) {
				Log.v("height", (linearHeight.getMeasuredHeight()) + "-------x: " + x + "----------y: " + y);
				Log.v("height", linearHeight.getMeasuredHeightAndState() + "-------");
				if (y >= linearHeight.getMeasuredHeight()) {
					linearHead.setVisibility(View.VISIBLE);
					userRgp.setVisibility(View.GONE);
				} else if (y < linearHeight.getMeasuredHeight()) {
					linearHead.setVisibility(View.GONE);
					userRgp.setVisibility(View.VISIBLE);
				}
			}
		});
		
	}
	
	class MainPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
			super(fragmentManager);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
	}
}
