package com.iz.rootfeeder.fragments;

import java.util.ArrayList;

import com.iz.rootfeeder.MainActivity;
import com.iz.rootfeeder.R;
import com.iz.rootfeeder.adapters.CustomCountryAdapter;
import com.iz.rootfeeder.adapters.DisableSwipeViewPager;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CodeCountryPair;

import android.content.Context;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CountryFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private String IndicatorTag = "com.iz.fragment.indicatorfragment";
	private CustomCountryAdapter mAdapter;
	private ListView countryListView;
	private EditText searchFilterEditText;
	private FragmentManager fm;
	private ArrayList<CodeCountryPair> list;
	private Util utils;
	private FrameLayout frame_layout;
	private LinearLayout actionBackLayout;
	
	public static CountryFragment instanceOf() {
		CountryFragment fragment = new CountryFragment();
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.country_fragment, container, false);
		Context context = getActivity().getApplicationContext();
		fm = getActivity().getSupportFragmentManager();
		utils = new Util(context);
		list = Util.fetchCountries(context);
		searchFilterEditText = (EditText) view.findViewById(R.id.filterCountries);
		countryListView = (ListView) view.findViewById(R.id.listOfCountries);
		frame_layout = (FrameLayout) view.findViewById(R.id.confirmButton);
		mAdapter = new CustomCountryAdapter(getActivity(), list);
		searchFilterEditText.addTextChangedListener(textFilter);
		countryListView.setAdapter(mAdapter);
		utils.setFontForView((ViewGroup) view);
		frame_layout.setOnClickListener(this);
		countryListView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			return true;
		}
	}
	private ArrayList<CodeCountryPair> getSelectedItems(){
		ArrayList<CodeCountryPair> codeCountrylist = new ArrayList<CodeCountryPair>();
		SparseBooleanArray checkedItems = countryListView.getCheckedItemPositions();
		int checkedItemSize = checkedItems.size();
		for(int i=0;i<checkedItemSize;i++){
			int position = checkedItems.keyAt(i);
			if(checkedItems.valueAt(i)){
				codeCountrylist.add((CodeCountryPair)mAdapter.getItem(position));
			}
		}
		return codeCountrylist;
	}
	@Override
	public void onClick(View v) {
		IndicatorFragment indicatorFragment = IndicatorFragment.instanceOf();
		changeFragment(indicatorFragment, IndicatorTag);
		if(v.getId()==R.id.confirmButton){
			ArrayList<CodeCountryPair> list = getSelectedItems();
			utils.savePreferencesForCountry(list);
			IndicatorFragment fragment = IndicatorFragment.instanceOf();
			String tag = "com.iz.rootfeeder.indicator";
			changeFragment(fragment, tag);
			Log.v("onClick", "Size: " + list.size());
		}

	}

	private void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.replace(R.id.frame_content, fragment,fragmentTag);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
		// TODO Auto-generated method stub
		frame_layout.setVisibility(View.VISIBLE);
		
	}
	private TextWatcher textFilter = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.v("filterTextwatcher", s.toString());
			mAdapter.getFilter().filter(s.toString());
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	

}