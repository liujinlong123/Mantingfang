package com.android.mantingfanggsc;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONObject;

import com.android.mantingfang.bean.Info;
import com.android.mantingfang.bean.Poetry;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.model.Poem;
import com.android.mantingfang.second.SecondWenkuPoemListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Search extends Activity{

	private ListView listview;
	private SecondWenkuPoemListAdapter adapter;
	private List<Poem> list;
	private List<Poetry> poetryList;
	private List<Writer> writerList;
	private List<Info> infoList;
	private boolean network;
	
	private EditText editer;
	private String currentStr = "";
	
	
	private List<List<String>> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		initListView();
	}
	
	private void initListView() {
		editer = (EditText)findViewById(R.id.search_editer);
		listview = (ListView)findViewById(R.id.search_list);
		
		editer.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				sendRequestWithHttpClient();
				currentStr = editer.getText().toString();
				Log.v("currentStr", "----" + currentStr);
				if (currentStr != null && !currentStr.equals("")) {
						adapter = new SecondWenkuPoemListAdapter(Search.this, list, false);
						listview.setAdapter(adapter);
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								UIHelper.showPoemDetail(Search.this, 1, 0);
							}
						});
				} else if (list.size() == 0){
					//listview.removeAllViews();
				}
				
				return false;
			}
		});
		
	}
	
	/*private List<Poem> getData() {
		list = new ArrayList<Poem>();
		network = true;
		if (network) {
			sendRequestWithHttpClient();
		} else {
			
		}
		
		return list;
	}*/
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Bundle bundle = msg.getData();
				String str = bundle.getString("tables");
				if (str != null && !str.equals("")) {
					Log.v("reponse--str-----", str);
				}
				dataList = new ArrayList<>();
				
				poetryList = new ArrayList<Poetry>();
				writerList = new ArrayList<Writer>();
				infoList = new ArrayList<Info>();
				list = new ArrayList<Poem>();
				
				if (str != null && !str.equals("")) {
					String[] tokenOne = str.split("[@]");
					for (int i = 1; i < tokenOne.length; i++) {
						String[] tokenTwo = tokenOne[i].split("[%]");
						dataList.add(Arrays.asList(tokenTwo));
					}
					
					for (int i = 0; i < dataList.size(); i++) {
						try {
							JSONObject joP = new JSONObject(dataList.get(i).get(0));
							JSONObject joW = new JSONObject(dataList.get(i).get(1));
							JSONObject joI = new JSONObject(dataList.get(i).get(2));
							Poetry p = new Poetry(
									Integer.parseInt(joP.getString("poetry_id")),
									joP.getString("poetry_label_id"),
									Integer.parseInt(joP.getString("poetry_writer_id")),
									//Integer.parseInt(joP.getString("poetry_language")),
									1,
									joP.getString("poetry_name"),
									joP.getString("poetry_content"),
									joP.getString("poetry_rhesis"));
							Log.v("Poetry", p.getContent());
							Writer w = new Writer(
									Integer.parseInt(joW.getString("writer_id")),
									joW.getString("writer_label_id"),
									Integer.parseInt(joW.getString("writer_dynasty_id")),
									Integer.parseInt(joW.getString("writer_country_id")),
									joW.getString("writer_name"),
									joW.getString("writer_career"));
							Log.v("Writer--name", w.getWriterName());
							Info info = new Info(
									Integer.parseInt(joI.getString("info_id")),
									Integer.parseInt(joI.getString("info_poetry_id")),
									joI.getString("info_background"),
									joI.getString("info_praise"),
									joI.getString("info_note"),
									joI.getString("info_tonow"),
									joI.getString("info_translation"));
							Log.v("Info--poetryId", info.getPoetryId() + "");
							
							poetryList.add(p);
							writerList.add(w);
							infoList.add(info);
							Poem poem = new Poem(p.getLabelId(), p.getPoetryId(), w.getDynastyId(), w.getWriterId(), w.getWriterName(),
									p.getName(), p.getContent(), p.getRhesis());
							list.add(poem);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					Log.v("poem-list", list.size() + "");
				}
			}
		};
	};
	
	
	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message msg = new Message();
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://1692ab4340.51mypc.cn:41189/test.php");
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("AUkey", currentStr));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
					httpPost.setEntity(entity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					
					Log.v("msms", httpResponse.getStatusLine().getStatusCode() + "");
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity httpEntity = httpResponse.getEntity();
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
