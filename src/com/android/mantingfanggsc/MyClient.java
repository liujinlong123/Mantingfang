package com.android.mantingfanggsc;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MyClient {

	private static MyClient client;
	private HttpClient httpClient;
	
	public MyClient() {
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
	}
	
	/**
	 * 返回实例
	 * @return
	 */
	public synchronized static MyClient getInstance() {
		if (client == null)
			client = new MyClient();
		return client;
	}
	
	/**
	 *界面一
	 * @return
	 */
	public String http_postOne(String userId, String TypeNum, String number) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returndata.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
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
	 * 通过诗词Id请求诗词
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
				//Log.v("poetry_id: " + poetry_id, response);
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * user Two 界面请求
	 * @param user_id
	 * @return
	 */
	public String http_postUserTwo(String user_id) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchuser2.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", user_id));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("user_id: " + user_id, response);
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * user three 界面请求
	 * @param user_id
	 * @return
	 */
	public String http_postUserThree(String user_id) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchuser3.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", user_id));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("user_id: " + user_id, response);
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//----------------------------------------------------用户评论--------------------------------------------------
	
	/**
	 * 评论请求
	 * @param tpye_num
	 * @param post_id
	 * @return
	 */
	public String Http_postComment(String userId, String tpye_num, String post_id) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//search_comment.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("type_num", tpye_num));
			param.add(new BasicNameValuePair("post_id", post_id));
			param.add(new BasicNameValuePair("comment_id", "-1"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("Comment--", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 发送评论
	 * @param params
	 * @return
	 */
	public String Http_SendComment(Map<String, String> params) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//receive_comment.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", params.get("userId")));
			param.add(new BasicNameValuePair("type_num", params.get("typeNum")));
			param.add(new BasicNameValuePair("post_id", params.get("post_id")));
			param.add(new BasicNameValuePair("comment_content", params.get("content")));
			param.add(new BasicNameValuePair("comment_time", params.get("time")));
			param.add(new BasicNameValuePair("commented_id", params.get("bePostId")));
			
			//Log.v("Comment---1", params.get("userId") + "---" + params.get("typeNum") + "---" + params.get("post_id"));
			//Log.v("Comment---2", params.get("content") + "---" + params.get("time") + "---" + params.get("bePostId"));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("AAAA", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//----------------------------------------------------用户评论--------------------------------------------------
	
	
	/**
	 * ViewPager
	 * @param tpye_num
	 * @param post_id
	 * @return
	 */
	public String Http_postViewPager () {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchrhesis.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("type_num", "1"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("TESTViewP", response);
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public String Http_postUser (String username, String password) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//landuser.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("username", username));
			param.add(new BasicNameValuePair("password", password));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存ViewPager
	 * @param userId
	 * @param rhesis
	 * @param writer
	 * @return
	 */
	public String Http_postSaveCard (String userId, String rhesis, String writer) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//receiverhesiscard.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("rhesis", rhesis));
			param.add(new BasicNameValuePair("writer_name", writer));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("TEST", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取用户个人信息
	 * @param userId
	 * @return
	 */
	@SuppressLint("SdCardPath")
	public String Http_postUserInfo (String userId) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchusermessage.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("TEST22", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取图片方法
	 * @param bmurl
	 * @return
	 */
	public Bitmap DownloadBitmap(String bmurl)    //bmurl是解析出来的utl， iv是显示图片的imageView控件
	{
		Bitmap bm=null;
		InputStream is =null;
		BufferedInputStream bis=null;
		try{
			URL  url=new URL(bmurl);
			URLConnection connection=url.openConnection();
			bis=new BufferedInputStream(connection.getInputStream());
			bm= BitmapFactory.decodeStream(bis);
			final Bitmap bm1 = bm;
			
			return bm1;
		}catch (Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if(bis!=null)
					bis.close();
				if (is!=null)
					is.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	/**
	 * 通过关键字搜索
	 * @param key
	 * @return
	 */
	public String Http_postPoemKey (String key) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchpoemmessage.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("keyword", key));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("TEST22", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取一类诗词
	 * @param keyword
	 * @return
	 */
	public String Http_postPoemKind (String keyword) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//matchpoemkeyword.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("keyword", keyword));
			//Log.v("KEYWORD", keyword + "-----");
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POEM--KInd", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 请求返回所有诗人
	 * @param code
	 * @return
	 */
	public String Http_postWriters (String code) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchwritermessage.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("writer", code));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("Writer--", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过诗人id获取诗人信息 和 诗词
	 * @param writerId
	 * @return
	 */
	public String Http_postWriterById (String writerId) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//searchwriterallpoem.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("writer_id", writerId));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("Writer--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//---------------------搜索---------------------------------------------------------------------
	public String Http_postSearchPoem (String keyword) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returnpoem.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("keyword", keyword));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("Search--Poem--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_postSearchWriter (String keyword) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returnwriter.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("keyword", keyword));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("Search--Writer--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_postSearchContent (String keyword) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//returnpoemcontent.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("keyword", keyword));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("Search--PoemContent--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//--------------------------------------------------------------------------------------------------------
	
	//---------------------------------用户注册----------------------------------------------------------------
	
	public String Http_postPhone (String phoneNum) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//user_reg1.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("phone_number", phoneNum));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--PHone--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_postPhoneVerCode (String phoneNum, String verCode) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//user_reg2.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_phonenumber", phoneNum));
			param.add(new BasicNameValuePair("ver_code", verCode));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--VerCode--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_postSendUserName (String phoneNum, String nickName, String password) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//user_reg3.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_phonenumber", phoneNum));
			param.add(new BasicNameValuePair("user_nickname", nickName));
			param.add(new BasicNameValuePair("user_password", password));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--User--Detail", response);
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//---------------------------------用户注册----------------------------------------------------------------
	
	//---------------------------------用户点赞----------------------------------------------------------------
	
	public String Http_postDianZan (String userId, String topicId, String typeNum, String zan) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//receive_zanpost.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("type_num", typeNum));
			param.add(new BasicNameValuePair("post_id", topicId));
			param.add(new BasicNameValuePair("zan_post", zan));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--Dian--Detail", response + "----");
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//---------------------------------用户点赞----------------------------------------------------------------
	
	
	
	
	
	//---------------------------------用户收藏----------------------------------------------------------------
	
	public String Http_postCollection (String userId, String poemId, String typeNum, String collection) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//collect_poem.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("type_num", typeNum));
			param.add(new BasicNameValuePair("poem_id", poemId));
			param.add(new BasicNameValuePair("collection", collection));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--send---Collection--Detail", response + "----");
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_postGetCollection (String userId, String poemId, String typeNum) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//judge_collection.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("type_num", typeNum));
			param.add(new BasicNameValuePair("poetry_id", poemId));
			
			//Log.v("POST--Send--L--Detail", userId + "----" + typeNum + "----" + poemId);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				//Log.v("POST--Get--K--Detail", response + "----");
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Http_Collection (String userId, String typeNum) {
		try {
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://1696824u8f.51mypc.cn:12755//collect_poem.php");
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("user_id", userId));
			param.add(new BasicNameValuePair("type_num", typeNum));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				Log.v("POST--Collection--Detail", response + "----");
				
				return response;
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//---------------------------------用户收藏----------------------------------------------------------------
}
