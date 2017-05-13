package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Info extends Base {

	private int info_id;
	private int info_poetry_id;
	private String info_background;
	private String info_praise;
	private String info_note;
	private String info_tonow;
	private String info_translation;
	
	public Info(int info_id, int info_poetry_id, String info_background, 
			String info_praise, String info_note, String info_tonow, String info_tranlation) {
		this.info_id = info_id;
		this.info_poetry_id = info_poetry_id;
		this.info_background = info_background;
		this.info_praise = info_praise;
		this.info_note = info_note;
		this.info_tonow = info_tonow;
		this.info_translation = info_tranlation;
	}
	
	public int getInfoId() {
		return info_id;
	}
	
	public void setInfoId(int info_id) {
		this.info_id = info_id;
	}
	
	public int getPoetryId() {
		return info_poetry_id;
	}
	
	public void setPoetryId(int info_poetry_id) {
		this.info_poetry_id = info_poetry_id;
	}
	
	public String getBackground() {
		return info_background;
	}
	
	public void setBackground(String info_background) {
		this.info_background = info_background;
	}
	
	public String getPraise() {
		return info_praise;
	}
	
	public void setPraise(String info_praise) {
		this.info_praise = info_praise;
	}
	
	public String getNote() {
		return info_note;
	}
	
	public void setNote(String info_note) {
		this.info_note = info_note;
	}
	
	public String getTonow() {
		return info_tonow;
	}
	
	public void setTonow(String info_tonow) {
		this.info_tonow = info_tonow;
	}
	
	public String getTranslation() {
		return info_translation;
	}
	
	public void setTranslation(String info_translation) {
		this.info_translation = info_translation;
	}
}
