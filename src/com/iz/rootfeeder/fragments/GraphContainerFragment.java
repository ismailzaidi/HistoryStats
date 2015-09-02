package com.iz.rootfeeder.fragments;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.adapters.DisableSwipeViewPager;
import com.iz.rootfeeder.adapters.GraphViewPagerAdapter;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GraphContainerFragment extends Fragment {

	private static String IndicatorTag = "com.iz.fragment.graphfragment";
	private FragmentManager fm;
	private DisableSwipeViewPager pager;
	private TabLayout tabLayout;
	private GraphViewPagerAdapter pagerAdapter;
	private GridView countryGridView;

	public static GraphContainerFragment instanceOf() {
		GraphContainerFragment fragment = new GraphContainerFragment();
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
		View view = inflater.inflate(R.layout.graph_fragment, container, false);
		// this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		pager = (DisableSwipeViewPager) view.findViewById(R.id.graphpager);
		tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
		fm = this.getChildFragmentManager();
		pagerAdapter = new GraphViewPagerAdapter(fm,getActivity().getApplicationContext());
		pager.setAdapter(pagerAdapter);
		tabLayout.setupWithViewPager(pager);
		setupbackButton();
		return view;
	}
	public void setupbackButton(){
		LinearLayout actionBackLayout = (LinearLayout) getActivity().findViewById(R.id.icon_layout);
		actionBackLayout.setVisibility(View.VISIBLE);
		actionBackLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(1);
			}
		});
	}
	public void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}
}