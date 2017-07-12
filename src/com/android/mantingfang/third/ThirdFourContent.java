package com.android.mantingfang.third;

import com.android.mantingfang.model.Poem;

public class ThirdFourContent {
	private String userId;		//用户id
	private String headPath;	//头像
	private String name;		//昵称
	private String time;		//时间
	private String soundPath;	//音频路径
	//private String content;		//内容
	//private ArrayList<String> picture;	//图片组
	private Poem poem;			//诗
	private boolean zan;		//是否被赞
	private int post_com_num;	//帖子标号
	private int post_com_pId;	//帖子ID
	private int post_com_cId;	//评论ID
	
	public ThirdFourContent() {}
	
	public ThirdFourContent(String userId, String headPath, String name, String time, String soundPath,
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
