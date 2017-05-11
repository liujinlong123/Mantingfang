package com.android.mantingfang.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "poem.db";
	private final static int DATABASE_VERSION = 1;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String WRsql = "create table writer (writerid interger, writername text, "
				+ "summary text, dynastyid integer)";
		db.execSQL(WRsql);
		
		String POsql = "create table poetry (poetryid integer, typeid text, kindid integer,"
				+ "writerid integer, title text, content text, rhesis text)";
		db.execSQL(POsql);
		
		String INsql = "create table info (infoid integer, cateid integer, fid integer,"
				+ " adder text, title text, content text)";
		db.execSQL(INsql);
		
		String MYsql = "create table my(myid integer)";
		db.execSQL(MYsql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public Cursor getAllData(String tableName, DbHelper helper) {
		
		SQLiteDatabase database = helper.getReadableDatabase();
		Cursor cur = database.query(tableName, null, null, null, null, null, null);
		return cur;
	}
	
	public boolean isExist(String tableName, String columnName, int idValue, SQLiteDatabase db) {
		boolean flag = false;
		String sql = "select * from " + tableName + " where " +columnName + "=" + idValue;
		Cursor cur = db.rawQuery(sql, null);
		if (cur.moveToFirst()) {
			flag = true;
		}
		cur.close();
		return flag;
	}

}
