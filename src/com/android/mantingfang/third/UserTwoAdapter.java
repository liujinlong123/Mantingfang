package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfang.third.ThirdOneAdapter.ViewHolder;
import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserTwoAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;

	public UserTwoAdapter(Context context, List<UserTwoContent> list) {
		this.mContext = context;
		this.list = list;
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
			holder.linearHead = (LinearLayout) view.findViewById(R.id.third_pager_one_listhead);
			holder.headPhoto = (ImageView) view.findViewById(R.id.third_pager_user_photo);
			holder.userName = (TextView) view.findViewById(R.id.third_pager_user_name);
			holder.time = (TextView) view.findViewById(R.id.third_pager_user_time);
			holder.content = (TextView) view.findViewById(R.id.third_pager_user_content);
			holder.linearSound = (LinearLayout) view.findViewById(R.id.third_pager_sound);
			holder.imgSound = (ImageView) view.findViewById(R.id.third_pager_img_sound);
			holder.grdview = (KindGridView) view.findViewById(R.id.third_pager_user_grdphoto);
			holder.linearPoem = (LinearLayout) view.findViewById(R.id.third_pager_linearPoem);
			holder.poemName = (TextView) view.findViewById(R.id.third_pager_tv_poemName);
			holder.poemQuote = (TextView) view.findViewById(R.id.third_pager_tv_poem);
			holder.zan = (ImageView) view.findViewById(R.id.third_pager_one_zan);
			holder.comment = (ImageView) view.findViewById(R.id.third_pager_one_comment);
			holder.share = (ImageView) view.findViewById(R.id.third_pager_one_share);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		UserTwoContent content = list.get(position);
		//initViews(content, holder, view);

		return view;
	}

	final static class ViewHolder {
		LinearLayout linearHead;

		ImageView headPhoto;

		TextView userName;

		TextView time;

		TextView content;

		LinearLayout linearSound;

		ImageView imgSound;

		KindGridView grdview;

		LinearLayout linearPoem;

		TextView poemName;

		TextView poemQuote;

		ImageView zan;

		ImageView comment;

		ImageView share;
	}

	private void initViews(UserTwoContent content, ViewHolder holder, View view) {
		// ��ͬ����
		String path = content.getHeadPath();

		// �ǳ�
		//holder.userName.setText(content.getName());
		// ʱ��
		//holder.time.setText(content.getTime());

		// ͷ�����¼�
		holder.linearHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserPager.class);
				mContext.startActivity(intent);
			}
		});

		// ���ް�ť���ʱ��
		holder.zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// ���۰�ť����¼�
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// ����ť����¼�
		holder.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		
		if (content.getPostComNum() == 1) {
			//����
			holder.content.setText(content.getContent());
			//����ͼƬ
			initGridView(content.getPicture(), holder);
			//����ʫ��
			holder.poemName.setText(content.getPoemName());
			holder.poemQuote.setText(content.getPoemContent());
			holder.linearPoem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��תʫ��", Toast.LENGTH_SHORT).show();
				}
			});
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��ת����", Toast.LENGTH_SHORT).show();
				}
			});
		} 
		else if (content.getPostComNum() == 2) {
			//����
			holder.content.setText(content.getContent());
			//����ͼƬ
			initGridView(content.getPicture(), holder);
			//����ʫ��
			holder.poemName.setText(content.getPoemName());
			holder.poemQuote.setText(content.getPoemContent());
			holder.linearPoem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��תʫ��", Toast.LENGTH_SHORT).show();
				}
			});
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��ת����", Toast.LENGTH_SHORT).show();
				}
			});
		} 
		else if (content.getPostComNum() == 3) {
			//����
			holder.content.setText(content.getContent());
			//����ͼƬ
			initGridView(content.getPicture(), holder);
			holder.linearPoem.setVisibility(View.GONE);
			holder.content.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��ת����", Toast.LENGTH_SHORT).show();
				}
			});
		} 
		else if (content.getPostComNum() == 4) {
			holder.content.setVisibility(View.GONE);
			
			holder.linearSound.setVisibility(View.VISIBLE);
			
			holder.linearSound.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			//����ʫ��
			holder.poemName.setText(content.getPoemName());
			holder.poemQuote.setText(content.getPoemContent());
			holder.linearPoem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, "��תʫ��", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	private void initGridView(ArrayList<String> picture, ViewHolder holder) {
		holder.grdview.setNumColumns(3);
		if (picture.size() == 0 || picture == null) {
			holder.grdview.setVisibility(View.GONE);
		} else {
			
		}
	}

}
