package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LabelDao {

	private DbHelper helper = null;
	public LabelDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertLabel(List<Label> labelList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < labelList.size(); i++) {
				Label label = labelList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("label_id", label.getLabelId());
				cv.put("label_kind_id", label.getKindId());
				cv.put("label_name", label.getLabelName());
				if (!helper.isExist("Label", "label_id", label.getLabelId(), database)) {
					database.insert("Label", null, cv);
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
	
	public String getLabelById(int label_id) {
		String labelName = null;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from Label where label_id = " + label_id;
			Log.v("sql", sql);
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				labelName = cursor.getString(cursor.getColumnIndexOrThrow("label_name"));
				return labelName;
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
