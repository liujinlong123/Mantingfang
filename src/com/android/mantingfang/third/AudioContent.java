package com.android.mantingfang.third;

import com.android.mantingfang.model.Poem;

public class AudioContent {
	private String userId;
	private String headPath;
	private String name;
	private String time;
	private String soundPath;
	private Poem poem;
	private boolean zan;
	private int post_com_num;
	private int post_com_pId;
	private int post_com_cId;
	
	public AudioContent() {}
	
	public AudioContent(String userId, String headPath, String name, String time, String soundPath,
			Poem poem, boolean zan, int post_com_num, int post_com_pId, int post_com_cId) {
		this.userId = userId;
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.soundPath = soundPath;
		this.poem = poem;
		this.zan = zan;
		this.post_com_num = post_com_num;
		this.post_com_pId = post_com_pId;
		this.post_com_cId = post_com_cId;
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
	
	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}
	
	public String getSoundPath() {
		return soundPath;
	}
	
	public void setPoem(Poem poem) {
		this.poem = poem;
	}
	
	public Poem getPoem() {
		return poem;
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
	
	public void setPostComCId(int post_com_cId) {
		this.post_com_cId = post_com_cId;
	}
	
	public int getPostComCId() {
		return post_com_cId;
	}
}
