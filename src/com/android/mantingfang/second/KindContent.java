package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

public class KindContent {

	private String kindName;
	
	private List<SingleNames> singleName;
	
	private int[] images;
	
	public KindContent() {};
	
	public KindContent(String kindName, ArrayList<SingleNames> singleName) {
		this.kindName = kindName;
		this.singleName = singleName;
	}
	
	public KindContent(String kindName, ArrayList<SingleNames> singleName, int[] images) {
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
	
	public void setSingleName(ArrayList<SingleNames> singleName) {
		this.singleName = singleName;
	}
	
	public ArrayList<SingleNames> getSingleName() {
		return (ArrayList<SingleNames>) singleName;
	}
	
	public void setImages(int[] images) {
		this.images = new int[images.length];
		System.arraycopy(images, 0, this.images, 0, images.length);
	}
	
	public int[] getImages() {
		return images;
	}
	
}
