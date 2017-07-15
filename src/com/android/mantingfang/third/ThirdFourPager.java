package com.android.mantingfang.third;

import java.util.ArrayList;
import java.util.List;

import com.android.mantingfanggsc.CustomListView;
import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdFourPager extends Fragment {
	private View view;
	private CustomListView thirdFourListView;
	private ThirdFourAdapter adapterFour;
	private List<ThirdFourContent> listFour;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.third_pager_four, null);

			initViews();
			return view;
		}

		return view;
	}

	// ≥ı ºªØThirdFour
	private void initViews() {
		thirdFourListView = (CustomListView) view.findViewById(R.id.third_pager_four_listview);
		adapterFour = new ThirdFourAdapter(getActivity(), getData());
		thirdFourListView.setAdapter(adapterFour);
	}

	private List<ThirdFourContent> getData() {
		listFour = new ArrayList<ThirdFourContent>();
		for (int i = 0; i < 10; i++) {
			listFour.add(new ThirdFourContent());
		}

		return listFour;
	}
}
