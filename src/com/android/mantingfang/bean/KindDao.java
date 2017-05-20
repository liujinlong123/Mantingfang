package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.second.KindContent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
	
	public List<KindContent> findAllLabelByKind() {
		
		List<KindContent> kinds = new ArrayList<>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from kind";
			Cursor cursor = database.rawQuery(sql, null);
			Log.v("sql", sql);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					int kind_id = cursor.getInt(cursor.getColumnIndexOrThrow("kind_id"));
					String kind_name = cursor.getString(cursor.getColumnIndexOrThrow("kind_name"));
					String sqltwo = "select l. label_name from Label l join Kind k on k.kind_id = l.label_kind_id where "
							+ "l.label_kind_id = " + kind_id;
					Cursor cr = database.rawQuery(sqltwo, null);
					Log.v("sql", sqltwo);
					String[] singleName = new String[cursor.getCount()];
					if (cr.moveToFirst()) {
						for (int j = 0; j < cursor.getCount(); j++) {
							singleName[j] = cursor.getString(cursor.getColumnIndexOrThrow("label_name"));
						}
						cr.moveToNext();
					}
					KindContent kindc = new KindContent(kind_name, singleName);
					kinds.add(kindc);
					cursor.moveToNext();
				}
				return kinds;
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
