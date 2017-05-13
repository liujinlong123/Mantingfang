package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class WriterList extends Base {

	private List<Writer> writerList = new ArrayList<Writer>();
	
	private int writerCount;
	
	public List<Writer> getWriterList() {
		return writerList;
	}
	
	public int getWriterCount() {
		return writerCount;
	}
	
	public static WriterList parse(JSONArray obj) throws JSONException {
		
		WriterList wList = new WriterList();
		if (obj != null) {
			wList.writerCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Writer writer = new Writer(
						Integer.parseInt(jo.getString("writer_id")),
						Integer.parseInt(jo.getString("writer_label_id")),
						Integer.parseInt(jo.getString("writer_dynasty_id")),
						Integer.parseInt(jo.getString("writer_country_id")),
						jo.getString("writer_name"),
						jo.getString("writer_career"));
				wList.writerList.add(writer);
			}
		}
		
		return wList;
	}
}
