package com.iz.rootfeeder.fragments;

import java.util.ArrayList;
import java.util.Collections;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.CodeIndicatorPair;
import com.iz.rootfeeder.model.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class IndicatorFragment extends Fragment{
	
	private ListView viewIndicator;
	public static IndicatorFragment InstanceOf(){
		IndicatorFragment fragment = new IndicatorFragment();
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.indicator_fragment, container, false);
		Context context = getActivity().getApplicationContext();
		viewIndicator = (ListView) view.findViewById(R.id.listOfCountries);
		populateListViewIndicators();
		return view;
	}
	
	
	public void populateListViewIndicators(){
		ArrayList<CodeIndicatorPair> indicators = Util.readIndicatorsCSV(getActivity().getApplicationContext());
		ArrayAdapter<CodeIndicatorPair> indicatorsAdapter = new ArrayAdapter<CodeIndicatorPair>(getActivity().getApplicationContext(), R.layout.layout_indicator, indicators);
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
	
	
}
