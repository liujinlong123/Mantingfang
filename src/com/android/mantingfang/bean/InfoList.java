package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class InfoList extends Base {

	private List<Info> infoList = new ArrayList<Info>();
	
	private int infoCount;
	
	public List<Info> getInfoList() {
		return infoList;
	}
	
	public int getInfoCount() {
		return infoCount;
	}
	
	public static InfoList parse(JSONArray obj) throws JSONException {
		InfoList infos = new InfoList();
		
		if (obj != null) {
			infos.infoCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Info info = new Info(
						Integer.parseInt(jo.getString("infoid")),
						Integer.parseInt(jo.getString("cateid")),
						Integer.parseInt(jo.getString("fid")),
						jo.getString("title"),
						jo.getString("adder"),
						jo.getString("content"));
				infos.infoList.add(info);
			}
		}
		return infos;
	}
}
