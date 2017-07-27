package com.android.mantingfang.fourth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MyBroadcast extends BroadcastReceiver {

	private TextView quit;
	
	public MyBroadcast(TextView quit) {
		this.quit = quit;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		quit.setVisibility(View.VISIBLE);
	}
	
}
