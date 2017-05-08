package com.android.mantingfang.second;

import java.util.List;

import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecondWriterListViewAdapter extends BaseAdapter{

	private List<Poem> list;
	private Context mContext;
	private LayoutInflater inflater;
	
	public SecondWriterListViewAdapter(Context context, List<Poem> list) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
		notifyDataSetChanged();
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
			view = inflater.inflate(R.layout.item_listview_writer, null);
			holder = new ViewHolder();
			holder.dynasty = (TextView)view.findViewById(R.id.secondlist_writer_dynasty);
			holder.writerName = (TextView)view.findViewById(R.id.secondlist_writer_name);
			holder.poemRhesis = (TextView)view.findViewById(R.id.secondlist_poem_rhesis);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		Poem poem = list.get(position);
		initViews(holder, poem);
		
		return view;
	}
	
	final static class ViewHolder {
		TextView dynasty;
		
		TextView writerName;
		
		TextView poemRhesis;
	}
	
	private void initViews(ViewHolder holder, Poem poem) {
		holder.dynasty.setText("唐朝");
		holder.writerName.setText("李白");
		holder.poemRhesis.setText("窗前明月光，疑是地上霜");
	}

}
