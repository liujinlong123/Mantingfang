package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
	private List<PoemRhesis> dataList = RhesisList.getInstance().getList();
	private ViewAdapter adapter;
	
	private List<FragViewPager> fragmentList = FragmentList.getInstance().getFragmentList();
	private static final int CHOOSETYPE = 1;
	
	private Button btnAdd;
	private ImageView imgCollect;
	private ImageView imgMore;
	private ImageView camera;
	//private SharedPreferences pref;
	//private String userId;
	
	private String collect;
	
	public static Typeface typefaceKT;
	public static Typeface typefaceLS;
	public static Typeface typefaceHWXK;
	

	private ArrayList<String> listTitles;
	private static int MY_PERMISSIONS_REQUEST_LOCATION = 5;
	private LocationManagerProxy aMapManager;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_first_pager, null);
			
			typefaceKT = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KT.ttf");
			typefaceLS = Typeface.createFromAsset(getActivity().getAssets(), "fonts/LS.ttf");
			typefaceHWXK = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HWXK.ttf");
			
			initViews();
			Accessibility();
			
			return view;
		}
		
		return view;
	}
	
	
	@SuppressWarnings("deprecation")
	private void initViews() {
		btnAdd = (Button)view.findViewById(R.id.topbar_first_add);
		imgCollect = (ImageView)view.findViewById(R.id.topbar_first_collect);
		imgMore = (ImageView)view.findViewById(R.id.topbar_first_more);
		camera = (ImageView)view.findViewById(R.id.topbar_first_pai);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		
		/**
		 * 用户自己选择的标签
		 */
		listTitles = ChoosePicture.getInstance(getContext()).getChooseTitle();
		if (listTitles == null || listTitles.size() == 0) {
			btnAdd.setText("全部");
		} else if (listTitles.size() == 1){
			
			if (listTitles.get(0).length() > 2) {
				btnAdd.setText(listTitles.get(0).subSequence(0, 2));
			} else {
				btnAdd.setText(listTitles.get(0));
			}
		} else if (listTitles.size() > 1){
			btnAdd.setText(listTitles.get(0).substring(0, 2) + "等");
		}
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(UserId.getInstance(getContext()).getUserId()) < 0) {
					Intent intentL = new Intent(getActivity(), LogOn.class);
					startActivity(intentL);
				} else {
					Intent intent = new Intent(getActivity(), FirstPagerAdd.class);
					startActivityForResult(intent, CHOOSETYPE);
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
		//getData();
		if (fragmentList != null && fragmentList.size() > 0) {
			viewPager.setOffscreenPageLimit(fragmentList.size());
			adapter = new ViewAdapter(getChildFragmentManager());
			viewPager.setAdapter(adapter);
			viewPager.setCurrentItem(0);
		} else {
			getData();
		}
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				System.out.println("Position----" +  arg0 + "----");
				getCollection(dataList.get(arg0).getPoemId());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), CameraAcitivty.class);
				startActivity(intent);
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
		
		public void setContent() {
			for (int i = 0; i < 50; i++) {
				fragmentList.get(i).setContent(dataList.get(i));
			}
		}
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				listTitles = ChoosePicture.getInstance(getContext()).getChooseTitle();
				String titles = listTitles.toString();
				
				if (titles.equals("[]")) {
					return MyClient.getInstance().Http_postViewPager("全部", getContext(), "0");
				} else {
					return MyClient.getInstance().Http_postViewPager(titles.substring(1, titles.length() - 1), getContext(), "0");
				}
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
						viewPager.setOffscreenPageLimit(50);
						adapter = new ViewAdapter(getChildFragmentManager());
						viewPager.setAdapter(adapter);
						viewPager.setCurrentItem(0);
					} else {
						Toast.makeText(getActivity(), "没有数据返回", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		task.execute();
	}
	
	private void getDataAgain(final String loclabel) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				listTitles = ChoosePicture.getInstance(getContext()).getChooseTitle();
				String titles = listTitles.toString();
				
				if (titles.equals("[]")) {
					return MyClient.getInstance().Http_postViewPager("全部", getContext(), loclabel);
				} else {
					return MyClient.getInstance().Http_postViewPager(titles.substring(1, titles.length() - 1), getContext(), loclabel);
				}
			}
			
			@Override
			protected void onPostExecute(String result) {
				try {
					if (result != null && !result.equals("")) {
						if (dataList.size() > 0) {
							dataList.clear();
						}
						dataList = TopicList.parseRhesis(StringUtils.toJSONArray(result)).getRhesisList();
						RhesisList.getInstance().setRhesisList(dataList);
						adapter.setContent();
						
					} else {
						Toast.makeText(getActivity(), "没有数据返回", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
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
	
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case CHOOSETYPE:
			if (resultCode == getActivity().RESULT_OK) {
				btnAdd.setText(data.getStringExtra("choose"));
				getDataAgain("0");
			}
			break;
			
		default:
			break;
		}
	}


	public void Accessibility() {
		if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else
        {
        	//MyLocation.getInstance(MainActivity.this).startAmap();
        	startAmap();
        }

	}

	@SuppressLint("Override")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] arg1, int[] grantResults) {
		if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
            	//MyLocation.getInstance(MainActivity.this).startAmap();
            	startAmap();
                
            } else
            {
                // Permission Denied
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

	}
	
	public void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(getContext());
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, mAMapLocationListener);
	}
	
	@SuppressWarnings("deprecation")
	private void stopAmap() {
		if (aMapManager != null) {
			aMapManager.removeUpdates(mAMapLocationListener);
			aMapManager.destory();
		}
		
		System.out.println("TEST--Location" + "---stop");
		aMapManager = null;
	}
	
	private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				String str =location.getProvince() + location.getCity() + location.getDistrict();
				
				//写入文件
				//0--not change 1--change
				@SuppressWarnings("static-access")
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", getContext().MODE_PRIVATE).edit();
				String loc = UserId.getInstance(getContext()).getLocation();
				editor.putString("mylocation", str);
				if (!loc.equals("")) {
					if (loc.equals(str)) {
						editor.putString("loclabel", "0");
					} else {
						editor.putString("loclabel", "1");
						getDataAgain("1");
					}
				} else {
					editor.putString("loclabel", "0");
				}
				editor.commit();
				//写入完之后
				System.out.println("TEST--Location" + str);
				stopAmap();
			}
		}

		@Override
		public void onLocationChanged(android.location.Location location) {
			// TODO Auto-generated method stub
			
		}
	};
}
