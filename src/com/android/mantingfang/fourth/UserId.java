package com.android.mantingfang.fourth;

import android.content.Context;
import android.content.SharedPreferences;

public class UserId {
	private String userId;
	private String headPath;
	private String nickName;

	private static UserId user;
	private static SharedPreferences pref;
	
	private UserId() {}
	
	@SuppressWarnings("static-access")
	public static UserId getInstance(Context context) {
		if (user == null) {
			user = new UserId();
		}
		
		pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
		user.userId = pref.getString("userId", "-1");
		user.headPath = pref.getString("headPath", "null");
		user.nickName = pref.getString("nickName", "");
		return user;
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
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
}
