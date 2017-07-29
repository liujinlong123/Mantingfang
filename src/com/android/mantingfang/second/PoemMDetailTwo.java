package com.android.mantingfang.second;

import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.PoetryList;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.model.PoemM;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;

import android.app.Activity;
import android.os.AsyncTask;
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

public class PoemMDetailTwo extends Activity {
	
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
	
	private String poemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poetrydetail);
		
		initViews();
		getData();
	}
	
	private void initViews() {
		poetrydetail_rgp = (RadioGroup)findViewById(R.id.poetrydetail_rgp);
		poemname = (TextView)findViewById(R.id.poetrydetail_tv_poemname);
		writername = (TextView)findViewById(R.id.poetrydetail_tv_writername);
		content = (TextView)findViewById(R.id.poetrydetail_tv_content);
		kind = (TextView)findViewById(R.id.poetrydetail_tv_kind);
		kindDetail = (TextView)findViewById(R.id.poetrydetail_tv_kind_detail);
		
		poemId = getIntent().getExtras().getString("poemId");
		
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
				Toast.makeText(PoemMDetailTwo.this, "Collect", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoemMDetailTwo.this, "Comment", Toast.LENGTH_SHORT).show();
			}
		});
		
		img_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(PoemMDetailTwo.this, "more", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initshow(final PoemM poem) {
		poemname.setText(poem.getPoemName());
		writername.setText("[" + poem.getDynasty() + "]" + poem.getWriter());
		content.setText(poem.getPoemContent());
		
		poetrydetail_rgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.poetrydetail_rbtn_beijing:
					kind.setText("背景");
					if (poem.getPoemBg() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getPoemBg());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_zhushi:
					kind.setText("注释");
					if (poem.getNotes() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getNotes());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_yiwen:
					kind.setText("译文");
					if (poem.getToNow() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getToNow());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_fanyi:
					kind.setText("翻译");
					if (poem.getTrans() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getTrans());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shangxi:
					kind.setText("赏析");
					if (poem.getApprec() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getApprec());
					}
					
					break;
					
				case R.id.poetrydetail_rbtn_shoucang:
					kind.setText("作者");
					if (poem.getWriterBg() == null) {
						Log.v("info--poetrydetail", "null");
					} else {
						kindDetail.setText(poem.getWriterBg());
					}
					break;
					
				case R.id.poetrydetail_rbtn_pinglun:
					kind.setText("评论");
					kindDetail.setText("");
					break;
				}
			}
		});
	}
	
	private void getData() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().http_postPoem(poemId);
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null && !result.equals("")) {
					final List<PoemM> poemList;
					try {
						poemList = PoetryList.parsePoem(StringUtils.toJSONArray(result)).getPoemMList();
						if (poemList != null) {
							initshow(poemList.get(0));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(PoemMDetailTwo.this, "没有返回数据", Toast.LENGTH_SHORT).show();
				}
			}

		};

		task.execute();
	}
}
