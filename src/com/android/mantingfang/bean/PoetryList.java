package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class PoetryList extends Base {

	private List<Poetry> poetryList = new ArrayList<Poetry>();
	private int poetryCount;
	
	public List<Poetry> getPoetryList() {
		return poetryList;
	}
	
	public int getPoetryCount() {
		return poetryCount;
	}
	
	public static PoetryList parse(JSONArray obj) throws JSONException {
		
		PoetryList plist = new PoetryList();
		if (obj != null) {
			plist.poetryCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Poetry p = new Poetry(Integer.parseInt(jo.getString("poetry_id")),
						jo.getString("poetry_label_id"),
						Integer.parseInt(jo.getString("poetry_writer_id")),
						Integer.parseInt(jo.getString("poetry_language_id")),
						
						jo.getString("poetry_name"),
						jo.getString("poetry_content"),
						jo.getString("poetry_rhesis"));
				plist.getPoetryList().add(p);
			}
		}
		return plist;
	}
}
