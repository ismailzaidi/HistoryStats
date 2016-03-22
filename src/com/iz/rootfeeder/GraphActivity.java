package com.iz.rootfeeder;

import java.util.ArrayList;
import java.util.Collections;

import com.iz.rootfeeder.adapters.CustomGridAdapter;
import com.iz.rootfeeder.adapters.DisableSwipeViewPager;
import com.iz.rootfeeder.adapters.GraphViewPagerAdapter;
import com.iz.rootfeeder.fragments.AboutDialogFragment;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CountryCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GraphActivity extends AppCompatActivity implements OnClickListener{

	private static String IndicatorTag = "com.iz.fragment.graphfragment";
	private FragmentManager fm;
	private DisableSwipeViewPager pager;
	private TabLayout tabLayout;
	private GraphViewPagerAdapter pagerAdapter;
	private GridView countryGridView;
	private CustomGridAdapter adapter;
	private ImageView changeView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_fragment);
		// this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		pager = (DisableSwipeViewPager) findViewById(R.id.graphpager);
		tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
		countryGridView = (GridView) findViewById(R.id.gridCountry);
		changeView = (ImageView) findViewById(R.id.changeCountry);
		changeView.setOnClickListener(this);
		adapter = new CustomGridAdapter(this, getGridItems());
		countryGridView.setAdapter(adapter);
		
		fm = this.getSupportFragmentManager();
		pagerAdapter = new GraphViewPagerAdapter(fm,getApplicationContext());
		pager.setAdapter(pagerAdapter);
		tabLayout.setupWithViewPager(pager);
		setupAboutButton();
		// setupbackButton();
	}

	public void setupbackButton() {
		LinearLayout actionBackLayout = (LinearLayout) this.findViewById(R.id.icon_layout);
		actionBackLayout.setVisibility(View.VISIBLE);
		actionBackLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(1);
			}
		});
	}
	public ArrayList<CountryCode> getGridItems(){
		ArrayList<CountryCode> list = new ArrayList<CountryCode>();
		Util utils = new Util(this);
		String countryDetails = utils.loadSavedPreferencesForCountryPair();
		String[] countryContent = countryDetails.split(";");
		int map_size = countryContent.length;
		for(int i=0;i<map_size;i++){
			String countryCode = countryContent[i].split(",")[0];
			String countryName = countryContent[i].split(",")[1];
			list.add(new CountryCode(countryCode, countryName));
		}
		Collections.sort(list);
		return list;
		
	}
	public void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}
	private void setupAboutButton(){
		ImageView imageView = (ImageView) findViewById(R.id.about_icon);
		imageView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.changeCountry){
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
		}
		if(v.getId()==R.id.about_icon){
			FragmentManager fm = this.getSupportFragmentManager();
			AboutDialogFragment aboutFragment = AboutDialogFragment.instanceOf();
			aboutFragment.show(fm, "dialogFragment");
		}
	}
}