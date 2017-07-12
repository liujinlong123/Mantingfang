package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

@SuppressLint("ResourceAsColor")
public class FragmentThird extends Fragment {

	private View view;
	private ViewPager viewPager;
	//private RadioGroup btnTeam;
	private RadioButton btnOne;
	private RadioButton btnTwo;
	private RadioButton btnThree;
	private RadioButton btnFour;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private Fragment pagerOne;
	private Fragment pagerTwo;
	private Fragment pagerThree;
	private Fragment pagerFour;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_third_pager, null);
			
			initViews();
			initViewPager();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		viewPager = (ViewPager)view.findViewById(R.id.third_view_pager);
		//btnTeam = (RadioGroup)view.findViewById(R.id.third_head_radioGroup);
		btnOne = (RadioButton)view.findViewById(R.id.third_head_one);
		btnTwo = (RadioButton)view.findViewById(R.id.third_head_two);
		btnThree = (RadioButton)view.findViewById(R.id.third_head_three);
		btnFour = (RadioButton)view.findViewById(R.id.third_head_four);
		
		btnOne.setOnClickListener(new MyOnClickListener(0));
		btnTwo.setOnClickListener(new MyOnClickListener(1));
		btnThree.setOnClickListener(new MyOnClickListener(2));
		btnFour.setOnClickListener(new MyOnClickListener(3));
	}
	
	private void initViewPager() {
		pagerOne = new ThirdOnePager();
		pagerTwo = new ThirdTwoPager();
		pagerThree = new ThirdThreePager();
		pagerFour = new ThirdFourPager();
		
		fragmentList.add(pagerOne);
		fragmentList.add(pagerTwo);
		fragmentList.add(pagerThree);
		fragmentList.add(pagerFour);
		viewPager.setOffscreenPageLimit(4);
		viewPager.setAdapter(new HomePageAdapter(getChildFragmentManager(), fragmentList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
		btnOne.setTextColor(Color.RED);
		changeHeadSelectedState(0);
	}
	
	private void changeHeadSelectedState(int currentPosition) {
	}
	
	class HomePageAdapter extends android.support.v4.app.FragmentStatePagerAdapter {// FragmentPagerAdapter

		// private FragmentManager fm;
		private List<Fragment> fragments = null;
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
			// TODO Auto-generated method stub
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
                btnOne.setTextColor(Color.RED);
                btnTwo.setTextColor(Color.BLACK);
                btnThree.setTextColor(Color.BLACK);
                btnFour.setTextColor(Color.BLACK);
                
                break;
            case 1:
            	btnOne.setTextColor(Color.BLACK);
                btnTwo.setTextColor(Color.RED);
                btnThree.setTextColor(Color.BLACK);
                btnFour.setTextColor(Color.BLACK);
                
                break;
                
            case 2:
            	btnOne.setTextColor(Color.BLACK);
                btnTwo.setTextColor(Color.BLACK);
                btnThree.setTextColor(Color.RED);
                btnFour.setTextColor(Color.BLACK);
            	
            	break;
            	
            case 3:
            	btnOne.setTextColor(Color.BLACK);
                btnTwo.setTextColor(Color.BLACK);
                btnThree.setTextColor(Color.BLACK);
                btnFour.setTextColor(Color.RED);
            	
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
