package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class KindDao {

	private DbHelper helper = null;
	public KindDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertKIND(List<Kind> kindList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < kindList.size(); i++) {
				Kind kind = kindList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("kind_id", kind.getKindId());
				cv.put("kind_name", kind.getKindName());
				if (!helper.isExist("Kind", "kind_id", kind.getKindId(), database)) {
					database.insert("Kind", null, cv);
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
