package com.android.mantingfang.picture;

import java.util.List;

import com.android.mantingfanggsc.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

public class ImageDirPopWindow extends PopupWindow {

	private int width;
	private int height;

	private View convertView;

	private ListView listView;

	private List<FolderBean> datas;

	public ImageDirPopWindow(Context context, List<FolderBean> datas) {
		calWidthAndHeight(context);

		convertView = (LayoutInflater.from(context)).inflate(
				R.layout.popup_main, null);

		this.datas = datas;

		this.setContentView(convertView);
		this.setWidth(width);
		this.setHeight(height);

		this.setFocusable(true);

		this.setTouchable(true);

		// ���õ���ⲿ��ʧ
		this.setOutsideTouchable(true);
		this.setBackgroundDrawable(new BitmapDrawable());

		this.setTouchInterceptor(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		initViews(context);
		initEvent();

	}

	private void initViews(Context context) {
		listView = (ListView) convertView.findViewById(R.id.id_list_dir);

			DirListAdapter adapter = new DirListAdapter(context, datas);

				listView.setAdapter(adapter);
	}
	/**
	 * �ص��ӿ�
	 * @author ShouLun
	 *
	 */
	public interface OnDirSelectedListener {
		void onSelected(FolderBean folderBean);
	}
	
	public OnDirSelectedListener listener;
	
	public void setOnDirSelectedListener(OnDirSelectedListener listener) {
		this.listener = listener;
	}

	private void initEvent() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if(listener != null) {
					listener.onSelected(datas.get(position));
				}
			}
		});
	}

	/**
	 * ����popupWindow�Ŀ��
	 * 
	 * @param context
	 */
	private void calWidthAndHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		width = metrics.widthPixels;
		height = (int) (metrics.heightPixels * 0.7);
	}
}
