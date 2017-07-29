package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private List<CommentContent> list;
	private LayoutInflater inflater;
	
	public CommentAdapter(Context context, List<CommentContent> list) {
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
			view = inflater.inflate(R.layout.comment_itemlist, null);
			holder = new ViewHolder();
			
			holder.imgHead = (CircleImageView)view.findViewById(R.id.comment_item_headphoto);
			holder.tvName = (TextView)view.findViewById(R.id.comment_item_name);
			holder.time = (TextView)view.findViewById(R.id.comment_item_time);
			holder.zan = (ImageView)view.findViewById(R.id.comment_item_zan);
			holder.content = (TextView)view.findViewById(R.id.comment_item_content);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		
		//控件设置
		CommentContent content = list.get(position);
		
		if (content.getName() != null && content.getHeadPath() != null && content.getTime() != null && content.getContent() != null) {
			holder.tvName.setText(content.getName());
			holder.time.setText(content.getTime());
			holder.content.setText(content.getContent());
			PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.imgHead);
		}
		
		
		return view;
	}
	
	final static class ViewHolder {
		CircleImageView imgHead;
		
		TextView tvName;
		
		TextView time;
		
		ImageView zan;
		
		TextView content;
	}
	
}