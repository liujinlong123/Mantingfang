package com.android.mantingfang.fourth;

import android.content.Context;
import android.content.SharedPreferences;

public class UserId {
	private String userId;

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
		return user;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
}
