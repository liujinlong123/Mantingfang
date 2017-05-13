package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Poetry extends Base {

	private int poetry_id;
	private String poetry_label_id;
	private int poetry_writer_id;
	private int poetry_language_id;
	private String poetry_name;
	private String poetry_content;
	private String poetry_rhesis;
	
	public Poetry(int poetry_id, String poetry_label_id, int poetry_writer_id, int poetry_language_id, 
			String poetry_name, String poetry_content, String poetry_rhesis) {
		this.poetry_id = poetry_id;
		this.poetry_label_id = poetry_label_id;
		this.poetry_writer_id = poetry_writer_id;
		this.poetry_language_id = poetry_language_id;
		this.poetry_name = poetry_name;
		this.poetry_content = poetry_content;
		this.poetry_rhesis = poetry_rhesis;
	}
	
	public int getPoetryId() {
		return poetry_id;
	}
	
	public void setPoetryId(int poetry_id) {
		this.poetry_id = poetry_id;
	}
	
	public String getLabelId() {
		return poetry_label_id;
	}
	
	public void setLabelId(String poetry_label_id) {
		this.poetry_label_id = poetry_label_id;
	}
	
	public int getWriterId() {
		return poetry_writer_id;
	}
	
	public void setWriterId(int poetry_writer_id) {
		this.poetry_writer_id = poetry_writer_id;
	}
	
	public int getLanguageId() {
		return poetry_language_id;
	}
	
	public void setLanguageId(int poetry_language_id) {
		this.poetry_language_id = poetry_language_id;
	}
	
	public String getName() {
		return poetry_name;
	}
	
	public void setName(String poetry_name) {
		this.poetry_name = poetry_name;
	}
	
	public String getContent() {
		return poetry_content;
	}
	
	public void setCotent(String poetry_content) {
		this.poetry_content = poetry_content;
	}
	
	public String getRhesis() {
		return poetry_rhesis;
	}
	
	public void setRhesis(String poetry_rhesis) {
		this.poetry_rhesis = poetry_rhesis;
	}
}
