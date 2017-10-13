package com.android.mantingfang.fourth;

import android.content.Context;
import android.content.SharedPreferences;

public class UserId {
	private String reUserId;
	private String userId;
	private String headPath;
	private String nickName;
	private String location;
	private String loclabel;

	private static UserId user;
	private static SharedPreferences pref;
	
	private UserId() {}
	
	@SuppressWarnings("static-access")
	public static UserId getInstance(Context context) {
		if (user == null) {
			user = new UserId();
		}
		
		user.reUserId = user.userId;
		pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
		user.userId = pref.getString("userId", "-1");
		user.headPath = pref.getString("headPath", "null");
		user.nickName = pref.getString("nickName", "");
		user.location = pref.getString("mylocation", "");
		user.loclabel = pref.getString("locabel", "0");
		
		return user;
	}

	public String getReUserId() {
		return reUserId;
	}

	public void setReUserId(String reUserId) {
		this.reUserId = reUserId;
	}

	public String getLoclabel() {
		return loclabel;
	}

	public void setLoclabel(String loclabel) {
		this.loclabel = loclabel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPath() {
		
		return "D:/receive picture/" + headPath.substring(headPath.lastIndexOf("/") + 1);
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public void setUserId(String userId) {
		this.reUserId = user.userId;
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public boolean isChanged() {
		if (this.reUserId == null && this.userId != null && !this.userId.equals("-1")) {
			return true;
		} else if (!reUserId.equals(userId)) {
			return true;
		} else if (reUserId.equals(userId)){
			return false;
		} else {
			return true;
		}
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
