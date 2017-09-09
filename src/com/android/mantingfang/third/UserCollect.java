package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.model.Poem;
import com.android.mantingfang.second.SecondWenkuPoemListAdapter;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.SuccinctProgress;
import com.android.mantingfanggsc.UIHelper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class UserCollect extends Activity {
	
	private LinearLayout linearBack;
	private CustomListView listview;
	private SecondWenkuPoemListAdapter adapter;
	private List<Poem> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_one_frag);
		
		linearBack = (LinearLayout)findViewById(R.id.user_collect_back);
		listview = (CustomListView)findViewById(R.id.user_collect_listview);
		
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		String userId = getIntent().getStringExtra("userId");
		getData(userId);
	}
	
	private void getData(final String userId) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected void onPreExecute() {
				SuccinctProgress.showSuccinctProgress(UserCollect.this,
						"正在加载", SuccinctProgress.THEME_LINE, false, true);
			}
			
			
			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_Collection (userId, "1");
			}

			@Override
			protected void onPostExecute(String result) {
				SuccinctProgress.dismiss();
				list = new ArrayList<>();
				if (result != null && !result.equals("") && !result.equals("[]")) {
					try {
						JSONArray obj = StringUtils.toJSONArray(result);
						for (int i = 0; i < obj.length(); i++) {
							JSONObject jo = obj.getJSONObject(i);
							Poem p = new Poem(
									jo.getString("poetry_id"),
									jo.optString("poetry_name"),
									null,
									jo.optString("poetry_rhesis"),
									jo.optString("dynasty_name"),
									jo.optString("writer_name"));
							list.add(p);
						}
						
						adapter = new SecondWenkuPoemListAdapter(UserCollect.this, list, true);
						listview.setAdapter(adapter);
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								UIHelper.showPoemMDetailTwoById(UserCollect.this, list.get(position - 1).getPoemId(), 0);
							}
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};

		task.execute();
	}
}
