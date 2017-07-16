package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.third.ThirdOneContent;
import com.android.mantingfang.third.UserTwoContent;

import android.util.Log;

@SuppressWarnings("serial")
public class TopicList extends Base {

	private List<ThirdOneContent> topicList = new ArrayList<ThirdOneContent>();
	private List<UserTwoContent> listTwo = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listThree = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listFour = new ArrayList<UserTwoContent>();
	
	private int topicCount;
	private int topicTwoCount;
	private int topicThreeCount;
	private int topicFourCount;
	
	public List<ThirdOneContent> getTopicList() {
		return topicList;
	}
	
	public List<UserTwoContent> getTopicTwo() {
		return listTwo;
	}
	
	public List<UserTwoContent> getTopicThree() {
		return listThree;
	}
	
	public List<UserTwoContent> getTopicFour() {
		return listFour;
	}
	
	public int getTopicCount() {
		return topicCount;
	}
	
	public int getTopicTwoCount() {
		return topicTwoCount;
	}
	
	public int getTopicThreeCount() {
		return topicThreeCount;
	}
	
	public int getTopicFourCount() {
		return topicFourCount;
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
				Log.v("TEST", jo.toString());
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
				//Log.v("Test", content.getContent());
			}
		}
		
		return tList;
	}
	
	/**
	 * 界面二
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseTwo(JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicTwoCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				UserTwoContent content = new UserTwoContent(
						jo.getString("note_user_id"),
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("note_time"),
						jo.getString("note_content"),
						StringUtils.getPictures(jo.getString("note_picture")),
						null,
						jo.getString("note_poem_id"),
						jo.getString("poetry_content"),
						jo.getString("poetry_name"),
						false,
						2,
						Integer.parseInt(jo.getString("note_id")));
				
				tList.listTwo.add(content);
				//Log.v("Test", content.getContent());
			}
		}
		return tList;
	}
	
	/**
	 * 界面三
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseThree(JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicThreeCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				UserTwoContent content = new UserTwoContent(
						jo.getString("original_user_id"),
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("original_time"),
						jo.getString("original_content"),
						StringUtils.getPictures(jo.getString("original_picture")),
						null,
						null,
						null,
						null,
						false,
						3,
						Integer.parseInt(jo.getString("original_id")));
				
				tList.listThree.add(content);
				//Log.v("Test", content.getContent());
			}
		}
		
		return tList;
	}
	
	public static TopicList parseFour(JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicThreeCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				UserTwoContent content = new UserTwoContent(
						jo.getString("audio_user_id"),
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("audio_time"),
						null,
						null,
						jo.getString("audio_content"),
						jo.getString("audio_poem_id"),
						jo.getString("poetry_content"),
						jo.getString("poetry_name"),
						false,
						4,
						Integer.parseInt(jo.getString("audio_id")));
				
				tList.listFour.add(content);
				//Log.v("Test", content.getSoundPath());
			}
		}
		
		return tList;
	}}
