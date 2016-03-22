package com.iz.rootfeeder.adapters;

import java.util.List;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CountryCode;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryPickerAdapter extends ArrayAdapter<CountryCode>{

	private Activity activity;
	private ViewHolder holder;
	
	public CountryPickerAdapter(Activity activity, int resource, List<CountryCode> codeCountryPairs) {
		super(activity, resource, codeCountryPairs);
		this.activity = activity;
	}	
	
	private static class ViewHolder {
		ImageView imageViewFlag;
		TextView textViewCountry;
	}
	

	@Override
	public View getView(final int pos, View convertView, ViewGroup viewGroup) {
		View v = convertView;
		holder = new ViewHolder();
		LayoutInflater vi;
		
		if (v == null) {
			vi = activity.getLayoutInflater();
			v = vi.inflate(R.layout.layout_country_picker, null);

			holder.imageViewFlag = (ImageView) v.findViewById(R.id.imageView_flag);
			holder.textViewCountry = (TextView) v.findViewById(R.id.textView_country);
			
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		CountryCode codeCountryPair = getItem(pos);
		Integer flagResId = Util.getFlag(activity, codeCountryPair.getCode());
		
		if(flagResId !=0){
			holder.imageViewFlag.setImageResource(flagResId);
		}
		else{
			holder.imageViewFlag.setImageResource(R.drawable.none);
		}
		
		holder.textViewCountry.setText(codeCountryPair.getName());
				
		return v;
	}
}