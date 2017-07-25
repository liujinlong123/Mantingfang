package com.android.mantingfang.first;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class PoemRhesis extends Base {

	private String poemId;
	private String writer;
	private String rhesis;
	
	public PoemRhesis() {};
	
	public PoemRhesis(String poemId, String writer, String rhesis) {
		this.poemId = poemId;
		this.writer = writer;
		this.rhesis = rhesis;
	}

	public String getPoemId() {
		return poemId;
	}

	public void setPoemId(String poemId) {
		this.poemId = poemId;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getRhesis() {
		return rhesis;
	}

	public void setRhesis(String rhesis) {
		this.rhesis = rhesis;
	}
	
	
}
