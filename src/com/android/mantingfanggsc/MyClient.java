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
	 * ��ȡMyClient ʵ��
	 * @return
	 */
	public synchronized static MyClient getInstance() {
		if (client == null)
			client = new MyClient();
		return client;
	}
	
	/**
	 * ����һ��������
	 * @return
	 */
	public String http_postOne() {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returndata.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("TypeNum", "1"));
			param.add(new BasicNameValuePair("number", "0"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("����һ", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �������������
	 * @return
	 */
	public String http_postTwo() {
		return null;
	}
}
