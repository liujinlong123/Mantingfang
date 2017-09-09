package com.android.mantingfang.second;

import java.util.List;

import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KindPictureAdapter extends BaseAdapter {

	private Context mContext;
	private List<KindPictureAll> list;
	private LayoutInflater inflater;
	
	public KindPictureAdapter(Context context, List<KindPictureAll> list) {
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
			view = inflater.inflate(R.layout.item_gridview_wenku, null);
			holder = new ViewHolder();
			holder.img = (CircleImageView)view.findViewById(R.id.secondgrd_img_kind);
			holder.text = (TextView)view.findViewById(R.id.secondgrd_tv_kindname);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		KindPictureAll kinds = list.get(position);
		//holder.img.setImageResource(kinds.getImgId());
		PictureLoadSecond.getInstance(mContext).loadImage(kinds.getImgId() + "", holder.img, kinds.getImgId());
		holder.text.setText(kinds.getLabelName());
		
		return view;
	}
	
	class ViewHolder {
		CircleImageView img;
		
		TextView text;
	}

}
