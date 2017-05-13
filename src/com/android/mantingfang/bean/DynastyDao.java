package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DynastyDao {
	
	private DbHelper helper = null;
	public DynastyDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertDY(List<Dynasty> dynastyList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < dynastyList.size(); i++) {
				Dynasty dynasty = dynastyList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("dynasty_id", dynasty.getDynastyId());
				cv.put("dynasty_name", dynasty.getDynastyName());
				if (!helper.isExist("Dynasty", "dynasty_id", dynasty.getDynastyId(), database)) {
					database.insert("Dynasty", null, cv);
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
