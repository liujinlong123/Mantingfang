package com.android.mantingfanggsc;

import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.bean.Info;
import com.android.mantingfang.bean.InfoDao;
import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.model.Poem;

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

public class PoetryDetail extends Activity {
	
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
	
	private Poem poem;
	private Info info;
	private String dynasty = null;
	
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
		
		int poetry_id = getIntent().getIntExtra("poetry_id", 0);
		poem = new PoetryDao(PoetryDetail.this).findPoemById(poetry_id);
		info = (new InfoDao(PoetryDetail.this)).getInfoByPId(poetry_id);
		dynasty = new DynastyDao(PoetryDetail.this).getDynastyById(poem.getDynastyid()).getDynastyName();
		
		//topbar
		linearback = (LinearLayout)findViewById(R.id.topbar_all_back);
		tv_back = (TextView)findViewById(R.id.topbar_tv_back);
		tv_theme = (TextView)findViewById(R.id.topbar_tv_theme);
		img_collect = (ImageView)findViewById(R.id.topbar_all_collect);
		img_comment = (ImageView)findViewById(R.id.topbar_all_comment);
		img_more = (ImageView)findViewById(R.id.topbar_all_more);
		
		tv_back.setText("返回");
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
				Toast.makeText(PoetryDetail.this, "Collect", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoetryDetail.this, "Comment", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoetryDetail.this, "more", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initshow() {
		poemname.setText(poem.getTitle());
		writername.setText("[" + dynasty + "]" + poem.getWritername());
		content.setText(poem.getContent());
		
		poetrydetail_rgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.poetrydetail_rbtn_beijing:
					kind.setText("背景");
					if (info == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(info.getBackground());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_zhushi:
					kind.setText("注释");
					if (info == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(info.getNote());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_yiwen:
					kind.setText("译文");
					if (info == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(info.getTonow());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_fanyi:
					kind.setText("翻译");
					if (info == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(info.getTranslation());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shangxi:
					kind.setText("赏析");
					if (info == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(info.getPraise());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shoucang:
					kind.setText("作者");
					kindDetail.setText("");
					break;
					
				case R.id.poetrydetail_rbtn_pinglun:
					kind.setText("评论");
					kindDetail.setText("");
					break;
				}
			}
		});
	}
}
