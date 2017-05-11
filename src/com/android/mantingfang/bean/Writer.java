package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Writer extends Base {

	private int writerid;
	private String writername;
	private String summary;
	private int dynastyid;
	
	public Writer(int writerid, String writername, String summary, int dynastyid) {
		this.writerid = writerid;
		this.writername = writername;
		this.summary = summary;
		this.dynastyid = dynastyid;
	}
	
	public int getWriterid() {
		return writerid;
	}
	
	public void setWriterid(int writerid) {
		this.writerid = writerid;
	}
	
	public String getWritername() {
		return writername;
	}
	
	public void setWritername(String writername) {
		this.writername = writername;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getDynastyid() {
		return dynastyid;
	}
	
	public void setDynastyid(int dynastyid) {
		this.dynastyid = dynastyid;
	}
}
