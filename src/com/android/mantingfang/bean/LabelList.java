package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class LabelList extends Base {

	private List<Label> labelList = new ArrayList<Label>();
	
	private int labelCount;
	
	public List<Label> getLabelList() {
		return labelList;
	}
	
	public int getLabelCount() {
		return labelCount;
	}
	
	public static LabelList parse(JSONArray obj) throws JSONException {
		LabelList labels = new LabelList();
		
		if (obj != null) {
			labels.labelCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Label label = new Label(
						Integer.parseInt(jo.getString("label_id")),
						jo.getString("label_poetry_id"),
						Integer.parseInt(jo.getString("label_kind_id")),
						jo.getString("label_name"));
				labels.labelList.add(label);
			}
		}
		
		return labels;
	}
}
