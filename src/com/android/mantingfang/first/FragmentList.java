package com.android.mantingfang.first;

import java.util.ArrayList;
import java.util.List;

public class FragmentList {

	private List<FragViewPager> fragmentList = new ArrayList<>();
	
	private static FragmentList list;
	
	private FragmentList() {}
	
	public static FragmentList getInstance() {
		if (list == null) {
			list = new FragmentList();
		}
		
		return list;
	}

	public List<FragViewPager> getFragmentList() {
		return fragmentList;
	}

	public void setFragmentList(List<FragViewPager> fragmentList) {
		this.fragmentList = fragmentList;
	}
	
	
}
