package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
			view = inflater.inflate(R.layout.item_listview_shiyou, null);
			holder = new ViewHolder();
			holder.linearHead = (LinearLayout)view.findViewById(R.id.thirdlist_linear_headuser);
			holder.headPhoto = (ImageView)view.findViewById(R.id.thirdlist_img_headPhoto);
			holder.userName = (TextView)view.findViewById(R.id.thirdlist_tv_username);
			holder.more = (ImageView)view.findViewById(R.id.thirdlist_img_more);
			holder.content = (TextView)view.findViewById(R.id.thirdlist_tv_content);
			holder.linearPoem = (LinearLayout)view.findViewById(R.id.thirdlist_linear_poem);
			holder.poemName = (TextView)view.findViewById(R.id.thirdlist_tv_poemName);
			holder.poemQuote = (TextView)view.findViewById(R.id.thirdlist_tv_poem);
			holder.time = (TextView)view.findViewById(R.id.thirdlist_tv_time);
			holder.share = (ImageView)view.findViewById(R.id.thirdlist_img_share);
			holder.comment = (ImageView)view.findViewById(R.id.thirdlist_img_comment);
			holder.collect = (ImageView)view.findViewById(R.id.thirdlist_img_collect);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		initViews(holder, list);
		return view;
	}
	
	final static class ViewHolder {
		LinearLayout linearHead;
		
		ImageView headPhoto;
		
		TextView userName;
		
		ImageView more;
		
		TextView content;
		
		LinearLayout linearPoem;
		
		TextView poemName;
		
		TextView poemQuote;
		
		TextView time;
		
		ImageView share;
		
		ImageView comment;
		
		ImageView collect;
	}

	private void initViews(ViewHolder holder, List<ThirdContent> list) {
		holder.headPhoto.setImageResource(R.drawable.ic_launcher);
		holder.userName.setText("user");
		holder.linearHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "跳转到用户界面", Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.poemName.setText("静夜思");
		holder.content.setText("窗前明月光,疑是地上霜,举杯邀明月,低头思故乡");
		holder.poemQuote.setText("窗前明月光,\n\n疑是地上霜.\n\n举杯邀明月,\n\n低头思故乡.");
		holder.linearPoem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "跳转到诗", Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.time.setText("09:00");
		
		holder.share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "comment", Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "collect", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
