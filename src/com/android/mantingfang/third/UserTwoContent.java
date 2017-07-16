package com.android.mantingfang.third;

import java.util.ArrayList;

public class UserTwoContent {
	private String userId;		//用户id
	private String headPath;	//头像
	private String name;		//昵称
	private String time;		//时间
	private String content;		//内容
	private ArrayList<String> picture;	//图片组
	private String soundPath;	//音频路径
	//private Poem poem;			//诗
	private String poemId;		//诗的id
	private String poemContent;	//诗内容
	private String poemName;	//诗名
	private boolean zan;		//是否被赞
	private int post_com_num;	//帖子标号
	private int post_com_pId;	//帖子ID
	
	public UserTwoContent() {}
	
	public UserTwoContent(String userId, String headPath, String name, String time, String content, ArrayList<String> picture,
			String soundPath, String poemId, String poemContent, String poemName, boolean zan, int post_com_num, int post_com_pId) {
		this.userId = userId;
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.content = content;
		this.picture = picture;
		this.poemId = poemId;
		this.poemContent = poemContent;
		this.poemName = poemName;
		this.soundPath = soundPath;
		this.zan = zan;
		this.post_com_num = post_com_num;
		this.post_com_pId = post_com_pId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
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
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setPicture(ArrayList<String> picture) {
		this.picture = picture;
	}
	
	public ArrayList<String> getPicture() {
		return picture;
	}
	
	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}
	
	public String getSoundPath() {
		return soundPath;
	}
	
	public void setPoemId(String poemId) {
		this.poemId = poemId;
	}
	
	public String getPoemId() {
		return poemId;
	}
	
	public void setPoemContent(String poemContent) {
		this.poemContent = poemContent;
	}
	
	public String getPoemContent() {
		return poemContent;
	}
	
	public void setPoemName(String poemName) {
		this.poemName = poemName;
	}
	
	public String getPoemName() {
		return poemName;
	}
	
	public void setZan(boolean zan) {
		this.zan = zan;
	}
	
	public boolean getZan() {
		return zan;
	}
	
	public void setPostComNum(int post_com_num) {
		this.post_com_num = post_com_num;
	}
	
	public int getPostComNum() {
		return post_com_num;
	}
	
	public void setPostComPId(int post_com_pId) {
		this.post_com_pId = post_com_pId;
	}
	
	public int getPostComPId() {
		return post_com_pId;
	}
}
