package com.android.mantingfang.first;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class SetFronts {
	private TextView tv;
	private Context context;
	private int type;
	
	public SetFronts(Context context, TextView textview){
		this.context = context;
		this.tv = textview;
	}
	
	/**
	 * 设置字体为楷体
	 */
	public void setKT() {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/KT.ttf"));
	}
	
	/**
	 * 设置字体为隶书
	 */
	public void setLS() {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/LS.ttf"));
	}
	
	/**
	 * 华文行楷
	 */
	public void setHWXK() {
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/HWXK.ttf"));
	}

	public void setTv(TextView tv) {
		this.tv = tv;
	}
}
