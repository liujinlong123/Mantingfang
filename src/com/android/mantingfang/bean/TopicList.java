package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mantingfang.first.PoemRhesis;
import com.android.mantingfang.model.Poem;
import com.android.mantingfang.third.CommentContent;
import com.android.mantingfang.third.User;
import com.android.mantingfang.third.UserTwoContent;

@SuppressWarnings("serial")
public class TopicList extends Base {

	private List<UserTwoContent> topicList = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listTwo = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listThree = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listFour = new ArrayList<UserTwoContent>();
	private List<UserTwoContent> listUser = new ArrayList<UserTwoContent>();
	private List<CommentContent> listComment = new ArrayList<CommentContent>();
	private List<PoemRhesis> listRhesis = new ArrayList<>();
	private List<User> listUserinfo = new ArrayList<>();
	private List<PoemRhesis> listSearch = new ArrayList<>();
	private List<Poem> listKindPoem = new ArrayList<>();
	private List<Writer> listAllWriters = new ArrayList<>();
	private List<Poem> listWriterPoem = new ArrayList<>();
	//-------------------Search-----------------------------//
	private List<PoemRhesis> listSearchPoem = new ArrayList<>();
	private List<Writer> listSearchWriter = new ArrayList<>();
	private List<PoemRhesis> listSearchContent = new ArrayList<>();
	private int searchPoemCount;
	private int searchWriterCount;
	private int searchContentCount;
	//-------------------Search-----------------------------//
	
	private int topicCount;
	private int topicTwoCount;
	private int topicThreeCount;
	private int topicFourCount;
	private int userCount;
	private int commentCount;
	private int rhesisCount;
	private int userInfoCount;
	private int searchCount;
	private int kindPoemCount;
	private int allWritersCount;
	private int writerPoemCount;
	
	//------------------------------返回ArrayList-----------------------------------
	
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
	
	public List<User> getUserInfoList() {
		return listUserinfo;
	}
	
	public List<PoemRhesis> getSearchList() {
		return listSearch;
	}
	
	public List<Poem> getKindPoemList() {
		return listKindPoem;
	}
	
	public List<Writer> getAllWritersList() {
		return listAllWriters;
	}
	
	public List<Poem> getWriterPoemList() {
		return listWriterPoem;
	}
	
	public List<PoemRhesis> getSearchPoemList() {
		return listSearchPoem;
	}
	
	public List<Writer> getSearchWriterList() {
		return listSearchWriter;
	}
	
	public List<PoemRhesis> getSearchContentList() {
		return listSearchContent;
	}
	
	
	//------------------------------返回ArrayList Size()-----------------------------------
	
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
	
	public int getUserInfoCount() {
		return userInfoCount;
	}
	
	public int getSearchCount() {
		return searchCount;
	}
	
	public int getKindPoemCount() {
		return kindPoemCount;
	}
	
	public int getAllWritersCount() {
		return allWritersCount;
	}
	
	public int getWrterPoemCount() {
		return writerPoemCount;
	}
	
	public int getSearchPoemCount() {
		return searchPoemCount;
	}
	
	public int getSearchWriterCount() {
		return searchWriterCount;
	}
	
	public int getSearchContentCount() {
		return searchContentCount;
	}
	
	//------------------------------返回处理结果-----------------------------------
	
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
				//Log.v("ThirdOnePicture", content.getPicture().toString());
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
						null,
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
						jo.optString("user_photo"),
						jo.optString("user_nickname"),
						jo.optString("audio_time"),
						null,
						null,
						jo.optString("audio_content"),
						jo.optString("audio_poem_id"),
						jo.optString("poetry_content"),
						jo.optString("poetry_name"),
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
				String topicTime = jo.optString("topic_time");
				String noteTime = jo.optString("note_time");
				String originalTime = jo.optString("original_time");
				String audioTime = jo.optString("audio_time");
				UserTwoContent content = null;
				if (topicTime != null && !topicTime.equals("")) {
					content = new UserTwoContent(
							userId,
							headPath,
							nickName,
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
							userId,
							headPath,
							nickName,
							jo.getString("note_time"),
							jo.getString("note_content"),
							null,
							null,
							jo.getString("note_poem_id"),
							jo.getString("poetry_content"),
							jo.getString("poetry_name"),
							false,
							2,
							Integer.parseInt(jo.getString("note_id")));
				} else if (originalTime != null && !originalTime.equals("")) {
					content = new UserTwoContent(
							userId,
							headPath,
							nickName,
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
							userId,
							headPath,
							nickName,
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
			tList.rhesisCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemRhesis content = new PoemRhesis(
						jo.optString("poetry_id"),
						jo.optString("writer_name"),
						jo.optString("poetry_rhesis"));
				
				tList.listRhesis.add(content);
				//Log.v("Test", content.getRhesis());
			}
		}
		
		return tList;
	}
	
	/**
	 * 获取用户个人信息
	 * @param obj
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseUserInfo(JSONArray obj, String userId) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.userInfoCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				User content = new User(
						userId,
						jo.optString("user_nickname"),
						jo.optString("user_photo"),
						jo.optString("user_age"),
						jo.optString("user_sex"),
						jo.optString("user_area"),
						jo.optString("user_introduce"),
						jo.optString("user_label"));
				
				tList.listUserinfo.add(content);
				//Log.v("Test", content.getRhesis());
			}
		}
		
		return tList;
	}
	
	/**
	 * 解析通过关键字搜索获取的诗词信息
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseSearchPoem (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.searchCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemRhesis poem = new PoemRhesis(
						jo.optString("poetry_id"),
						jo.optString("poetry_name"),
						jo.optString("dynasty_name"),
						jo.optString("writer_name"),
						jo.optString("poetry_rhesis"),
						jo.optString("poetry_content"));
				
				tList.listSearch.add(poem);
				//Log.v("Test", content.getRhesis());
			}
		}
		
		return tList;
	}
	
	/**
	 * 解析一类诗词
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseKindPoem (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.kindPoemCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Poem poem = new Poem(
						jo.getString("poetry_id"),
						jo.getString("poetry_name"),
						jo.getString("poetry_label_id"),
						jo.getString("poetry_rhesis"),
						jo.getString("dynasty_name"),
						jo.getString("writer_name"));
				
				tList.listKindPoem.add(poem);
				//Log.v("Test", content.getRhesis());
			}
		}
		
		return tList;
	}
	
	/**
	 * 解析获取到的所有诗人
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseAllWriters (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.allWritersCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Writer writer = new Writer(
						jo.getString("writer_id"),
						jo.optString("writer_name"),
						jo.optString("dynasty_name"),
						jo.optString("writer_career"));
				
				tList.listAllWriters.add(writer);
			}
		}
		
		return tList;
	}
	
	/**
	 * 解析一个诗人的所有诗
	 * @param obj
	 * @param dynastyName
	 * @param writerName
	 * @return
	 * @throws JSONException
	 */
	public static TopicList parseWritersPoem (JSONArray obj, String dynastyName, String writerName) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.writerPoemCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Poem poem = new Poem(
						jo.getString("poetry_id"),
						jo.getString("poetry_name"),
						null,
						jo.getString("poetry_rhesis"),
						dynastyName,
						writerName);
				
				tList.listWriterPoem.add(poem);
				//Log.v("POMEEEE", poem.getPoemId() + "---" + poem.getPoemName());
			}
		}
		
		return tList;
	}
	
	
	//----------------------------------------------解析搜索-------------------------------------------------------------
	public static TopicList parseSearchPoemList (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.searchPoemCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemRhesis rhesis = new PoemRhesis(
						jo.getString("poetry_id"),
						jo.getString("poetry_name"),
						jo.getString("dynasty_name"),
						jo.getString("writer_name"),
						jo.getString("poetry_rhesis"));
				
				tList.listSearchPoem.add(rhesis);
				//Log.v("POMEEEE", poem.getPoemId() + "---" + poem.getPoemName());
			}
		}
		
		return tList;
	}
	
	public static TopicList parseSearchWriterList (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.searchWriterCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Writer writer = new Writer(
						jo.getString("writer_id"),
						jo.optString("writer_name"),
						jo.optString("dynasty_name"),
						jo.optString("writer_career"));
				
				tList.listSearchWriter.add(writer);
				//Log.v("POMEEEE", poem.getPoemId() + "---" + poem.getPoemName());
			}
		}
		
		return tList;
	}
	
	public static TopicList parseSearchContentList (JSONArray obj) throws JSONException {
		TopicList tList = new TopicList();
		if (obj != null) {
			tList.searchContentCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				PoemRhesis rhesis = new PoemRhesis(
						jo.getString("poetry_id"),
						jo.getString("poetry_name"),
						null,
						null,
						jo.getString("poetry_content"));
				
				tList.listSearchContent.add(rhesis);
				//Log.v("POMEEEE", poem.getPoemId() + "---" + poem.getPoemName());
			}
		}
		
		return tList;
	}
}