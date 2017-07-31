package com.android.mantingfang.fourth;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.android.mantingfanggsc.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Location extends Activity implements OnClickListener, OnRequestPermissionsResultCallback{
	
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;
	private TextView save;
	private EditText editor;
	private TextView getLoc;
	//amap
	private LocationManagerProxy aMapManager;
	private static final int MY_PERMISSIONS_REQUEST_LOCATION = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locate);
		
		editor = (EditText)findViewById(R.id.location_editor);
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("所在地");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("返回");
		save = (TextView)findViewById(R.id.topbar_all_saveOn);
		editor = (EditText)findViewById(R.id.location_editor);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("location", editor.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		Accessibility();
	}
	
	private void initViews() {
		getLoc = (TextView)findViewById(R.id.get_location);
		getLoc.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_location:
			if (getLoc.getText().toString().equals("获取位置")) {
				startAmap();
				getLoc.setText("停止定位");
			} else {
				stopAmap();
				getLoc.setText("开启定位");
			}
			break;

		default:
			break;
		}
	}

	private void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(this);
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
				String str =location.getProvince() +  location.getCity()
						+ location.getDistrict();
				editor.setText(str);
				Toast.makeText(Location.this, str, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onLocationChanged(android.location.Location location) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
        	initViews();
        }

	}

	@SuppressLint("Override")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] arg1, int[] grantResults) {
		if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initViews();
                
            } else
            {
                // Permission Denied
                Toast.makeText(Location.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                getLoc.setClickable(false);
            }
            return;
        }

	}

}
