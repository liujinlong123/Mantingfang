package com.android.mantingfang.third;

import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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

public class ThirdFourAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<UserTwoContent> list;
	
	public ThirdFourAdapter(Context context, List<UserTwoContent> list) {
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
			holder.linearSound = (LinearLayout)view.findViewById(R.id.third_pager_sound);
			holder.imgSound = (ImageView)view.findViewById(R.id.third_pager_img_sound);
			//holder.grdview = (KindGridView)view.findViewById(R.id.third_pager_user_grdphoto);
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
		
		UserTwoContent content = list.get(position);
		initViews(content, holder);
		
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
		
		//KindGridView grdview;
		
		LinearLayout linearPoem;
		
		TextView poemName;
		
		TextView poemQuote;
		
		ImageView zan;
		
		ImageView comment;
		
		ImageView share;
	}

	private void initViews(final UserTwoContent content, final ViewHolder holder) {
		//����ͷ��
		//String path = content.getHeadPath();
		
		
		//�ǳ�
		holder.userName.setText(content.getName());
		//ʱ��
		holder.time.setText(content.getTime());
		//����
		holder.content.setVisibility(View.GONE);
		
		holder.linearSound.setVisibility(View.VISIBLE);
		//����ͼƬ
		//initGridView(content.getPicture(), holder);
		//����ʫ��
		holder.poemName.setText(content.getPoemName());
		holder.poemQuote.setText(content.getPoemContent());
		
		//ͷ�����¼�
		holder.linearHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showUserDetail(mContext, 0, content.getUserId(), content.getHeadPath(), content.getName());
			}
		});
		
		//content����¼�
		holder.content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showCommentMain(mContext, 0);
			}
		});
		
		//�漰ʫ�ʵ���¼�
		holder.linearPoem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(content.getPoemId(), holder);
			}
		});
		
		//���ް�ť���ʱ��
		holder.zan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//���۰�ť����¼�
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showComment(mContext, 0);
			}
		});
		
		//����ť����¼�
		holder.share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void getData(final String poem_id, final ViewHolder holder) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				return MyClient.getInstance().http_postPoem(poem_id);
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("TEST", result);
				final List<PoemM> poemList;
				try {
					poemList = PoetryList.parsePoem(StringUtils.toJSONArray(result)).getPoemMList();
					if (poemList != null) {

						UIHelper.showPoemMDetail(mContext, poemList.get(0), 0);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		task.execute();
	}
}
