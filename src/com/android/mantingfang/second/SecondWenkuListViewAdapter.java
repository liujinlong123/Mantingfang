package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SecondWenkuListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<KindContent> list;
	private LayoutInflater inflater;
	
	private KindPictureAdapter adapter;
	//List<KindPictureAll> dataList;
	
	public SecondWenkuListViewAdapter(Context context, List<KindContent> list) {
		this.list = list;
		mContext = context;
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

	@SuppressWarnings({ })
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_listview_wenku, null);
			holder = new ViewHolder();
			holder.tvKind = (TextView)view.findViewById(R.id.secondlist_tv_kind);
			holder.grdKind = (KindGridView)view.findViewById(R.id.secondlist_grd_kind);
			
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		KindContent content = list.get(position);
		
		initGridView(content, holder, position);
		
		return view;
	}
	
	private class ViewHolder {
		TextView tvKind;
		
		GridView grdKind;
	}
	
	private void initGridView(final KindContent content, ViewHolder holder, final int listPosition) {
		holder.tvKind.setText(content.getKindName());
		holder.grdKind.setNumColumns(4);
		if (content.getSingleName() != null) {
			adapter = new KindPictureAdapter(mContext, getData(content));
			holder.grdKind.setAdapter(adapter);
		}
		
		final String kindName = content.getKindName();
		final List<SingleNames> label = content.getSingleName();
		final int[] pictures = content.getImages();
		
		holder.grdKind.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(view.getContext(), SecondWenkuPoem.class);
				Bundle bundle = new Bundle();
				bundle.putString("kindName", kindName);
				bundle.putString("singlename", label.get(position).getLableName());
				Log.v("singlename: ", label.get(position).getLableName());
				
				bundle.putInt("label_id", label.get(position).getLabelId());
				bundle.putInt("imgId", pictures[position]);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
	}
	
	private List<KindPictureAll> getData(KindContent content) {
		List<SingleNames> text = content.getSingleName();
		int[] pictures = content.getImages();
		List<KindPictureAll> dataList = new ArrayList<>();
		for (int i = 0; i < text.size(); i++) {
			dataList.add(new KindPictureAll(text.get(i).getLableName(), pictures[i]));
		}
		
		return dataList;
	}

}
