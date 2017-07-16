package com.android.mantingfanggsc;

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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class MyClient {

	private static MyClient client;
	private HttpClient httpClient;
	
	public MyClient() {
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
	}
	
	/**
	 * 获取MyClient 实例
	 * @return
	 */
	public synchronized static MyClient getInstance() {
		if (client == null)
			client = new MyClient();
		return client;
	}
	
	/**
	 * 界面一返回数据
	 * @return
	 */
	public String http_postOne(String TypeNum, String number) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returndata.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("TypeNum", TypeNum));
			param.add(new BasicNameValuePair("number", number));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("界面" + TypeNum, response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回诗词
	 * @return
	 */
	public String http_postPoem(String poetry_id) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchpoem.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("poetry_id", poetry_id));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("poetry_id: " + poetry_id, response);
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
