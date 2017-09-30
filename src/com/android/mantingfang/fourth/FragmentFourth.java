package com.android.mantingfang.fourth;

import java.io.File;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.third.PictureLoad;
import com.android.mantingfang.third.User;
import com.android.mantingfanggsc.CircleImageView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentFourth extends Fragment implements OnClickListener{
	
	private static final int USERID = 7;
	private static final int LOGON = 8;
	private static final int MYUSER = 9;

	private View view;
	private LinearLayout linearMy;
	private LinearLayout linearZhuye;
	private LinearLayout linearGuanzhu;
	private LinearLayout linearShoucang;
	private LinearLayout linearDianzan;
	private LinearLayout linearTuijian;
	private LinearLayout linearTongzhi;
	private LinearLayout linearGuanyu;
	private String userId;
	private SharedPreferences pref;
	
	private static ImageView imgHead;
	private static TextView nickName;
	private static TextView label;
	
	private ImageView setting;
	
	
	private IntentFilter intentFilterOne;
	private MyBroadcastOff myBroadcastoff;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_fourth_pager, null);
			
			
			initViews();
			
			return view;
		}
		return view;
	}

	private void initViews() {
		pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
		userId = pref.getString("userId", "-1");
		//Log.v("userIdFour", userId);
		
		linearMy = (LinearLayout)view.findViewById(R.id.fourth_linear_my);
		linearZhuye = (LinearLayout)view.findViewById(R.id.fourth_linear_zhuye);
		linearGuanzhu = (LinearLayout)view.findViewById(R.id.fourth_linear_guanzhu);
		linearShoucang = (LinearLayout)view.findViewById(R.id.fourth_linear_shoucang);
		linearDianzan = (LinearLayout)view.findViewById(R.id.fourth_linear_dianzan);
		linearTuijian = (LinearLayout)view.findViewById(R.id.fourth_linear_tuijian);
		linearTongzhi = (LinearLayout)view.findViewById(R.id.fourth_linear_tongzhi);
		linearGuanyu = (LinearLayout)view.findViewById(R.id.fourth_linear_guanyu);
		setting = (ImageView)view.findViewById(R.id.topbar_fourth_setting);
		
		
		linearMy.setOnClickListener(this);
		linearZhuye.setOnClickListener(this);
		linearGuanzhu.setOnClickListener(this);
		linearShoucang.setOnClickListener(this);
		linearDianzan.setOnClickListener(this);
		linearTuijian.setOnClickListener(this);
		linearTongzhi.setOnClickListener(this);
		linearGuanyu.setOnClickListener(this);
		
		//设置按钮
		setting.setOnClickListener(this);
		
		userId = pref.getString("userId", "-1");
		imgHead = (CircleImageView)view.findViewById(R.id.fourth_img_user);
		nickName = (TextView)view.findViewById(R.id.fourth_tv_username);
		label = (TextView)view.findViewById(R.id.fourth_tv_userQ);
		if (userId != null && !userId.equals("")) {
			if (Integer.parseInt(userId) >= 0) {
				getImage(userId);
			} else {
				nickName.setText("点击登录");
				label.setText(".....");
			}
		} else {
			nickName.setText("点击登录");
			label.setText(".....");
		}
		
		intentFilterOne = new IntentFilter();
		intentFilterOne.addAction("com.android.mantingfang.fourth.MyBroadcast.LOG_OFF");
		myBroadcastoff = new MyBroadcastOff();
		getActivity().registerReceiver(myBroadcastoff, intentFilterOne);
	}

	@Override
	public void onClick(View v) {
		userId = pref.getString("userId", "-1");
		//Log.v("userIdTwo", userId);
		
		int id = v.getId();
		Intent intent = new Intent(getActivity(), LogOn.class);
		switch(id) {
		//0
		case R.id.fourth_linear_my:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent0 = new Intent(getActivity(), FourthMy.class);
				startActivityForResult(intent0, MYUSER);
			}
			break;
			
		//1
		case R.id.fourth_linear_zhuye:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent1 = new Intent(getActivity(), MyUserPager.class);
				Bundle bundle = new Bundle();
				bundle.putString("userId", userId);
				bundle.putString("headPath", UserId.getInstance(getContext()).getHeadPath());
				bundle.putString("nickName", UserId.getInstance(getContext()).getNickName());
				intent1.putExtras(bundle);
				startActivity(intent1);
			}
			break;
		
		//2
		case R.id.fourth_linear_guanzhu:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent2 = new Intent(getActivity(), FourthGuanzhu.class);
				startActivity(intent2);
			}
			break;
			
		//3
		case R.id.fourth_linear_shoucang:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent3 = new Intent(getActivity(), FourthShoucang.class);
				startActivity(intent3);
			}
			break;
			
		//4
		case R.id.fourth_linear_dianzan:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent4 = new Intent(getActivity(), FourthDianzan.class);
				startActivity(intent4);
			}
			break;
			
		//5
		case R.id.fourth_linear_tuijian:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent5 = new Intent(getActivity(), FourthTuijian.class);
				startActivity(intent5);
			}
			break;
			
		//6
		case R.id.fourth_linear_tongzhi:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent6 = new Intent(getActivity(), FourthTongzhi.class);
				startActivity(intent6);
			}
			break;
			
		//7
		case R.id.fourth_linear_guanyu:
			
			break;
			
		//8
		case R.id.topbar_fourth_setting:
			if (Integer.parseInt(userId) < 0) {
				startActivityForResult(intent, LOGON);
			} else if (Integer.parseInt(userId) > -1) {
				Intent intent8 = new Intent(getContext(), Setting.class);
				startActivity(intent8);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(myBroadcastoff);
		super.onDestroy();
	}
	
	private void getImage(final String userId) {
	AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

		@Override
		protected String doInBackground(String... params) {
			
			return MyClient.getInstance().Http_postUserInfo(userId);
		}
		
		@Override
		protected void onPostExecute(String result) {
			try {
				if (result != null && !result.equals("")) {
					List<User> list = TopicList.parseUserInfo(StringUtils.toJSONArray(result), userId).getUserInfoList();
					if (list != null && list.size() > 0) {
						User user = list.get(0);
						nickName.setText(user.getUserNickname());
						if (user.getUserLabel() != null && !user.getUserLabel().equals("")) {
							label.setText(user.getUserLabel());
						} else {
							label.setText("描述一下你自己呗");
						}
						if (user.getUserPhoto() != null && !user.getUserPhoto().equals("")) {
							File file = new File(user.getUserPhoto());
							if (file.exists()) {
								imgHead.setImageBitmap(BitmapFactory.decodeFile(user.getUserPhoto()));
							} else {
								PictureLoad.getInstance().loadImage(user.getUserPhoto(), imgHead);
							}
						} else {
							imgHead.setImageResource(R.drawable.welcome);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
	task.execute();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case USERID:
			if (resultCode == getActivity().RESULT_OK) {
				userId = data.getStringExtra("userId");
				if (userId != null && !userId.equals("")) {
					if (Integer.parseInt(userId) >= 0) {
						getImage(userId);
					} else {
						nickName.setText("点击登录");
						label.setText("......");
					}
				} else {
					nickName.setText("点击登录");
					label.setText("......");
				}
			}
			break;
			
		case LOGON:
			if (resultCode == getActivity().RESULT_OK) {
				userId = data.getStringExtra("userId");
				if (userId != null && !userId.equals("")) {
					if (Integer.parseInt(userId) >= 0) {
						getImage(userId);
					} else {
						nickName.setText("点击登录");
						label.setText("......");
					}
				} else {
					nickName.setText("点击登录");
					label.setText("......");
				}
			}
			break;
			
		case MYUSER:
			if (resultCode == getActivity().RESULT_OK) {
				getImage(userId);
			}
			break;
		}
	}
	
	public static class MyBroadcastOff extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			nickName.setText("点击登录");
			label.setText(".....");
			imgHead.setImageResource(R.drawable.welcome);
		}
		
	}
	
	/*@Override
	public void onResume() {
		
		if (UserId.getInstance(getContext()).isChanged()) {
			getImage(UserId.getInstance(getContext()).getUserId());
		}
		
		super.onResume();
	}*/
}
