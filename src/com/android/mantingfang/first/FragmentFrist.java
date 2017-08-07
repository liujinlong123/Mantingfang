package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
	
	private List<FragViewPager> fragmentList = new ArrayList<>();
	
	private Button btnAdd;
	private ImageView imgCollect;
	private ImageView imgMore;
	private SharedPreferences pref;
	private String userId;
	
	private String collect;
	
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
	
	
	@SuppressWarnings("deprecation")
	private void initViews() {
		btnAdd = (Button)view.findViewById(R.id.topbar_first_add);
		imgCollect = (ImageView)view.findViewById(R.id.topbar_first_collect);
		imgMore = (ImageView)view.findViewById(R.id.topbar_first_more);
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(UserId.getInstance(getContext()).getUserId()) < 0) {
					Intent intentL = new Intent(getActivity(), LogOn.class);
					startActivity(intentL);
				} else {
					Intent intent = new Intent(getActivity(), FirstPagerAdd.class);
					startActivity(intent);
				}
				
			}
		});
		
		imgMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity()).setTitle("字体")
				.setItems(R.array.item_fonts_dialog, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichcountry) {
						switch (whichcountry) {
						case 0:
							setFonts(0);
							break;
						case 1:
							setFonts(1);
							break;
						case 2:
							setFonts(2);
							break;
						}
						
					}
				}).show();
			}
		});
		
		viewPager = (ViewPager) view.findViewById(R.id.frag_first_viewpager);
		getData();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				//Log.v("Position", arg0 + "----");
				getCollection(dataList.get(arg0).getPoemId());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	class ViewAdapter extends FragmentPagerAdapter {
		
		public ViewAdapter(FragmentManager fm) {
			super(fm);
		}

		/*public ViewAdapter(FragmentManager fm, List<FragViewPager> fragments) {
			super(fm);
			this.fragmentsList = fragments;
		}*/

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			//return super.getItemPosition(object);
			return POSITION_NONE;
		}
		
		public void setFonts(int type) {
			for (FragViewPager e: fragmentList) {
				e.setFronts(type);
			}
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
				try {
					if (result != null && !result.equals("")) {
						dataList = TopicList.parseRhesis(StringUtils.toJSONArray(result)).getRhesisList();
						for (PoemRhesis e: dataList) {
							fragmentList.add(new FragViewPager(e, getActivity(), Fonts.getInstance(getActivity()).getType()));
						}
						RhesisList.getInstance().setRhesisList(dataList);
						viewPager.setOffscreenPageLimit(fragmentList.size());
						adapter = new ViewAdapter(getChildFragmentManager());
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
	
	private void setFonts(int type) {
		switch(type) {
		case 0:
			if (Fonts.getInstance(getActivity()).getType() != 0) {
				setViewPagerAdapter(0); 
			}
			break;
			
		case 1:
			if (Fonts.getInstance(getActivity()).getType() != 1) {
				setViewPagerAdapter(1); 
			}
			break;
			
		case 2:
			if (Fonts.getInstance(getActivity()).getType() != 2) {
				setViewPagerAdapter(2); 
			}
			break;
		}
	}
	
	private void setViewPagerAdapter(int type) {
		@SuppressWarnings("static-access")
		SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE).edit();
		editor.putInt("fonts_type", type);
		editor.commit();
		adapter.setFonts(type);
	}
	
	private void sendCollection(final String poemId, final String collection) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postCollection(UserId.getInstance(getActivity()).getUserId(), poemId, "0", collection);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (collection.equals("0")) {
					collect = "0";
				} else if (collection.equals("1")) {
					collect = "1";
				}
			}
			
		};
		
		task.execute();
	}
	
	private void getCollection(final String poemId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postGetCollection(UserId.getInstance(getContext()).getUserId(), poemId, "0");
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					collect = result;
				} else {
					collect = "0";
				}
				
				if (collect.equals("0")) {		//没收藏
					imgCollect.setImageResource(R.drawable.collection_off);
				} else if (collect.equals("1")) { //收藏
					imgCollect.setImageResource(R.drawable.collection_on);
				}
				
				imgCollect.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (Integer.parseInt(UserId.getInstance(getContext()).getUserId()) >= 0) {
							if (collect.equals("0")) {		//没收藏-->收藏
								imgCollect.setImageResource(R.drawable.collection_on);
								sendCollection(poemId, "1");
							} else if (collect.equals("1")) { //收藏-->没收藏
								imgCollect.setImageResource(R.drawable.collection_off);
								sendCollection(poemId, "0");
							}
						} else {
							Intent intent = new Intent(getContext(), LogOn.class);
							startActivity(intent);
						}
					}
				});
			}
			
		};
		
		task.execute();
	}
	
	/*private void setViewPagerAdapter(final int type) {
		new Thread() {
			@Override
			public void run() {
				fragmentList.clear();
				for (PoemRhesis e: dataList) {
					fragmentList.add(new FragViewPager(e, getActivity(), type));
				}
				handler.sendEmptyMessage(1);
			};
		}.start();
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (new ViewAdapter(getChildFragmentManager()) != null) {
					new ViewAdapter(getChildFragmentManager()).notifyDataSetChanged();
					viewPager.setAdapter(new ViewAdapter(getChildFragmentManager()));
					Log.v("ViewPager", "----tst");
				}
			}
		};
	};*/
}
