package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.model.PoemM;

import android.util.Log;

@SuppressWarnings("serial")
public class PoetryList extends Base {

	private List<Poetry> poetryList = new ArrayList<Poetry>();
	private List<PoemM> poemList = new ArrayList<PoemM>();
	
	private int poetryCount;
	private int poemCount;
	
	public List<Poetry> getPoetryList() {
		return poetryList;
	}
	
	public List<PoemM> getPoemMList() {
		return poemList;
	}
	
	public int getPoetryCount() {
		return poetryCount;
	}
	
	public int getPoemMCount() {
		return poemCount;
	}
	
	public static PoetryList parse(JSONArray obj) throws JSONException {
		
		PoetryList plist = new PoetryList();
		if (obj != null) {
			plist.poetryCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Poetry p = new Poetry(jo.getInt("poetry_id"),
						jo.getString("poetry_label_id"),
						jo.getInt("poetry_writer_id"),
						jo.getInt("poetry_language_id"),
						
						jo.getString("poetry_name"),
						jo.getString("poetry_content"),
						jo.getString("poetry_rhesis"));
				plist.getPoetryList().add(p);
			}
		}
		return plist;
	}
	
	/**
	 * param poem
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static PoetryList parsePoem(JSONArray obj) throws JSONException {
		
		PoetryList pList = new PoetryList();
		if (obj != null) {
			pList.poemCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemM p = new PoemM(
						jo.getString("poetry_id"),
						jo.getString("poetry_name"),
						jo.getString("poetry_content"),
						null,
						jo.getString("writer_name"),
						jo.getString("dynasty_name"),
						jo.getString("info_background"),
						jo.getString("writer_career"),
						jo.getString("poetry_note"),
						jo.getString("info_tonow"),		//����
						null,							//����
						jo.getString("info_praise"));
				
				pList.poemList.add(p);
				Log.v("PoemM---", p.getPoemContent());
			}
		}
		
		return pList;
	}
}
