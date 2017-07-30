package com.android.mantingfang.second;

public class SortModel {

	private int writer_id;
	private String name;   //��ʾ������
	private String sortLetters;  //��ʾ����ƴ��������ĸ
	private String dynastyName;
	private String writer_career;
	private String wId;
	
	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public SortModel() {}
	
	public SortModel(int poetry_id, String name, String sortLetters, String dynastyName, String writer_career) {
		this.writer_id = poetry_id;
		this.name = name;
		this.sortLetters = sortLetters;
		this.dynastyName = dynastyName;
	}
	
	public String getWriter_career() {
		return writer_career;
	}

	public void setWriter_career(String writer_career) {
		this.writer_career = writer_career;
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
