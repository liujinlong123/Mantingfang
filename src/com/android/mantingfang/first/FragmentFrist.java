package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class FragmentFrist extends Fragment {
	private View view;
	
	//ViewPager
	private ViewPager mViewPager;
	
	private int[] mImagIds = {R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3,};
	private List<ImageView> mImages = new ArrayList<ImageView>();
	
	
	//��ť
	private Button btnAdd;
	private ImageView imgCollect;
	private ImageView imgMore;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_first_pager, null);
			initViewPager();
			
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
	}
	
	private void initViewPager() {
		mViewPager = (ViewPager)view.findViewById(R.id.frag_first_viewpager);
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
				imageview.setScaleType(ScaleType.CENTER_CROP);
				container.addView(imageview);
				mImages.add(imageview);
				imageview.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), FirstPagerInfoP.class);
						startActivity(intent);
					}
				});
				
				return imageview;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mImages.get(position));
			}
			
		});
	}
}
