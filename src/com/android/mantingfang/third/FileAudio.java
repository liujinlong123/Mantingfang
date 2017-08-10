package com.android.mantingfang.third;

import com.android.mantingfang.bean.Base;

@SuppressWarnings("serial")
public class FileAudio extends Base{

	private String type;
	private String path;
	
	//1--第一次播放------2--暂停后继续播放-------3--停止播放
	private String play;
	
	
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


	public String getPlay() {
		return play;
	}


	public void setPlay(String play) {
		this.play = play;
	}
}
