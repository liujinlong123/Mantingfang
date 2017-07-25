package com.android.mantingfang.first;

import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPagerAdd extends Activity {

	private ImageView imgBack;
	private TextView btnBeijing;
	private EditText editText;
	private EditText editWriter;
	private Button tvSave;
	private PictureContent content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_pager_add);
		
		initViews();
	}
	
	private void initViews() {
		imgBack = (ImageView)findViewById(R.id.first_pager_img_addBack);
		btnBeijing = (TextView)findViewById(R.id.first_pager_img_beijing);
		editText = (EditText)findViewById(R.id.first_pager_text);
		editWriter = (EditText)findViewById(R.id.first_pager_writer);
		tvSave = (Button)findViewById(R.id.first_pager_save);
		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnBeijing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FirstPagerAdd.this, "背景", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = editText.getText().toString();
				String writer = editWriter.getText().toString();
				if (text != null && !text.equals("")) {
					content = new PictureContent(text, writer);
					
					
					finish();
				}
			}
		});
	}
}
