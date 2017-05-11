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
	
	public void insertPO(List<Poetry> poetryList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < poetryList.size(); i++) {
				Poetry poetry = poetryList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("poetryid", poetry.getPoetryid());
				cv.put("title", poetry.getTitle());
				cv.put("kindid", poetry.getKindid());
				cv.put("typeid", poetry.getTypeid());
				cv.put("writerid", poetry.getWriterid());
				cv.put("content", poetry.getContent());
				cv.put("rhesis", "");
				if (!helper.isExist("Poetry", "poetryid", poetry.getPoetryid(), database)) {
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
	
	
	public List<Poem> getAllPoem() {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.kindid, p.poetryid, w.dynastyid, w.writerid, w.writername,"
					+ "p.title, p.content from Poetry p join Writer w on p.writerid = w.writerid "
					+ " order by p.poetryid desc";
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")),
							"");
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
	
	public Poem findPoemById(int pid) {
		
		Poem p = null;
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			String sql = "select p.kindid, p.poetryid, w.dynastyid, w.writerid, w.writername, p.title, p.content from "
					+ "Poetry p join Writer w on p.writerid = w.writerid where p.poetryid = " + pid;
			Log.v("sql", sql);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				p = new Poem(cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
						cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
						cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
						cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
						cursor.getString(cursor.getColumnIndexOrThrow("writername")),
						cursor.getString(cursor.getColumnIndexOrThrow("title")),
						cursor.getString(cursor.getColumnIndexOrThrow("content")),
						"");
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
		return null;
	}
	
	public List<Info> findInfoById(int fid) {
		
		List<Info> infos = new ArrayList<Info>();
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			String sql = "select * from Info where cateid = 1 and fid = " + fid;
			Log.v("sql", sql);
			Cursor cursor = db.rawQuery(sql, null);
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
			if (db != null) {
				db.close();
			}
		}
		
		return null;
	}
	
	public int getSumById(int kindid) {
		
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
	}
	
	public List<Poem> getPoemByWid(int writerid) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.kindid,p.poetryid,w.dynastyid,w.writerid,w.writername,p.title,p.content from Poetry p "
					+ " join Writer w on p.writerid = w.writerid where p.writerid = "
					+ writerid + " order by p.poetryid desc ";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")),
							"");
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
	
	public List<Poem> getPoemByMy(int[] myids) {
		
		String myString = StringUtils.getString4Array(myids);
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.kindid, p.poetryid, w.dynastyid, w.writerid, w.writername,"
					+ "p.title, p.content from Poetry p join Writer w on p.writerid = w.writerid "
					+ "where p.poetryid in (" + myString + ") order by p.poetryid desc";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")),
							"");
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
	
	public List<Poem> getPoemByTid(int tid) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql = "select p.kindid,p.poetryid,w.dynastyid,w.writerid,w.writername,p.title,p.content from Poetry p "
					+ " join Writer w on p.writerid = w.writerid where" +
					" p.typeid like '"+tid+",%' or p.typeid like '%,"+tid+",%' or p.typeid like '%,"+tid+"' or p.typeid ="+tid
					+ " order by p.poetryid desc ";
			Log.v("sql", sql);
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")),
							"");
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
	
	public List<Poem> getPoemBySearch(String sStr) {
		
		List<Poem> pList = new ArrayList<Poem>();
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			database.beginTransaction();
			Cursor cursor;
			String sql ="select p.kindid,p.poetryid,w.dynastyid,w.writerid,w.writername,p.title,p.content from Poetry p "
					+ " join Writer w on p.writerid = w.writerid where " +
					"p.title like '%"+ sStr +"%' or w.writername like '%"+ sStr +"%' or p.content like '%"+ sStr +"%'";
			cursor = database.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					Poem p = new Poem(
							cursor.getInt(cursor.getColumnIndexOrThrow("kindid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("poetryid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("dynastyid")),
							cursor.getInt(cursor.getColumnIndexOrThrow("writerid")),
							cursor.getString(cursor.getColumnIndexOrThrow("writername")),
							cursor.getString(cursor.getColumnIndexOrThrow("title")),
							cursor.getString(cursor.getColumnIndexOrThrow("content")),
							"");
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
	
}
