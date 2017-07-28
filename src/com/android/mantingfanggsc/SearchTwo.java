package com.android.mantingfanggsc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.first.PoemRhesis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchTwo extends Activity {

	private EditText editor;
	private List<PoemRhesis> dataList = new ArrayList<>();
	private ItemPoemAdapter adapter;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		editor = (EditText) findViewById(R.id.search_editer);
		listview = (ListView) findViewById(R.id.search_list);
		editor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (editor.getText().toString() != null && !editor.equals("")) {
					getData(editor.getText().toString());
				}
			}
		});
	}

	/**
	 * 查询
	 * 
	 * @param key
	 */
	private void getData(final String key) {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {

				if (key != null && !key.equals("")) {
					return MyClient.getInstance().Http_postPoemKey(key);
				} else {
					return null;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				try {
					if (result != null && !result.equals("")) {
						listview.setVisibility(View.VISIBLE);
						dataList = TopicList.parseSearchPoem(StringUtils.toJSONArray(result)).getSearchList();

						adapter = new ItemPoemAdapter(SearchTwo.this, dataList);
						listview.setAdapter(adapter);
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								Intent intent = new Intent();
								intent.putExtra("poemId", dataList.get(position).getPoemId());
								intent.putExtra("poemName", dataList.get(position).getPoemName());
								setResult(RESULT_OK, intent);
								finish();
							}
						});
					} else {
						listview.setVisibility(View.GONE);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		task.execute();
	}

}
