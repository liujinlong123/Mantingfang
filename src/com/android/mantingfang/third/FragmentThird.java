package com.android.mantingfang.third;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.FilesUpload;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

@SuppressLint("ResourceAsColor")
public class FragmentThird extends Fragment {
	
	private static final int LOGON = 8;
	private static final int ADDONE = 11;
	private static final int ADDTWO = 12;
	private static final int ADDTHREE = 13;
	private static final int ADDFOUR = 14;
	
	private View view;
	private ViewPager viewPager;
	private RadioButton btnOne;
	private RadioButton btnTwo;
	private RadioButton btnThree;
	private RadioButton btnFour;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	private ThirdPagerOne pagerOne;
	private ThirdTwoPager pagerTwo;
	private ThirdPagerThree pagerThree;
	private ThirdFourPager pagerFour;
	
	private ImageView imgAdd;
	private String userId;
	private String actionUrl = "http://1696824u8f.51mypc.cn:12755//receivecard.php";

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_third_pager, null);

			initViews();
			initViewPager();

			return view;
		}

		return view;
	}

	private void initViews() {
		viewPager = (ViewPager) view.findViewById(R.id.third_view_pager);
		btnOne = (RadioButton)view.findViewById(R.id.third_head_one);
		btnTwo = (RadioButton) view.findViewById(R.id.third_head_two);
		btnThree = (RadioButton)view.findViewById(R.id.third_head_three);
		btnFour = (RadioButton) view.findViewById(R.id.third_head_four);
		imgAdd = (ImageView)view.findViewById(R.id.topbar_third_addInfo);

		btnOne.setOnClickListener(new MyOnClickListener(0));
		btnTwo.setOnClickListener(new MyOnClickListener(1));
		btnThree.setOnClickListener(new MyOnClickListener(2));
		btnFour.setOnClickListener(new MyOnClickListener(3));
		
		imgAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences pref = getActivity().getSharedPreferences("data", FragmentActivity.MODE_PRIVATE);
				userId = pref.getString("userId", "-1");
				if (userId != null) {
					if (Integer.parseInt(userId) < 0) {
						Intent intent = new Intent(getActivity(), LogOn.class);
						startActivityForResult(intent, LOGON);
					} else {
						new AlertDialog.Builder(getActivity()).setTitle("选择")
						.setItems(R.array.item_irdc_dialog, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichcountry) {
								switch (whichcountry) {
								case 0:
									Intent intent0 = new Intent(getActivity(), AddOne.class);
									startActivityForResult(intent0, ADDONE);
									break;
								case 1:
									Intent intent1 = new Intent(getActivity(), AddTwo.class);
									startActivityForResult(intent1, ADDTWO);
									break;
								case 2:
									Intent intent2 = new Intent(getActivity(), AddThree.class);
									startActivityForResult(intent2, ADDTHREE);
									break;
								case 3:
									Intent intent3 = new Intent(getActivity(), AddFour.class);
									startActivityForResult(intent3, ADDFOUR);
									break;
								}
								
							}
						}).show();
					}
				}
			}
		});
	}
	
	

	@SuppressWarnings("deprecation")
	private void initViewPager() {
		pagerOne = new ThirdPagerOne();
		pagerTwo = new ThirdTwoPager();
		pagerThree = new ThirdPagerThree();
		pagerFour = new ThirdFourPager();

		fragmentList.add(pagerOne);
		fragmentList.add(pagerTwo);
		fragmentList.add(pagerThree);
		fragmentList.add(pagerFour);
		viewPager.setOffscreenPageLimit(4);
		viewPager.setAdapter(new HomePageAdapter(getChildFragmentManager(), fragmentList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
		btnOne.setTextColor(Color.BLUE);
		changeHeadSelectedState(0);
	}

	private void changeHeadSelectedState(int currentPosition) {
	}

	class HomePageAdapter extends android.support.v4.app.FragmentStatePagerAdapter {// FragmentPagerAdapter

		// private FragmentManager fm;
		private List<Fragment> fragments = null;
		@SuppressWarnings("unused")
		private FragmentManager fm;

		public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return fragments.size();// hotIssuesList.size();
		}
	}

	public class MyOnClickListener implements View.OnClickListener {

		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	@SuppressLint("ResourceAsColor")
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				btnOne.setTextColor(Color.BLUE);
				btnTwo.setTextColor(getContext().getResources().getColor(R.color.gray));
				btnThree.setTextColor(Color.GRAY);
				btnFour.setTextColor(Color.GRAY);
				
				break;
				
			case 1:
				btnOne.setTextColor(Color.GRAY);
				btnTwo.setTextColor(Color.BLUE);
				btnThree.setTextColor(Color.GRAY);
				btnFour.setTextColor(Color.GRAY);
				
				break;
				
			case 2:
				btnOne.setTextColor(Color.GRAY);
				btnTwo.setTextColor(Color.GRAY);
				btnThree.setTextColor(Color.BLUE);
				btnFour.setTextColor(Color.GRAY);
				
				break;
				
			case 3:
				btnOne.setTextColor(Color.GRAY);
				btnTwo.setTextColor(Color.GRAY);
				btnThree.setTextColor(Color.GRAY);
				btnFour.setTextColor(Color.BLUE);
				
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ADDONE:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					Map<String, String> param = new HashMap<>();
					param.put("user_id", UserId.getInstance(getContext()).getUserId());
					param.put("datatime", data.getStringExtra("datatime"));
					param.put("content", data.getStringExtra("content"));
					param.put("type_num", "1");
					ArrayList<String> setPath = data.getStringArrayListExtra("setPath");
					Map<String, File> files = new HashMap<>();
					for (String e: setPath) {
						File f = new File(e);
						files.put(f.getName(), f);
					}
					ArrayList<FileImgs> imgs = new ArrayList<>();
					for (String e: setPath) {
						imgs.add(new FileImgs("1", e));
					}
					
					UserTwoContent item = new UserTwoContent(
							param.get("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							param.get("datatime"),
							null,
							param.get("content"),
							imgs,
							null,
							null,
							null,
							null,
							"0",
							1,
							-1);
					if (item.getContent() != null && !item.getContent().equals("")) {
						//pagerOne.addOne(item);
						saveData(param, files, item);
					}
				}
			}
			break;
			
		case ADDTWO:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					Map<String, String> param2 = new HashMap<>();
					param2.put("user_id", UserId.getInstance(getContext()).getUserId());
					param2.put("poetry_id", data.getStringExtra("poetry_id"));
					param2.put("datatime", data.getStringExtra("datatime"));
					param2.put("content", data.getStringExtra("content"));
					param2.put("type_num", "2");
					
					UserTwoContent item2 = new UserTwoContent(
							param2.get("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							param2.get("datatime"),
							null,
							param2.get("content"),
							null,
							null,
							param2.get("poetry_id"),
							data.getStringExtra("poetry_content"),
							data.getStringExtra("poetry_name"),
							"0",
							2,
							-1);
					
					if (item2.getContent() != null && !item2.getContent().equals("")) {
						pagerTwo.addOne(item2);
						saveDataTwo(param2, item2);
					}
				}
			}
			break;
			
		case ADDTHREE:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					Map<String, String> param3 = new HashMap<>();
					param3.put("user_id", UserId.getInstance(getContext()).getUserId());
					param3.put("datatime", data.getStringExtra("datatime"));
					param3.put("content", data.getStringExtra("content"));
					param3.put("type_num", "3");
					param3.put("original_title", data.getStringExtra("title"));
					ArrayList<String> setPath3 = data.getStringArrayListExtra("setPath");
					Map<String, File> files3 = new HashMap<>();
					for (String e: setPath3) {
						File f = new File(e);
						files3.put(f.getName(), f);
					}
					ArrayList<FileImgs> imgs3 = new ArrayList<>();
					for (String e: setPath3) {
						imgs3.add(new FileImgs("1", e));
					}
					
					UserTwoContent item3 = new UserTwoContent(
							param3.get("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							param3.get("datatime"),
							param3.get("original_title"),
							param3.get("content"),
							imgs3,
							null,
							null,
							null,
							null,
							"0",
							3,
							-1);
					
					Log.v("PagerThree", item3.getUserId() + "---" + item3.getHeadPath() + "---" + item3.getName() 
								+"-----" + item3.getTime() + "---" + item3.getContent());
					Log.v("param", param3.toString());
					if (item3.getContent() != null && !item3.getContent().equals("")) {
						pagerThree.addOne(item3);
						
						saveData(param3, files3, item3);
					}
				}
			}
			break;
			
		case ADDFOUR:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					Map<String, String> param4 = new HashMap<>();
					param4.put("user_id", UserId.getInstance(getContext()).getUserId());
					param4.put("poetry_id", data.getStringExtra("poetry_id"));
					param4.put("datatime", data.getStringExtra("datatime"));
					param4.put("type_num", "4");
					String fileName = data.getStringExtra("audio");
					Map<String, File> fileaudio = new HashMap<>();
					File f = new File(fileName);
					if (f.exists()) {
						fileaudio.put(f.getName(), f);
					}
					
					UserTwoContent item4 = new UserTwoContent(
							param4.get("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							param4.get("datatime"),
							null,
							null,
							null,
							new FileAudio("1", data.getStringExtra("audio")),
							param4.get("poetry_id"),
							data.getStringExtra("poetry_content"),
							data.getStringExtra("poetry_name"),
							"0",
							4,
							-1);
					
					if (item4.getContent() != null && !item4.getContent().equals("")) {
						pagerFour.addOne(item4);
						saveData(param4, fileaudio, item4);
					}
				}
			}
			break;
		}
	}
	
	private void saveData(final Map<String, String> param, final Map<String, File> files, final UserTwoContent item) {
	AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

		// String Answer = null;
		@Override
		protected String doInBackground(String... params) {
			
			try {
				return FilesUpload.post(actionUrl, param, files);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			
			if (result != null && !result.equals("")) {
				Log.v("REST---", result + "---" + result.substring(result.lastIndexOf("}") + 1));
				if (item.getPostComNum() == 1) {
					//pagerOne.refresh(Integer.parseInt(result.substring(result.lastIndexOf("}") + 1)));
				} else if (item.getPostComNum() == 2) {
					pagerTwo.refresh(Integer.parseInt(result.substring(result.lastIndexOf("}") + 1)));
				} else if (item.getPostComNum() == 3) {
					//pagerThree.refresh(Integer.parseInt(result.substring(result.lastIndexOf("}") + 1)));
				} else if (item.getPostComNum() == 4) {
					pagerFour.refresh(Integer.parseInt(result.substring(result.lastIndexOf("}") + 1)));
				}
				
			}
		}

	};

	task.execute();
}
	
	private void saveDataTwo(final Map<String, String> param, final UserTwoContent item) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			// String Answer = null;
			@Override
			protected String doInBackground(String... params) {
				
				
				try {
					return FilesUpload.post(actionUrl, param, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					item.setPost_com_pId(Integer.parseInt(result));
				}
			}

		};

		task.execute();
	}
}
