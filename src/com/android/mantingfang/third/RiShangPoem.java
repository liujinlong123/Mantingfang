package com.android.mantingfang.third;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RiShangPoem extends Fragment {

	private View view;
	private TextView poemName;
	private TextView poemWriter;
	private TextView poemContent;
	
	private String name;
	private String writer;
	private String content;
	
	public RiShangPoem(String poemName, String writerName, String poemContent)  {
		this.name = poemName;
		this.writer = writerName;
		this.content = poemContent;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.rishang_poem, null);
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		poemName = (TextView)view.findViewById(R.id.rishang_poem_name);
		poemWriter = (TextView)view.findViewById(R.id.rishang_poem_writer);
		poemContent = (TextView)view.findViewById(R.id.rishang_poem_content);
		
		SharedPreferences pref = getActivity().getSharedPreferences("data", FragmentActivity.MODE_PRIVATE);
		int type = pref.getInt("rishang_fonts_type", -1);
		poemName.setText(name);
		poemWriter.setText(writer);
		poemContent.setText(getPoem(content));
		if (type > -1 && type < 3) {
			setFronts(type);
		}
	}
	
	private String getPoem(String poem) {
		String str = "";
		String[] tokens = poem.split("[。]");
		for (String e: tokens) {
			str += (e + "\n");
		}
		
		return str;
	}
	
	public void setContent(String name, String writer, String content) {
		poemName.setText(name);
		poemWriter.setText(writer);
		poemContent.setText(getPoem(content));
	}
	
	public void setFronts(int type) {
		switch(type) {
		case 0:
			poemName.setTypeface(RiShangPager.typefaceKT);
			poemWriter.setTypeface(RiShangPager.typefaceKT);
			poemContent.setTypeface(RiShangPager.typefaceKT);
			break;
			
		case 1:
			poemName.setTypeface(RiShangPager.typefaceLS);
			poemWriter.setTypeface(RiShangPager.typefaceLS);
			poemContent.setTypeface(RiShangPager.typefaceLS);
			break;
			
		case 2:
			poemName.setTypeface(RiShangPager.typefaceHWXK);
			poemWriter.setTypeface(RiShangPager.typefaceHWXK);
			poemContent.setTypeface(RiShangPager.typefaceHWXK);
			break;
		}
	}
}
