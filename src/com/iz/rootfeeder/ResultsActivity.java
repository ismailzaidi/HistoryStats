package com.iz.rootfeeder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.beans.CodeCountryPair;
import com.iz.rootfeeder.model.beans.Query;
import com.iz.rootfeeder.model.http.JSONResponseTask;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * This class where the results will be displayed in activity page
 * 
 * @author Team V
 * @version 1.00
 */
public class ResultsActivity extends Activity {
	/**
	 * All the required variables are declared for displaying the data
	 */
	private final int MAX_QUERY_RESULTS = 10000;
	private final int MENU_ITEM_COMPARE = 0;
	private final int MENU_ITEM_GRAPH = 1;

	private String queryURL;

	private ImageView imageViewFlag;
	private TextView textViewCountry, textViewIndicator, textViewCompareCountries, textViewCompareCountriesVs;
	private TableLayout tableLayoutResult;
	private FragmentManager fragmentManager;
	private ArrayList<CodeCountryPair> checkedCodeCouuntryPairs;
	private Query query;
	private SparseArray<ArrayList<TextView>> rowTextViews;
	private Handler handler;
	private String graphData;
	private SparseArray<String> graphDataArray;

	/**
	 * Sample URL
	 * http://api.worldbank.org/countries/br/indicators/SL.UEM.TOTL.ZS?per_page=
	 * 1000&date=1940:2010&format=json
	 * 
	 * JSON For A year
	 * 
	 * [
	 * 
	 * { "page": 1, "pages": 1, "per_page": "1000", "total": 51 },
	 * 
	 * [
	 * 
	 * { "indicator": { "id": "SL.UEM.TOTL.ZS", "value":
	 * "Unemployment, total (% of total labor force) (modeled ILO estimate)" },
	 * "country": { "id": "BR", "value": "Brazil" }, "value":
	 * "7.90000009536743", "decimal": "1", "date": "2010" },
	 * 
	 * 
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_result);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		fragmentManager = getFragmentManager();
		rowTextViews = new SparseArray<ArrayList<TextView>>();
		query = (Query) getIntent().getSerializableExtra("query");
		/** URL used for parsing and fetching data */
		queryURL = "http://api.worldbank.org/countries/" + query.getCodeCountryPair().getCode() + "/indicators/"
				+ query.getCodeIndicatorPair().getCode() + "?per_page=" + MAX_QUERY_RESULTS + "&date="
				+ query.getStartYear() + ":" + query.getFinishYear() + "&format=json";

		handler = new Handler();

		/** JSONArray declared to parse in the JSONResponseTask */
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
		Integer flagResId = Util.getFlag(getApplicationContext(), query.getCodeCountryPair().getCode());
		if (flagResId != 0) {
			/** Setting the flag to be displayed */
			imageViewFlag.setImageResource(flagResId);
		} else {
			/** If not available display no flag */
			imageViewFlag.setImageResource(R.drawable.none);
		}
	}


	public Query getQuery() {
		return query;
	}

	public void setCheckedCodeCouuntryPairs(ArrayList<CodeCountryPair> checkedCodeCouuntryPairs) {
		this.checkedCodeCouuntryPairs = checkedCodeCouuntryPairs;

		String countryIDs = ";";
		String countryNames = "";
		for (CodeCountryPair codeCountryPair : checkedCodeCouuntryPairs) {

			countryIDs = countryIDs + codeCountryPair.getCode() + ";";
			countryNames = countryNames + codeCountryPair.getName() + ", ";
		}
		countryIDs = countryIDs.substring(0, countryIDs.length() - 1);
		queryURL = "http://api.worldbank.org/countries/" + query.getCodeCountryPair().getCode() + countryIDs
				+ "/indicators/" + query.getCodeIndicatorPair().getCode() + "?per_page=" + MAX_QUERY_RESULTS + "&date="
				+ query.getStartYear() + ":" + query.getFinishYear() + "&format=json";
		System.out.println("queryURL: " + queryURL);
		JSONArray result = null;
		try {
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
		;
	}

	public HashMap<String, Integer> getDataForSingleCountry(JSONArray jsonResponse) throws JSONException {
		HashMap<String, Integer> countryMap = new HashMap<String, Integer>();
		for (int j = 1; j < jsonResponse.length(); j++) {
			JSONArray indicatorResults = (JSONArray) jsonResponse.get(j);
			for (int i = 0; i < indicatorResults.length(); i++) {
				JSONObject rowData = indicatorResults.getJSONObject(i);
				String value = rowData.getString("value").toString().replaceAll("\\s", "");
				int year = Integer.valueOf(rowData.getString("date"));
				if (!value.equals("null")) {
					value = Util.formatNumber(value);
				} else {
					value = "0";
				}
				countryMap.put(value, year);

			}
		}
		return countryMap;

	}

	/**
	 * Method returns a BitMap According to the imageFullUrl
	 * 
	 * @param imageFullURL
	 * @return
	 */

	public String getGraphData() {
		return graphData;
	}

	public Bitmap downloadFullFromUrl(String imageFullURL) {
		Bitmap bm = null;
		try {
			URL url = new URL(imageFullURL);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0, baf.toByteArray().length);
		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}
		return bm;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ITEM_COMPARE, 0, "Compare")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(0, MENU_ITEM_GRAPH, 0, "Graph")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case MENU_ITEM_COMPARE:
			Thread compareFragment = new Thread() {
				public void run() {
					if (Util.isNetworkAvailable(getApplicationContext())) {
					} else {
						handler.post(new Runnable() {

							@Override
							public void run() {
								Util.noConnectionToastMessage(getApplicationContext());
							}
						});

					}
				}
			};
			compareFragment.start();
			return true;
		case MENU_ITEM_GRAPH:
			Thread graphFragment = new Thread() {
				public void run() {
					if (Util.isNetworkAvailable(getApplicationContext())) {
						// GraphDialogFragment.newInstance(ResultsActivity.this).show(fragmentManager,
						// null);
					} else {
						handler.post(new Runnable() {

							@Override
							public void run() {
								Util.noConnectionToastMessage(getApplicationContext());
							}
						});

					}
				}
			};
			graphFragment.start();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}