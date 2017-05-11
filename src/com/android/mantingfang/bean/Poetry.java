package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Poetry extends Base {

	private int poetryid;
	private String title;
	private String typeid;
	private int kindid;
	private int writerid;
	private String content;
	private String rhesis;
	
	public Poetry(int poetryid, String title, String typeid, int kindid, int writerid, String content, String rhesis) {
		this.poetryid = poetryid;
		this.title = title;
		this.typeid = typeid;
		this.kindid = kindid;
		this.writerid = writerid;
		this.content = content;
		this.rhesis = rhesis;
	}
	
	public int getPoetryid() {
		return poetryid;
	}
	
	public void setPoetryid(int poetryid) {
		this.poetryid = poetryid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTypeid() {
		return typeid;
	}
	
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	public int getKindid() {
		return kindid;
	}
	
	public void setKindid(int kindid) {
		this.kindid = kindid;
	}
	
	public int getWriterid() {
		return writerid;
	}
	
	public void setWriterid(int writerid) {
		this.writerid = writerid;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRhesis() {
		return rhesis;
	}
	
	public void setRhesis(String rhesis) {
		this.rhesis = rhesis;
	}
}
