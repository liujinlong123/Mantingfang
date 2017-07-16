package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdOneAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<ThirdOneContent> list;
	
	public ThirdOneAdapter(Context context, List<ThirdOneContent> list) {
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
			view = inflater.inflate(R.layout.third_pager_one_itemlist, null);
			holder = new ViewHolder();
			holder.linearHead = (LinearLayout)view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (ImageView)view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView)view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView)view.findViewById(R.id.third_pager_user_time);
			holder.content = (TextView)view.findViewById(R.id.third_pager_user_content);
			holder.grdview = (KindGridView)view.findViewById(R.id.third_pager_user_grdphoto);
			holder.linearPoem = (LinearLayout)view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView)view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView)view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView)view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView)view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView)view.findViewById(R.id.third_pager_one_share);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}
		
		ThirdOneContent content = list.get(position);
		initViews(content, holder, view);
		
		return view;
	}
	
	final static class ViewHolder {
		LinearLayout linearHead;
		
		ImageView headPhoto;
		
		TextView userName;
		
		TextView time;
		
		TextView content;
		
		KindGridView grdview;
		
		LinearLayout linearPoem;
		
		TextView poemName;
		
		TextView poemQuote;
		
		ImageView zan;
		
		ImageView comment;
		
		ImageView share;
	}

	private void initViews(ThirdOneContent content, ViewHolder holder, final View view) {
		//设置头像
		String path = content.getHeadPath();
		
		
		//昵称
		Log.v("userName", content.getName());
		holder.userName.setText(content.getName());
		//时间
		Log.v("time", content.getTime());
		holder.time.setText(content.getTime());
		//内容
		holder.content.setText(content.getContent());
		//设置图片
		//initGridView(content.getPicture(), holder);
		//设置诗词
		holder.linearPoem.setVisibility(View.GONE);
		
		//头像点击事件
		holder.linearHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showUserDetail(mContext, 0);
			}
		});
		
		//content点击事件
		holder.content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0);
			}
		});
		
		//点赞按钮点击时间
		holder.zan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//评论按钮点击事件
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showComment(mContext, 0);
			}
		});
		
		//分享按钮点击事件
		holder.share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initGridView(ArrayList<String> picture, ViewHolder holder) {
		holder.grdview.setNumColumns(3);
		if (picture.size() == 0 || picture == null) {
			holder.grdview.setVisibility(View.GONE);
		} else {
			
		}
	}
}
