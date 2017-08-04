package com.android.mantingfanggsc;

import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.model.Poem;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WriterDetail extends Activity {

	private ListView listview;
	private List<Poem> plist;
	private WriterDetailListAdapter adapter;

	// topbar
	private LinearLayout linearback;
	private TextView tv_back;
	private TextView tv_theme;
	private ImageView img_collect;
	private ImageView img_comment;
	private ImageView img_more;
	
	private boolean isNetwork;
	private Writer writer;
	
	private int writerId;
	//private WriterDao writerDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writerdetail);

		Bundle bundle = getIntent().getExtras();
		isNetwork = bundle.getBoolean("isNetwork");
		writer = (Writer) bundle.getSerializable("writer");
		writerId = bundle.getInt("writerId");
		
		
		initViews();
		listview = (ListView) findViewById(R.id.writerdetail_listview);
		getDatas(isNetwork, writerId, writer);
	}

	private void initViews() {
		linearback = (LinearLayout) findViewById(R.id.topbar_all_back);
		tv_back = (TextView) findViewById(R.id.topbar_tv_back);
		tv_theme = (TextView) findViewById(R.id.topbar_tv_theme);
		img_collect = (ImageView) findViewById(R.id.topbar_all_collect);
		img_comment = (ImageView) findViewById(R.id.topbar_all_comment);
		img_more = (ImageView) findViewById(R.id.topbar_all_more);

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
	}

	/*private void getData() {
		plist = new ArrayList<Poem>();
		plist = new PoetryDao(WriterDetail.this).getAllPoemM();
	}*/

	private void getDatas(final boolean isNetwork, final int writerId, final Writer writer) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				
				return MyClient.getInstance().Http_postWriterById(writer.getStringWriterId());
			}

			@Override
			protected void onPostExecute(String result) {
				if (isNetwork) {
					try {
						plist = TopicList.parseWritersPoem(StringUtils.toJSONArray(result), writer.getDynastyName(),
								writer.getWriterName()).getWriterPoemList();
						adapter = new WriterDetailListAdapter(WriterDetail.this, plist, writer);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} /*else {
					writerDao = new WriterDao(WriterDetail.this);
					Writer writers = writerDao.getWriterMById(writerId);
					getData();
					adapter = new WriterDetailListAdapter(WriterDetail.this, plist, writers);
				}*/
				writer.setWorksNum(plist.size() + " ");
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (position == 0) {
							
						} else if (!isNetwork){
							//UIHelper.showPoemDetail(WriterDetail.this, writerId, 0);
						} else if (isNetwork) {
							UIHelper.showPoemMDetailTwoById(WriterDetail.this, plist.get(position - 1).getPoemId(), 0);
						}
					}
				});
			}

		};

		task.execute();
	}
}
