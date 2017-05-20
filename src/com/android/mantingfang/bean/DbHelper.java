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
		
		//poetry
		String POsql = "create table Poetry (poetry_id integer, poetry_label_id integer, "
				+ "poetry_writer_id integer, poetry_language_id integer, poetry_name text,"
				+ "poetry_content text, poetry_rhesis text)";
		db.execSQL(POsql);
		
		//writer
		String WRsql = "create table Writer (writer_id integer, writer_label_id integer, writer_dynasty_id integer,"
				+ "writer_country_id integer, writer_name text, writer_career text)";
		db.execSQL(WRsql);
		
		//info
		String INsql = "create table Info (info_id integer, info_poetry_id integer, info_background text, info_praise text,"
				+ "info_note text, info_tonow text, info_translation text)";
		db.execSQL(INsql);
		
		//country
		String COUNTRYsql = "create table Country (country_id integer, country_name text)";
		db.execSQL(COUNTRYsql);
		
		//dynasty
		String DYsql = "create table Dynasty (dynasty_id integer, dynasty_name text)";
		db.execSQL(DYsql);
		
		//label
		String LBsql = "create table Label (label_id integer, label_poetry_id text, label_kind_id integer, label_name text)";
		db.execSQL(LBsql);
		
		//language
		String LAsql = "create table Language (language_id integer, language_name text)";
		db.execSQL(LAsql);
		
		//kind
		String KINDsql = "create table Kind (kind_id integer, kind_name text)";
		db.execSQL(KINDsql);
		
		//my
		String MYsql = "create table My(myid integer)";
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
