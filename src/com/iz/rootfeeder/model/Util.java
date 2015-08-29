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

import com.iz.rootfeeder.model.http.JSONResponseTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;
/**
 * The purpose of this class is to fetch all the information from Http with additional function to aide the application
 * @author Team V
 * @version 1.00
 */
public class Util {
	
	private final static String countryQueryURL = "http://api.worldbank.org/country?per_page=300&format=json";
	
	/**
	 * This method will fetch the URL and return a String which be the HTML contents
	 * The Parameter will a URL parsned in via the Async class during the background
	 * @param url
	 * @return
	 */
	public static String readData(String url){ 
		/**Calling the HTTP client */
		HttpClient client = new DefaultHttpClient();
		/**Initialising HTTP get to accept the URL as a parameter*/
		HttpGet get = new HttpGet(url);
		/**Create a string content for fetching all details in the html*/
		StringBuilder content = new StringBuilder();
		/** */
		try {
			/**Retrieving a response to aide in http retrieval */
			HttpResponse response = client.execute(get);
			/**Fetching the status of the response*/
			int responseCode = response.getStatusLine().getStatusCode();
			/**Making an if statement to check time*/
			if (responseCode == 200) {
				/**Retrieving the data*/
				InputStream in = response.getEntity().getContent();
				/**Create a buffered reader to read the input stream*/
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				/**Read each line of the html file*/
				String readLine = reader.readLine();
				/**Looping through the content*/
				while (readLine != null) {
					/**Using the String builder to append*/
					content.append(readLine);
					/**Initialising the readline to the next line*/
					readLine = reader.readLine();
				}
			} else {
				/**If response was too slow, an exception will be thrown*/
				Log.w("DATA RETRIEVAL",
						"Unable to read data.  HTTP response code = "
								+ responseCode);
				/**Making the content of the String builder null*/
				content = null;
			}
		} catch (ClientProtocolException e) {
			Log.e("readData", "ClientProtocolException:\n" + e.getMessage());
		} catch (IOException e) {
			Log.e("readData", "IOException:\n+e.getMessage()");
		}

		/**Returning null if String builder is empty*/
		if (content == null) {
			return (null);
		} else {
			/**Return the results of the String*/
			return (content.toString());
		}
	}
	/**
	 * This will return the flag that is contained in the drawable folder, it will also get the country code together
	 * Method will return a integer to attach to the ListView
	 * @param context
	 * @param countryCode
	 * @return
	 */
	public static int getFlag(Context context, String countryCode){
		
		return context.getResources().getIdentifier(countryCode.toLowerCase(Locale.getDefault()), "drawable", context.getPackageName());
	}	
	/**
	 * The country code will be fetched from a CSV file, the contents are then loaded into an ArrayList
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<CodeIndicatorPair> readIndicatorsCSV(Context context) {
		/**Initialise the input stream */
		InputStream is = null;
		try {
		/**Reading the file from the assets folder*/
			is = context.getAssets().open("indicators.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/**Declaring the CSVReader*/
		CSVReader reader;		
		/**Delcaring the Arraylist which has a generic type of CodeIndicatorPair Object */
		ArrayList<CodeIndicatorPair> indicators = new ArrayList<CodeIndicatorPair>();
	
		try {
			/**Reading the input stream for the CSVReader*/
			reader = new CSVReader(new InputStreamReader(is));
			/**Declaring an array of type String to locate the position of csv index*/
			String[] nextLine;
			/**Loop through the csv file*/
			while ((nextLine = reader.readNext()) != null) {
				/**Adding the contents of the file to the ArrayList*/
				indicators.add(new CodeIndicatorPair(nextLine[0], nextLine[1], nextLine[2]));
			}
			/**Close InputStreams and CSVReader*/
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**Returning the indicator ArrayList*/
		return indicators;
	}
	/**
	 * This is a method to return the format the String which will be the values returned from the JSON
	 * @param value
	 * @return
	 */
	public static String formatNumber(String value) {
		/**Decimal Format declared with a pattern to aide the values to be displayed properly*/
		DecimalFormat formatNumber = new DecimalFormat("####,###,###,###.##");
		/**double declared with an intial nan value*/
		double ConverToDouble = Double.NaN;
		try {
			/** Convert the string value to double*/
			ConverToDouble = Double.parseDouble(value);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/** Returns the String according to the decimalFormat*/
		return new String(formatNumber.format(ConverToDouble));
	}
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
	
	public static boolean isWiFiConnected(Context context){
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wiFi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wiFi.isConnected();
	}
	
	public static void noConnectionToastMessage(Context context){
		Toast.makeText(context, "No data connection, connect to cellular data or WiFi!", Toast.LENGTH_LONG).show();						

	}
	
	public static ArrayList<CodeCountryPair> fetchCountries(){
		ArrayList<CodeCountryPair> codeCountryPairs = new ArrayList<CodeCountryPair>();
		JSONArray countriesJsonArray = null;
		JSONArray countries = null;	
		
		try {
			countriesJsonArray = new JSONResponseTask().execute(countryQueryURL).get();
		
			countries = countriesJsonArray.getJSONArray(1);			
					
			for (int i = 1; i < countries.length(); i++){				
				JSONObject country = countries.getJSONObject(i);
				String capitalCity = country.getString("capitalCity");
				
				if(capitalCity.length() > 0){
					codeCountryPairs.add(new CodeCountryPair(country.getString("iso2Code"), country.getString("name")));
				}								
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Collections.sort(codeCountryPairs);
		return codeCountryPairs;		
	}

}
