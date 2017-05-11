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
				cv.put("infoid", info.getInfoid());
				cv.put("cateid", info.getCateid());
				cv.put("fid", info.getFid());
				cv.put("title", info.getTitle());
				cv.put("adder", info.getAddr());
				cv.put("content", info.getContent());
				if (!helper.isExist("Info", "infoid", info.getInfoid(), database)) {
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
