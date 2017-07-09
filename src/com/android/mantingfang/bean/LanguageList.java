package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class LanguageList extends Base {

	private List<Language> lanList = new ArrayList<Language>();
	
	private int lanCount;
	
	public List<Language> getLanguageList() {
		return lanList;
	}
	
	public int getLanguageCount() {
		return lanCount;
	}
	
	public static LanguageList parse(JSONArray obj) throws JSONException {
		LanguageList lans = new LanguageList();
		
		if (obj != null) {
			lans.lanCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Language lan = new Language(
						jo.getInt("language_id"),
						jo.getString("language_name"));
				lans.lanList.add(lan);
			}
		}
		
		return lans;
	}
}
