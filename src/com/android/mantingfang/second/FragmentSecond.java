package com.android.mantingfang.second;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;

import com.android.mantingfang.bean.KindDao;
import com.android.mantingfang.bean.StringUtils;
import com.android.mantingfang.bean.TopicList;
import com.android.mantingfang.bean.Writer;
import com.android.mantingfang.second.SideBar.OnTouchingLetterChangedListener;
import com.android.mantingfanggsc.CharacterParser;
import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.MyClient;
import com.android.mantingfanggsc.R;
import com.android.mantingfanggsc.UIHelper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	// private WriterDao writerDao;

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
		getWenkuDataFromSql();
		wenkuAdapter = new SecondWenkuListViewAdapter(getActivity(), wenkuList);
		wenkuListView.setAdapter(wenkuAdapter);
	}

	private void getWenkuDataFromSql() {
		wenkuList = new ArrayList<KindContent>();

		KindDao kindDao = new KindDao(getActivity());
		if (kindDao.findAllLabelByKind() != null) {
			wenkuList = kindDao.findAllLabelByKind();
		}
	}

	// 初始化诗人页
	private void initWriter() {
		writerListView = (CustomListView) view.findViewById(R.id.frag_second_writer_list);
		frame = (FrameLayout) view.findViewById(R.id.frame_list_writer);

		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) view.findViewById(R.id.sidrbar);
		dialog = (TextView) view.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		getWriterDatas();

	}

	private void getWriterDatas() {
		AsyncTask<String, Long, String> task = new AsyncTask<String, Long, String>() {

			@Override
			protected String doInBackground(String... params) {
				return MyClient.getInstance().Http_postWriters("1");
			}

			@Override
			protected void onPostExecute(String result) {

				try {
					if (result != null && !result.equals("")) {
						writers = TopicList.parseAllWriters(StringUtils.toJSONArray(result)).getAllWritersList();
						String[] writerss = new String[writers.size()];
						for (int i = 0; i < writers.size(); i++) {
							writerss[i] = writers.get(i).getWriterName();
						}
						SourceDateList = filledData(writerss, writers);
						Collections.sort(SourceDateList, pinyinComparator);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				writerAdapter = new SecondWriterListViewAdapter(getActivity(), SourceDateList);
				writerListView.setAdapter(writerAdapter);
				writerListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						SortModel model = SourceDateList.get(position - 1);
						Writer w = new Writer(model.getwId(), model.getName(), model.getDynastyName(),
								model.getWriter_career());
						UIHelper.showWriterDetail(getActivity(), w, true);

					}
				});

				sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

					@Override
					public void onTouchingLetterChanged(String s) {

						int position = writerAdapter.getPositionForSection(s.charAt(0));
						if (position != -1) {
							writerListView.setSelection(position);
						}

					}
				});
			}

		};

		task.execute();
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
			sortModel.setDynastyName(writer.get(i).getDynastyName());
			sortModel.setWriter_career(writer.get(i).getWriterCareer());
			sortModel.setwId(writer.get(i).getStringWriterId());

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
