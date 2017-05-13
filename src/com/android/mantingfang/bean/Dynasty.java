package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Dynasty extends Base {

	private int dynasty_id;
	private String dynasty_name;
	
	public Dynasty(int dynasty_id, String dynasty_name) {
		this.dynasty_id = dynasty_id;
		this.dynasty_name = dynasty_name;
	}
	
	public int getDynastyId() {
		return dynasty_id;
	}
	
	public void setDynastyId(int dynasty_id) {
		this.dynasty_id = dynasty_id;
	}
	
	public String getDynastyName() {
		return dynasty_name;
	}
	
	public void setDynastyName(String dynasty_name) {
		this.dynasty_name = dynasty_name;
	}
}
