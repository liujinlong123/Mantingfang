package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LanguageDao {

	private DbHelper helper = null;
	public LanguageDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertLan(List<Language> lanList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < lanList.size(); i++) {
				Language lan = lanList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("language_id", lan.getLanguageId());
				cv.put("language_name", lan.getLanguageName());
				if (!helper.isExist("Language", "language_id", lan.getLanguageId(), database)) {
					database.insert("Language", null, cv);
				}
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
	}
}
