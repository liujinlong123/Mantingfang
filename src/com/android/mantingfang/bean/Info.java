package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Info extends Base {

	private int infoid;
	private int cateid;
	private int fid;
	private String title;
	private String adder;
	private String content;
	
	public Info(int infoid, int cateid, int fid, String title, String adder, String content) {
		this.infoid = infoid;
		this.cateid = cateid;
		this.fid = fid;
		this.title = title;
		this.adder = adder;
		this.content = content;
	}
	
	public int getInfoid() {
		return infoid;
	}
	
	public void setInfoid(int infoid) {
		this.infoid = infoid;
	}
	
	public int getCateid() {
		return cateid;
	}
	
	public void setCateid(int cateid) {
		this.cateid = cateid;
	}
	
	public int getFid() {
		return fid;
	}
	
	public void setFid(int fid) {
		this.fid = fid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddr() {
		return adder;
	}
	
	public void setAdder(String adder) {
		this.adder = adder;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
