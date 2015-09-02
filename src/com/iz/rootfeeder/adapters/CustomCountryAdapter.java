package com.iz.rootfeeder.adapters;

import java.util.ArrayList;
import java.util.List;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CodeCountryPair;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCountryAdapter extends BaseAdapter implements Filterable {

	private Activity activity;
	private ViewHolder holder;
	private ArrayList<CodeCountryPair> original_list;
	private ArrayList<CodeCountryPair> filter_list;
	private CountryFilter filter;
	private Util utils;

	public CustomCountryAdapter(Activity activity, ArrayList<CodeCountryPair> original_list) {
		this.activity = activity;
		this.original_list = original_list;
		this.filter_list=original_list;
		utils = new Util(activity.getApplicationContext());
	}

	private static class ViewHolder {
		ImageView imageViewFlag;
		TextView textViewCountry;
	}

	@Override
	public int getCount() {
		return original_list.size();
	}

	@Override
	public Object getItem(int pos) {
		return original_list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup viewGroup) {
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

		CodeCountryPair codeCountryPair = original_list.get(pos);
		Integer flagResId = Util.getFlag(activity, codeCountryPair.getCode());

		if (flagResId != 0) {
			holder.imageViewFlag.setImageResource(flagResId);
		} else {
			holder.imageViewFlag.setImageResource(R.drawable.do_fix);
		}

		holder.textViewCountry.setText(codeCountryPair.getName());
		
		utils.setFontForView((ViewGroup) v);

		return v;
	}

	@Override
	public Filter getFilter() {
		if(filter==null){
			filter= new CountryFilter();
		}
		return filter;
	}

	private class CountryFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			String filterString = constraint.toString().toLowerCase();
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				results.values = filter_list;
				results.count = filter_list.size();
			} else {
				ArrayList<CodeCountryPair> nlist = new ArrayList<CodeCountryPair>();
				for (int i = 0; i < filter_list.size(); i++) {
					String countryName = filter_list.get(i).getName().toLowerCase();
					if (countryName.contains(filterString)) {
						CodeCountryPair element = filter_list.get(i);
						Log.v("CountryFilter Checker", "True: " + countryName);
						nlist.add(element);
					}
				}
				results.values = nlist;
				results.count = nlist.size();

			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			original_list = (ArrayList<CodeCountryPair>) results.values;
			notifyDataSetChanged();
		}

	}
}