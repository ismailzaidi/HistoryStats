package com.iz.rootfeeder.fragments;

import java.util.ArrayList;

import com.iz.rootfeeder.QueryResultActivity;
import com.iz.rootfeeder.R;
import com.iz.rootfeeder.adapters.CountryPickerAdapter;
import com.iz.rootfeeder.model.CodeCountryPair;
import com.iz.rootfeeder.model.Util;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class CountryPickerDialogFragment extends DialogFragment{

	private Handler handler;
	
	public static DialogFragment newInstance(Context context){
		DialogFragment countryPickerDialogFragment = new CountryPickerDialogFragment();
		return countryPickerDialogFragment;		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_country_picker, container);
		getDialog().setTitle("Select Countries");		
		
		handler = new Handler();
		
		final ListView listViewCountryPicker = (ListView) view.findViewById(R.id.listView_country_picker);
		listViewCountryPicker.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		listViewCountryPicker.setAdapter(new CountryPickerAdapter(getActivity(), R.layout.layout_country_picker, Util.fetchCountries()));
		
		Button buttonCompare = (Button) view.findViewById(R.id.button_compare);
		buttonCompare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Thread compareQuery = new Thread() {
					public void run() {
						if (Util.isNetworkAvailable(getActivity())) {
							final QueryResultActivity queryResultActivity = (QueryResultActivity) getActivity();				
							SparseBooleanArray checkedCountries = listViewCountryPicker.getCheckedItemPositions();
							final ArrayList<CodeCountryPair> checkedCodeCouuntryPairs = new ArrayList<CodeCountryPair>();
							
							for (int i = 0; i < listViewCountryPicker.getAdapter().getCount(); i++){
								if(checkedCountries.get(i)){
									checkedCodeCouuntryPairs.add((CodeCountryPair) listViewCountryPicker.getAdapter().getItem(i));
								}
							}							
							
							handler.post(new Runnable() {								
								@Override
								public void run() {
									queryResultActivity.setCheckedCodeCouuntryPairs(checkedCodeCouuntryPairs);
									dismiss();						
								}
							});
						} else {
							handler.post(new Runnable() {								
								@Override
								public void run() {
									Util.noConnectionToastMessage(getActivity());									
								}
							});
							
						}
					}
				};
				compareQuery.start();	
				
				
			}
		});
		return view;
	}
	
}
