package com.android.mantingfang.third;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.mantingfang.fourth.LogOn;
import com.android.mantingfang.fourth.UserId;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentThird extends Fragment{
	
	private static final int LOGON = 8;
	//private static final int ADDONE = 11;
	private static final int ADDTWO = 12;
	private static final int ADDTHREE = 13;
	private static final int ADDFOUR = 14;

	private FragmentManager fManager;
	
	private RiShangPager pagerOne;
	private CreatePagers pagerTwo;
	
	private View view;
	private RadioGroup rgp;
	private ImageView imgAdd;
	private String userId;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager, null);
			
			initViews();
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		fManager = getActivity().getSupportFragmentManager();
		
		rgp = (RadioGroup)view.findViewById(R.id.topbar_third_radgp);
		imgAdd = (ImageView)view.findViewById(R.id.topbar_third_addInfo);
		imgAdd.setVisibility(View.GONE);
		
		clickMenu(view.findViewById(R.id.topbar_third_rbtnL));
		rgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.topbar_third_rbtnL:
					clickMenu(view.findViewById(R.id.topbar_third_rbtnL));
					imgAdd.setVisibility(View.GONE);
					break;
					
				case R.id.topbar_third_rbtnR:
					clickMenu(view.findViewById(R.id.topbar_third_rbtnR));
					imgAdd.setVisibility(View.VISIBLE);
					break;
				}
			}
		});
		
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
									startActivity(intent0);
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
	
	public void clickMenu(View v) {
		FragmentTransaction trans = fManager.beginTransaction();
		int vId = v.getId();
		
		hideFragment(trans);
		
		setFragment(vId, trans);
		
		trans.commit();
		
	}
	
	private void hideFragment(FragmentTransaction trans) {
		if (pagerOne != null) {
			trans.hide(pagerOne);
		}
		
		if (pagerTwo != null) {
			trans.hide(pagerTwo);
		}
	}
	
	private void setFragment(int vId, FragmentTransaction trans) {
		switch(vId) {
		case R.id.topbar_third_rbtnL:
			if (pagerOne == null) {
				pagerOne = new RiShangPager();
				trans.add(R.id.third_pager_content, pagerOne);
			} else {
				trans.show(pagerOne);
			}
			
		case R.id.topbar_third_rbtnR:
			if (pagerTwo == null) {
				pagerTwo = new CreatePagers();
				trans.add(R.id.third_pager_content, pagerTwo);
			} else {
				trans.show(pagerTwo);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		/*case ADDONE:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					
					ArrayList<String> setPath = data.getStringArrayListExtra("setPath");
					
					ArrayList<FileImgs> imgs = new ArrayList<>();
					for (String e: setPath) {
						imgs.add(new FileImgs("1", e));
					}
					
					UserTwoContent item = new UserTwoContent(
							data.getStringExtra("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							data.getStringExtra("datatime"),
							null,
							data.getStringExtra("content"),
							imgs,
							null,
							null,
							null,
							null,
							"0",
							1,
							Integer.parseInt(data.getStringExtra("postId")));
					
					Log.v("TEST_topic", item.getContent() + "----" + item.getPost_com_pId());
				}
			}
			break;*/
			
		case ADDTWO:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					UserTwoContent item2 = new UserTwoContent(
							data.getStringExtra("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							data.getStringExtra("datatime"),
							null,
							data.getStringExtra("content"),
							null,
							null,
							data.getStringExtra("poetry_id"),
							data.getStringExtra("poetry_content"),
							data.getStringExtra("poetry_name"),
							"0",
							2,
							Integer.parseInt(data.getStringExtra("postId")),
							1);
					
					pagerTwo.addPagerNote(item2);
				}
			}
			break;
			
		case ADDTHREE:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					ArrayList<String> setPath3 = data.getStringArrayListExtra("setPath");
					
					ArrayList<FileImgs> imgs3 = new ArrayList<>();
					for (String e: setPath3) {
						imgs3.add(new FileImgs("1", e));
					}
					
					UserTwoContent item3 = new UserTwoContent(
							data.getStringExtra("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							data.getStringExtra("datatime"),
							data.getStringExtra("title"),
							data.getStringExtra("content"),
							imgs3,
							null,
							null,
							null,
							null,
							"0",
							3,
							Integer.parseInt(data.getStringExtra("postId")),
							1);
					
					Log.v("PagerThree", item3.getUserId() + "---" + item3.getHeadPath() + "---" + item3.getName() 
								+"-----" + item3.getTime() + "---" + item3.getContent());
					pagerTwo.addPagerOriginal(item3);
				}
			}
			break;
			
		case ADDFOUR:
			if (resultCode == getActivity().RESULT_OK) {
				if (data.getBooleanExtra("isSave", false)) {
					
					String fileName = data.getStringExtra("audio");
					Map<String, File> fileaudio = new HashMap<>();
					File f = new File(fileName);
					if (f.exists()) {
						fileaudio.put(f.getName(), f);
					}
					
					UserTwoContent item4 = new UserTwoContent(
							data.getStringExtra("user_id"), 
							UserId.getInstance(getContext()).getHeadPath(),
							UserId.getInstance(getContext()).getNickName(),
							data.getStringExtra("datatime"),
							null,
							null,
							null,
							new FileAudio("1", data.getStringExtra("audio")),
							data.getStringExtra("poetry_id"),
							data.getStringExtra("poetry_content"),
							data.getStringExtra("poetry_name"),
							"0",
							4,
							Integer.parseInt(data.getStringExtra("postId")),
							1);
					
					pagerTwo.addPagerAudio(item4);
				}
			}
			break;
		}
	}
}
