package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class ChoosePicture {

	private static Set<Integer> setChoose = new HashSet<>();

	public static ChoosePicture choose;
	private static SharedPreferences pref;
	private static SharedPreferences.Editor editor;

	private ChoosePicture() {
	}

	/**
	 * 获取单一实例
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static ChoosePicture getInstance(Context context) {
		if (choose == null) {
			choose = new ChoosePicture();
		}
		pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
		editor = context.getSharedPreferences("data", context.MODE_PRIVATE).edit();

		return choose;
	}

	public static Set<Integer> getSetChoose() {
		return setChoose;
	}

	public static void setSetChoose(Set<Integer> setChoose) {
		ChoosePicture.setChoose = setChoose;
	}

	/**
	 * 获取标号
	 * 
	 * @return
	 */
	public Set<Integer> getChoose() {
		String str = pref.getString("choose", "");
		if (str.equals("")) {
			return setChoose;
		} else {
			String[] tokens = str.split("[#]");
			for (String e : tokens) {
				setChoose.add(Integer.parseInt(e));
			}

			return setChoose;
		}
	}

	/**
	 * 存储标号
	 * 
	 * @param set
	 */
	public void saveChoose(Set<Integer> set) {
		String str = "";
		String titles = "";
		for (int e : set) {
			str += (e + "#");
			titles += ((FirstPagerAdd.titles[e]) + "#");
		}

		editor.putString("chooseTitle", titles);
		editor.putString("choose", str);
		editor.commit();
	}

	/**
	 * 存储类型名称
	 * 
	 * @param str
	 */
	/*
	 * public void saveChooseTitle(String str) { editor.putString("chooseTitle",
	 * str); editor.commit(); }
	 */

	/**
	 * 返回名字
	 * 
	 * @return
	 */
	public ArrayList<String> getChooseTitle() {
		String t = pref.getString("chooseTitle", "");
		ArrayList<String> titles = new ArrayList<>();
		if (t != null && !t.equals("")) {
			String[] token = t.split("[#]");
			for (String e : token) {
				if (e != null && !e.equals("")) {
					titles.add(e);
				}
			}
		}

		return titles;

	}
}
