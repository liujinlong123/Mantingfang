package com.android.mantingfang.third;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class FileAudio extends Base{

	private String type;
	private String path;
	
	
	public FileAudio(String type, String path) {
		this.type = type;
		this.path = path;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	
}
