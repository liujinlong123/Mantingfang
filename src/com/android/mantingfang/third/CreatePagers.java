package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

@SuppressLint("ResourceAsColor")
public class CreatePagers extends Fragment {
	
	private View view;
	private ViewPager viewPager;
	private RadioButton btnTwo;
	private RadioButton btnThree;
	private RadioButton btnFour;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	private NotePager pagerTwo;
	private OriginalPager pagerThree;
	private AudioPager pagerFour;
	
	//private ImageView imgAdd;
	//private String userId;
	//private String actionUrl = "http://1696824u8f.51mypc.cn:12755//receivecard.php";

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
	
	public void addPagerNote(UserTwoContent item) {
		pagerTwo.addOne(item);
	}
	
	public void addPagerOriginal(UserTwoContent item) {
		pagerThree.addOne(item);
	}
	
	public void addPagerAudio(UserTwoContent item) {
		pagerFour.addOne(item);
	}

	private void initViews() {
		viewPager = (ViewPager) view.findViewById(R.id.third_view_pager);
		btnTwo = (RadioButton) view.findViewById(R.id.third_head_two);
		btnThree = (RadioButton)view.findViewById(R.id.third_head_three);
		btnFour = (RadioButton) view.findViewById(R.id.third_head_four);
		
		btnTwo.setOnClickListener(new MyOnClickListener(0));
		btnThree.setOnClickListener(new MyOnClickListener(1));
		btnFour.setOnClickListener(new MyOnClickListener(2));
	}
	
	

	@SuppressWarnings("deprecation")
	private void initViewPager() {
		pagerTwo = new NotePager();
		pagerThree = new OriginalPager();
		pagerFour = new AudioPager();

		fragmentList.add(pagerTwo);
		fragmentList.add(pagerThree);
		fragmentList.add(pagerFour);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setAdapter(new HomePageAdapter(getChildFragmentManager(), fragmentList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
		btnTwo.setTextColor(Color.BLUE);
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
				btnTwo.setTextColor(Color.BLUE);
				btnThree.setTextColor(Color.GRAY);
				btnFour.setTextColor(Color.GRAY);
				
				break;
				
			case 1:
				btnTwo.setTextColor(Color.GRAY);
				btnThree.setTextColor(Color.BLUE);
				btnFour.setTextColor(Color.GRAY);
				
				break;
				
			case 2:
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
	
	/*private void saveData(final Map<String, String> param, final Map<String, File> files, final UserTwoContent item) {
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
	}*/
}
