package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Kind extends Base {
	private int kind_id;
	private String kind_name;
	
	public Kind(int kind_id, String kind_name) {
		this.kind_id = kind_id;
		this.kind_name = kind_name;
	}
	
	public int getKindId() {
		return kind_id;
	}
	
	public void setKindId(int kind_id) {
		this.kind_id = kind_id;
	}
	
	public String getKindName() {
		return kind_name;
	}
	
	public void setKindName(String kind_name) {
		this.kind_name = kind_name;
	}
}
