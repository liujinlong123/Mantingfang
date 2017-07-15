package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static String getString4Array(int[] ids){
		
		String result ="";
		for(int i=0;i<ids.length;i++){
			result = result + ids[i]+",";
		}
		return result.substring(0,result.length()-1);
	}
	
	/** 
     * °ë½Ç×ª»»ÎªÈ«½Ç 
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
    

    public static String[] removeArrIndex(String[] arr ,int index){
    	
        List<String> list = new ArrayList<String>();  
        for (int i=0; i<arr.length; i++) {  
            list.add(arr[i]);  
        }  
    	list.remove(index);   	  
    	return list.toArray(new String[1]);
    	
    }
    
    public static ArrayList<String> getPictures(String pic) {
    	ArrayList<String> pictures = new ArrayList<>();
    	String[] tokens = pic.split("[ ]");
    	for (int i = 0; i < tokens.length; i++) {
    		pictures.add(tokens[i]);
    	}
    	
    	return pictures;
    }

}
