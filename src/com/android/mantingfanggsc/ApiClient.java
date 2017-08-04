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

import com.android.mantingfang.bean.Country;
import com.android.mantingfang.bean.CountryList;
import com.android.mantingfang.bean.Dynasty;
import com.android.mantingfang.bean.DynastyList;
import com.android.mantingfang.bean.Kind;
import com.android.mantingfang.bean.KindList;
import com.android.mantingfang.bean.Label;
import com.android.mantingfang.bean.LabelList;
import com.android.mantingfang.bean.Language;
import com.android.mantingfang.bean.LanguageList;
import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.WriterList;

import android.content.Context;

public class ApiClient{

	public static final String ENCODING = "UTF-8";
	public static final String URLTOPIC = "http://1696824u8f.51mypc.cn:12755//returndata.php";
	
	/**
	 * ��ȡʫ���б�
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
	 * ��ȡ�����б�
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
	 * ��ȡ��Ϣ�б�
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	/*public static InfoList getInfoList(Context context) throws JSONException {
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
	}*/
	
	/**
	 * �ӷ�������ȡ����
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
	 * ��assets �ļ����л�ȡ�ļ�����ȡ����
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
	 * ��ȡ��Ϣ�б�
	 * @param infoname
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	/*public static List<Info> getInfoListByAs(String infoname, Context context) throws JSONException {
		String resultString = getFromAssets(infoname, context);
		return InfoList.parse(StringUtils.toJSONArray(resultString)).getInfoList();
	}*/
	
	/*public static List<Writer> getWriterListByAs(String writername, Context context) throws JSONException {
		String resultString = getFromAssets(writername, context);
		return WriterList.parse(StringUtils.toJSONArray(resultString)).getWriterList();
	}*/
	
	/*public static List<Poetry> getPoetryListByAs(String poetryname, Context context) throws JSONException {
		String resultString = getFromAssets(poetryname, context);
		return PoetryList.parse(StringUtils.toJSONArray(resultString)).getPoetryList();
	}*/
	
	public static List<Dynasty> getDynastyListByAs(String dynastyname, Context context) throws JSONException {
		String resultString = getFromAssets(dynastyname, context);
		return DynastyList.parse(StringUtils.toJSONArray(resultString)).getDynastyList();
	}
	
	public static List<Country> getCountryListByAs(String countryname, Context context) throws JSONException {
		String resultString = getFromAssets(countryname, context);
		return CountryList.parse(StringUtils.toJSONArray(resultString)).getCountryList();
	}
	
	public static List<Language> getLanguageListByAs(String languagename, Context context) throws JSONException {
		String resultString = getFromAssets(languagename, context);
		return LanguageList.parse(StringUtils.toJSONArray(resultString)).getLanguageList();
	}
	
	public static List<Kind> getKindListByAs(String kindname, Context context) throws JSONException {
		String resultString = getFromAssets(kindname, context);
		return KindList.parse(StringUtils.toJSONArray(resultString)).getKindList();
	}
	
	public static List<Label> getLabelListByAs(String labelname, Context context) throws JSONException {
		String resultString = getFromAssets(labelname, context);
		return LabelList.parse(StringUtils.toJSONArray(resultString)).getLabelList();
	}
}
