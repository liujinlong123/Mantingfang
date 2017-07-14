package com.android.mantingfang.third;

import java.util.ArrayList;

import com.android.mantingfang.model.Poem;

public class UserTwoContent {
	private String userId;		//�û�id
	private String headPath;	//ͷ��
	private String name;		//�ǳ�
	private String time;		//ʱ��
	private String content;		//����
	private ArrayList<String> picture;	//ͼƬ��
	private String soundPath;	//��Ƶ·��
	private Poem poem;			//ʫ
	private boolean zan;		//�Ƿ���
	private int post_com_num;	//���ӱ��
	private int post_com_pId;	//����ID
	private int post_com_cId;	//����ID
	
	public UserTwoContent() {}
	
	public UserTwoContent(String userId, String headPath, String name, String time, String content, ArrayList<String> picture,
			String soundPath, Poem poem, boolean zan, int post_com_num, int post_com_pId, int post_com_cId) {
		this.userId = userId;
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.content = content;
		this.picture = picture;
		this.poem = poem;
		this.soundPath = soundPath;
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
