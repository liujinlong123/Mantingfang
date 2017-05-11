package com.android.mantingfang.second;

import java.util.List;

import com.android.mantingfang.bean.URLs;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecondWenkuPoemListAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private List<Poem> list;
	private LayoutInflater inflater;
	
	public SecondWenkuPoemListAdapter(Context context, List<Poem> list) {
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
		
		Poem poem = list.get(position);
		
		holder.poemName.setText(poem.getTitle());
		holder.poemRhesis.setText("关关雎鸠，在河之洲");
		//holder.poemRhesis.setText(poem.getRhesis());
		//holder.wirterName.setText("[周]无名氏");
		holder.wirterName.setText("[" + URLs.DYNASTYS[poem.getDynastyid()] + "]" + poem.getWritername());
		
		return view;
	}
	
	final static class ViewHolder {
		TextView poemName;
		
		TextView poemRhesis;
		
		TextView wirterName;
	}

}
