package com.android.mantingfanggsc;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.android.mantingfang.bean.Info;
import com.android.mantingfang.bean.InfoList;
import com.android.mantingfang.bean.Poetry;
import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.bean.WriterList;

import android.content.Context;

public class ApiClient {

	public static final String ENCODING = "UTF-8";
	
	/**
	 * 获取诗人列表
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static WriterList getWriterList(Context context) throws JSONException {
		String newUrl = null;
		try {
			String resultString = http_get(newUrl);
			return WriterList.parse(StringUtils.toJSONArray(resultString));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取作者列表
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static PoetryList getPoetryList(Context context) throws JSONException {
		String newUrl = null;
		try {
			String resultString = http_get(newUrl);
			return PoetryList.parse(StringUtils.toJSONArray(resultString));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取信息列表
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static InfoList getInfoList(Context context) throws JSONException {
		String newUrl = null;
		try {
			String resultString = http_get(newUrl);
			return InfoList.parse(StringUtils.toJSONArray(resultString));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 从服务器获取内容
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String http_get(String url) throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = new DefaultHttpClient().execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, HTTP.UTF_8);
		}
		
		return result;
	}
	
	/**
	 * 从assets 文件夹中获取文件并读取数据
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static String getFromAssets(String fileName, Context context) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 获取信息列表
	 * @param infoname
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static List<Info> getInfoListByAs(String infoname, Context context) throws JSONException {
		String resultString = getFromAssets(infoname, context);
		return InfoList.parse(StringUtils.toJSONArray(resultString)).getInfoList();
	}
	
	public static List<Writer> getWriterListByAs(String writername, Context context) throws JSONException {
		String resultString = getFromAssets(writername, context);
		return WriterList.parse(StringUtils.toJSONArray(resultString)).getWriterList();
	}
	
	public static List<Poetry> getPoetryListByAs(String poetryname, Context context) throws JSONException {
		String resultString = getFromAssets(poetryname, context);
		return PoetryList.parse(StringUtils.toJSONArray(resultString)).getPoetryList();
	}
}
