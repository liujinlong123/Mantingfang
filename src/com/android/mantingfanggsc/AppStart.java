package com.android.mantingfanggsc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.Info;
import com.android.mantingfang.bean.InfoDao;
import com.android.mantingfang.bean.Poetry;
import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.bean.WriterDao;
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
import android.widget.Toast;

public class AppStart extends Activity {

	private Context context;
	private List<Writer> writerList;
	private List<Poetry> poetryList;
	private List<Info> infoList;
	List<Writer> wrs = new ArrayList<Writer>();
	List<Poetry> pos = new ArrayList<Poetry>();
	List<Info> ins = new ArrayList<Info>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.start, null);
		setContentView(view);
		context = getApplication();
		
		
		//final String isInsert = this.getProperty("isInsert");
		int waitTime = 4000;
		/*if(StringUtils.isEmpty(isInsert)){
			waitTime = 4000;
		}*/
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(waitTime);
		view.setAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation){
				//if(StringUtils.isEmpty(isInsert)){
					initData();	
				//}								
			}

			public void onAnimationEnd(Animation animation){
				redirectTo();
			}

			public void onAnimationRepeat(Animation animation){
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
				writerList = (List<Writer>) list.get(0);
				if (writerList != null) {
					WriterDao ww = new WriterDao(context);
					ww.insertWR(writerList);
					Log.v("writerList", "------successful");
				}
				
				poetryList = (List<Poetry>) list.get(1);
				if (poetryList != null) {
					PoetryDao pp = new PoetryDao(context);
					pp.insertPO(poetryList);
					Log.v("poetryList", "------successful");
				}
				
				infoList = (List<Info>)list.get(2);
				if (infoList != null) {
					InfoDao ii = new InfoDao(context);
					ii.insertIN(infoList);
					Log.v("info", "------successful");
				}
				long endTime = System.currentTimeMillis();
				Log.v("插入时间", "------" + (endTime - startTime));
				Toast.makeText(context, endTime - startTime + "", Toast.LENGTH_LONG).show();
			} else if (msg.what == -1) {
				Toast.makeText(context, "无数据", Toast.LENGTH_SHORT).show();
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
					for (int i = 1; i < 11; i++) {
						List<Info> tempInfos = ApiClient.getInfoListByAs("info" + i + ".json", context);
						ins.addAll(tempInfos);
					}
					
					wrs = ApiClient.getWriterListByAs("writer.json", context);
					pos = ApiClient.getPoetryListByAs("poetry.json", context);
					Log.v("Appstart", "initData");
					
					if ((wrs.size() > 0) || (pos.size() > 0) || (ins.size() > 0)) {
						msg.what = 1;
						Bundle data = new Bundle();
						@SuppressWarnings("rawtypes")
						ArrayList list = new ArrayList();
						list.add(wrs);
						list.add(pos);
						list.add(ins);
						data.putParcelableArrayList("list", list);
						msg.setData(data);
					} else {
						msg.what = -1;
					}
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
