package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfang.model.Poem;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class FragmentSecond extends Fragment {

	private View view;
	
	//�Ŀ�
	private ListView wenkuListView;
	private SecondWenkuListViewAdapter wenkuAdapter;
	private List<KindContent> wenkuList;
	
	private String[] singleName = {"ʫ��ȫ��", "���ǽ�ѡ", "���¾�", "��վ�", "��ʫʮ����", "��ʫ������", "�δ�������", "�����ӵ�ʫ", "�ָ�ʫ��", "����"};
	private int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,};
	
	
	//ʫ��
	private ListView writerListView;
	private SecondWriterListViewAdapter writerAdapter;
	private List<Poem> writerList;
	
	
	//������
	private RadioGroup radgp;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frag_second_pager, null);
			
			initWriter();
			initWenku();
			
			initMain();
			
			return view;
		}
		
		return view;
	}
	
	
	//��ʼ���Ŀ����
	private void initWenku() {
		wenkuListView = (ListView)view.findViewById(R.id.frag_second_wenku_list);
		wenkuAdapter = new SecondWenkuListViewAdapter(getActivity(), getWenkuData());
		wenkuListView.setAdapter(wenkuAdapter);
	}
	
	private List<KindContent> getWenkuData() {
		wenkuList = new ArrayList<KindContent>();
		for (int i = 0; i < 5; i++) {
			KindContent content = new KindContent("kind " + i, singleName, images);
			wenkuList.add(content);
		}
		
		return wenkuList;
	}
	
	//��ʼ��ʫ�˽���
	private void initWriter() {
		writerListView = (ListView)view.findViewById(R.id.frag_second_writer_list);
		writerAdapter = new SecondWriterListViewAdapter(getActivity(), getWriterData());
		writerListView.setAdapter(writerAdapter);
	}
	
	private List<Poem> getWriterData() {
		writerList = new ArrayList<Poem>();
		for (int i = 0; i < 15; i++) {
			Poem poem = new Poem(i, i, i, i, "���", "fuck", "fuck");
			writerList.add(poem);
		}
		
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
}