package com.iz.rootfeeder.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iz.rootfeeder.fragments.PieContainerFragment;
import com.iz.rootfeeder.fragments.charts.BarChartFragment;
import com.iz.rootfeeder.fragments.charts.BubbleChartFragment;
import com.iz.rootfeeder.fragments.charts.LineChartFragment;
import com.iz.rootfeeder.fragments.charts.PieChartFragment;
import com.iz.rootfeeder.fragments.charts.RadarChartFragment;
import com.iz.rootfeeder.fragments.charts.ScatterChartFragment;
import com.iz.rootfeeder.model.JSONHandler;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.http.GlobalJSONReader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class GraphViewPagerAdapter extends FragmentPagerAdapter {
	private String[] graph_fragment_titles = { "Line", "Bar", "Scatter", "Pie", "Rader","Bubble"};
	private int number_tabs = graph_fragment_titles.length;
	private Context context;
	private GlobalJSONReader json_data;
	private ArrayList<HashMap<Float, Integer>> data;
	public GraphViewPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		this.context = context;
		json_data = GlobalJSONReader.getInstance();
		data = json_data.retrieveCountryList(context); 
	}
	public CharSequence getPageTitle(int position) {
		return graph_fragment_titles[position];
	}
	@Override
	public Fragment getItem(int position) {
		return getFragments().get(position);
	}
	private List<Fragment> getFragments() {
		List<Fragment> fragment = new ArrayList<Fragment>();
		 fragment.add(LineChartFragment.instanceOf(data));
		 fragment.add(BarChartFragment.instanceOf(data));
		 fragment.add(ScatterChartFragment.instanceOf(data));
		 fragment.add(PieContainerFragment.instanceOf());
		 fragment.add(RadarChartFragment.instanceOf(data));
		 fragment.add(BubbleChartFragment.instanceOf(data));
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
