package com.iz.rootfeeder.views;

import com.iz.rootfeeder.MainActivity;
import com.iz.rootfeeder.model.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
/**
 * This class runs a splash screen which loads up for a few seconds then launches the Main Activity
 * @author Team V
 *@version 1.00
 */
public class Splash extends Activity {
	/** The splash screen will run for 2 seconds, Integer declared as final*/
	private final int SLEEP_TIME = 2;
	private Handler handler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		/**Removing action bar*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**Setting the window of the splash screen to fill the entire window */
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/**Setting the Layout to the contentview*/
		
		/**Creating a handler to post UI updates*/
		handler = new Handler();

	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		/**Start class QueryActivityLauncher which has a thread*/
		new QueryActivityLauncher().start();		
	}

	class QueryActivityLauncher extends Thread {
		/**Run method*/
		public void run() {
			try {
				/**Setting the sleep time to be 2000 milliseconds = 2 seconds*/
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(Util.isNetworkAvailable(getApplicationContext())){
					/**After completing an intent will be declared and start the main Activity  */
					Intent myIntent = new Intent(Splash.this, MainActivity.class);
					startActivity(myIntent);
					/**Finish the thread by declaring Finish()*/
					finish();
				}
				else{
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
					}
					finally{
						finish();
					}
				}
				
			}
			
		}

	}

}
