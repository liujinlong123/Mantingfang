package com.android.mantingfang.third;

import java.util.ArrayList;

import com.android.mantingfang.bean.Base;

import android.graphics.Bitmap;


public class UserTwoContent extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;		//用户Id
	private String headPath;	//头像路径
	private Bitmap headPhoto;	//头像
	
	private String name;		//用户昵称
	private String time;		//发表时间
	private String content;		//发表内容
	private ArrayList<String> picture;	//图片组
	private String soundPath;	//音频路径
	private String poemId;		//诗词Id
	private String poemContent;	//诗词内容
	private String poemName;	//诗词名字
	private String zan;		//是否被赞
	private int post_com_num;	//帖子标号
	private int post_com_pId;	//帖子Id
	
	public UserTwoContent() {}
	
	public UserTwoContent(String userId, String headPath, String name, String time, String content, ArrayList<String> picture,
			String soundPath, String poemId, String poemContent, String poemName, String zan, int post_com_num, int post_com_pId) {
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
	
	public UserTwoContent(String userId, Bitmap headPhoto, String name, String time, String content, ArrayList<String> picture,
			String soundPath, String poemId, String poemContent, String poemName, String zan, int post_com_num, int post_com_pId) {
		this.userId = userId;
		this.headPhoto = headPhoto;
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
	
	public Bitmap getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(Bitmap headPhoto) {
		this.headPhoto = headPhoto;
	}

	public int getPost_com_num() {
		return post_com_num;
	}

	public void setPost_com_num(int post_com_num) {
		this.post_com_num = post_com_num;
	}

	public int getPost_com_pId() {
		return post_com_pId;
	}

	public void setPost_com_pId(int post_com_pId) {
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
	
	public void setZan(String zan) {
		this.zan = zan;
	}
	
	public String getZan() {
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
