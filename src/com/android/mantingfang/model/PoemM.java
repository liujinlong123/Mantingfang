package com.android.mantingfang.model;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class PoemM extends Base {
	
	private String poemId;		//Ê«´ÊId
	private String poemName;	//Ê«´ÊÃû×Ö
	private String poemContent;	//Ê«´ÊÄÚÈÝ
	private String poemRhesis;	//Ê«´ÊÃû¾ä
	private String writer;		//Ê«ÈËÃû×Ö
	private String dynasty;		//³¯´úÃû×Ö
	private String poem_bg;		//Ê«´Ê±³¾°
	private String writer_bg;	//Ê«ÈË±³¾°
	private String notes;		//×¢ÊÍ
	private String toNow;		//ÒëÎÄ
	private String trans;		//·­Òë
	private String apprec;		//ÉÍÎö
	
	public PoemM() {};
	
	public PoemM(String poemId, String poemName, String poemContent, String poemRhesis,
			String writer, String dynasty, String poem_bg, String writer_bg, String notes,
			String toNow, String trans, String apprec) {
		this.poemId = poemId;
		this.poemName = poemName;
		this.poemContent = poemContent;
		this.poemRhesis = poemRhesis;
		this.writer = writer;
		this.dynasty = dynasty;
		this.poem_bg = poem_bg;
		this.writer_bg = writer_bg;
		this.notes = notes;
		this.toNow = toNow;
		this.trans = trans;
		this.apprec = apprec;
	}
	
	public void setPoemId(String poemId) {
		this.poemId = poemId;
	}
	
	public String getPoemId() {
		return poemId;
	}
	
	public void setPoemName(String poemName) {
		this.poemName = poemName;
	}
	
	public String getPoemName() {
		return poemName;
	}
	
	public void setPoemContent(String poemContent) {
		this.poemContent = poemContent;
	}
	
	public String getPoemContent() {
		return poemContent;
	}
	
	
	public void setPoemRhesis(String poemRhesis) {
		this.poemRhesis = poemRhesis;
	}
	
	public String getPoemRhesis() {
		return poemRhesis;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}
	
	public String getDynasty() {
		return dynasty;
	}
	
	public void setPoemBg(String poem_bg) {
		this.poem_bg = poem_bg;
	}
	
	public String getPoemBg() {
		return poem_bg;
	}
	
	public void setWriterBg(String writer_bg) {
		this.writer_bg = writer_bg;
	}
	
	public String getWriterBg() {
		return writer_bg;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setToNow(String toNow) {
		this.toNow = toNow;
	}
	
	public String getToNow() {
		return toNow;
	}
	
	public void setTrans(String trans) {
		this.trans = trans;
	}
	
	public String getTrans() {
		return trans;
	}
	
	public void setApprec(String apprec) {
		this.apprec = apprec;
	}
	
	public String getApprec() {
		return apprec;
	}
}
