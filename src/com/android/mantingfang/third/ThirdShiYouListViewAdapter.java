package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdShiYouListViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<ThirdContent> list;
	
	public ThirdShiYouListViewAdapter(Context context, List<ThirdContent> list) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list == null? 0 : list.size();
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
			view = inflater.inflate(R.layout.activity_main, null);
		}
		return null;
	}
	
	final static class ViewHolder {
		ImageView headPhoto;
		
		TextView userName;
		
		ImageView more;
		
		TextView content;
		
		TextView poemQuote;
		
		TextView time;
		
		ImageView share;
		
		ImageView comment;
		
		ImageView collect;
		
		
	}

}
