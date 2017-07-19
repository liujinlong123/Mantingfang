package com.android.mantingfanggsc;

import java.util.List;

import com.android.mantingfang.bean.Base;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WriterDetailListAdapter extends BaseAdapter {

	private static final int TYPE_ONE = 0;
	private static final int TYPE_TWO = 1;
	
	//private Context context;
	private List<? extends Base> list;
	private LayoutInflater inflater;
	
	
	public WriterDetailListAdapter(Context context, List<? extends Base>list) {
		//this.context = context;
		this.list = list;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getItemViewType(int position) {
		int p = position;
		if (p == 0) {
			return TYPE_ONE;
		} else {
			return TYPE_TWO;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getCount() {
		return list.size();
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
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		int type = getItemViewType(position);
		
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				convertView = inflater.inflate(R.layout.writerdetail_item_one, null);
				holder1 = new ViewHolder1();
				holder1.writername = (TextView)convertView.findViewById(R.id.writerdetail_one_tv_writername);
				holder1.themename = (TextView)convertView.findViewById(R.id.writerdetail_one_tv_themename);
				holder1.themedetail = (TextView)convertView.findViewById(R.id.writerdetail_one_tv_detail);
				holder1.works = (TextView)convertView.findViewById(R.id.writerdetail_one_works);
				
				convertView.setTag(holder1);
				break;
				
			case TYPE_TWO:
				convertView = inflater.inflate(R.layout.writerdetail_item_two, null);
				holder2 = new ViewHolder2();
				holder2.poemname = (TextView)convertView.findViewById(R.id.writerdetail_item_two_poemname);
				holder2.kindname = (TextView)convertView.findViewById(R.id.writerdetail_item_two_kind);
				holder2.poemrhesis = (TextView)convertView.findViewById(R.id.writerdetail_item_two_rhesis);
				
				convertView.setTag(holder2);
				break;
				}
		} else {
			switch (type) {
			case TYPE_ONE:
				holder1 = (ViewHolder1)convertView.getTag();
				holder1.writername.setText("白居易");
				holder1.themename.setText("���");
				holder1.themedetail.setText("�׾��׵ļ��");
				holder1.works.setText("��Ʒ/118");
				break;
				
			case TYPE_TWO:
				holder2 = (ViewHolder2)convertView.getTag();
				holder2.poemname.setText("®ɽ���ü�");
				holder2.kindname.setText("[��]");
				holder2.poemrhesis.setText("��®���㣬������ɽ");
				break;
			}
		}
		
		return convertView;
	}
	
	static class ViewHolder1 {
		TextView writername;
		
		TextView themename;
		
		TextView themedetail;
		
		TextView works;
	}
	
	static class ViewHolder2 {
		TextView poemname;
		
		TextView kindname;
		
		TextView poemrhesis;
	}

}
