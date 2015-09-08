package com.iz.rootfeeder.adapters;

import java.util.List;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CountryCode;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerCountryAdapter extends ArrayAdapter<CountryCode> {
	private List<CountryCode> objects;
	private Activity context;
	public CustomSpinnerCountryAdapter(Activity context, int txtViewResourceId, List<CountryCode> objects) {
		super(context, txtViewResourceId, objects);
		this.context = context;
		this.objects=objects;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	private static class ViewHolder{
		ImageView countryFlag;
		TextView countryName;
	}
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		
		LayoutInflater inflater = context.getLayoutInflater(); 
		ViewHolder holder = null;
		View mySpinner = convertView;
		if(mySpinner == null){
			holder = new ViewHolder();
			mySpinner = inflater.inflate(R.layout.custom_country_item, parent, false);
			holder.countryName = (TextView) mySpinner.findViewById(R.id.textView_country);
			holder.countryFlag = (ImageView) mySpinner.findViewById(R.id.imageView_flag);
			mySpinner.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Log.v("getCustomView", String.valueOf(objects.size()));
		String countryName = objects.get(position).getName();
		String countryIso= objects.get(position).getCode();
		int isoFlag = Util.getFlag(context, countryIso);
		holder.countryName.setText(countryName);
		holder.countryFlag.setImageResource(isoFlag);
		return mySpinner;
	}
}
