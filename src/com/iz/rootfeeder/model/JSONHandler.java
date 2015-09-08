package com.iz.rootfeeder.model;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iz.rootfeeder.model.http.JSONResponseTask;

import android.util.Log;

public class JSONHandler {
	private HashMap<Float, Integer> countryMap;
	private String queryURL;
	public JSONHandler(String queryURL) {
		this.queryURL = queryURL;
	}
	public void generateJSONResponse() {
		JSONArray result = null;
		try {
			/** Getting the results of the JSONParser */
			result = new JSONResponseTask().execute(queryURL).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			try {
				getDataForSingleCountry(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	private void getDataForSingleCountry(JSONArray jsonResponse) throws JSONException {
		countryMap = new HashMap<Float, Integer>();
		for (int j = 1; j < jsonResponse.length(); j++) {
			JSONArray indicatorResults = (JSONArray) jsonResponse.get(j);
			for (int i = 0; i < indicatorResults.length(); i++) {
				JSONObject rowData = indicatorResults.getJSONObject(i);
				String value = rowData.getString("value").toString().replaceAll("\\s", "");
				float innerValue = 0;
				int year = Integer.valueOf(rowData.getString("date"));
				if (!value.equals("null")) {
					innerValue = Float.parseFloat(value);
				} else {
					innerValue = 1;
				}
				countryMap.put(innerValue, year);
			}
		}
		Log.v("getDataForSingleCountry", "Size: " + countryMap.size());
	}

	public HashMap<Float, Integer> getCountryList() {
		return countryMap;
	}

}
