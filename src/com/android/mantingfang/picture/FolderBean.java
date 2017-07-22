package com.android.mantingfang.picture;

/**
 * 文件夹
 * @author MrKID
 *
 */
public class FolderBean {
	private String dir;				//当前文件夹路径
	private String firstImgPath;	//第一张图片路径
	private String name;			//当前文件夹的名称
	private int count;				//当前图片数量
	
	
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
		
		int lastIndexOf = this.dir.lastIndexOf("/");
		this.name = this.dir.substring(lastIndexOf + 1);
	}
	public String getFirstImgPath() {
		return firstImgPath;
	}
	public void setFirstImgPath(String firstImgPath) {
		this.firstImgPath = firstImgPath;
	}
	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
