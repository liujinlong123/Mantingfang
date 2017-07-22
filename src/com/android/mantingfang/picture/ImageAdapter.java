package com.android.mantingfang.picture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.android.mantingfang.picture.ImageLoader.Type;
import com.android.mantingfanggsc.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Set<String> selectedImg = new HashSet<String>();

	private List<String> datas;
	// private Context context;
	private String dirPath;
	private LayoutInflater inflater;

	/**
	 * 图片适配器
	 * @param context
	 * @param datas	     图片的名称
	 * @param dirPath 图片所在文件夹的路径
	 */
	public ImageAdapter(Context context, List<String> datas, String dirPath) {
		// this.context = context;
		this.datas = datas;
		this.dirPath = dirPath;

		this.inflater = LayoutInflater.from(context);
	}
	
	public Set<String> getSelectedImg() {
		return selectedImg;
	}
	
	public void setSelectedImg(ArrayList<String> list) {
		selectedImg = new HashSet<>(list);
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
		viewHolder.vhButton.setVisibility(View.INVISIBLE);
		viewHolder.vhImage.setColorFilter(null);

		final String imgPath = dirPath + "/" + datas.get(position);

		ImageLoader.getInstance(3, Type.LIFO).loadImage(imgPath,
				viewHolder.vhImage);

		viewHolder.vhImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 已经被选择
				if (selectedImg.contains(imgPath)) {
					selectedImg.remove(imgPath);
					viewHolder.vhImage.setColorFilter(null);
					//viewHolder.vhButton.setImageResource(R.drawable.picture_unselected);
					viewHolder.vhButton.setVisibility(View.INVISIBLE);
				}
				// 未被选择
				else {
					selectedImg.add(imgPath);
					viewHolder.vhImage.setColorFilter(Color.parseColor("#77000000"));
					viewHolder.vhButton.setVisibility(View.VISIBLE);
					viewHolder.vhButton.setImageResource(R.drawable.tick);
				}
				//notifyDataSetChanged();
			}
		});

		if (selectedImg.contains(imgPath)) {
			viewHolder.vhImage.setColorFilter(Color.parseColor("#77000000"));
			viewHolder.vhButton.setVisibility(View.VISIBLE);
			viewHolder.vhButton.setImageResource(R.drawable.tick);
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView vhImage;
		ImageButton vhButton;
	}
}