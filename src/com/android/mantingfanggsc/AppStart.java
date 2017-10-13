package com.android.mantingfanggsc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.Country;
import com.android.mantingfang.bean.CountryDao;
import com.android.mantingfang.bean.Dynasty;
import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.bean.Kind;
import com.android.mantingfang.bean.KindDao;
import com.android.mantingfang.bean.Label;
import com.android.mantingfang.bean.LabelDao;
import com.android.mantingfang.bean.Language;
import com.android.mantingfang.bean.LanguageDao;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.first.ChoosePicture;
import com.android.mantingfang.first.Fonts;
import com.android.mantingfang.first.FragViewPager;
import com.android.mantingfang.first.FragmentList;
import com.android.mantingfang.first.PoemRhesis;
import com.android.mantingfang.first.RhesisList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AppStart extends Activity{

	private Context context;
	private List<Dynasty> dynastyList;
	private List<Country> countryList;
	private List<Language> languageList;
	private List<Kind> kindList;
	private List<Label> labelList;
	List<Dynasty> dys = new ArrayList<Dynasty>();
	List<Country> cos = new ArrayList<Country>();
	List<Language> lans = new ArrayList<Language>();
	List<Kind> kinds = new ArrayList<Kind>();
	List<Label> labs = new ArrayList<Label>();
	
	private List<PoemRhesis> dataList;

	private ArrayList<String> listTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.start, null);
		setContentView(view);
		context = getApplication();

		// final String isInsert = this.getProperty("isInsert");
		int waitTime = 2000;
		/*
		 * if(StringUtils.isEmpty(isInsert)){ waitTime = 4000; }
		 */
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(waitTime);
		view.setAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// if(StringUtils.isEmpty(isInsert)){
				initData();
				// }
			}

			public void onAnimationEnd(Animation animation) {
				redirectTo();
			}

			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Bundle bundle = msg.getData();
				@SuppressWarnings("rawtypes")
				ArrayList list = bundle.getParcelableArrayList("list");
				long startTime = System.currentTimeMillis();
				dynastyList = (List<Dynasty>) list.get(0);
				if (dynastyList != null) {
					DynastyDao dd = new DynastyDao(context);
					dd.insertDY(dynastyList);
					Log.v("dynasty", "------successful");
				}

				countryList = (List<Country>) list.get(1);
				if (countryList != null) {
					CountryDao cc = new CountryDao(context);
					cc.insertCountry(countryList);
					Log.v("country", "------successful");
				}

				languageList = (List<Language>) list.get(2);
				if (languageList != null) {
					LanguageDao ll = new LanguageDao(context);
					ll.insertLan(languageList);
					Log.v("Language", "------successful");
				}

				kindList = (List<Kind>) list.get(3);
				if (kindList != null) {
					KindDao kk = new KindDao(context);
					kk.insertKIND(kindList);
					Log.v("kind", "------successful");
				}

				labelList = (List<Label>) list.get(4);
				if (labelList != null) {
					LabelDao lla = new LabelDao(context);
					lla.insertLabel(labelList);
					Log.v("Label", "------successful");
				}
				
				
				String result = (String) msg.obj;
				if (result != null && !result.equals("")) {
					try {
						dataList = TopicList.parseRhesis(StringUtils.toJSONArray(result)).getRhesisList();
						Log.v("Size---", dataList.size() + "----");
						FragmentList.getInstance().getFragmentList().clear();
						for (PoemRhesis e: dataList) {
							FragmentList.getInstance().getFragmentList().add(new FragViewPager(e, AppStart.this, Fonts.getInstance(AppStart.this).getType()));
						}
						RhesisList.getInstance().setRhesisList(dataList);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				} else {
					//Toast.makeText(AppStart.this, "没有数据返回", Toast.LENGTH_SHORT).show();
				}
				
				
				
				long endTime = System.currentTimeMillis();
				Log.v("时间", "------" + (endTime - startTime));
				//Toast.makeText(context, endTime - startTime + "", Toast.LENGTH_LONG).show();
			} else if (msg.what == -1) {
				//Toast.makeText(context, "������", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void initData() {
		new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				Message msg = new Message();
				try {
					listTitles = ChoosePicture.getInstance(AppStart.this).getChooseTitle();
					String titles = listTitles.toString();
					
					String result = "";
					
					if (titles.equals("[]")) {
						result = MyClient.getInstance().Http_postViewPager("全部", AppStart.this);
					} else {
						result = MyClient.getInstance().Http_postViewPager(titles.substring(1, titles.length() - 1), AppStart.this);
					}
					
					dys = ApiClient.getDynastyListByAs("dynasty.json", context);
					cos = ApiClient.getCountryListByAs("country.json", context);
					lans = ApiClient.getLanguageListByAs("language.json", context);
					kinds = ApiClient.getKindListByAs("kind.json", context);
					labs = ApiClient.getLabelListByAs("label.json", context);
					Log.v("Appstart", "initData");

					msg.what = 1;
					Bundle data = new Bundle();
					@SuppressWarnings("rawtypes")
					ArrayList list = new ArrayList();
					list.add(dys);
					list.add(cos);
					list.add(lans);
					list.add(kinds);
					list.add(labs);
					data.putParcelableArrayList("list", list);
					msg.setData(data);
					msg.obj = result;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	private void redirectTo() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
