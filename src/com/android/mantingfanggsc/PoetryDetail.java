package com.android.mantingfanggsc;

import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.bean.Info;
import com.android.mantingfang.bean.InfoDao;
import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.model.Poem;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PoetryDetail extends Activity {
	
	private RadioGroup poetrydetail_rgp;
	private TextView poemname;
	private TextView writername;
	private TextView content;
	private TextView kind;
	private TextView kindDetail;
	
	private Poem poem;
	private Info info;
	private String dynasty = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poetrydetail);
		
		initViews();
		show();
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
	}
	
	private void show() {
		poemname.setText(poem.getTitle());
		writername.setText("[" + dynasty + "]" + poem.getWritername());
		content.setText(poem.getContent());
		
		kind.setText("±³¾°");
		if (info == null) {
			Log.v("info--poetrydetail", "null");
		} else {
			kindDetail.setText(info.getBackground());
		}
	}
}
