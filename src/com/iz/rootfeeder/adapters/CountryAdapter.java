package com.iz.rootfeeder.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.CodeCountryPair;
import com.iz.rootfeeder.model.Util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class CountryAdapter extends BaseAdapter implements SectionIndexer{

	private Activity activity;
	private ViewHolder holder;
	private ArrayList<CodeCountryPair> codeCountryPairs;
	private HashMap<String, Integer> alphaIndexer;
    private String[] sections;
    
	public CountryAdapter(Activity activity, ArrayList<CodeCountryPair> codeCountryPairs){
		this.activity = activity;
		this.codeCountryPairs = codeCountryPairs;
		
		alphaIndexer = new HashMap<String, Integer>();
		int size = codeCountryPairs.size();
		
		for (int x = 0; x < size; x++) {
			String s = codeCountryPairs.get(x).getName().substring(0, 1);
			s = s.toUpperCase(Locale.getDefault());
			if (!alphaIndexer.containsKey(s)){
				alphaIndexer.put(s, x);
			}				
		}

		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sections = sectionList.toArray(sections);
	}
	
	private static class ViewHolder {
		ImageView imageViewFlag;
		TextView textViewCountry;
	}
	
	@Override
	public int getCount() {
		return codeCountryPairs.size();
	}

	@Override
	public Object getItem(int pos) {
		return codeCountryPairs.get(pos);
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
			v = vi.inflate(R.layout.layout_country, null);

			holder.imageViewFlag = (ImageView) v.findViewById(R.id.imageView_flag);
			holder.textViewCountry = (TextView) v.findViewById(R.id.textView_country);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		CodeCountryPair codeCountryPair = codeCountryPairs.get(pos);
		Integer flagResId = Util.getFlag(activity, codeCountryPair.getCode());
		
		if(flagResId !=0){
			holder.imageViewFlag.setImageResource(flagResId);
		}
		else{
			//Temp fix for Dominican Republic, Android will not allow an image called do.png
			holder.imageViewFlag.setImageResource(R.drawable.do_fix);
		}
		
		
		holder.textViewCountry.setText(codeCountryPair.getName());

		
		return v;
	}

	@Override
	public int getPositionForSection(int section) {
		return alphaIndexer.get(sections[section]);
	}

	@Override
	public int getSectionForPosition(int arg0) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}
}