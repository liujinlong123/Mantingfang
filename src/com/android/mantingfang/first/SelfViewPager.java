package com.android.mantingfang.first;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SelfViewPager extends ViewPager {
	
	public SelfViewPager(Context context) {
		super(context);
	}

	public SelfViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private float startX;
	private float endX;
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			//Log.e("sss", "按下");
			startX = event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_MOVE){
//			Log.e("ssss","滑动");
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			//Log.e("ssss","抬起");
			endX = event.getX();
			if(endX-startX<-20){
				//Log.e("ssss","1");
				if(getCurrentItem()==getAdapter().getCount()-1){
					//Log.e("ssss","2");
					setCurrentItem(0);
				}
			}else if(endX-startX>20){
				//Log.e("ssss","3");
				if(getCurrentItem()==0){
					//Log.e("ssss","4");
					setCurrentItem(getAdapter().getCount()-1);
				}
			}
			
		}
		return super.onTouchEvent(event);
	}

}
