package com.android.mantingfanggsc;

import com.android.mantingfang.first.FragmentFrist;
import com.android.mantingfang.fourth.FragmentFourth;
import com.android.mantingfang.second.FragmentSecond;
import com.android.mantingfang.third.FragmentThird;
import com.android.mantingfang.topic.FragmentTopic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

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
}
