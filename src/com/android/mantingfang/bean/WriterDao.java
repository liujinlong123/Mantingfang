package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WriterDao {

	private DbHelper helper = null;
	
	public WriterDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertWR(List<Writer> writerList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < writerList.size(); i++) {
				Writer writer = writerList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("writerid", writer.getWriterid());
				cv.put("writername", writer.getWritername());
				cv.put("summary", writer.getSummary());
				cv.put("dynastyid", writer.getDynastyid());
				if (!helper.isExist("Writer", "writerid", writer.getWriterid(), database)) {
					database.insert("Writer", null, cv);
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
	
	public List<Writer> getAllWriter(int dynastyid) {
		
		List<Writer> writerList = new ArrayList<Writer>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select * form Writer";
			if (dynastyid > 0) {
				sql = sql + " where dynastyid = " + dynastyid;
			}
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Writer w = new Writer(
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("summary")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")));
					
					writerList.add(w);
					cursor.moveToNext();
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
		
		return writerList;
	}
	
	public int getWriterSumByDynastyid(int dynastyid) {
		
		int sum = 0;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select count(*) from Writer";
			if (dynastyid > 0) {
				sql = sql + " where dynastyid = " + dynastyid;
			}
			Cursor cursor;
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				sum = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		
		return sum;
	}
	
	public Writer getWriterById(int writerid) {
		
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from Writer where writerid = " + writerid;
			Log.v("sql", sql);
			Cursor cursor;
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				Writer w = new Writer(
						cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
						cursor.getString(cursor.getColumnIndexOrThrow("writername")),
						cursor.getString(cursor.getColumnIndexOrThrow("summary")),
						cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")));
				Log.v("name", w.getWritername());
				return w;
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
	
	public List<Info> getInfoById(int writerid) {
		
		List<Info> infos = new ArrayList<Info>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from¡¡Info where cateid = 0 and fid = " + writerid;
			Log.v("sql", sql);
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Info info = new Info(
							cursor.getInt(cursor.getColumnIndexOrThrow("infoid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("cateid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("fid")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("adder")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")));
					infos.add(info);
					Log.v("title", info.getTitle());
					cursor.moveToNext();
				}
				return infos;
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
