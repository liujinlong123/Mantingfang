package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentFrist extends Fragment {
	private View view;
	
	//ViewPager
	private ViewPager viewPager;
	private List<PoemRhesis> dataList;
	private ViewAdapter adapter;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	private Button btnAdd;
	private ImageView imgCollect;
	private ImageView imgMore;
	private SharedPreferences pref;
	private String userId;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_first_pager, null);
			
			pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
			userId = pref.getString("userId", "-1");
			
			initViews();
			
			return view;
		}
		
		return view;
	}
	
	
	private void initViews() {
		btnAdd = (Button)view.findViewById(R.id.topbar_first_add);
		imgCollect = (ImageView)view.findViewById(R.id.topbar_first_collect);
		imgMore = (ImageView)view.findViewById(R.id.topbar_first_more);
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(userId) < 0) {
					Intent intentL = new Intent(getActivity(), LogOn.class);
					startActivity(intentL);
				} else if (Integer.parseInt(userId) > -1) {
					Intent intent = new Intent(getActivity(), FirstPagerAdd.class);
					startActivity(intent);
				}
				
			}
		});
		
		imgCollect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imgMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		initViewPager();
	}
	
	private void initViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.frag_first_viewpager);
		getData();
	}
	
	class ViewAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentsList;
		
		public ViewAdapter(FragmentManager fm) {
			super(fm);
		}

		public ViewAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragmentsList = fragments;
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentsList.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			//return super.getItemPosition(object);
			return POSITION_NONE;
		}
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postViewPager();
				//return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				//Log.v("TEEEEE", result);
				/*for (int i = 0; i < 100; i++) {
					fragmentList.add(new FragViewPager(new PoemRhesis("1", "无名氏" + i, "关关雎鸠，在河之洲")));
				}*/
				try {
					if (result != null && !result.equals("")) {
						dataList = TopicList.parseRhesis(StringUtils.toJSONArray(result)).getRhesisList();
						for (PoemRhesis e: dataList) {
							fragmentList.add(new FragViewPager(e));
						}
						
						viewPager.setOffscreenPageLimit(fragmentList.size());
						adapter = new ViewAdapter(getChildFragmentManager(), fragmentList);
						viewPager.setAdapter(adapter);
						viewPager.setCurrentItem(0);
					} else {
						Toast.makeText(getActivity(), "没有数据返回", Toast.LENGTH_SHORT).show();
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
