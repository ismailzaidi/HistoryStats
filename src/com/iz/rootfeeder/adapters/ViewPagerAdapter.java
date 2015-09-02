package com.iz.rootfeeder.adapters;

import java.util.ArrayList;
import java.util.List;

import com.iz.rootfeeder.fragments.CountryFragment;
import com.iz.rootfeeder.fragments.GraphContainerFragment;
import com.iz.rootfeeder.fragments.IndicatorFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Log.v("Size ", "Size: " + getFragments().size());
		return getFragments().get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getFragments().size();
	}

	public List<Fragment> getFragments() {
		List<Fragment> fragment = new ArrayList<Fragment>();
		 fragment.add(CountryFragment.instanceOf());
		 fragment.add(IndicatorFragment.instanceOf());
		 fragment.add(GraphContainerFragment.instanceOf());
		return fragment;
	}

}
