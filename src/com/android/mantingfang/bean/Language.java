package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Language extends Base {

	private int language_id;
	private String language_name;
	
	public Language(int language_id, String language_name) {
		this.language_id = language_id;
		this.language_name = language_name;
	}
	
	public int getLanguageId() {
		return language_id;
	}
	
	public void setLanguageId(int language_id) {
		this.language_id = language_id;
	}
	
	
	public String getLanguageName() {
		return language_name;
	}
	
	public void setLanguageName(String language_name) {
		this.language_name = language_name;
	}
	
}
