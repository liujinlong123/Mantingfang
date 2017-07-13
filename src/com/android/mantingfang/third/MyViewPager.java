package com.android.mantingfang.third;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class MyViewPager extends ViewPager {
	
	//private int current;
	//private int height = 0;
	//private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
	
	public MyViewPager(Context context) {
		super(context);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attribute set
	 */
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/*if (mChildrenViews.size() > current) {
			View child = mChildrenViews.get(current);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			height = child.getMeasuredHeight();
		}
		
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);*/
	}
	
	/*public void resetHeight(int current) {
		this.current = current;
		if (mChildrenViews.size() > current) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)getLayoutParams();
			
			if (layoutParams == null) {
				layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
			} else {
				layoutParams.height = height;
			}
			setLayoutParams(layoutParams);
		}
	}
	
	public void setObjectForPosition(View view, int position) {
		mChildrenViews.put(position, view);
	}*/

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @param view
	 *            the base view with already measured height
	 * 
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	@SuppressWarnings("unused")
	private int measureHeight(int measureSpec, View view) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			// set the height from the base view if available
			if (view != null) {
				result = view.getMeasuredHeight();
			}
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	
	

}
