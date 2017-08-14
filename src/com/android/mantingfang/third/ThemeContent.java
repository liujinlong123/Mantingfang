package com.android.mantingfang.third;

import java.util.ArrayList;

import com.android.mantingfang.model.Poem;

public class ThemeContent {
	private String userId;
	private String headPath;
	private String name;
	private String time;	
	private String content;	
	private ArrayList<String> picture;
	private Poem poem;
	private int post_com_num;
	private int post_com_pId;
	
	public ThemeContent() {}
	
	public ThemeContent(String userId, String headPath, String name, String time, String content, ArrayList<String> picture,
			Poem poem, int post_com_num, int post_com_pId) {
		this.userId = userId;
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.content = content;
		this.picture = picture;
		this.poem = poem;
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
	
	public void setPoem(Poem poem) {
		this.poem = poem;
	}
	
	public Poem getPoem() {
		return poem;
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
