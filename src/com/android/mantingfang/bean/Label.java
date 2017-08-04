package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Label extends Base {

	private int label_id;
	private int label_kind_id;
	private String label_name;
	
	public Label(int label_id, int label_kind_id, String label_name) {
		this.label_id = label_id;
		this.label_kind_id = label_kind_id;
		this.label_name = label_name;
	}
	
	public int getLabelId() {
		return label_id;
	}
	
	public void setLabelId(int label_id) {
		this.label_id = label_id;
	}
	
	public int getKindId() {
		return label_kind_id;
	}
	
	public void setKindId(int label_kind_id) {
		this.label_kind_id = label_kind_id;
	}
	
	public String getLabelName() {
		return label_name;
	}
	
	public void setLabelName(String label_name) {
		this.label_name = label_name;
	}
}
