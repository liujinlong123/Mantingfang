package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

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

public class ThirdThreeAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;

	public ThirdThreeAdapter(Context context, List<UserTwoContent> list) {
		this.list = list;
		mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
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

			holder = new ViewHolder();
			holder.linearHead = (LinearLayout) view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (CircleImageView) view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView) view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView) view.findViewById(R.id.third_pager_user_time);
			holder.content = (TextView) view.findViewById(R.id.third_pager_user_content);
			holder.grdview = (KindGridView) view.findViewById(R.id.third_pager_user_grdphoto);
			holder.linearPoem = (LinearLayout) view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView) view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView) view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView) view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView) view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView) view.findViewById(R.id.third_pager_one_share);

			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		UserTwoContent content = list.get(position);
		initViews(content, holder);

		return view;
	}

	final static class ViewHolder {
		LinearLayout linearHead;

		CircleImageView headPhoto;

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

	private void initViews(final UserTwoContent content, ViewHolder holder) {
		// 头像路径
		holder.headPhoto.setImageBitmap(content.getHeadPhoto());

		// 用户昵称
		// Log.v("userName", content.getName());
		holder.userName.setText(content.getName());
		// 发表时间
		holder.time.setText(content.getTime());
		// 发表内容
		holder.content.setText(content.getContent());
		// 初始化图片
		// initGridView(content.getPicture(), holder);
		// 相关诗词
		holder.linearPoem.setVisibility(View.GONE);
		// holder.poemName.setText(content.getPoem().getTitle());
		// holder.poemQuote.setText(content.getPoem().getContent());

		// 跳转用户详情
		holder.linearHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showUserDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
			}
		});

		// 跳转内容
		holder.content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0, content, content.getPostComPId() + "", content.getPostComNum() + "",
						content.getHeadPath());
			}
		});

		// 点赞
		holder.zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 评论
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showComment(mContext, 0, content.getPostComPId() + "", content.getPostComNum() + "");

			}
		});

		// 分享
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
