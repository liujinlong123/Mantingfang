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
	
	/**
	 * 为Writer表添加内容
	 * @param writerList
	 */
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
	
	/**
	 * 该朝代下的所有诗人
	 * @param dynastyid
	 * @return
	 */
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
	
	/**
	 * 返回某朝代下所有作品的数目
	 * @param dynastyid
	 * @return
	 */
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
	
	/**
	 * 通过writer_id返回诗人信息
	 * @param writerid
	 * @return
	 */
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
	
	
	/**
	 * 该国籍下的所有诗人
	 * @param writer_country_id
	 * @return
	 */
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
	
	/**
	 * 该风格下的所有诗人
	 * @param writer_label_id
	 * @return
	 */
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
