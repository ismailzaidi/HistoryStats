package com.iz.rootfeeder;

import com.iz.rootfeeder.adapters.DisableSwipeViewPager;
import com.iz.rootfeeder.adapters.ViewPagerAdapter;
import com.iz.rootfeeder.fragments.CountryFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity {
	
	private String MainMenuTag = "com.iz.MainMenuFragment";
	private ActionBar actionBar;
	private FragmentManager fm;
	private DisableSwipeViewPager viewPager;
	private ViewPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		viewPager = (DisableSwipeViewPager) findViewById(R.id.viewpager);
		actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		CountryFragment mainMenuFragment = CountryFragment.instanceOf();
		adapter = new ViewPagerAdapter(fm);
		viewPager.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = this.getMenuInflater();
		 inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		printStack();
		if (fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
		} else {
			super.onBackPressed();
		}
		super.onBackPressed();
	}
	
	public void printStack(){
		FragmentManager fm = getSupportFragmentManager();
		for(int i=0;i<fm.getBackStackEntryCount();i++){
			Log.v("Stack printStack", fm.getBackStackEntryAt(i).getName());
		}
	}
	
}