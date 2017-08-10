package com.android.mantingfanggsc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SuccinctProgress {

	private static int[] iconStyles = { R.drawable.icon_progress_style1,
			R.drawable.icon_progress_style2, R.drawable.icon_progress_style3,
			R.drawable.icon_progress_style4 };
	private static ProgressDialog pd;
	/** ICON ???? */
	public static final int THEME_ULTIMATE = 0;
	/** ICON ??? */
	public static final int THEME_DOT = 1;
	/** ICON ????? */
	public static final int THEME_LINE = 2;
	/** ICON ????? */
	public static final int THEME_ARC = 3;

	@SuppressLint("InflateParams")
	public static void showSuccinctProgress(Context context, String message,
			int theme, boolean isCanceledOnTouchOutside, boolean isCancelable) {

		
		pd = new ProgressDialog(context, R.style.succinctProgressDialog);
		pd.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
		pd.setCancelable(isCancelable);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		View view = LayoutInflater.from(context).inflate(
				R.layout.succinct_progress_content, null);
		ImageView mProgressIcon = (ImageView) view
				.findViewById(R.id.progress_icon);
		mProgressIcon.setImageResource(iconStyles[theme]);
		TextView mProgressMessage = (TextView) view
				.findViewById(R.id.progress_message);
		mProgressMessage.setText(message);
		new AnimationUtils();
		Animation jumpAnimation = AnimationUtils.loadAnimation(context,
				R.anim.succinct_animation);
		mProgressIcon.startAnimation(jumpAnimation);
		pd.show();
		pd.setContentView(view, params);

	}

	/**
	 * @return true??????��?false????????
	 */
	public static boolean isShowing() {

		if (pd != null && pd.isShowing()) {

			return true;
		}
		return false;

	}

	/**
	 * ???Dialog
	 */
	public static void dismiss() {

		if (isShowing()) {

			pd.dismiss();
		}

	}
}
