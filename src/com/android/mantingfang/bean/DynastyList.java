package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class DynastyList extends Base {

	private List<Dynasty> dynastyList = new ArrayList<Dynasty>();
	
	private int dynastyCount;
	
	public List<Dynasty> getDynastyList() {
		return dynastyList;
	}
	
	public int getInfoCount() {
		return dynastyCount;
	}
	
	public static DynastyList parse(JSONArray obj) throws JSONException {
		DynastyList dynastyList = new DynastyList();
		
		if (obj != null) {
			dynastyList.dynastyCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Dynasty dynasty = new Dynasty(
						Integer.parseInt(jo.getString("dynasty_id")),
						jo.getString("dynasty_name"));
				dynastyList.dynastyList.add(dynasty);
			}
		}
		
		return dynastyList;
	}
}
