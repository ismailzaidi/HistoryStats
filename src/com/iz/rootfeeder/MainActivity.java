package com.iz.rootfeeder;

import com.iz.rootfeeder.adapters.ViewPagerAdapter;
import com.iz.rootfeeder.fragments.AboutDialogFragment;
import com.iz.rootfeeder.fragments.CountryFragment;
import com.iz.rootfeeder.model.Util;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements OnClickListener {

	private String MAIN_TAG = "com.iz.rootfeeder.mainActivity";
	private ActionBar actionBar;
	private FragmentManager fm;
	private ViewPagerAdapter adapter;
	private RelativeLayout toolbar;
	private Util util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		util = new Util(getApplicationContext());
		toolbar = (RelativeLayout) findViewById(R.id.toolbar);
		util.setFontForView((ViewGroup) toolbar);
		CountryFragment fragment = CountryFragment.instanceOf();
		fm.beginTransaction().replace(R.id.frame_content, fragment, "com.iz.rootfeeder.country")
				.addToBackStack("com.iz.rootfeeder.country").commit();
		setupAboutButton();

	}

	private void setupAboutButton() {
		ImageView imageView = (ImageView) findViewById(R.id.about_icon);
		imageView.setOnClickListener(this);
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
		if (fm.getBackStackEntryCount() > 1) {
			fm.popBackStack();
		} else {
			super.onBackPressed();
		}
		super.onBackPressed();
	}

	public void printStack() {
		FragmentManager fm = getSupportFragmentManager();
		for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
			Log.v("Stack printStack", fm.getBackStackEntryAt(i).getName());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.about_icon) {
			FragmentManager fm = this.getSupportFragmentManager();
			AboutDialogFragment aboutFragment = AboutDialogFragment.instanceOf();
			aboutFragment.show(fm, "dialogFragment");
		}

	}
}