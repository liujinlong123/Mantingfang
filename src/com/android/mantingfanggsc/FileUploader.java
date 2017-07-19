package com.android.mantingfanggsc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

/**
 * Created by kaiyi.cky on 2015/8/16.
 */
public class FileUploader {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000; //��ʱʱ��
    private static final String CHARSET = "utf-8"; //���ñ���
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";

    public static String upload(String host,File file,Map<String,String> params,FileUploadListener listener){
        String BOUNDARY = UUID.randomUUID().toString(); //�߽��ʶ ������� String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //��������
        try {
            URL url = new URL(host);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod("POST"); //����ʽ
            conn.setRequestProperty("Charset", CHARSET);//���ñ���
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            conn.setDoInput(true); //����������
            conn.setDoOutput(true); //���������
            conn.setUseCaches(false); //������ʹ�û���
           // if(file!=null) {
                /** * ���ļ���Ϊ�գ����ļ���װ�����ϴ� */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(LINE_END);
                if(params!=null){//���ݸ�ʽ����ʼƴ���ı�����
                    for(Map.Entry<String,String> entry: params.entrySet()){                        
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);//�ֽ��
                        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                        sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                        sb.append(LINE_END);
                        sb.append(entry.getValue());
                        sb.append(LINE_END);//���У�
                    }
                }
                sb.append(PREFIX);//��ʼƴ���ļ�����
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * �����ص�ע�⣺
                 * name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
                 * filename���ļ������֣�������׺���� ����:abc.png
                 */
                if(file!=null) {
                sb.append("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                }
                //д���ļ�����
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                long totalbytes = file.length();
                long curbytes = 0;
                Log.v("cky","total="+totalbytes);
                int len = 0;
                while((len=is.read(bytes))!=-1){
                    curbytes += len;
                    dos.write(bytes, 0, len);
                    listener.onProgress(curbytes,1.0d*curbytes/totalbytes);
                }
                is.close();
                dos.write(LINE_END.getBytes());//һ�����л���
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * ��ȡ��Ӧ�� 200=�ɹ�
                 * ����Ӧ�ɹ�����ȡ��Ӧ����
                 */
                int code = conn.getResponseCode();
                sb.setLength(0);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                listener.onFinish(code,sb.toString(),conn.getHeaderFields());
                return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public interface FileUploadListener{
        public void onProgress(long pro,double precent);
        public void onFinish(int code,String res,Map<String,List<String>> headers);
    }
}