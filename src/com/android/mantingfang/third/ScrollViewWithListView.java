package com.android.mantingfang.third;

import android.widget.ListView;

/**
 * 
 * @Description: scrollview����Ƕlistview�ļ�ʵ��
 * 
 * @File: ScrollViewWithListView.java
 * 
 * @Paceage com.meiya.ui
 * 
 * 
 * @Date ����03:02:38
 * 
 * @Version
 */
public class ScrollViewWithListView extends ListView {

	public ScrollViewWithListView(android.content.Context context, android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Integer.MAX_VALUE >> 2,��������ã�ϵͳĬ����������ʾ����
	 */
	/*public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}*/

}