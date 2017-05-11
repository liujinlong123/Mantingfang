package com.android.mantingfang.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDao {

	private DbHelper helper = null;
	public MyDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void InsertMy(int myid) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put("myid", myid);
			database.insert("My", null, cv);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}
	
	public boolean isExist(int myid) {
		
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from My where myid = " + myid;
			Log.v("sql", sql);
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				Log.v("isExist", "гаЪ§Он");
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		
		return false;
	}
	
	public void deleteMy(int myid) {
		
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "delete from my where myid = " + myid;
			Log.v("deleteMy", sql);
			database.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}
	
	public int[] getMys() {
		
		SQLiteDatabase database = null;
		int[] myIds;
		try {
			database = helper.getWritableDatabase();
			String sql = "select * from my";
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				myIds = new int[cursor.getCount()];
				for (int i = 0; i < cursor.getCount(); i++) {
					myIds[i] = cursor.getInt(cursor.getColumnIndexOrThrow("myid"));
					cursor.moveToNext();
				}
				return myIds;
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
