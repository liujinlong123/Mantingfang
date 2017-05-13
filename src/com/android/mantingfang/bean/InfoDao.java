package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class InfoDao {

	private DbHelper helper = null;
	public InfoDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertIN(List<Info> infoList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < infoList.size(); i++) {
				Info info = infoList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("info_id", info.getInfoId());
				cv.put("info_poetry_id", info.getPoetryId());
				cv.put("info_background", info.getBackground());
				cv.put("info_praise", info.getPraise());
				cv.put("info_note", info.getNote());
				cv.put("info_tonow", info.getTonow());
				cv.put("info_translation", info.getTranslation());
				if (!helper.isExist("Info", "info_id", info.getInfoId(), database)) {
					database.insert("Info", null, cv);
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
