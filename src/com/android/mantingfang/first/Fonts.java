package com.android.mantingfang.first;

import android.content.Context;
import android.content.SharedPreferences;

public class Fonts {

	private int type;
	
	private static Fonts fonts;
	private static SharedPreferences pref;
	
	private Fonts() {}
	
	@SuppressWarnings("static-access")
	public static Fonts getInstance(Context context) {
		if (fonts == null) {
			fonts = new Fonts();
		}
		pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
		fonts.setType(pref.getInt("fonts_type", 0));
		
		return fonts;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
