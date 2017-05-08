package com.android.mantingfang.second;

public class KindContent {

	private String kindName;
	
	private String[] singleName;
	
	private int[] images;
	
	public KindContent() {};
	
	public KindContent(String kindName, String[] singleName, int[] images) {
		this.kindName = kindName;
		this.singleName = singleName;
		this.images = images;
	}
	
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
	public String getKindName() {
		return kindName;
	}
	
	public void setSingleName(String[] singleName) {
		this.singleName = new String[singleName.length];
		System.arraycopy(singleName, 0, this.singleName, 0, singleName.length);
	}
	
	public String[] getSingleName() {
		return singleName;
	}
	
	public void setImages(int[] images) {
		this.images = new int[images.length];
		System.arraycopy(images, 0, this.images, 0, images.length);
	}
	
	public int[] getImages() {
		return images;
	}
}
