package com.android.mantingfang.second;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class KindGridView extends GridView {

	public KindGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public KindGridView(Context context) {
		super(context);
	}
	
	public KindGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec+50);
    }
}
