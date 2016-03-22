package com.iz.rootfeeder;

import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
public class Splash extends Activity {
	private final int SLEEP_TIME = 2;
	private Handler handler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Util utils = new Util(getApplicationContext());
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.splashLayout);
		utils.setFontForView(splashLayout);
		handler = new Handler();

	}
	
	@Override
	public void onResume() {
		super.onResume();
		new MainActivityLauncher().start();
	}

	class MainActivityLauncher extends Thread {
		public void run() {
			try {
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (Util.isNetworkAvailable(getApplicationContext())) {
					Intent myIntent = new Intent(Splash.this, MainActivity.class);
					startActivity(myIntent);
					finish();
				} else {
					handler.post(new Runnable() {

						@Override
						public void run() {
							Util.noConnectionToastMessage(getApplicationContext());
						}
					});
					try {
						Thread.sleep(SLEEP_TIME * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						finish();
					}
				}

			}

		}

	}

}
