package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
	
	/**
	 * 通过诗词id 返回对应信息
	 * @param info_poetry_id
	 * @return
	 */
	public Info getInfoByPId(int info_poetry_id) {
		Info info = null;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from Info where info_poetry_id = " + info_poetry_id;
			Log.v("sql", sql);
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				info = new Info(
						cursor.getInt(cursor.getColumnIndexOrThrow("info_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("info_poetry_id")),
						cursor.getString(cursor.getColumnIndexOrThrow("info_background")),
						cursor.getString(cursor.getColumnIndexOrThrow("info_praise")),
						cursor.getString(cursor.getColumnIndexOrThrow("info_note")),
						cursor.getString(cursor.getColumnIndexOrThrow("info_tonow")),
						cursor.getString(cursor.getColumnIndexOrThrow("info_translation"))
						);
				Log.v("info", info.getInfoId() + "");
				Log.v("info", info.getBackground() + "");
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		
		return null;
	}
}
