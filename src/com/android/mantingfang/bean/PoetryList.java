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
				Poetry p = new Poetry(
						Integer.parseInt(jo.getString("poetryid")),
						jo.getString("title"),
						jo.getString("typeid"),
						Integer.parseInt(jo.getString("kindid")),
						Integer.parseInt(jo.getString("writerid")),
						jo.getString("content"),
						"");
				plist.getPoetryList().add(p);
			}
		}
		return plist;
	}
}
