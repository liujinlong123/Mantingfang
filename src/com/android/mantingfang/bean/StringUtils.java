package com.android.mantingfang.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class StringUtils {

	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static JSONArray toJSONArray(String json) throws JSONException {
		if (!isEmpty(json)) {
			// if (json.indexOf("[") == 0) {
			// json = json.substring(1, json.length());
			// }
			// if (json.lastIndexOf("]") == json.length()) {
			// json = json.substring(0, json.length() - 1);
			// }
		}
		return new JSONArray(json);
	}

	public static JSONObject toJSONObject(String json) throws JSONException {
		if (!isEmpty(json)) {
			if (json.indexOf("[") == 0) {
				json = json.substring(1, json.length());
			}
			if (json.lastIndexOf("]") == json.length()) {
				json = json.substring(0, json.length() - 1);
			}
			return new JSONObject(json);
		}
		return null;
	}

	public static String getString4Array(int[] ids) {

		String result = "";
		for (int i = 0; i < ids.length; i++) {
			result = result + ids[i] + ",";
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * ���ת��Ϊȫ��
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String[] removeArrIndex(String[] arr, int index) {

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		list.remove(index);
		return list.toArray(new String[1]);

	}

	/**
	 * ��ȡͼƬ·��List
	 * 
	 * @param pic
	 * @return
	 */
	public static ArrayList<String> getPictures(String pic) {
		ArrayList<String> pictures = new ArrayList<>();
		String[] tokens = pic.split("[ ]");
		for (int i = 0; i < tokens.length; i++) {
			pictures.add(tokens[i]);
		}

		return pictures;
	}

	/**
	 * ��UserTwoPager������з������ݴ���
	 * 
	 * @param result
	 * @return
	 */
	public static String[] getUserTopic(String result) {
		if (result != null && !result.equals("")) {
			String[] tokens = result.split("[&&]");
			Log.v("token[0]", tokens[0]);
			Log.v("token[0]", tokens[1]);
			return tokens;
		}
		return null;
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) // 二进制转字符串
	{
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * 将二进制转换成图片保存
	 * 
	 * @param imgStr
	 *            二进制流转换的字符串
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByBytes(File imgFile, String imgPath, String imgName) {

		int stateInt = 1;
		if (imgFile.length() > 0) {
			try {
				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				FileInputStream fis = new FileInputStream(imgFile);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = fis.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				fis.close();
			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}

	/**
	 *   把Bitmap转Byte 
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 将字节数组转换为ImageView可调用的Bitmap对象 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

}
