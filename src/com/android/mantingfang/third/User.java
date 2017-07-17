package com.android.mantingfang.third;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class User extends Base {
	private String userId;
	private String userNickname;
	private String userPhoto;
	private String userAge;
	private String userSex;
	private	String userArea;
	private String userIntro;
	private String userLabel;
	
	public User() {}
	
	public User(String userId, String userNickname, String userPhoto, String userAge, String userSex,
			String userArea, String userIntro, String userLabel) {
		this.userId = userId;
		this.userNickname = userNickname;
		this.userPhoto = userPhoto;
		this.userAge = userAge;
		this.userSex = userSex;
		this.userArea = userArea;
		this.userIntro = userIntro;
		this.userLabel = userLabel;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	public String getUserNickname() {
		return userNickname;
	}
	
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	
	public String getUserPhoto() {
		return userPhoto;
	}
	
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	
	public String getUserAge() {
		return userAge;
	}
	
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
	public String getUserSex() {
		return userSex;
	}
	
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
	
	public String getUserArea() {
		return userArea;
	}
	
	public void setUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}
	
	public String getUserIntro() {
		return userIntro;
	}
	
	public void setUserLabel(String userLabel) {
		this.userLabel = userLabel;
	}
	
	public String getUserLabel() {
		return userLabel;
	}
}
