package com.android.mantingfang.second;

import java.util.ArrayList;

import com.android.mantingfanggsc.R;

public class KindPicture {

	private static int[] images1 = {R.drawable.a1_1, R.drawable.a1_2, R.drawable.a1_3, R.drawable.a1_4, R.drawable.a1_5,
					R.drawable.a1_6, R.drawable.a1_7, R.drawable.a1_8};
	private static int[] images2 = {R.drawable.a2_1, R.drawable.a2_2, R.drawable.a2_3, R.drawable.a2_4, R.drawable.a2_5, 
					R.drawable.a2_6, R.drawable.a2_7, R.drawable.a2_8};
	private static int[] images3 = {R.drawable.a3_1, R.drawable.a3_2, R.drawable.a3_3, R.drawable.a3_4, R.drawable.a3_5, 
					R.drawable.a3_6, R.drawable.a3_7, R.drawable.a3_8, R.drawable.a3_9, R.drawable.a3_10, R.drawable.a3_11, 
					R.drawable.a3_12};
	private static int[] images4 = {R.drawable.a4_1, R.drawable.a4_2, R.drawable.a4_3, R.drawable.a4_4, R.drawable.a4_5, 
					R.drawable.a4_6};
	private static int[] images5 = {R.drawable.a5_1, R.drawable.a5_2, R.drawable.a5_3, R.drawable.a5_4, R.drawable.a5_5, 
					R.drawable.a5_6, R.drawable.a5_7, R.drawable.a5_8, R.drawable.a5_9, R.drawable.a5_10, R.drawable.a5_11, 
					R.drawable.a5_12};
	private static int[] images6 = {R.drawable.a6_1, R.drawable.a6_2, R.drawable.a6_3, R.drawable.a6_4, R.drawable.a6_5, 
					R.drawable.a6_6, R.drawable.a6_7, R.drawable.a6_8, R.drawable.a6_9, R.drawable.a6_10, R.drawable.a6_11, 
					R.drawable.a6_12};
	private static int[] images7 = {R.drawable.a7_1, R.drawable.a7_2, R.drawable.a7_3, R.drawable.a7_4, R.drawable.a7_5, 
					R.drawable.a7_6, R.drawable.a7_7, R.drawable.a7_8, R.drawable.a7_9, R.drawable.a7_10};
	private static int[] images8 = {R.drawable.a8_1, R.drawable.a8_2, R.drawable.a8_3, R.drawable.a8_4, R.drawable.a8_5, 
					R.drawable.a8_6, R.drawable.a8_7, R.drawable.a8_8, R.drawable.a8_9, R.drawable.a8_10};
	private static int[] images9 = {R.drawable.a9_1, R.drawable.a9_2, R.drawable.a9_3, R.drawable.a9_4, R.drawable.a9_5, 
					R.drawable.a9_6, R.drawable.a9_7, R.drawable.a9_8};
	private static int[] images10 = {R.drawable.a10_1, R.drawable.a10_2, R.drawable.a10_3, R.drawable.a10_4};
	
	private ArrayList<int[]> list = new ArrayList<>();
	
	private KindPicture() {};
	private static KindPicture kind;
	
	public static KindPicture getInstance() {
		if (kind == null) {
			kind = new KindPicture();
		}
		kind.list.add(images1);
		kind.list.add(images2);
		kind.list.add(images3);
		kind.list.add(images4);
		kind.list.add(images5);
		kind.list.add(images6);
		kind.list.add(images7);
		kind.list.add(images8);
		kind.list.add(images9);
		kind.list.add(images10);
		
		return kind;
	}
	
	public ArrayList<int[]> getPictures() {
		return kind.list;
	}
}
