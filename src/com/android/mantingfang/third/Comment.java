package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.second.KindGridView;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Comment extends Activity {

	private ListView listTv;
	private CommentTextAdapter adapterTv;
	private List<String> dataListTv;
	
	private ListView listOne;
	private CommentAdapter adapterOne;
	private List<CommentContent> dataListOne;

	private ListView listTwo;
	private CommentAdapter adapterTwo;
	private List<CommentContent> dataListTwo;

	private EditText editer;
	private Button btnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		
		listOne = (ListView)findViewById(R.id.comment_better);
		dataListOne = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			dataListOne.add(new CommentContent());
		}
		adapterOne = new CommentAdapter(this, dataListOne);
		listOne.setAdapter(adapterOne);
		setListViewHeightBasedOnChildren(listOne);
		
		listTv = (ListView)findViewById(R.id.comment_tv_better);
		dataListTv = new ArrayList<>();
		dataListTv.add("��������");
		adapterTv = new CommentTextAdapter(Comment.this, dataListTv);
		listTv.setAdapter(adapterTv);
		listTv.setEnabled(false);
		
		listTwo = (ListView)findViewById(R.id.comment_new);
		dataListTwo = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			dataListTwo.add(new CommentContent());
		}
		adapterTwo = new CommentAdapter(this, dataListTwo);
		listTwo.setAdapter(adapterTwo);
		setListViewHeightBasedOnChildren(listTwo);
	}
	
	/**
	 * ScrollViewǶ��listview
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			// ��������View �Ŀ��
			listItem.measure(0, 0);
			// ͳ������������ܸ߶�
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}
}
