package com.android.mantingfanggsc;

import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfang.second.PoemMDetailTwo;
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
	 * 诗词PoemM详情页
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
	
	public static void showPoemMDetailTwoById(Context context, String poemId, int flag) {
		Intent intent = new Intent(context, PoemMDetailTwo.class);
		Bundle bundle = new Bundle();
		bundle.putString("poemId", poemId);
		intent.putExtra("flag", flag);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * User 详情
	 * @param context
	 * @param flag
	 */
	public static void showUserDetail(Context context, int flag, String userId, String headPath, String nickName) {
		Intent intent = new Intent(context, UserPager.class);
		intent.putExtra("flag", flag);
		Bundle bundle = new Bundle();
		bundle.putString("userId", userId);
		bundle.putString("nickName", nickName);
		bundle.putString("headPath", headPath);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 诗人详情
	 * @param context
	 * @param writerId
	 */
	public static void showWriterDetail(Context context, int writer_id, boolean isNetwork) {
		Intent intent = new Intent(context, WriterDetail.class);
		Bundle bundle = new Bundle();
		bundle.putInt("writerId", writer_id);
		bundle.putBoolean("isNetwork", isNetwork);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void showWriterDetail(Context context, Writer writer, boolean isNetwork) {
		Intent intent = new Intent(context, WriterDetail.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("writer", writer);
		bundle.putBoolean("isNetwork", isNetwork);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 
	 * @param context
	 * @param writerId
	 */
	public static void showAllPoemByWid(Context context, int writerId) {
		
	}
	
	/**
	 * 
	 * @param context
	 * @param typeId
	 */
	public static void showPoemByType(Context context, int typeId) {
		
	}
	
	/**
	 * 跳转search
	 * @param context
	 */
	public static void showSearch(Context context) {
		Intent intent = new Intent(context, Search.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * Comment详情
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
	 * CommentMain详情
	 * @param context
	 * @param flag
	 */
	public static void showCommentMain(Context context, int flag, UserTwoContent content, String topicId, String typeNum
			, String headPath) {
		Intent intent = new Intent(context, CommentMain.class);
		intent.putExtra("flag", flag);
		Bundle bundle = new Bundle();
		bundle.putSerializable("commentM", content);
		bundle.putString("topicId", topicId);
		bundle.putString("typeNum", typeNum);
		bundle.putString("headPath", headPath);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 跳转USERTwo
	 * @param context
	 * @param flag
	 */
	public static void showSearchTwo(Context context, int flag) {
		Intent intent = new Intent(context, SearchTwo.class);
		intent.putExtra("flag", flag);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
