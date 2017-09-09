package com.android.mantingfang.second;

public class KindPictureAll {

	private String labelName;
	private int imgId;
	
	public KindPictureAll(String labelName, int imgId) {
		this.labelName = labelName;
		this.imgId = imgId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
}
