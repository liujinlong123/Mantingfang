package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.bean.MyDao;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserCollectAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<Integer> list;
	//private PoetryDao poetryDao;
	private DynastyDao dynastyDao;
	private MyDao myDao;
	private boolean isCancel;
	
	public UserCollectAdapter(Context context, List<Integer> myIds, boolean isCancel) {
		this.mContext = context;
		this.list = myIds;
		inflater = LayoutInflater.from(context);
		//this.poetryDao = new PoetryDao(mContext);
		this.dynastyDao = new DynastyDao(mContext);
		this.myDao = new MyDao(context);
		this.isCancel = isCancel;
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
			view = inflater.inflate(R.layout.user_one_listview_item, null);
			holder = new ViewHolder();
			holder.poemName = (TextView)view.findViewById(R.id.user_one_list_name);
			holder.poemRhesis = (TextView)view.findViewById(R.id.user_one_list_poemrhesis);
			holder.writerName = (TextView)view.findViewById(R.id.user_one_list_writername);
			holder.imgCancel = (ImageView)view.findViewById(R.id.user_one_list_cancel);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		final int myId = list.get(position);
		//Poem poem = poetryDao.findPoemById(myId);
		//Log.v("poem", poem.getContent());
		
		/*holder.poemName.setText(poem.getTitle());
		holder.poemRhesis.setText("关关雎鸠，在河之洲");
		if (!isCancel) {
			//holder.writerName.setVisibility(View.VISIBLE);
			holder.writerName.setText("[" + dynastyDao.getDynastyById(poem.getDynastyid()).getDynastyName() + "]"
					+ poem.getWritername());
		}
		
		if (isCancel) {
			holder.imgCancel.setVisibility(View.VISIBLE);
			holder.writerName.setVisibility(View.GONE);
			holder.imgCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					myDao.deleteMy(myId);
				}
			});
		}*/
		
		return view;
	}
	
	final static class ViewHolder {
		TextView poemName;
		
		TextView poemRhesis;
		
		TextView writerName;
		
		ImageView imgCancel;
	}

}
