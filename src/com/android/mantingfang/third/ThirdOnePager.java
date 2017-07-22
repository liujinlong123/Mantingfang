package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ThirdOnePager extends Fragment {
	private View view;
	private CustomListView thirdOneListView;
	private ThirdOneAdapter adapterOne;
	private List<UserTwoContent> listOne;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_one, null);

			// initViews();
			sendRequestWithHttpClient();

			return view;
		}

		return view;
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("tables");
				// Log.v("reponse--str-----", str);
				if (str != null && !str.equals("")) {
					//Log.v("reponse--str-----", str);
				}
				try {
					if (str != null && !str.equals("")) {
						listOne = (List<UserTwoContent>) (TopicList.parseOne(StringUtils.toJSONArray(str), 1)
								.getTopicList());

						thirdOneListView = (CustomListView) view.findViewById(R.id.third_pager_one_listview);
						adapterOne = new ThirdOneAdapter(getActivity(), listOne);
						thirdOneListView.setAdapter(adapterOne);
						thirdOneListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

							}
						});
					}

					//Log.v("ListOne", listOne.size() + "----");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = new Message();
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returndata.php");
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("TypeNum", "1"));
					param.add(new BasicNameValuePair("number", "0"));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
					httpPost.setEntity(entity);
					HttpResponse httpResponse = httpClient.execute(httpPost);

					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity httpEntity = httpResponse.getEntity();
						// Log.v("AAAA", EntityUtils.toString(httpEntity,
						// "utf-8"));
						String response = EntityUtils.toString(httpEntity, "utf-8");
						msg.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("tables", response);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}
}
