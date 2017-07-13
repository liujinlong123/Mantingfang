package com.android.mantingfang.third;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VerticalScrollView extends ScrollView {

	private ScrollViewListener scrollViewListener = null;
	
    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownPosX = 0;
    private float mDownPosY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
    
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
    	this.scrollViewListener = scrollViewListener;
    }
    
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
    	super.onScrollChanged(x, y, oldx, oldy);
    	if (scrollViewListener != null) {
    		scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
    	}
    }
}

interface ScrollViewListener {

	void onScrollChanged(VerticalScrollView scrollView, int x, int y, int oldx, int oldy);

}