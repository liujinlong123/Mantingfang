package com.android.mantingfang.third;

import java.util.ArrayList;

import com.android.mantingfang.model.Poem;

public class ThirdOneContent {
	private String userId;		//用户id	
	private String headPath;	//头像	
	private String name;		//昵称
	private String time;		//时间		
	private String content;		//内容
	private ArrayList<String> picture;	//图片组
	private Poem poem;			//诗
	private int post_com_num;	//帖子标号
	private int post_com_pId;	//帖子ID
	
	public ThirdOneContent() {}
	
	public ThirdOneContent(String userId, String headPath, String name, String time, String content, ArrayList<String> picture,
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
