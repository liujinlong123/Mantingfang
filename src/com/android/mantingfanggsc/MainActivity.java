package com.android.mantingfanggsc;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.android.mantingfang.first.FragmentFrist;
import com.android.mantingfang.fourth.FragmentFourth;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfang.second.FragmentSecond;
import com.android.mantingfang.third.FragmentThird;
import com.android.mantingfang.topic.FragmentTopic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnRequestPermissionsResultCallback{

	//���ֹ�����
	private FragmentManager fManager;
	
	private FragmentFrist fragment_zhailu;
	private FragmentSecond fragment_wenku;
	private FragmentTopic fragment_topic;
	private FragmentThird fragment_shiyou;
	private FragmentFourth fragment_wode;
	
	//摘录
	private ImageView img_menu_zhailu;
	private TextView tv_menu_zhailu;
	
	//文库
	private ImageView img_menu_wenku;
	private TextView tv_menu_wenku;
	
	//话题
	private ImageView img_menu_topic;
	private TextView tv_menu_topic;
	
	//诗友
	private ImageView img_menu_shiyou;
	private TextView tv_menu_shiyou;
	
	//我的
	private ImageView img_menu_wode;
	private TextView tv_menu_wode;
	
	private static int MY_PERMISSIONS_REQUEST_LOCATION = 5;
	private LocationManagerProxy aMapManager;
	
	/**
	 * 连续点击  出时间间
	 */
	private long mExitTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ʼ�����
		initViews();
		
		clickMenu(findViewById(R.id.bottom_linear_zhailu));
	}
	
	private void initViews() {
		fManager = getSupportFragmentManager();
		
		img_menu_zhailu = (ImageView)findViewById(R.id.bottom_menu_img_zhailu);
		tv_menu_zhailu = (TextView)findViewById(R.id.bottom_menu_tv_zhailu);
		
		img_menu_wenku = (ImageView)findViewById(R.id.bottom_menu_img_wenku);
		tv_menu_wenku = (TextView)findViewById(R.id.bottom_menu_tv_wenku);
		
		img_menu_topic = (ImageView)findViewById(R.id.bottom_menu_img_topic);
		tv_menu_topic = (TextView)findViewById(R.id.bottom_menu_tv_topic);
		
		img_menu_shiyou = (ImageView)findViewById(R.id.bottom_menu_img_shiyou);
		tv_menu_shiyou = (TextView)findViewById(R.id.bottom_menu_tv_shiyou);
		
		img_menu_wode = (ImageView)findViewById(R.id.bottom_menu_img_wode);
		tv_menu_wode = (TextView)findViewById(R.id.bottom_menu_tv_wode);
		
		Accessibility();
	}
	
	public void clickMenu(View v) {
		FragmentTransaction trans = fManager.beginTransaction();
		int vId = v.getId();
		
		setMenuStyle(vId);
		
		hideFragment(trans);
		
		setFragment(vId, trans);
		
		trans.commit();
		
	}
	
	private void hideFragment(FragmentTransaction trans) {
		if (fragment_zhailu != null) {
			trans.hide(fragment_zhailu);
		}
		
		if (fragment_wenku != null) {
			trans.hide(fragment_wenku);
		}
		
		if (fragment_topic != null) {
			trans.hide(fragment_topic);
		}
		
		if (fragment_shiyou != null) {
			trans.hide(fragment_shiyou);
		}
		
		if (fragment_wode != null) {
			trans.hide(fragment_wode);
		}
	}
	
	private void setMenuStyle(int vId) {
		//摘录
		if (vId == R.id.bottom_linear_zhailu) {
			img_menu_zhailu.setImageDrawable(getResources().getDrawable(R.drawable.mtab1_on));
			tv_menu_zhailu.setTextColor(getResources().getColor(R.color.blue));
		} else {
			img_menu_zhailu.setImageDrawable(getResources().getDrawable(R.drawable.mtab1_off));
			tv_menu_zhailu.setTextColor(getResources().getColor(R.color.gray));
		}
		
		//文库
		if (vId == R.id.bottom_linear_wenku) {
			img_menu_wenku.setImageDrawable(getResources().getDrawable(R.drawable.mtab3_on));
			tv_menu_wenku.setTextColor(getResources().getColor(R.color.blue));
		} else {
			img_menu_wenku.setImageDrawable(getResources().getDrawable(R.drawable.mtab3_off));
			tv_menu_wenku.setTextColor(getResources().getColor(R.color.gray));
		}

		// 文库
		if (vId == R.id.bottom_linear_topic) {
			img_menu_topic.setImageDrawable(getResources().getDrawable(R.drawable.mtab_findon));
			tv_menu_topic.setTextColor(getResources().getColor(R.color.blue));
		} else {
			img_menu_topic.setImageDrawable(getResources().getDrawable(R.drawable.mtab_findoff));
			tv_menu_topic.setTextColor(getResources().getColor(R.color.gray));
		}

		// 诗友
		if (vId == R.id.bottom_linear_shiyou) {
			img_menu_shiyou.setImageDrawable(getResources().getDrawable(R.drawable.mtab2_on));
			tv_menu_shiyou.setTextColor(getResources().getColor(R.color.blue));
		} else {
			img_menu_shiyou.setImageDrawable(getResources().getDrawable(R.drawable.mtab2_off));
			tv_menu_shiyou.setTextColor(getResources().getColor(R.color.gray));
		}
		
		//我的
		if (vId == R.id.bottom_linear_wode) {
			img_menu_wode.setImageDrawable(getResources().getDrawable(R.drawable.mtab4_on));
			tv_menu_wode.setTextColor(getResources().getColor(R.color.blue));
		} else {
			img_menu_wode.setImageDrawable(getResources().getDrawable(R.drawable.mtab4_off));
			tv_menu_wode.setTextColor(getResources().getColor(R.color.gray));
		}
	}
	
	private void setFragment(int vId, FragmentTransaction trans) {
		switch(vId) {
		case R.id.bottom_linear_zhailu:
			if (fragment_zhailu == null) {
				fragment_zhailu = new FragmentFrist();
				trans.add(R.id.main_content, fragment_zhailu);
			} else {
				trans.show(fragment_zhailu);
			}
			
		case R.id.bottom_linear_wenku:
			if (fragment_wenku == null) {
				fragment_wenku = new FragmentSecond();
				trans.add(R.id.main_content, fragment_wenku);
			} else {
				trans.show(fragment_wenku);
			}
			
		case R.id.bottom_linear_topic:
			if (fragment_topic == null) {
				fragment_topic = new FragmentTopic();
				trans.add(R.id.main_content, fragment_topic);
			} else {
				trans.show(fragment_topic);
			}
			
		case R.id.bottom_linear_shiyou:
			if (fragment_shiyou == null) {
				fragment_shiyou = new FragmentThird();
				trans.add(R.id.main_content, fragment_shiyou);
			} else {
				trans.show(fragment_shiyou);
			}
			
		case R.id.bottom_linear_wode:
			if (fragment_wode == null) {
				fragment_wode = new FragmentFourth();
				trans.add(R.id.main_content, fragment_wode);
			} else {
				trans.show(fragment_wode);
			}
		}
	}
	
	/*
	 * 按两次返回退出程序
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			// System.out.println(System.currentTimeMillis()+"--"+mExitTime);

			if ((System.currentTimeMillis() - mExitTime) > 2000) {

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
				// System.out.println(mExitTime+"---");
			} else {
				// System.out.println("退出");
				MainActivity.this.finish();
				/*
				 * ActivityCollector.finishAll();
				 * MyApplication.getInstance().exit();
				 */
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void Accessibility() {
		if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
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
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }

	}
	
	public void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(MainActivity.this);
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
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
				String loc = UserId.getInstance(MainActivity.this).getLocation();
				if (!loc.equals("")) {
					if (loc.equals(str)) {
						editor.putString("loclabel", "0");
					} else {
						editor.putString("loclabel", "1");
					}
				} else {
					editor.putString("loclabel", "1");
				}
				editor.putString("mylocation", str);
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
