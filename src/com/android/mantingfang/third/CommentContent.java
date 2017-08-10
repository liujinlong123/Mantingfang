package com.android.mantingfang.third;

public class CommentContent {
	private String topicId;	
	private String topicNum;
	private String headPath;
	private String name;
	private String time;
	private String zan;
	private String content;
	private String userId;
	
	private String zanNumber;
	private String bePostContent;
	private String bePostUserId;
	private String bePostNickame;
	private String postId;
	private String bePostId;

	public String getBePostId() {
		return bePostId;
	}

	public void setBePostId(String bePostId) {
		this.bePostId = bePostId;
	}

	public CommentContent() {};
	
	public CommentContent(String headPath, String name, String time, String zan, String content) {
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.zan = zan;
		this.content = content;
	}
	
	public CommentContent(String topicId, String topicNum, String userId, String headPath, String nickName,
			String time, String content, String zan, String zanNumber, String bePostContent, String bePostUserId, 
			String bePostNickname, String postId, String bePostId) {
		this.topicId = topicId;
		this.topicNum = topicNum;
		this.userId = userId;
		this.headPath = headPath;
		this.name = nickName;
		this.time = time;
		this.content = content;
		this.zan = zan;
		this.zanNumber = zanNumber;
		this.bePostContent = bePostContent;
		this.bePostUserId = bePostUserId;
		this.bePostNickame = bePostNickname;
		this.postId = postId;
		this.bePostId = bePostId;
	}
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public String getZanNumber() {
		return zanNumber;
	}

	public void setZanNumber(String zanNumber) {
		this.zanNumber = zanNumber;
	}

	public String getBePostContent() {
		return bePostContent;
	}

	public void setBePostContent(String bePostContent) {
		this.bePostContent = bePostContent;
	}

	public String getBePostUserId() {
		return bePostUserId;
	}

	public void setBePostUserId(String bePostUserId) {
		this.bePostUserId = bePostUserId;
	}

	public String getBePostNickame() {
		return bePostNickame;
	}

	public void setBePostNickame(String bePostNickame) {
		this.bePostNickame = bePostNickame;
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
	
	public void setZan(String zan) {
		this.zan = zan;
	}
	
	public String getZan() {
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
