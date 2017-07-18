package com.android.mantingfanggsc;

import com.android.mantingfang.model.PoemM;
import com.android.mantingfang.third.Comment;
import com.android.mantingfang.third.CommentMain;
import com.android.mantingfang.third.PoemMDetail;
import com.android.mantingfang.third.UserPager;
import com.android.mantingfang.third.UserTwoContent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
	 * 显示PoemM详情
	 * @param context
	 * @param poem
	 * @param flag
	 */
	public static void showPoemMDetail(Context context, PoemM poem, int flag) {
		Intent intent = new Intent(context, PoemMDetail.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("poemM", poem);
		intent.putExtra("flag", flag);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 显示用户详情
	 * @param context
	 * @param flag
	 */
	public static void showUserDetail(Context context, int flag, String userId, String headPath, String nickName) {
		Intent intent = new Intent(context, UserPager.class);
		intent.putExtra("flag", flag);
		Bundle bundle = new Bundle();
		bundle.putString("userId", userId);
		bundle.putString("headPath", headPath);
		bundle.putString("nickName", nickName);
		intent.putExtras(bundle);
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
		Intent intent = new Intent(context, Search.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 显示评论页
	 * @param context
	 * @param flag
	 */
	public static void showComment(Context context, int flag, String topicId, String typeNum) {
		Intent intent = new Intent(context, Comment.class);
		intent.putExtra("flag", flag);
		Bundle bundle = new Bundle();
		bundle.putString("topicId", topicId);
		bundle.putString("typeNum", typeNum);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 显示评论Main页
	 * @param context
	 * @param flag
	 */
	public static void showCommentMain(Context context, int flag, UserTwoContent content, String topicId, String typeNum) {
		Intent intent = new Intent(context, CommentMain.class);
		intent.putExtra("flag", flag);
		Bundle bundle = new Bundle();
		bundle.putSerializable("commentM", content);
		bundle.putString("topicId", topicId);
		bundle.putString("typeNum", typeNum);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
