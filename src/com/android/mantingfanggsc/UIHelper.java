package com.android.mantingfanggsc;

import android.content.Context;
import android.content.Intent;

public class UIHelper {
	
	/**
	 * 显示诗词详情
	 * @param context
	 * @param poetryId
	 * @param flag
	 */
	public static void showPoemDetail(Context context, int poetry_id, int flag) {
		Intent intent = new Intent(context, PoetryDetail.class);
		intent.putExtra("poetry_id", poetry_id);
		intent.putExtra("flag", flag);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 显示作者详情
	 * @param context
	 * @param writerId
	 */
	public static void showWriterDetail(Context context, int writer_id) {
		Intent intent = new Intent(context, WriterDetail.class);
		intent.putExtra("writer_id", writer_id);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 显示某作者的全部作品
	 * @param context
	 * @param writerId
	 */
	public static void showAllPoemByWid(Context context, int writerId) {
		
	}
	
	/**
	 * 通过类型曲查找相关作品
	 * @param context
	 * @param typeId
	 */
	public static void showPoemByType(Context context, int typeId) {
		
	}
	
	/**
	 * 搜索界面
	 * @param context
	 */
	public static void showSearch(Context context) {
		
	}
}
