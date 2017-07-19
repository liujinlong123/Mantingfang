package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.model.Poem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PoetryDao {

	private DbHelper helper = null;
	
	public PoetryDao(Context context) {
		helper = new DbHelper(context);
	}
	
	/**
	 * insert Poem
	 * @param poetryList
	 */
	public void insertPO(List<Poetry> poetryList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < poetryList.size(); i++) {
				Poetry poetry = poetryList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("poetry_id", poetry.getPoetryId());
				cv.put("poetry_label_id", poetry.getLabelId());
				cv.put("poetry_writer_id", poetry.getWriterId());
				cv.put("poetry_language_id", poetry.getLanguageId());
				cv.put("poetry_name", poetry.getName());
				cv.put("poetry_content", poetry.getContent());
				cv.put("poetry_rhesis", poetry.getRhesis());
				
				if (!helper.isExist("Poetry", "poetry_id", poetry.getPoetryId(), database)) {
					database.insert("Poetry", null, cv);
				}
			}
			database.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
	}
	
	/**
	 * GEt All Poem
	 * @return
	 */
	public List<Poem> getAllPoem() {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.poetry_label_id, p.poetry_id, w.writer_dynasty_id, w.writer_id, w.writer_name,"
					+ "p.poetry_name, p.poetry_content, p.poetry_rhesis from Poetry p join Writer w on p.poetry_writer_id = w.writer_id "
					+ " order by p.poetry_id desc";
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					pList.add(p);
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
		return pList;
	}
	
	/**
	 *通过Id 获取 诗词
	 * @param pid
	 * @return
	 */
	public Poem findPoemById(int pid) {
		
		Poem p = null;
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			String sql = "select p.poetry_label_id, p.poetry_id, w.writer_dynasty_id, w.writer_id, w.writer_name, p.poetry_name, p.poetry_content, p.poetry_rhesis from "
					+ "Poetry p join Writer w on p.poetry_writer_id = w.writer_id where p.poetry_id = " + pid;
			Log.v("sql", sql);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				p = new Poem(cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
						cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
						cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
						cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
						cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
						cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
				Log.v("p", p.getWritername());
				return p;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return p;
	}
	
	/**
	 * 通过诗词ID获取信息
	 * @param poetry_id
	 * @return
	 */
	public List<Info> findInfoById(int poetry_id) {
		
		List<Info> infos = new ArrayList<Info>();
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			String sql = "select * from Info where poetry_id = " + poetry_id;
			Log.v("sql", sql);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Info info = new Info(
							cursor.getInt(cursor.getColumnIndexOrThrow("info_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("info_poetry_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("info_background")),
							cursor.getString(cursor.getColumnIndexOrThrow("info_praise")),
							cursor.getString(cursor.getColumnIndexOrThrow("info_note")),
							cursor.getString(cursor.getColumnIndexOrThrow("info_tonow")),
							cursor.getString(cursor.getColumnIndexOrThrow("info_translation")));
					infos.add(info);
					Log.v("title", info.getInfoId() + "");
					cursor.moveToNext();
				}
				return infos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		
		return null;
	}
	
	/*public int getSumById(int kindid) {
		
		int sum = 0;
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			String sql = "select count(*) from Poetry";
			if (kindid > 0) {
				sql = sql + " where kindid = " + kindid;
			}
			Cursor cursor;
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				sum = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		
		return sum;
	}*/
	
	/**
	 * get poem by writer Id
	 * @param writerid
	 * @return
	 */
	public List<Poem> getPoemByWid(int writerid) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.poetry_label_id,p.poetry_id,w.writer_dynasty_id,w.writer_id,w.writer_name,p.poetry_name,p.poetry_content, poetry_rhesis from Poetry p "
					+ " join Writer w on p.poetry_writer_id = w.writer_id where p.poetry_writer_id = "
					+ writerid + " order by p.poetry_id desc ";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					pList.add(p);
					cursor.moveToNext();
				}
				return pList;
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
	 *get my collect poems
	 * @param myids
	 * @return
	 */
	public List<Poem> getPoemByMy(int[] myids) {
		
		String myString = StringUtils.getString4Array(myids);
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.poetry_label_id, p.poetry_id, w.writer_dynasty_id, w.writer_id, w.writer_name,"
					+ "p.poetry_name, p.poetry_content, poetry_rhesis from Poetry p join Writer w on p.poetry_writer_id = w.writer_id "
					+ "where p.poetry_id in (" + myString + ") order by p.poetry_id desc";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					pList.add(p);
					cursor.moveToNext();
				}
				return pList;
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
	 * get Poem by type ID
	 * @param tid
	 * @return
	 */
	public List<Poem> getPoemByTid(int tid) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.poetry_label_id, p.poetry_id,w.writer_dynasty_id,w.writer_id,w.writer_name,p.poetry_name,p.poetry_content, poetry_rhesis from Poetry p "
					+ " join Writer w on p.poetry_writer_id = w.writer_id where" +
					" p.poetry_label_id like '"+tid+",%' or p.poetry_label_id like '%,"+tid+",%' or p.poetry_label_id like '%,"+tid+"' or p.poetry_label_id ="+tid
					+ " order by p.poetry_id desc ";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					pList.add(p);
					cursor.moveToNext();
				}
				return pList;
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
	 *get Poem by search
	 * @param sStr
	 * @return
	 */
	public List<Poem> getPoemBySearch(String sStr) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql ="select p.poetry_label_id,p.poetry_id,w.writer_dynasty_id,w.writer_id,w.writer_name,p.poetry_name,p.poetry_content,poetry_rhesis from Poetry p "
					+ " join Writer w on p.poetry_writer_id = w.writer_id where " +
					"p.poetry_name like '%"+ sStr +"%' or w.writer_name like '%"+ sStr +"%' or p.poetry_content like '%"+ sStr +"%'";
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					pList.add(p);
					cursor.moveToNext();
				}
				return pList;
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
	
	public List<Poem> getPoemByLanId(int poetry_language_id) {
		
		List<Poem> poemList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			String sql = "select p.poetry_label_id,p.poetry_id,w.writer_dynasty_id,w.writer_id,w.writer_name,p.poetry_name,p.poetry_content, poetry_rhesis from Poetry p "
					+ " join Writer w on p.poetry_writer_id = w.writer_id where p.poetry_language_id = "
					+ poetry_language_id + " order by p.poetry_id desc ";
			Cursor cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_label_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetry_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_dynasty_id")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writer_id")),
							cursor.getString(cursor.getColumnIndexOrThrow("writer_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_name")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_content")),
							cursor.getString(cursor.getColumnIndexOrThrow("poetry_rhesis")));
					poemList.add(p);
					cursor.moveToNext();
				}
				return poemList;
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
