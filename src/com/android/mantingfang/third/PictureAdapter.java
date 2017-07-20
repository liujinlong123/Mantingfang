package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PictureAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private List<String> list;
	private LayoutInflater inflater;
	
	public PictureAdapter(Context context, List<String> list) {
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list == null? 0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.picture_item, null);
			holder = new ViewHolder();
			
			holder.img = (ImageView)view.findViewById(R.id.img_picture);
			holder.imgDel = (ImageView)view.findViewById(R.id.img_del);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		Bitmap bitmap = BitmapFactory.decodeFile(list.get(position));
		holder.img.setImageBitmap(bitmap);
		
		
		return view;
	}
	
	final static class ViewHolder {
		ImageView img;
		
		ImageView imgDel;
	}
	
}
