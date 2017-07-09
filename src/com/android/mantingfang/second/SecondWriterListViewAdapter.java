package com.android.mantingfang.second;

import java.util.List;

import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SecondWriterListViewAdapter extends BaseAdapter implements SectionIndexer{

	private List<SortModel> list;
	private Context mContext;
	private LayoutInflater inflater;
	
	public SecondWriterListViewAdapter(Context context, List<SortModel> list) {
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
		
		SortModel sortm = list.get(position);
		int section = getSectionForPosition(position);
		initViews(holder, sortm, section, position);
		
		return view;
	}
	
	final static class ViewHolder {
		TextView dynasty;
		
		TextView writerName;
		
		TextView poemRhesis;
	}
	
	private void initViews(ViewHolder holder, SortModel sort, int section, int position) {
		holder.dynasty.setText(sort.getDynastyName());
		holder.writerName.setText(sort.getName());
		holder.poemRhesis.setText("窗前明月光，疑是地上霜");
		//holder.poemRhesis.setText(sort.getPoetryRhesis());
		//根据position获取分类的首字母的Char ascii值
		
				
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			holder.dynasty.setVisibility(View.VISIBLE);
			holder.dynasty.setText(sort.getSortLetters());
		}
		else{
			holder.dynasty.setVisibility(View.GONE);
		}
		holder.writerName.setText(this.list.get(position).getName());
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

}
