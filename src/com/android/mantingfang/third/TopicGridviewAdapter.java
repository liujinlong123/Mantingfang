package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfang.picture.ImageLoader.Type;
import com.android.mantingfanggsc.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TopicGridviewAdapter extends BaseAdapter {
	//private static Set<String> selectedImg = new HashSet<String>();

	private List<String> datas;
	// private Context context;
	private LayoutInflater inflater;

	/**
	 * 图片适配器
	 * @param context
	 * @param datas	     图片的名称
	 * @param dirPath 图片所在文件夹的路径
	 */
	public TopicGridviewAdapter(Context context, List<String> datas) {
		// this.context = context;
		this.datas = datas;

		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (datas != null) {
			return datas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (datas != null) {
			return datas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;

		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.item_gridview, parent,
					false);
			viewHolder = new ViewHolder();

			viewHolder.vhImage = (ImageView) convertView
					.findViewById(R.id.id_item_image);
			viewHolder.vhButton = (ImageButton) convertView
					.findViewById(R.id.id_item_select);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.vhImage.setImageResource(R.drawable.picture_no);
		viewHolder.vhButton.setVisibility(View.GONE);
		viewHolder.vhImage.setColorFilter(null);

		final String imgPath = datas.get(position);

		PictureLoad.getInstances(8, Type.FIFO).loadImage(imgPath, viewHolder.vhImage);
		Log.v("imgPath", imgPath);

		viewHolder.vhImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});

		return convertView;
	}

	private class ViewHolder {
		ImageView vhImage;
		ImageButton vhButton;
	}
}