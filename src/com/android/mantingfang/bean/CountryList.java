package com.android.mantingfang.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryList {

	private List<Country> countryList = new ArrayList<Country>();
	
	private int countryCount;
	
	public List<Country> getCountryList() {
		return countryList;
	}
	
	public int getCountryCount() {
		return countryCount;
	}
	
	public static CountryList parse(JSONArray obj) throws JSONException {
		CountryList countrys = new CountryList();
		
		if (obj != null) {
			countrys.countryCount = obj.length();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject jo = obj.getJSONObject(i);
				Country country = new Country(
						jo.getInt("country_id"),
						jo.getString("country_name"));
				countrys.countryList.add(country);
			}
		}
		
		return countrys;
	}
}
