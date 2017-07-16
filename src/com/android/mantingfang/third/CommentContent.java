package com.android.mantingfang.third;

public class CommentContent {
	private String headPath;	//ͷ��·��
	private String name;		//�û��ǳ�
	private String time;		//����ʱ��
	private boolean zan;		//�Ƿ���
	private String content;		//��������
	
	public CommentContent() {};
	
	public CommentContent(String headPath, String name, String time, boolean zan, String content) {
		this.headPath = headPath;
		this.name = name;
		this.time = time;
		this.zan = zan;
		this.content = content;
	}
	
	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}
	
	public String getHeadPath() {
		return headPath;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setZan(boolean zan) {
		this.zan = zan;
	}
	
	public boolean getZan() {
		return zan;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
}
