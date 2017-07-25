package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.first.PoemRhesis;
import com.android.mantingfang.third.CommentContent;
import com.android.mantingfang.third.UserTwoContent;

import android.util.Log;

@SuppressWarnings("serial")
public class TopicList extends Base {

	private List<UserTwoContent> topicList = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listTwo = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listThree = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listFour = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listUser = new ArrayList<UserTwoContent>();
	private List<CommentContent> listComment = new ArrayList<CommentContent>();
	private List<PoemRhesis> listRhesis = new ArrayList<>();
	
	private int topicCount;
	private int topicTwoCount;
	private int topicThreeCount;
	private int topicFourCount;
	private int userCount;
	private int commentCount;
	private int rhesisCount;
	
	public List<UserTwoContent> getTopicList() {
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
	
	public List<UserTwoContent> getUserList() {
		return listUser;
	}
	
	public List<CommentContent> getCommentList() {
		return listComment;
	}
	
	public List<PoemRhesis> getRhesisList() {
		return listRhesis;
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
	
	public int getUserCount() {
		return userCount;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	
	public int getRhesisCount() {
		return rhesisCount;
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
				UserTwoContent content = new UserTwoContent(
						jo.getString("topic_user_id"),
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("topic_time"),
						jo.getString("topic_content"),
						StringUtils.getPictures(jo.getString("topic_picture")),
						null,
						null,
						null,
						null,
						false,
						1,
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
	
	/**
	 * 界面四
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
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
	}
	
	/**
	 * User Two
	 * @param obj
	 * @param userId
	 * @param headPath
	 * @param nickName
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseUser(JSONArray obj, String userId, String headPath, String nickName) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.userCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Log.v("JO", jo.toString());
				String topicTime = jo.optString("topic_time");
				String noteTime = jo.optString("note_time");
				String originalTime = jo.optString("original_time");
				String audioTime = jo.optString("audio_time");
				UserTwoContent content = null;
				if (topicTime != null && !topicTime.equals("")) {
					content = new UserTwoContent(
							"",
							"",
							"",
							jo.getString("topic_time"),
							jo.getString("topic_content"),
							StringUtils.getPictures(jo.getString("topic_picture")),
							null,
							null,
							null,
							null,
							false,
							1,
							Integer.parseInt(jo.getString("topic_id")));
				} else if (noteTime != null && !noteTime.equals("")) {
					content = new UserTwoContent(
							"",
							"",
							"",
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
				} else if (originalTime != null && !originalTime.equals("")) {
					content = new UserTwoContent(
							"",
							"",
							"",
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
				} else if (audioTime != null && !audioTime.equals("")) {
					content = new UserTwoContent(
							"",
							"",
							"",
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
				}
				
				tList.listUser.add(content);
				Log.v("TEST" + content.getPostComNum(), content.getTime());
			}
		}
		
		return tList;
	}
	
	/**
	 * 解析评论数据
	 * @param obj
	 * @param topicId
	 * @param topicNum
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseComment(JSONArray obj, String topicId, String topicNum) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicThreeCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				CommentContent content = new CommentContent(
						topicId,
						topicNum,
						jo.getString("user_photo"),
						jo.getString("user_nickname"),
						jo.getString("comment_time"),
						jo.getString("comment_content"),
						jo.getString("comment_user_id"));
				
				tList.listComment.add(content);
				Log.v("Test", content.getContent());
			}
		}
		
		return tList;
	}
	
	/**
	 * ViewPager 名句解析
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseRhesis(JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.topicThreeCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemRhesis content = new PoemRhesis(
						jo.getString("poem_id"),
						jo.getString("writer_name"),
						jo.getString("poem_rhesis"));
				
				tList.listRhesis.add(content);
				Log.v("Test", content.getRhesis());
			}
		}
		
		return tList;
	}
}