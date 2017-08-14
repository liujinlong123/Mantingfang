package com.android.mantingfang.third;

import java.util.List;

import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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

public class CommentAdapter extends BaseAdapter {
	
	private static final int VALUE_COMMENT_ONE = 0;
	private static final int VALUE_COMMENT_TWO = 1;

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
		int type = getItemViewType(position);
		if (convertView == null) {
			view = inflater.inflate(R.layout.comment_itemlist, null);
			holder = new ViewHolder();
			
			holder.imgHead = (CircleImageView)view.findViewById(R.id.comment_item_headphoto);
			holder.tvName = (TextView)view.findViewById(R.id.comment_item_name);
			holder.time = (TextView)view.findViewById(R.id.comment_item_time);
			holder.zan = (ImageView)view.findViewById(R.id.comment_item_zan);
			holder.content = (TextView)view.findViewById(R.id.comment_item_content);
			holder.beContent = (TextView)view.findViewById(R.id.comment_item_huifu);
			holder.zanNum = (TextView)view.findViewById(R.id.comment_zan_number);
			holder.linear = (LinearLayout)view.findViewById(R.id.comment_linear_huifu);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		
		//控件设置--昵称--时间--头像--赞--赞的数量
		CommentContent content = list.get(position);
		
		holder.tvName.setText(content.getName());
		holder.time.setText(content.getTime());
		PictureLoad.getInstance().loadImage(content.getHeadPath(), holder.imgHead);
		if (content.getZan().equals("1")) {
			holder.zan.setImageResource(R.drawable.a7u);
		} else {
			holder.zan.setImageResource(R.drawable.a7r);
		}
		initZan(holder.zan, content);
		if (!content.getZanNumber().equals("") && content.getZanNumber() != null) {
			holder.zanNum.setText(content.getZanNumber());
		} else {
			holder.zanNum.setText("0");
		}
		
		//内容
		switch (type) {
		case VALUE_COMMENT_ONE:
			holder.content.setText(content.getContent());
			Log.v("TEST", content.getBePostUserId() + "-----");
			break;
			
		case VALUE_COMMENT_TWO:
			holder.linear.setVisibility(View.VISIBLE);
			//holder.content.setText("回复@" + content.getBePostNickame() + ":" + content.getContent());
			//holder.beContent.setText("@" + content.getBePostNickame() + ":" + content.getBePostContent());
			
			//评论内容
			holder.content.setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
			
			SpannableString spanableInfo = new SpannableString("回复@" + content.getBePostNickame() + ":" + content.getContent());
			spanableInfo.setSpan(new Clickable(clickListener),2,3 + content.getBePostNickame().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.content.setText(spanableInfo);
			holder.content.setMovementMethod(LinkMovementMethod.getInstance());
			
			//被评论内容
			holder.beContent.setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
			
			SpannableString spanableInfoTwo = new SpannableString("@" + content.getBePostNickame() + ":" + content.getBePostContent());
			spanableInfoTwo.setSpan(new Clickable(clickListener),0,content.getBePostNickame().length() + 1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.beContent.setText(spanableInfoTwo);
			holder.beContent.setMovementMethod(LinkMovementMethod.getInstance());
			break;
		}
		
		return view;
	}
	
	private void initZan(final ImageView zan, final CommentContent content) {
		zan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (content.getZan() != null) {
					if (content.getZan().equals("0")) {
						zan.setImageResource(R.drawable.a7u);
						content.setZan("1");
						sendZan(UserId.getInstance(mContext).getUserId(), content.getPostId() + "", "1");
					} else if (content.getZan().equals("1")){
						zan.setImageResource(R.drawable.a7r);
						content.setZan("0");
						sendZan(UserId.getInstance(mContext).getUserId(), content.getPostId() + "", "0");
					}
				}
			}
		});
	}
	
	final static class ViewHolder {
		CircleImageView imgHead;
		
		TextView tvName;
		
		TextView time;
		
		ImageView zan;
		
		TextView content;
		
		TextView beContent;
		
		TextView zanNum;
		
		LinearLayout linear;
	}
	
	private OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(mContext, "点击成功....",Toast.LENGTH_SHORT).show();
		}
	};
	
	
	class Clickable extends ClickableSpan{
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;
		}

		/**
		 * 重写父类点击事件
		 */
		@Override
		public void onClick(View v) {
			mListener.onClick(v);
		}

		/**
		 * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
		 */
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(mContext.getResources().getColor(R.color.blue));
		}
	}
	
	/**
	 * 根据数据源的position返回需要显示的的layout的type
	 * 
	 * type的值必须从0开始
	 * 
	 * */
	@Override
	public int getItemViewType(int position) {
		CommentContent content = list.get(position);
		if (content.getBePostId() == null || content.getBePostId().equals("-1") || content.getBePostId().equals("")) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * 返回所有的layout的数量
	 * 
	 * */
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	private void sendZan(final String userId, final String commentId, final String zan) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postCommentZan(userId, commentId, zan);
			}
			
			@Override
			protected void onPostExecute(String result) {
				
			}
			
		};
		
		task.execute();
	}
}