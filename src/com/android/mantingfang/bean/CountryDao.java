package com.android.mantingfang.bean;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CountryDao {

	private DbHelper helper = null;
	public CountryDao(Context context) {
		helper = new DbHelper(context);
	}
	
	public void insertCountry(List<Country> countryList) {
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			database.beginTransaction();
			for (int i = 0; i < countryList.size(); i++) {
				Country country = countryList.get(i);
				ContentValues cv = new ContentValues();
				cv.put("country_id", country.getCountryId());
				cv.put("country_name", country.getCountryName());
				if (!helper.isExist("Country", "country_id", country.getCountryId(), database)) {
					database.insert("Country", null, cv);
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
