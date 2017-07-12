package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ThirdThreePager extends Fragment {

	private View view;
	private ListView thirdThreeListView;
	private ThirdThreeAdapter adapterThree;
	private List<ThirdOneContent> listThree;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_three, null);

			initViews();

			return view;
		}

		return view;
	}

	// ≥ı ºªØThirdThree
	private void initViews() {
		thirdThreeListView = (ListView) view.findViewById(R.id.third_pager_three_listview);
		adapterThree = new ThirdThreeAdapter(getActivity(), getData());
		thirdThreeListView.setAdapter(adapterThree);
	}

	private List<ThirdOneContent> getData() {
		listThree = new ArrayList<ThirdOneContent>();
		for (int i = 0; i < 10; i++) {
			listThree.add(new ThirdOneContent());
		}

		return listThree;
	}
}
