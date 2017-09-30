package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.Set;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class FirstPagerAdd extends Activity {
	
	private GridView grdChoose;
	private Button save;
	private GridViewAdapter adapter;
	private Set<Integer> setImgs;
	
	private int[] imgInt = {R.drawable.interest_ziran, R.drawable.interest_aiqing, R.drawable.interest_nvxing,
			R.drawable.interest_zheli, R.drawable.interest_lishi, R.drawable.interest_lvyou,
			R.drawable.interest_renjijiaowang, R.drawable.interest_sijixinqing, R.drawable.interest_riyueshanchuan,
			R.drawable.interest_haofang, R.drawable.interest_wanyueshiren, R.drawable.interest_lizhishiren,
			R.drawable.interest_gudushiren, R.drawable.interest_beitan, R.drawable.interest_chouku};
	
	public static String[] titles = {"自然", "爱情", "女性",
			"哲理", "历史", "旅游",
			"人际交往", "四季心情", "日月山川",
			"豪放诗人", "婉约诗人", "励志诗人",
			"孤独诗人", "悲叹诗人", "愁苦诗人"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_pager_add);
		setImgs = ChoosePicture.getInstance(FirstPagerAdd.this).getChoose();
		
		grdChoose = (GridView)findViewById(R.id.grd_choose_tuisong);
		save = (Button)findViewById(R.id.choose_save);
		
		grdChoose.setSelector(new ColorDrawable(Color.TRANSPARENT)); 
		grdChoose.setNumColumns(3);
		adapter = new GridViewAdapter();
		grdChoose.setAdapter(adapter);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ChoosePicture.getInstance(FirstPagerAdd.this).saveChoose(setImgs);
				Intent intent = new Intent();
				ArrayList<Integer> list = new ArrayList<>(setImgs);
				if (list != null && list.size() != 0) {
					if (list.size() > 1) {
						intent.putExtra("choose", titles[list.get(0)].substring(0, 2) + "等");
					} else if (list.size() == 1) {
						intent.putExtra("choose", titles[list.get(0)].substring(0, 2));
					} else if (list.size() == 15) {
						intent.putExtra("choose", "全部");
					}
				} else {
					intent.putExtra("choose", "全部");
				}
				
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	class GridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(FirstPagerAdd.this).inflate(R.layout.grd_choose_item, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.grd_choose_title);
				holder.imgTitle = (ImageView)convertView.findViewById(R.id.grd_choose_img);
				holder.choose = (ImageView)convertView.findViewById(R.id.grd_if_choose);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tvTitle.setText(titles[position]);
			holder.imgTitle.setImageResource(imgInt[position]);
			if (setImgs.contains(position)) {
				holder.choose.setVisibility(View.VISIBLE);
			} else {
				holder.choose.setVisibility(View.INVISIBLE);
			}
			initViews(holder, position);
			
			return convertView;
		}
		
	}
	
	private void initViews(final ViewHolder holder, final int position) {
		holder.imgTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (setImgs.contains(position)) {
					holder.choose.setVisibility(View.INVISIBLE);
					setImgs.remove(position);
				} else {
					holder.choose.setVisibility(View.VISIBLE);
					setImgs.add(position);
				}
			}
		});
	}
	
	private class ViewHolder {
		TextView tvTitle;
		
		ImageView imgTitle;
		
		ImageView choose;
	}
}
