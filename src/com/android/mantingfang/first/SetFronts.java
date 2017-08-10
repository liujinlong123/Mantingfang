package com.android.mantingfang.first;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class SetFronts {
	private TextView tv;
	private int type;
	
	public SetFronts(TextView textview){
		this.tv = textview;
	}
	
	/**
	 * 设置字体为楷体
	 */
	public void setKTFragmentFrist() {
		tv.setTypeface(FragmentFrist.typefaceKT);
	}
	
	/**
	 * 设置字体为隶书
	 */
	public void setLSFragmentFrist() {
		tv.setTypeface(FragmentFrist.typefaceLS);
	}
	
	/**
	 * 华文行楷
	 */
	public void setHWXKFragmentFrist() {
		tv.setTypeface(FragmentFrist.typefaceHWXK);
	}
	
	
	/**
	 * 设置字体为楷体
	 */
	public void setKTFirstPagerInfoP() {
		tv.setTypeface(FirstPagerInfoP.typefaceKT);
	}
	
	/**
	 * 设置字体为隶书
	 */
	public void setLSFirstPagerInfoP() {
		tv.setTypeface(FirstPagerInfoP.typefaceLS);
	}
	
	/**
	 * 华文行楷
	 */
	public void setHWXKFirstPagerInfoP() {
		tv.setTypeface(FirstPagerInfoP.typefaceHWXK);
	}

	public void setTv(TextView tv) {
		this.tv = tv;
	}
}
