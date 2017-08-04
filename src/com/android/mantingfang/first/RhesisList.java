package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

public class RhesisList {

	private static List<PoemRhesis> list = new ArrayList<>();
	
	private static RhesisList rhesis;
	
	private RhesisList() {
		
	}
	
	public static RhesisList getInstance() {
		if (rhesis == null) {
			rhesis = new RhesisList();
		}
		
		return rhesis;
	}
	
	public void setRhesisList(List<PoemRhesis> list) {
		RhesisList.list = list;
	}
	
	public List<PoemRhesis> getList() {
		return list;
	}
}
