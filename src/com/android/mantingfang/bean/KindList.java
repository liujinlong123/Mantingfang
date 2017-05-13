package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class KindList extends Base {
	
	private List<Kind> kindList = new ArrayList<Kind>();
	
	private int kindCount;
	
	public List<Kind> getKindList() {
		return kindList;
	}
	
	public int getKindCount() {
		return kindCount;
	}
	
	public static KindList parse(JSONArray obj) throws JSONException {
		KindList kinds = new KindList();
		
		if (obj != null) {
			kinds.kindCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Kind kind = new Kind(
						Integer.parseInt(jo.getString("kind_id")),
						jo.getString("kind_name"));
				kinds.kindList.add(kind);
			}
		}
		return kinds;
	}
}
