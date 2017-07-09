package com.android.mantingfang.second;

public class SortModel {

	private int writer_id;
	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String dynastyName;
	
	public SortModel() {}
	
	public SortModel(int poetry_id, String name, String sortLetters, String dynastyName) {
		this.writer_id = poetry_id;
		this.name = name;
		this.sortLetters = sortLetters;
		this.dynastyName = dynastyName;
	}
	
	public int getWriterId() {
		return writer_id;
	}
	
	public void setWriterId(int poetry_id) {
		this.writer_id = poetry_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	public String getDynastyName() {
		return dynastyName;
	}
	
	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}
}
