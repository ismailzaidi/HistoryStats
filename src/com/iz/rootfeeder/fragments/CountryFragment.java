package com.iz.rootfeeder.fragments;

import java.util.ArrayList;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.adapters.CountryAdapter;
import com.iz.rootfeeder.adapters.CustomSpinnerCountryAdapter;
import com.iz.rootfeeder.model.CodeCountryPair;
import com.iz.rootfeeder.model.CodeIndicatorPair;
import com.iz.rootfeeder.model.Util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CountryFragment extends Fragment implements OnClickListener {

	private String IndicatorTag = "com.iz.fragment.indicatorfragment";
	private CountryAdapter adapter;
	private ListView countryListView;
	private FragmentManager fm;
	private ArrayList<CodeCountryPair> list;
	private ArrayAdapter<CodeIndicatorPair> indicatorAdapter;
	public static CountryFragment instanceOf() {
		CountryFragment fragment = new CountryFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.country_fragment, container, false);
		Context context = getActivity().getApplicationContext();
		fm = getActivity().getSupportFragmentManager();
		list = Util.fetchCountries();
		countryListView = (ListView) view.findViewById(R.id.listOfCountries);
		adapter = new CountryAdapter(getActivity(), list);
		countryListView.setAdapter(adapter);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		IndicatorFragment indicatorFragment = IndicatorFragment.InstanceOf();
		changeFragment(indicatorFragment, IndicatorTag);

	}

	public void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}

}