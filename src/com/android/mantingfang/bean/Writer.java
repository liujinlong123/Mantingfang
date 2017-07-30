package com.android.mantingfang.bean;

@SuppressWarnings("serial")
public class Writer extends Base {

	private int writer_id;				//诗人id
	private String writer_label_id;		//诗人标签id
	private int writer_dynasty_id;		//诗人朝代id
	private int writer_country_id;		//诗人国家id
	
	
	private String writerId;			//诗人ID
	private String writer_name;			//诗人名字
	private String dynastyName;			//诗人朝代
	private String writer_career;		//诗人生平
	
	private String worksNum;			//作品数目
	
	public String getWorksNum() {
		return worksNum;
	}

	public void setWorksNum(String worksNum) {
		this.worksNum = worksNum;
	}

	public Writer() {}
	
	public Writer(int writer_id, String writer_label_id, int writer_dynasty_id, int writer_country_id,
			String writer_name, String writer_career) {
		this.writer_id = writer_id;
		this.writer_label_id = writer_label_id;
		this.writer_dynasty_id = writer_dynasty_id;
		this.writer_country_id = writer_country_id;
		this.writer_name = writer_name;
		this.writer_career = writer_career;
	}
	
	public Writer(String writerId, String writerName, String dynastyName, String writer_career) {
		this.writerId = writerId;
		this.writer_name = writerName;
		this.dynastyName = dynastyName;
		this.writer_career = writer_career;
	}
	
	public int getWriterId() {
		return writer_id;
	}

	public void setWriter_id(int writer_id) {
		this.writer_id = writer_id;
	}

	public String getWriter_label_id() {
		return writer_label_id;
	}

	public void setWriter_label_id(String writer_label_id) {
		this.writer_label_id = writer_label_id;
	}

	public int getWriter_dynasty_id() {
		return writer_dynasty_id;
	}

	public void setWriter_dynasty_id(int writer_dynasty_id) {
		this.writer_dynasty_id = writer_dynasty_id;
	}

	public int getWriter_country_id() {
		return writer_country_id;
	}

	public void setWriter_country_id(int writer_country_id) {
		this.writer_country_id = writer_country_id;
	}

	public String getWriter_name() {
		return writer_name;
	}

	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}

	public String getDynastyName() {
		return dynastyName;
	}

	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}

	public String getWriter_career() {
		return writer_career;
	}

	public void setWriter_career(String writer_career) {
		this.writer_career = writer_career;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getStringWriterId() {
		return writerId;
	}
	
	public void setWriterId(int writer_id) {
		this.writer_id = writer_id;
	}
	
	public String getLabelId() {
		return writer_label_id;
	}
	
	public void setLabelId(String writer_label_id) {
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
