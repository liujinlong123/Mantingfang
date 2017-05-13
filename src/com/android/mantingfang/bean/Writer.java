package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Writer extends Base {

	private int writer_id;
	private int writer_label_id;
	private int writer_dynasty_id;
	private int writer_country_id;
	private String writer_name;
	private String writer_career;
	
	public Writer(int writer_id, int writer_label_id, int writer_dynasty_id, int writer_country_id,
			String writer_name, String writer_career) {
		this.writer_id = writer_id;
		this.writer_label_id = writer_label_id;
		this.writer_dynasty_id = writer_dynasty_id;
		this.writer_country_id = writer_country_id;
		this.writer_name = writer_name;
		this.writer_career = writer_career;
	}
	
	public int getWriterId() {
		return writer_id;
	}
	
	public void setWriterId(int writer_id) {
		this.writer_id = writer_id;
	}
	
	public int getLabelId() {
		return writer_label_id;
	}
	
	public void setLabelId(int writer_label_id) {
		this.writer_label_id = writer_label_id;
	}
	
	public int getDynastyId() {
		return writer_dynasty_id;
	}
	
	public void setDynastyId(int writer_dynasty_id) {
		this.writer_dynasty_id = writer_dynasty_id;
	}
	
	public int getCountryId() {
		return writer_country_id;
	}
	
	public void setCountryId(int writer_country_id) {
		this.writer_country_id = writer_country_id;
	}
	
	public String getWriterName() {
		return writer_name;
	}
	
	public void setWriterName(String writer_name) {
		this.writer_name = writer_name;
	}
	
	public String getWriterCareer() {
		return writer_career;
	}
	
	public void setWriterCareer(String writer_career) {
		this.writer_career = writer_career;
	}
}
