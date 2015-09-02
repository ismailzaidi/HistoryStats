package com.iz.rootfeeder.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iz.rootfeeder.model.beans.CodeCountryPair;
import com.iz.rootfeeder.model.beans.CodeIndicatorPair;
import com.iz.rootfeeder.model.http.JSONResponseTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;

public class Util {

	private final static String countryQueryURL = "http://api.worldbank.org/country?per_page=300&format=json";
	private String countryCodeTag = "com.iz.rootfeeder.codecountry";
	private String indicatorCodeTag = "com.iz.rootfeeder.indicator";
	private Context context;

	public Util(Context context) {
		this.context = context;
	}

	public static String readData(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		StringBuilder content = new StringBuilder();
		/** */
		try {
			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				InputStream in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String readLine = reader.readLine();
				while (readLine != null) {
					content.append(readLine);
					readLine = reader.readLine();
				}
			} else {
				Log.w("DATA RETRIEVAL", "Unable to read data.  HTTP response code = " + responseCode);
				content = null;
			}
		} catch (ClientProtocolException e) {
			Log.e("readData", "ClientProtocolException:\n" + e.getMessage());
		} catch (IOException e) {
			Log.e("readData", e.getMessage());
		}

		if (content == null) {
			return (null);
		} else {
			return (content.toString());
		}
	}

	public static int getFlag(Context context, String countryCode) {

		return context.getResources().getIdentifier(countryCode.toLowerCase(Locale.getDefault()), "drawable",
				context.getPackageName());
	}

	public static ArrayList<CodeIndicatorPair> readIndicatorsCSV(Context context) {
		InputStream is = null;
		try {
			is = context.getAssets().open("indicators.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		CSVReader reader;
		ArrayList<CodeIndicatorPair> indicators = new ArrayList<CodeIndicatorPair>();

		try {
			reader = new CSVReader(new InputStreamReader(is));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				indicators.add(new CodeIndicatorPair(nextLine[0], nextLine[1], nextLine[2]));
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indicators;
	}

	public static String getJSONFromAssets(Context context) {
		String fileData = "";
		try {
			InputStream inputStream = context.getAssets().open("country_json.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			while ((line = reader.readLine()) != null) {
				fileData += line;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileData;
	}

	public static String formatNumber(String value) {
		DecimalFormat formatNumber = new DecimalFormat("####,###,###,###.##");
		double ConverToDouble = Double.NaN;
		try {
			ConverToDouble = Double.parseDouble(value);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new String(formatNumber.format(ConverToDouble));
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wiFi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wiFi.isConnected();
	}

	public static void noConnectionToastMessage(Context context) {
		Toast.makeText(context, "No data connection, connect to cellular data or WiFi!", Toast.LENGTH_LONG).show();

	}

	public static ArrayList<CodeCountryPair> fetchCountries(Context context) {
		ArrayList<CodeCountryPair> codeCountryPairs = new ArrayList<CodeCountryPair>();
		JSONArray countriesJsonArray = null;
		JSONArray countries = null;

		try {
			String json_country = getJSONFromAssets(context);
			Log.v("JSON", json_country);
			countriesJsonArray = new JSONArray(json_country);

			if (countriesJsonArray != null) {
				countries = countriesJsonArray.getJSONArray(1);

				for (int i = 1; i < countries.length(); i++) {
					JSONObject country = countries.getJSONObject(i);
					String capitalCity = country.getString("capitalCity");

					if (capitalCity.length() > 0) {
						codeCountryPairs
								.add(new CodeCountryPair(country.getString("iso2Code"), country.getString("name")));
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		Collections.sort(codeCountryPairs);

		return codeCountryPairs;
	}

	public String loadSavedPreferencesForCountryPair() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(countryCodeTag, "0");

	}

	public void savePreferencesForCountry(ArrayList<CodeCountryPair> items) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		String countryList = "";
		for(CodeCountryPair list: items){
			countryList += list.getCode() +"," + list.getName() + ";";
		}
		countryList = countryList.substring(0,countryList.length()-1); //Removes the last ";" which isn't required
		editor.putString(countryCodeTag, countryList);
		editor.commit();
	}

	public String loadSavedPreferencesForIndicator() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(indicatorCodeTag, "0");

	}

	public void savePreferencesForIndicator(CodeIndicatorPair splashboolean) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		String data = splashboolean.getCode() + "," + splashboolean.getName();
		editor.putString(indicatorCodeTag, data);
		editor.commit();
	}

	public void setFontForView(ViewGroup viewChildren) {
		Typeface font = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
		View child;
		for (int i = 0; i < viewChildren.getChildCount(); i++) {
			child = viewChildren.getChildAt(i);
			if (child instanceof TextView) {
				((TextView) child).setTypeface(font);
				;
			} else if (child instanceof Button) {
				((Button) child).setTypeface(font);
				;
			} else if (child instanceof EditText) {
				((EditText) child).setTypeface(font);
				;
			} else if (child instanceof ViewGroup) {
				setFontForView((ViewGroup) child);

			}

		}
	}
}
