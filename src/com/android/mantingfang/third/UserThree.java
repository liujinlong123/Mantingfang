package com.android.mantingfang.third;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserThree extends Fragment {

	// private MyViewPager vp;
	private View view;
	private TextView tvAge;
	private TextView tvSex;
	private TextView tvArea;
	private TextView tvLabel;
	//private LinearLayout linearInfo;
	private TextView tvIntro;

	/*
	 * public UserThree(MyViewPager vp) { this.vp = vp; }
	 */

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.user_three_frag, null);

			// vp.setObjectForPosition(view, 2);
			
			
			tvAge = (TextView)view.findViewById(R.id.user_tv_age);
			tvSex = (TextView)view.findViewById(R.id.user_tv_sex);
			tvArea = (TextView)view.findViewById(R.id.user_tv_area);
			tvLabel = (TextView)view.findViewById(R.id.user_tv_label);
			//linearInfo = (LinearLayout)view.findViewById(R.id.user_linear_intro);
			tvIntro = (TextView)view.findViewById(R.id.user_tv_intro);
			
			Log.v("TESTFRAGMENT", (String)getArguments().get("userId") + "-----Three");
			getData((String)getArguments().get("userId"));
			return view;
		}
		// vp.setObjectForPosition(view, 2);
		return view;
	}

	private void getData(final String user_id) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().http_postUserThree(user_id);
			}

			@Override
			protected void onPostExecute(String result) {

				try {
					JSONObject jo = new JSONObject(result);
					User user = new User(user_id, null, null, jo.optString("user_age"), jo.optString("user_sex"),
							jo.optString("user_area"), jo.optString("user_introduce"), jo.optString("user_label"));
					
					tvAge.setText("年龄: " + user.getUserAge());
					tvSex.setText("性别: " + user.getUserSex());
					tvArea.setText("地区: " + user.getUserArea());
					tvLabel.setText("个性签名: " + user.getUserIntro());
					tvIntro.setText(" " + user.getUserIntro());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		task.execute();
	}
}
