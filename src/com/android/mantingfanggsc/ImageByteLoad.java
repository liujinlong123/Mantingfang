package com.android.mantingfanggsc;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

public class ImageByteLoad {

	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";

	public static byte[] upload(String host, Map<String, String> params) {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n"；
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		try {
			URL url = new URL(host);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestMethod("POST"); // 请求方式ʽ
			conn.setRequestProperty("Charset", CHARSET);// 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			//if (file != null) {
				/**  * 当文件不为空，把文件包装并且上传  */
				OutputStream outputSteam = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(LINE_END);
				if (params != null) {// 根据格式，开始拼接文本参数
					for (Map.Entry<String, String> entry : params.entrySet()) {
						sb.append(PREFIX).append(BOUNDARY).append(LINE_END);//
						sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
						sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
						sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
						sb.append(LINE_END);
						sb.append(entry.getValue());
						sb.append(LINE_END);// 换行
					}
				}
				dos.write(sb.toString().getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX+ LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
				Log.v("String", sb.toString());
				/**
				 **  获取响应码 200=成功   当响应成功，获取响应的流                   
				 */
				//int code = conn.getResponseCode();
				sb.setLength(0);
				BufferedInputStream br = new BufferedInputStream(conn.getInputStream());
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] data = new byte[1024];
				int count = -1;
				while ((count = br.read(data, 0, 1024)) != -1)
					outStream.write(data, 0, count);
				
				data = null;
				br.close();
				return outStream.toByteArray();
			//}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
