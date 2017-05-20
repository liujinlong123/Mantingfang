package com.android.mantingfang.second;

public class SingleNames {
	String label_name;

	int label_id;

	public SingleNames(String label_name, int label_id) {
		this.label_id = label_id;
		this.label_name = label_name;
	}

	public String getLableName() {
		return label_name;
	}

	public void setLabelName(String label_name) {
		this.label_name = label_name;
	}

	public int getLabelId() {
		return label_id;
	}

	public void setLabelId(int label_id) {
		this.label_id = label_id;
	}

}