package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.third.ThirdOneContent;

import android.util.Log;

@SuppressWarnings("serial")
public class TopicList extends Base {

	private List<ThirdOneContent> topicList = new ArrayList<ThirdOneContent>();
	
	private int topicCount;
	
	public List<ThirdOneContent> getTopicList() {
		return topicList;
	}
	
	public int getTopicCount() {
		return topicCount;
	}
	
	/**
	 * Topic--One
	 * @param obj
	 * @param topic_num
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseOne(JSONArray obj, int topic_num) throws JSONException {
		
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				ThirdOneContent content = new ThirdOneContent(
						jo.getString("topic_user_id"),
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("topic_time"),
						jo.getString("topic_content"),
						StringUtils.getPictures(jo.getString("topic_picture")),
						null,
						topic_num,
						Integer.parseInt(jo.getString("topic_id")));
				
				tList.topicList.add(content);
				Log.v("Test", content.getContent());
			}
		}
		
		return tList;
	}
}
