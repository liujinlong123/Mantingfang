package com.android.mantingfanggsc;

import java.util.List;

import com.android.mantingfang.first.PoemRhesis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemPoemAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private List<PoemRhesis> list;
	private LayoutInflater inflater;
	
	public ItemPoemAdapter(Context context, List<PoemRhesis> list) {
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
			view = inflater.inflate(R.layout.item_listview_wenku_poem, null);
			holder = new ViewHolder();
			holder.poemName = (TextView)view.findViewById(R.id.poemlist_tv_name);
			holder.poemRhesis = (TextView)view.findViewById(R.id.poemlist_tv_poemrhesis);
			holder.wirterName = (TextView)view.findViewById(R.id.poemlist_tv_writername);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		PoemRhesis poem = list.get(position);
		
		holder.poemName.setText(poem.getPoemName());
		holder.poemRhesis.setText(poem.getRhesis());
		//holder.poemRhesis.setText(poem.getRhesis());
		holder.wirterName.setText("[" + poem.getDynasty() + "]" + poem.getWriter());
		
		return view;
	}
	
	final static class ViewHolder {
		TextView poemName;
		
		TextView poemRhesis;
		
		TextView wirterName;
	}

}
