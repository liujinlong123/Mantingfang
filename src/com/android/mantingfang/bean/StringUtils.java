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

import com.android.mantingfang.third.FileImgs;

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
	public static ArrayList<FileImgs> getPictures(String pic) {
		//Log.v("Stringutils--1", pic);
		ArrayList<FileImgs> pictures = new ArrayList<>();
		String[] tokens = pic.split("[,]");
		for (int i = 0; i < tokens.length; i++) {
			pictures.add(new FileImgs("0", tokens[i]));
		}
		//Log.v("Stringutils--2", pictures.toString());
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
	
	public static String SelectNote(String str){
		//分情况
		//【】
		if(str.charAt(0) == '【'){
			int[] length = new int [str.length()];
			int q = 0;
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) == '【'){
					length[q] = i;
					q++;
				}
			}
			
			String s = "";
			s += str.substring(0, length[1]) + "\n";
			
			for(int i = 1; i < q-1; i++){
				s += str.substring(length[i], length[i+1]) + "\n";
			}
			s += str.substring(length[q-1], str.length());
			return s;
		}
		// (1)
		else if(str.charAt(0) == '(' || str.charAt(0) == '（'){
			int[] length = new int [str.length()];
			int[] dotLeft = new int [str.length()];
			int[] dotRight = new int [str.length()];
			int l = 0, r = 0;
			int q = 0;
			for(int i = 0; i < str.length(); i++){
				//只找到离左括号最近的一个右括号，然后继续寻找下一对括号
				//筛选掉一部分左括号
				//左右括号数目相同
				if(str.charAt(i) == '(' || str.charAt(i) == '（'){
					dotLeft[l] = i;
					l++;
					for(int j = i+1; j < str.length(); j++){
						if(str.charAt(j) == ')' || str.charAt(j) == '）'){
							dotRight[r] = j;
							r++;
							break;
						}
					}
				}
			}
			
			for(int i = 0; i < l; i++){
				int sum = 0;
				ArrayList<Integer> list = new ArrayList<Integer>();
				if(dotRight[i]-dotLeft[i] <= 3){
					
					int count = dotRight[i]-dotLeft[i]-1;
					System.out.println(count);
					
					for(int k = 0; k < count; k++){
						int x = (int) Math.pow(10, count-k-1);
						list.add(x);
					}
					for(int j = 0; j < count; j++){
						sum += (str.charAt(dotLeft[i]+j+1) - '0') * list.get(j);
					}
					if(sum > 0 && sum < 200){
						length[q] = dotLeft[i];
						q++;
					}
					
				}
			}
			
			String s = "";
			s += str.substring(0, length[0]) + "\n";
			System.out.println(s);
			int i;
			for(i = 0; i < q-1; i++){
				s += str.substring(length[i],length[i+1]) + "\n";
			}
			s += str.substring(length[i], str.length());
			return s;
		}
		//①
		else if(str.charAt(0) == '①'){
			int[] length = new int [str.length()];
			int q = 0;
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) >= '①' && str.charAt(i) <= '⑳'){
					length[q] = i;
					q++;
				}
			}
			String s = "";
			s += str.substring(0, length[1]) + "\n";
			int i;
			for(i = 1; i < q-1; i++){
				s += str.substring(length[i], length[i+1]) + "\n";
			}
			s += str.substring(length[i], str.length());
			return s;
		}
		//⑴
		else if(str.charAt(0) == '⑴'){
			int[] length = new int [str.length()];
			int q = 0;
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) >= '⑴' && str.charAt(i) <= '⒇'){
					length[q] = i;
					q++;
				}
			}
			String s = "";
			s += str.substring(0, length[1]) + "\n";
			int i;
			for(i = 1; i < q-1; i++){
				s += str.substring(length[i], length[i+1]) + "\n";
			}
			s += str.substring(length[i], str.length());
			return s;
		}
		
		//1
		else if(str.charAt(0) == '1' && (str.charAt(1) != '.' && str.charAt(1) != '。')){
			int[] length = new int [str.length()];
			int q = 0;
			int[] dot = new int [str.length()];
			int l = 0;
			//收集所有的.
			for(int i = 0; i < str.length()-1; i++){
				if((str.charAt(i) >= '0' && str.charAt(i) <= '9') && (str.charAt(i+1) < '0' || str.charAt(i+1) > '9')){
					dot[l] = i+1;
					l++;
				}
			}
		
			//第一个符合条件的数字
			length[0] = 1; q++;
			//得到.前三个数字前的坐标
			for(int i = 1; i < l; i++){
				int j = dot[i] - 4;
				int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;
				if(str.charAt(j) >= '0' && str.charAt(j) <= '9'){
					flag1 = 1;
				}
				if(str.charAt(j+1) >= '0' && str.charAt(j+1) <= '9'){
					flag2 = 1;
				}
				if(str.charAt(j+2) >= '0' && str.charAt(j+2) <= '9'){
					flag3 = 1;
				}
				if(str.charAt(j+3) >= '0' && str.charAt(j+3) <= '9'){
					flag4 = 1;
				}
				//三位数
				int sum = 0;
				if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1){
					 sum = (str.charAt(j+1)-'0')*100+(str.charAt(j+2)-'0')*10+(str.charAt(j+3)-'0')*1;
					 if(sum >= 99 && sum <= 160){
						 length[q] = j+1;
						 q++;
						 continue;
					 }
				}
				//两位数
				if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 1){
					sum = (str.charAt(j+2)-'0')*10+(str.charAt(j+3)-'0')*1;
					if(sum >= 9 && sum <= 99){
						length[q] = j+2;
						q++;
						continue;
					}
				}
				//一位数
				if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1){
					sum = (str.charAt(j+3)-'0')*1;
					if(sum >=1 && sum <= 9){
						length[q] = j+3;
						q++;
						continue;
					}
				}
			}
			
			String s = "";
			s += str.substring(0, length[1]) + "\n";
			int i;
			for(i = 1; i < q-1; i++){
				s += str.substring(length[i], length[i+1]) + "\n";
			}
			s += str.substring(length[i], str.length());
			return s;
		}
		// 1. 或   1、
		else if(str.charAt(0) == '1' && (str.charAt(1) == '.' || str.charAt(1) == '、')){
			int[] length = new int [str.length()];
			int q = 0;
			int[] dot = new int [str.length()];
			int l = 0;
			//收集所有的.
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) == '.' || str.charAt(i) == '、'){
					dot[l] = i;
					l++;
				}
			}
		
			//第一个符合条件的数字
			length[0] = 1; q++;
			//得到.前三个数字前的坐标
			for(int i = 1; i < l; i++){
				int j = dot[i] - 3;
				int flag1 = 0, flag2 = 0, flag3 = 0;
				//个十百千    千位上的不是数字
				if(str.charAt(j) >= '0'   && str.charAt(j) <= '9'){
					flag1 = 1;
				}
				if(str.charAt(j+1) >= '0' && str.charAt(j+1) <= '9'){
					flag2 = 1;
				}
				if(str.charAt(j+2) >= '0' && str.charAt(j+2) <= '9'){
					flag3 = 1;
				}
				//三位数
				int sum = 0;
				if(flag1 == 1 && flag2 == 1 && flag3 == 1){
					 sum = (str.charAt(j)-'0')*100+(str.charAt(j+1)-'0')*10+(str.charAt(j+2)-'0')*1;
					 if(sum >= 100 && sum <= 170){
						 length[q] = j;
						 q++;
						 continue;
					 }
				}
				//两位数
				if(flag2 == 1 && flag3 == 1){
					sum = (str.charAt(j+1)-'0')*10+(str.charAt(j+2)-'0')*1;
					if(sum >= 10 && sum <= 99){
						length[q] = j+1;
						q++;
						continue;
					}
				}
				//一位数
				if(flag3 == 1){
					sum = (str.charAt(j+2)-'0')*1;
					if(sum >=1 && sum <= 9){
						length[q] = j+2;
						q++;
						continue;
					}
				}
			}
			
			String s = "";
			s += str.substring(0, length[1]) + "\n";
			int i;
			for(i = 1; i < q-1; i++){
				s += str.substring(length[i], length[i+1]) + "\n";
			}
			s += str.substring(length[i], str.length());
			return s;
		}
		//没有数字提示的注释
		else {
			//判断双引号与句号的个数
			double countDot = 0, countQuotation = 0;
			for(int i = 0; i < str.length(); i++){
				if(str.charAt(i) == '。'){
					countDot++;
				}
				if(str.charAt(i) == '“' || str.charAt(i) == '”'){
					countQuotation++;
				}
			}
			
			//判断出双引号很多
			if(countQuotation >= countDot*0.6){
				int[] length = new int [str.length()];
				int q = 0;
				//所有 冒号的坐标：
				int[] colon = new int [str.length()];
				int c = 0;
				//双引号外面  冒号的坐标：
				int[] colonOutside = new int [str.length()];
				int o = 0;
				//双引号的 左右坐标：
				int[] dotLeft = new int [str.length()];
				int[] dotRight = new int [str.length()];
				int l = 0, r = 0;
				//所有  结尾符号的坐标
				int[] dots = new int [str.length()];
				int s = 0;
				//双引号外 结尾符号的坐标
				int[] dotsOutside = new int [str.length()];
				int o1 = 0;
				int[] dotsBesides = new int [str.length()];
				int x = 0;
				
				for(int i = 0; i < str.length(); i++){
					//记录下 所有冒号的坐标：
					if(str.charAt(i) == '：'){
						colon[c] = i;
						c++;
					}
					//左双引号
					if(str.charAt(i) == '“'){
						dotLeft[l] = i;
						l++;
					}
					//右双引号
					if(str.charAt(i) == '”'){
						dotRight[r] = i;
						r++;
					}
					//所有结尾符号
					if(str.charAt(i) == '。' || str.charAt(i) == '！' || str.charAt(i) == '？'|| 
							str.charAt(i) == '”'|| str.charAt(i) == '’' || str.charAt(i) == '“' ||
							str.charAt(i) == '‘' ||str.charAt(i) == '《' || str.charAt(i) == '》' || str.charAt(i) == '·') {
						dots[s] = i;
						s++;
					}
					if(str.charAt(i) == '。' || str.charAt(i) == '！' || str.charAt(i) == '？'|| 
							str.charAt(i) == '”') {
						dotsBesides[x] = i;
						x++;
					}
				}
				
				int[] mark = new int [str.length()];
				int m = 0;
				int[] dotsMark = new int [str.length()];
				int d = 0;
				int[] dotsBesidesMark = new int [str.length()];
				int b = 0;
				//先判断在引号中的冒号，将其删去！
				for(int i = 0; i < c; i++){
					for(int j = 0; j < l; j++){
						//若引号在双引号内
						if(colon[i] > dotLeft[j] && colon[i] < dotRight[j]){
							mark[m] = colon[i];
							m++;
						}
					}
				}
				//将双引号内的dotsBesides去掉
				for(int i = 0; i < x; i++){
					int flag = 0;
					for(int j = 0; j < l; j++){
						if(dotsBesides[i] > dotLeft[j] && dotsBesides[i] < dotRight[j]){
							flag = 1;
						}
					}
					//dotsBesides 不在双引号内
					if(flag == 0){
						dotsBesidesMark[b] = dotsBesides[i];
						b++;
					}
				}
				//将双引号内的各种符号的坐标也标记
				for(int i = 0; i < s; i++){
					for(int j = 0; j < l; j++){
						if(dots[i] > dotLeft[j] && dots[i] < dotRight[j]){
							dotsMark[d] = dots[i];
							d++;
						}
					}
				}
				
				//将双引号之外的结尾符号存入新数组
				for(int i = 0; i < s; i++){
					int flag = 0;
					//该符号在双引号内
					for(int j = 0; j < d; j++){
						if(dots[i] == dotsMark[j]){
							flag = 1;
						}
					}
					//该符号不再双引号内
					if(flag == 0){
						dotsOutside[o1] = dots[i];
						o1++;
					}
				}
				//将不再双引号内的冒号存入新数组内
				for(int i = 0; i < c; i++){
					int flag = 0;
					for(int j = 0; j < m; j++){
						//说明该引号在双引号内
						if(colon[i] == mark[j]){
							flag = 1;
						}
					}
					//该冒号不再双引号内
					if(flag == 0){
						colonOutside[o] = colon[i];
						o++;
					}
				}
				
				String s1 = "";
				s1 += "(1)" + str.substring(0, dotsBesidesMark[0]+1) + "\n";
				//System.out.println(s1);
				//判断距离已经经过过滤的引号最近的一个 结尾符号，并记录该结尾符号的后一个坐标，作为换行依据
				//从第二个冒号开始判断
				for(int i = 1; i < o; i++){
					//j是冒号的前一个坐标
					for(int j = colonOutside[i]-1; j > 0 ; j--){
						boolean g = false;
						for(int k = 0; k < o1; k++){
							//判断该坐标是否是一个结尾符号坐标
							g = (j == dotsOutside[k]);
							//是结尾符号，找到这个结尾符号的下一个坐标
							if(g){
								// ①  。|……      ② ！|……     ③ ？|……
								if(str.charAt(dotsOutside[k]) == '。' || str.charAt(dotsOutside[k]) == '！' 
										|| str.charAt(dotsOutside[k]) == '？'){
									length[q] = dotsOutside[k]+1;
									q++;
									break;
								}
								if(str.charAt(dotsOutside[k]) == '”'){
									
									if(k > 3){
										//    ：“……  。”|
										if(str.charAt(dotsOutside[k]-1) == '。' && str.charAt(dotsOutside[k-1]) == '“'  
												&& str.charAt(dotsOutside[k-1]-1) == '：'){
											length[q] = dotsOutside[k]+1;
											q++;
											break;
										}
										//⑤ “……” |“ ”：
										//这种情况不换行
										if(str.charAt(dotsOutside[k-1]) == '“' && str.charAt(dotsOutside[k-2]) == '”' 
												&& str.charAt(dotsOutside[k-3]) == '“' && str.charAt(dotsOutside[k-2]-1) == '。'){
											if(str.charAt(dotsOutside[k-1]) != ' '){
												length[q] = dotsOutside[k-1];
												break;
											}
										}
									}
									
									if(k > 2){
										// ①  …… 。|“ ”:    ②  …… ！|“ ”：        ③  …… ？|“ ”：
										if(str.charAt(dotsOutside[k-1]) == '“' && 
											(str.charAt(dotsOutside[k-2]) == '。' || str.charAt(dotsOutside[k-2]) == '！' 
											|| str.charAt(dotsOutside[k-2]) == '？') ){
											length[q] = dotsOutside[k-1];
											q++;
											break;
										}
										
										//④ ：“ ” |……：
										if(str.charAt(dotsOutside[k-1]) == '“' && str.charAt(dotsOutside[k-1]-1)== '：'){
											length[q] = dotsOutside[k]+1;
											q++;
											break;
										}
									}
									
									// 。 |  ……：           
									if(k>1){
										if(str.charAt(dotsOutside[k]-1) == '。' || str.charAt(dotsOutside[k]-1) == '！' 
												|| str.charAt(dotsOutside[k]-1) == '？'){
											length[q] = dotsOutside[k]+1;
											q++;
											break;
										}
									}
									
								}							
							}
						}
						if(g){
							break;
						}	
					}
					
				}
				//已经得到所有结尾符号的坐标
				int i;
				for(i = 0; i < q-1; i++){
					s1 += "("+ (i+2) + ")" + str.substring(length[i], length[i+1]) + "\n";
				}
				s1 += "(" + (i+2) + ")" + str.substring(length[i], str.length());
				return s1;
			}
			//双引号比较少
			else{
				int[] length = new int [str.length()];
				int q = 0;
				//所有 冒号的坐标：
				int[] colon = new int [str.length()];
				int c = 0;
				//双引号外面  冒号的坐标：
				int[] colonOutside = new int [str.length()];
				int o = 0;
				//双引号的 左右坐标：
				int[] dotLeft = new int [str.length()];
				int[] dotRight = new int [str.length()];
				int l = 0, r = 0;
				//所有  结尾符号的坐标
				int[] dots = new int [str.length()];
				int s = 0;
				//双引号外 结尾符号的坐标
				int[] dotsOutside = new int [str.length()];
				int o1 = 0;
				
				for(int i = 0; i < str.length(); i++){
					//记录下 所有冒号的坐标：
					if(str.charAt(i) == '：'){
						colon[c] = i;
						c++;
					}
					//左双引号
					if(str.charAt(i) == '“'){
						dotLeft[l] = i;
						l++;
					}
					//右双引号
					if(str.charAt(i) == '”'){
					
						dotRight[r] = i;
						r++;
					}
					//所有结尾符号
					if(str.charAt(i) == '。' || str.charAt(i) == '！' || str.charAt(i) == '？'|| 
							str.charAt(i) == '”'|| str.charAt(i) == '’' || str.charAt(i) == '“' ||
							str.charAt(i) == '‘' ||str.charAt(i) == '《' || str.charAt(i) == '》' || str.charAt(i) == '·') {
						dots[s] = i;
						s++;
					}
				}
				
				int[] mark = new int [str.length()];
				int m = 0;
				int[] dotsMark = new int [str.length()];
				int d = 0;
				//先判断在引号中的冒号，将其删去！
				for(int i = 0; i < c; i++){
					for(int j = 0; j < l; j++){
						//若引号在双引号内
						if(colon[i] > dotLeft[j] && colon[i] < dotRight[j]){
							mark[m] = colon[i];
							m++;
						}
					}
				}
				//将双引号内的各种符号的坐标也标记
				for(int i = 0; i < s; i++){
					for(int j = 0; j < l; j++){
						if(dots[i] > dotLeft[j] && dots[i] < dotRight[j]){
							dotsMark[d] = dots[i];
							d++;
						}
					}
				}
				//将双引号之外的结尾符号存入新数组
				for(int i = 0; i < s; i++){
					int flag = 0;
					//该符号在双引号内
					for(int j = 0; j < d; j++){
						if(dots[i] == dotsMark[j]){
							flag = 1;
						}
					}
					//该符号不再双引号内
					if(flag == 0){
						dotsOutside[o1] = dots[i];
						o1++;
					}
				}
				//将不再双引号内的冒号存入新数组内
				for(int i = 0; i < c; i++){
					int flag = 0;
					for(int j = 0; j < m; j++){
						//说明该引号在双引号内
						if(colon[i] == mark[j]){
							flag = 1;
						}
					}
					//该冒号不再双引号内
					if(flag == 0){
						colonOutside[o] = colon[i];
						o++;
					}
				}
			
				String s1 = "";
				s1 += "(1)" + str.substring(0, dotsOutside[0]+1) + "\n";
				//判断距离已经经过过滤的引号最近的一个 结尾符号，并记录该结尾符号的后一个坐标，作为换行依据
				//从第二个冒号开始判断
				for(int i = 1; i < o; i++){
					//j是冒号的前一个坐标
					for(int j = colonOutside[i]-1; j > 0 ; j--){
						boolean b = false;
						for(int k = 0; k < o1; k++){
							//判断该坐标是否是一个结尾符号坐标
							b = (j == dotsOutside[k]);
							//是结尾符号，找到这个结尾符号的下一个坐标
							if(b){
									// 。 |  ……：           
								
								if(str.charAt(dotsOutside[k]) == '。' || str.charAt(dotsOutside[k]) == '！' 
										|| str.charAt(dotsOutside[k]) == '？'){
									
									length[q] = dotsOutside[k]+1;
									q++;
									break;
								}
							}							
						}
						if(b){
							break;
						}	
					}
				}
				//已经得到所有结尾符号的坐标
				int i;
				for(i = 0; i < q-1; i++){
					s1 += "("+ (i+2) + ")" + str.substring(length[i], length[i+1]) + "\n";
				}
				s1 += "(" + (i+2) + ")" + str.substring(length[i], str.length());
				return s1;
			}		
		}
	}

}
