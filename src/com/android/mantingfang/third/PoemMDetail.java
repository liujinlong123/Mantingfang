package com.android.mantingfang.third;

import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PoemMDetail extends Activity {
	
	private RadioGroup poetrydetail_rgp;
	private TextView poemname;
	private TextView writername;
	private TextView content;
	private TextView kind;
	private TextView kindDetail;
	
	//topbar
	private LinearLayout linearback;
	private TextView tv_back;
	private TextView tv_theme;
	private ImageView img_collect;
	private ImageView img_comment;
	private ImageView img_more;
	
	private PoemM poem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poetrydetail);
		
		initViews();
		initshow();
	}
	
	private void initViews() {
		poetrydetail_rgp = (RadioGroup)findViewById(R.id.poetrydetail_rgp);
		poemname = (TextView)findViewById(R.id.poetrydetail_tv_poemname);
		writername = (TextView)findViewById(R.id.poetrydetail_tv_writername);
		content = (TextView)findViewById(R.id.poetrydetail_tv_content);
		kind = (TextView)findViewById(R.id.poetrydetail_tv_kind);
		kindDetail = (TextView)findViewById(R.id.poetrydetail_tv_kind_detail);
		
		poem = (PoemM) getIntent().getSerializableExtra("poemM");
		
		//topbar
		linearback = (LinearLayout)findViewById(R.id.topbar_all_back);
		tv_back = (TextView)findViewById(R.id.topbar_tv_back);
		tv_theme = (TextView)findViewById(R.id.topbar_tv_theme);
		img_collect = (ImageView)findViewById(R.id.topbar_all_collect);
		img_comment = (ImageView)findViewById(R.id.topbar_all_comment);
		img_more = (ImageView)findViewById(R.id.topbar_all_more);
		
		tv_back.setText("·µ»Ø");
		tv_theme.setVisibility(View.INVISIBLE);
		img_collect.setVisibility(View.VISIBLE);
		img_comment.setVisibility(View.VISIBLE);
		img_more.setVisibility(View.VISIBLE);
		linearback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		img_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoemMDetail.this, "Collect", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoemMDetail.this, "Comment", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoemMDetail.this, "more", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initshow() {
		poemname.setText(poem.getPoemName());
		writername.setText("[" + poem.getDynasty() + "]" + poem.getWriter());
		content.setText(poem.getPoemContent());
		
		poetrydetail_rgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.poetrydetail_rbtn_beijing:
					kind.setText("±³¾°");
					if (poem.getPoemBg() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getPoemBg());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_zhushi:
					kind.setText("×¢ÊÍ");
					if (poem.getNotes() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getNotes());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_yiwen:
					kind.setText("ÒëÎÄ");
					if (poem.getToNow() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getToNow());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_fanyi:
					kind.setText("·­Òë");
					if (poem.getTrans() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getTrans());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shangxi:
					kind.setText("ÉÍÎö");
					if (poem.getApprec() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getApprec());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shoucang:
					kind.setText("×÷Õß");
					if (poem.getWriterBg() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getWriterBg());
					}
					break;
					
				case R.id.poetrydetail_rbtn_pinglun:
					kind.setText("ÆÀÂÛ");
					kindDetail.setText("");
					break;
				}
			}
		});
	}
}
