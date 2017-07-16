package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentTextAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<String> list;
	
	public CommentTextAdapter(Context context, List<String> list) {
		this.mContext = context;
		this.list = list;
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
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.comment_tv, null);
			holder.tv = (TextView)view.findViewById(R.id.commentlist_tv);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		holder.tv.setText(list.get(position));
		
		return view;
	}
	
	final static class ViewHolder {
		TextView tv;
	}

}
