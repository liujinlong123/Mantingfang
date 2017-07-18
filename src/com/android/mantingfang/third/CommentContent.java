package com.android.mantingfang.third;

public class CommentContent {
	private String topicId;		//帖子Id
	private String topicNum;	//帖子标号
	private String headPath;	//头像路径
	private String name;		//用户昵称
	private String time;		//发布时间
	private boolean zan;		//是否被赞
	private String content;		//发布内容
	private String userId;		//所属用户Id
	
	public CommentContent() {};
	
	public CommentContent(String headPath, String name, String time, boolean zan, String content) {
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.zan = zan;
		this.content = content;
	}
	
	public CommentContent(String topicId, String topicNum, String headPath,
			String name, String time, String content, String userId) {
		this.topicId = topicId;
		this.topicNum = topicNum;
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.content = content;
		this.userId = userId;
	}
	
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public String getTopicId() {
		return topicId;
	}
	
	public void setTopicNum(String topicNum) {
		this.topicNum = topicNum;
	}
	
	public String getTopicNum() {
		return topicNum;
	}
	
	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}
	
	public String getHeadPath() {
		return headPath;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setZan(boolean zan) {
		this.zan = zan;
	}
	
	public boolean getZan() {
		return zan;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
}
