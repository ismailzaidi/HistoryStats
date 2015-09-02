package com.iz.rootfeeder.fragments;

import java.util.ArrayList;
import java.util.Collections;

import com.iz.rootfeeder.MainActivity;
import com.iz.rootfeeder.R;
import com.iz.rootfeeder.adapters.DisableSwipeViewPager;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CodeCountryPair;
import com.iz.rootfeeder.model.beans.CodeIndicatorPair;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class IndicatorFragment extends Fragment implements OnItemClickListener,OnClickListener{
	
	private ListView viewIndicator;
	private Util utils;
	private ArrayAdapter<CodeIndicatorPair> indicatorsAdapter;
	private FragmentManager fm;
	public static IndicatorFragment instanceOf(){
		IndicatorFragment fragment = new IndicatorFragment();
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setupbackButton();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.indicator_fragment, container, false);
		Context context = getActivity().getApplicationContext();
		utils = new Util(context);
		fm = this.getFragmentManager();
		viewIndicator = (ListView) view.findViewById(R.id.listOfCountries);
		viewIndicator.setOnItemClickListener(this);
		populateListViewIndicators();
		utils.setFontForView((ViewGroup) view);
		
		return view;
	}
	
	
	public void populateListViewIndicators(){
		ArrayList<CodeIndicatorPair> indicators = Util.readIndicatorsCSV(getActivity().getApplicationContext());
		indicatorsAdapter = new ArrayAdapter<CodeIndicatorPair>(getActivity().getBaseContext(), R.layout.custom_indicator_item, indicators);
		Collections.sort(indicators);
		viewIndicator.setAdapter(indicatorsAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	@Override
	public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
		// TODO Auto-generated method stub
		CodeIndicatorPair indicatorpair = indicatorsAdapter.getItem(position);
		utils.savePreferencesForIndicator(indicatorpair);
		GraphContainerFragment fragment = GraphContainerFragment.instanceOf();
		String tag = "com.iz.rootfeeder.graph";
		changeFragment(fragment,tag);
	}
	private void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.replace(R.id.frame_content, fragment,fragmentTag);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}
	@Override
	public void onClick(View v) {
		
	}
//	public void setupbackButton(){
//		LinearLayout actionBackLayout = (LinearLayout) getActivity().findViewById(R.id.icon_layout);
//		actionBackLayout.setVisibility(View.VISIBLE);
//		actionBackLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				fm.popBackStack();
//			}
//		});
//	}
	
}
