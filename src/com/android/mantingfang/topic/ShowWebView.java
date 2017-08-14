package com.android.mantingfang.topic;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowWebView extends Activity {
	
	private WebView webview;
	private LinearLayout linearBack;
	private TextView title;
	private TextView backTitle;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_show);
		
		webview = (WebView)findViewById(R.id.webview_show);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient());
		webview.loadUrl("http://so.gushiwen.org/type.aspx");
		
		linearBack = (LinearLayout)findViewById(R.id.topbar_all_back);
		linearBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		 
		
		title = (TextView)findViewById(R.id.topbar_tv_theme);
		title.setText("");
		backTitle = (TextView)findViewById(R.id.topbar_tv_back);
		backTitle.setText("发现");
	}
}
