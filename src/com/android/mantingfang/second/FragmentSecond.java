package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.android.mantingfang.bean.DynastyDao;
import com.android.mantingfang.bean.KindDao;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.bean.WriterDao;
import com.android.mantingfang.second.SideBar.OnTouchingLetterChangedListener;
import com.android.mantingfanggsc.CharacterParser;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.NetWork;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSecond extends Fragment {

	private View view;

	// 文库
	private CustomListView wenkuListView;
	private SecondWenkuListViewAdapter wenkuAdapter;
	private List<KindContent> wenkuList;

	// 诗人
	private CustomListView writerListView;
	private SecondWriterListViewAdapter writerAdapter;
	private List<Writer> writers;
	private FrameLayout frame;
	private WriterDao writerDao;
	private DynastyDao dynastyDao;

	private SideBar sideBar;
	private TextView dialog;

	/**
	 * 
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 
	 */
	private PinyinComparator pinyinComparator;

	// 搜索
	private ImageView imgSearch;

	// 选择按钮
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
		imgSearch = (ImageView) view.findViewById(R.id.topbar_second_search);
	}

	// 初始化文库
	private void initWenku() {
		wenkuListView = (CustomListView) view.findViewById(R.id.frag_second_wenku_list);
		getWenkuDatas();
	}

	private void getWenkuDataFromSql() {
		wenkuList = new ArrayList<KindContent>();

		KindDao kindDao = new KindDao(getActivity());
		if (kindDao.findAllLabelByKind() != null) {
			wenkuList = kindDao.findAllLabelByKind();
		}
	}

	private void getWenkuDatas() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				boolean isNetwork = NetWork.isNetworkAvailable(getActivity());
				getWenkuDataFromSql();
				if (isNetwork) {
					return "有网络";
				} else {
					return null;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				//Toast.makeText(getActivity(), result + " --", Toast.LENGTH_SHORT).show();
				wenkuAdapter = new SecondWenkuListViewAdapter(getActivity(), wenkuList);
				wenkuListView.setAdapter(wenkuAdapter);
			}

		};

		task.execute();
	}

	// 初始化诗人页
	private void initWriter() {
		writerListView = (CustomListView) view.findViewById(R.id.frag_second_writer_list);
		frame = (FrameLayout) view.findViewById(R.id.frame_list_writer);

		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		dynastyDao = new DynastyDao(getActivity());

		sideBar = (SideBar) view.findViewById(R.id.sidrbar);
		dialog = (TextView) view.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {

				int position = writerAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					writerListView.setSelection(position);
				}

			}
		});

		writerDao = new WriterDao(getActivity());
		writers = writerDao.getAllWriter();
		Log.v("writer", writers.isEmpty() + " ");
		String[] writerss = new String[writers.size()];
		for (int i = 0; i < writers.size(); i++) {
			writerss[i] = writers.get(i).getWriterName();
		}

		SourceDateList = filledData(writerss, writers);

		Collections.sort(SourceDateList, pinyinComparator);

		writerAdapter = new SecondWriterListViewAdapter(getActivity(), SourceDateList);
		writerListView.setAdapter(writerAdapter);
		writerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UIHelper.showWriterDetail(getActivity(), 1);
			}
		});
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private List<SortModel> filledData(String[] date, List<Writer> writer) {

		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			sortModel.setWriterId(writer.get(i).getWriterId());
			sortModel.setDynastyName(dynastyDao.getDynastyById(writer.get(i).getDynastyId()).getDynastyName());
			System.out.println("ming--" + sortModel.getName() + "----" + date[i]);

			String pinyin = characterParser.getSelling(date[i]);

			String sortString = pinyin.substring(0, 1).toUpperCase();

			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}

		return mSortList;

	}

	// 初始化界面切换按钮
	private void initMain() {
		radgp = (RadioGroup) view.findViewById(R.id.topbar_second_radgp);

		radgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.topbar_second_rbtnL:
					wenkuListView.setVisibility(View.VISIBLE);
					frame.setVisibility(View.GONE);
					break;

				case R.id.topbar_second_rbtnR:
					frame.setVisibility(View.VISIBLE);
					wenkuListView.setVisibility(View.GONE);
					break;
				}
			}
		});
	}

	// 跳转search
	private void initSearch() {
		imgSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UIHelper.showSearch(getActivity());
			}
		});
	}
}
