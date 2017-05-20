package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.bean.PoetryDao;
import com.android.mantingfang.bean.URLs;
import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentSecond extends Fragment {

	private View view;
	
	//�Ŀ�
	private ListView wenkuListView;
	private SecondWenkuListViewAdapter wenkuAdapter;
	private List<KindContent> wenkuList;
	
	//private String[] singleName = {"ʫ��ȫ��", "���ǽ�ѡ", "���¾�", "��վ�", "��ʫʮ����", "��ʫ������", "�δ�������", "�����ӵ�ʫ", "�ָ�ʫ��", "����"};
	private int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,};
	
	
	//ʫ��
	private ListView writerListView;
	private SecondWriterListViewAdapter writerAdapter;
	private List<Poem> writerList;
	private PoetryDao poetryDao;
	
	//��ת����
	private ImageView imgSearch;
	
	//������
	private RadioGroup radgp;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_second_pager, null);
			
			initViews();
			initWriter();
			initWenku();
			
			initMain();
			initSearch();
			
			return view;
		}
		
		return view;
	}
	
	private void initViews() {
		imgSearch = (ImageView)view.findViewById(R.id.topbar_second_search);
	}
	
	
	//��ʼ���Ŀ����
	private void initWenku() {
		wenkuListView = (ListView)view.findViewById(R.id.frag_second_wenku_list);
		wenkuAdapter = new SecondWenkuListViewAdapter(getActivity(), getWenkuData());
		wenkuListView.setAdapter(wenkuAdapter);
	}
	
	private List<KindContent> getWenkuData() {
		wenkuList = new ArrayList<KindContent>();
		for (int i = 0; i < URLs.typeKind.length; i++) {
			//for (int j = 0; j < URLs.TYPES[i].length; j++) {
				KindContent content = new KindContent(URLs.typeKind[i], URLs.TYPES[i], images);
				wenkuList.add(content);
			//}
		}
		
		return wenkuList;
	}
	
	//��ʼ��ʫ�˽���
	private void initWriter() {
		writerListView = (ListView)view.findViewById(R.id.frag_second_writer_list);
		writerAdapter = new SecondWriterListViewAdapter(getActivity(), getWriterData());
		writerListView.setAdapter(writerAdapter);
		writerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UIHelper.showWriterDetail(getActivity(), 1);
			}
		});
	}
	
	private List<Poem> getWriterData() {
		writerList = new ArrayList<Poem>();
		//poetryDao = new PoetryDao(getActivity());
		//writerList = poetryDao.getPoemByWid(writerid);
		//writerList = poetryDao.getPoemByWid(1);
		for (int i = 0; i < 10; i++) {
			Poem p = new Poem("1", 1, 1, 1, "���", "��ҹ˼", "ʫ������", "ʫ������");
			writerList.add(p);
		}
		Log.v("writerList", writerList.size() + "");
		
		return writerList;
	}
	
	//��ʼ����ҳ��
	private void initMain() {
		radgp = (RadioGroup)view.findViewById(R.id.topbar_second_radgp);
		
		radgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.topbar_second_rbtnL:
					wenkuListView.setVisibility(View.VISIBLE);
					writerListView.setVisibility(View.GONE);
					break;
					
				case R.id.topbar_second_rbtnR:
					writerListView.setVisibility(View.VISIBLE);
					wenkuListView.setVisibility(View.GONE);
					break;
				}
			}
		});
	}
	
	//��ʼ��search
	private void initSearch() {
		imgSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UIHelper.showSearch(getActivity());
			}
		});
	}
}
