package com.iz.rootfeeder.adapters;

import com.iz.rootfeeder.fragments.IndicatorFragment;
import com.iz.rootfeeder.fragments.CountryFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private int number_tabs = 2;
	private Fragment fragment;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		int counter = position + 1;
		Log.v("Item Clicked", "Postition: " + counter);
		switch (position) {
		case 0:
			fragment = CountryFragment.instanceOf();
			return fragment;
		case 1:
			fragment = IndicatorFragment.InstanceOf();
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
