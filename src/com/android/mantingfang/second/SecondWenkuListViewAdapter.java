package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SecondWenkuListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<KindContent> list;
	private LayoutInflater inflater;
	
	private SimpleAdapter adapter;
	List<Map<String, Object>> dataList;
	
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
		
		initGridView(content, holder);
		
		return view;
	}
	
	private class ViewHolder {
		TextView tvKind;
		
		GridView grdKind;
	}
	
	private void initGridView(KindContent content, ViewHolder holder) {
		holder.tvKind.setText(content.getKindName());
		holder.grdKind.setNumColumns(4);
		adapter = new SimpleAdapter(mContext, getData(content), R.layout.item_gridview_wenku, new String[]{"image", "text"}, new int[]{R.id.secondgrd_img_kind, R.id.secondgrd_tv_kindname});
		holder.grdKind.setAdapter(adapter);
		holder.grdKind.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(mContext, "fuck you", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private List<Map<String, Object>> getData(KindContent content) {
		//int[] images = content.getImages();
		String[] text = content.getSingleName();
		dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < text.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.ic_launcher);
			map.put("text", text[i]);
			dataList.add(map);
		}
		
		return dataList;
	}

}
