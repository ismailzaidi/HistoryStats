package com.iz.rootfeeder.model.http;

import org.json.JSONArray;
import org.json.JSONException;

import com.iz.rootfeeder.model.Util;

import android.os.AsyncTask;
/**
 * Uses Async to return JSONArray for executing URL Results
 * @author Team V
 * @version 1.00
 */

public class JSONResponseTask extends AsyncTask<String, Void, JSONArray> {

/**
 * Method doInBackground Allows tasks to be performed during the use of the application
 */
	@Override
	protected JSONArray doInBackground(String... url) {
		/** JSONArrary declaration*/
		JSONArray jsonArray = null;
		try {
			/**JSONArray reads url as an array and fetches information via Util.readData(String) method*/
			jsonArray = new JSONArray(Util.readData(url[0]));
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		/**Returning JSONArray*/
		return jsonArray;
	}
	
}
