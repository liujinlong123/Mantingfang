/*package com.android.mantingfang.bean;

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
	
	*//**
	 * writer into the database
	 * @param writerList
	 *//*
	public void insertWR(List<Writer> writerList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < writerList.size(); i++) {
				Writer writer = writerList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("writer_id", writer.getWriterId());
				cv.put("writer_label_id", writer.getLabelId());
				cv.put("writer_dynasty_id", writer.getDynastyId());
				cv.put("writer_country_id", writer.getCountryId());
				cv.put("writer_name", writer.getWriterName());
				cv.put("writer_career", writer.getWriterCareer());
				if (!helper.isExist("Writer", "writer_id", writer.getWriterId(), database)) {
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
	
	*//**
	 *get All Writer
	 * @param dynastyid
	 * @return
	 *//*
	public List<Writer> getAllWriter(int dynastyid) {
		
		List<Writer> writerList = new ArrayList<Writer>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select * form Writer";
			if (dynastyid > 0) {
				sql = sql + " where writer_dynasty_id = " + dynastyid;
			}
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Writer w = new Writer(
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_country_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
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
	
	public List<Writer> getAllWriter() {
		List<Writer> writerList = new ArrayList<>();
		SQLiteDatabase database = null;
		
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select * from Writer";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Writer w = new Writer(
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_country_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
					writerList.add(w);
					cursor.moveToNext();
				}
			}
			Log.v("list_writer", writerList.size() + "");
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
	
	*//**
	 * get the number of writer in special dynasty
	 * @param dynastyid
	 * @return
	 *//*
	public int getWriterSumByDynastyid(int dynastyid) {
		
		int sum = 0;
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select count(*) from Writer";
			if (dynastyid > 0) {
				sql = sql + " where writer_dynasty_id = " + dynastyid;
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
	
	*//**
	 *get writer by writerId
	 * @param writerid
	 * @return
	 *//*
	public Writer getWriterById(int writerid) {
		
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from Writer where writer_id = " + writerid;
			Log.v("sql", sql);
			Cursor cursor;
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				Writer w = new Writer(
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
						cursor.getString(cursor.getColumnIndexOrThrow("writer_label_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_country_id")),
						cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
						cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
				Log.v("name", w.getWriterName());
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
	
public Writer getWriterMById(int writerid) {
		
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			//String sql = "select * from Writer where writer_id = " + writerid;
			String sql = "select w.writer_id,w.writer_name,w.writer_career,d.dynasty_name from Writer w "
					+ " join Dynasty d on w.writer_dynasty_id = d.dynasty_id where w.writer_id = "
					+ writerid;
			Log.v("sql", sql);
			Cursor cursor;
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				Writer w = new Writer(
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")) + "",
						cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
						cursor.getString(cursor.getColumnIndexOrThrow("dynasty_name")),
						cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
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
	
	
	*//**
	 *get Writers by country_id
	 * @param writer_country_id
	 * @return
	 *//*
	public List<Writer> getWriterByCountryId(int writer_country_id) {
		
		List<Writer> writerList = new ArrayList<Writer>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			String sql = "select * from Writer where writer_countryid " + writer_country_id;
			Log.v("sql", sql);
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Writer w = new Writer(
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_country_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
					writerList.add(w);
					cursor.moveToNext();
				}
				return writerList;
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
		return null;
	}
	
	*//**
	 * get writers by label_id
	 * @param writer_label_id
	 * @return
	 *//*
	public List<Writer> getWriterByLabelId(int writer_label_id) {
		
		List<Writer> writerList = new ArrayList<Writer>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from Writer where writer_label_id = " + writer_label_id;
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Writer w = new Writer(
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_country_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_career")));
					writerList.add(w);
					cursor.moveToNext();
				}
				return writerList;
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
		return null;
	}
}
*/