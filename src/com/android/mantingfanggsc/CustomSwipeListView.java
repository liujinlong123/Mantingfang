package com.android.mantingfanggsc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class CustomSwipeListView extends ListView {

	private static final String TAG = "ListViewCompat";

	public static SwipeItemView mFocusedItemView;
	//private int mPosition;

	public CustomSwipeListView(Context context) {
		super(context);
	}

	public CustomSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSwipeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void shrinkListItem(int position) {
		View item = getChildAt(position);

		if (item != null) {
			try {
				((SwipeItemView) item).shrink();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			int x = (int) event.getX();
			int y = (int) event.getY();
			
			int position = pointToPosition(x, y);
			Log.e(TAG, "postion=" + position);
			if (position != INVALID_POSITION) {
				int firstPos = getFirstVisiblePosition();
				mFocusedItemView = (SwipeItemView) getChildAt(position
						- firstPos);
				Log.d("gaolei", "position------------------" + position);
				Log.d("gaolei", "firstPos------------------" + firstPos);
				Log.d("gaolei", "mFocusedItemView-----isNull---------"
						+ (mFocusedItemView != null));
			}
		}
		default:
			break;
		}

		if (mFocusedItemView != null) {
			mFocusedItemView.onRequireTouchEvent(event);

		}

		return super.onTouchEvent(event);
	}
}
