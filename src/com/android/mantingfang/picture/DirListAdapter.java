package com.android.mantingfang.picture;

import java.util.List;

import com.android.mantingfanggsc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DirListAdapter extends ArrayAdapter<FolderBean> {

	private LayoutInflater inflater;

	private List<FolderBean> list;

	public DirListAdapter(Context context, List<FolderBean> list) {
		super(context,0, list);		
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder vh = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_popup_main, parent,
					false);
			vh = new ViewHolder();
			vh.img = (ImageView) convertView
					.findViewById(R.id.id_dir_item_image);
			vh.dirName = (TextView) convertView
					.findViewById(R.id.id_dir_item_name);
			vh.dirCount = (TextView) convertView
					.findViewById(R.id.id_dir_item_count);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		FolderBean bean = this.getItem(position);
		if(vh != null) {
			vh.img.setImageResource(R.drawable.picture_no);
			ImageLoader.getInstance().loadImage(bean.getFirstImgPath(), vh.img);
			
			
			vh.dirCount.setText(bean.getCount() + "");
			vh.dirName.setText(bean.getName());
		}
		

		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		TextView dirName;
		TextView dirCount;
	}

}
