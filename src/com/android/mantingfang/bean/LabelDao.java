package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
				cv.put("label_poetry_id", label.getPoetryId());
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
}
