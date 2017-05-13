package com.android.mantingfang.model;

import com.android.mantingfang.bean.Base;

import android.annotation.SuppressLint;

@SuppressWarnings("serial")
public class Poem extends Base {

	private int poetryid;
	private int kindid;
	private int dynastyid;
	private int writerid;
	private String writername;
	private String title;
	private String content;
	private String rhesis;

	public Poem(String kindid, int poid, int dynastyid, int writerid, String writername, String title, String content,
			String rhesis) {

		this.kindid = parseKind(kindid);
		this.poetryid = poid;
		this.dynastyid = dynastyid;
		this.writerid = writerid;
		this.writername = writername;
		this.title = title;
		this.content = content;
		this.rhesis = rhesis;

	}

	// 分类定为10
	@SuppressLint("UseValueOf")
	private int parseKind(String kindid) {
		String str = kindid.charAt(0) + "";
		int aa = new Integer(str);
		if (aa >= 0 && aa <= 10) {
			return aa;
		}
		return 0;
	}

	public int getDynastyid() {
		return dynastyid;
	}

	public void setDynastyid(int dynastyid) {
		this.dynastyid = dynastyid;
	}

	public int getKindid() {
		return kindid;
	}

	public void setKindid(int kindid) {
		this.kindid = kindid;
	}

	public int getPoetryid() {
		return poetryid;
	}

	public void setPoetryid(int poetryid) {
		this.poetryid = poetryid;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
