package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureAdapter extends BaseAdapter {

	private Context mContext;
	private List<Bitmap> list;
	private LayoutInflater inflater;
	
	public PictureAdapter(Context context, List<Bitmap> list) {
		this.mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list == null? 0: list.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view;
		if (convertView == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.picture_item, null);
			holder.img = (ImageView)view.findViewById(R.id.add_one_pic);
			holder.del = (ImageView)view.findViewById(R.id.add_one_del);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		Bitmap bp = list.get(position);
		holder.img.setImageBitmap(bp);
		
		if (position == list.size() - 1) {
			holder.del.setVisibility(View.INVISIBLE);
		} else {
			holder.img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 放大显示
					Toast.makeText(mContext, "放大显示", Toast.LENGTH_SHORT).show();
				}
			});
			
			holder.del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		return view;
	}
	
	final static class ViewHolder {
		ImageView img;
		
		ImageView del;
	}

}
