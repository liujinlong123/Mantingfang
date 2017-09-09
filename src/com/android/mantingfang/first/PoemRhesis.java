package com.android.mantingfang.first;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class PoemRhesis extends Base {

	private String poemId;
	private String writer;
	private String rhesis;
	private String dynasty;
	private String poemName;
	private String content;
	private String trans;
	private boolean isCollect;
	
	public PoemRhesis() {};
	
	public PoemRhesis(String poemId, String writer, String rhesis) {
		this.poemId = poemId;
		this.writer = writer;
		this.rhesis = rhesis;
	}
	
	public PoemRhesis(String poemId, String poemName, String dynasty, String writer, String rhesis) {
		this.poemId = poemId;
		this.writer = writer;
		this.rhesis = rhesis;
		this.dynasty = dynasty;
		this.poemName = poemName;
	}
	
	public PoemRhesis(String poemId, String poemName, String dynasty, String writer, String rhesis, String content) {
		this.poemId = poemId;
		this.poemName = poemName;
		this.dynasty = dynasty;
		this.writer = writer;
		this.rhesis = rhesis;
		this.content = content;
	}
	
	public PoemRhesis(String poemId, String poemName, String poemRhesis, String writer, String content, String trans, boolean isCollect) {
		this.poemId = poemId;
		this.poemName = poemName;
		this.rhesis = poemRhesis;
		this.writer = writer;
		this.content = content;
		this.trans = trans;
		this.isCollect = isCollect;
	}

	public String getTrans() {
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public boolean isCollect() {
		return isCollect;
	}

	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPoemName() {
		return poemName;
	}

	public void setPoemName(String poemName) {
		this.poemName = poemName;
	}

	public String getDynasty() {
		return dynasty;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
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
