package com.android.mantingfang.first;

public class PictureContent {
	private String rhesis;
	private String writer;
	
	public PictureContent(String rhesis, String writer) {
		this.rhesis = rhesis;
		this.writer = writer;
	}
	
	public String getRhesis() {
		return rhesis;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setRhesis(String rhesis) {
		this.rhesis = rhesis;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
}
