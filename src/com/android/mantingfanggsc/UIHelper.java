package com.android.mantingfanggsc;

import android.content.Context;
import android.content.Intent;

public class UIHelper {
	
	/**
	 * ��ʾʫ������
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
	 * ��ʾ��������
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
	 * ��ʾĳ���ߵ�ȫ����Ʒ
	 * @param context
	 * @param writerId
	 */
	public static void showAllPoemByWid(Context context, int writerId) {
		
	}
	
	/**
	 * ͨ�����������������Ʒ
	 * @param context
	 * @param typeId
	 */
	public static void showPoemByType(Context context, int typeId) {
		
	}
	
	/**
	 * ��������
	 * @param context
	 */
	public static void showSearch(Context context) {
		
	}
}
