package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FragmentFrist extends Fragment {
	private View view;
	
	//ViewPager
	private ViewPager mViewPager;
	
	private int[] mImagIds = {R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3,};
	private List<ImageView> mImages = new ArrayList<ImageView>();
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_first_pager, null);
			initViewPager();
			
			return view;
		}
		
		return view;
	}
	
	private void initViewPager() {
		mViewPager = (ViewPager)view.findViewById(R.id.frag_first_viewpager);
		//添加动画效果
		//mViewPager.setPageTransformer(arg0, arg1);
		mViewPager.setAdapter(new PagerAdapter(){

			@Override
			public int getCount() {
				return mImagIds.length;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView imageview = new ImageView(getActivity());
				imageview.setImageResource(mImagIds[position]);
				imageview.setScaleType(ScaleType.CENTER_CROP);		//不让图片变形
				container.addView(imageview);
				mImages.add(imageview);
				
				return imageview;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mImages.get(position));
			}
			
		});
	}
}
