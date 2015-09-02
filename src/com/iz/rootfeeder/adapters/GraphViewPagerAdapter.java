package com.iz.rootfeeder.adapters;

import com.iz.rootfeeder.fragments.LineChartFragment;
import com.iz.rootfeeder.fragments.IndicatorFragment;
import com.iz.rootfeeder.model.Util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class GraphViewPagerAdapter extends FragmentPagerAdapter {
	private String[] graph_fragment_titles = { "Line", "Bar", "Scatter", "Pie", "Rader", "Raw Data" };
	private int number_tabs = graph_fragment_titles.length;
	private Fragment fragment;
	private Context context;
	private Util util;

	public GraphViewPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
		util = new Util(context);
	}

	public CharSequence getPageTitle(int position) {
		return graph_fragment_titles[position];
	}

	@Override
	public Fragment getItem(int position) {
		int counter = position + 1;
		String countryDetails = util.loadSavedPreferencesForCountryPair();
		String indicatorDetails = util.loadSavedPreferencesForIndicator();
		Log.v("Item Clicked", "Postition: " + counter);
		switch (position) {
		case 0:
			fragment = LineChartFragment.instanceOf(countryDetails, indicatorDetails);
			return fragment;
		case 1:
			fragment = IndicatorFragment.instanceOf();
			return fragment;
		}
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return number_tabs;
	}

}
