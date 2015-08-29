package com.iz.rootfeeder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iz.rootfeeder.fragments.CountryPickerDialogFragment;
import com.iz.rootfeeder.fragments.GraphDialogFragment;
import com.iz.rootfeeder.model.CodeCountryPair;
import com.iz.rootfeeder.model.Query;
import com.iz.rootfeeder.model.Util;
import com.iz.rootfeeder.model.http.JSONResponseTask;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.TableRow;
import android.widget.TextView;
/**
 * This class where the results will be displayed in activity page
 * @author Team V
 *@version 1.00
 */
public class QueryResultActivity extends Activity {
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**The activity is displayed*/
		setContentView(R.layout.activity_query_result);
		/**Action bar is viewed*/
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/**Fragements declared*/
		fragmentManager = getFragmentManager();
		/**SparseArray declared with ArrayList Genertic type to get rows*/
		rowTextViews = new SparseArray<ArrayList<TextView>>();
		/**Method call back*/
		bindViews();
		/**Getting intent from Query Class*/
		query = (Query) getIntent().getSerializableExtra("query");
		/**URL used for parsing and fetching data*/
		queryURL = "http://api.worldbank.org/countries/"+query.getCodeCountryPair().getCode()+"/indicators/"+query.getCodeIndicatorPair().getCode()+"?per_page="+MAX_QUERY_RESULTS+"&date="+query.getStartYear()+":"+query.getFinishYear()+"&format=json";
		
		handler = new Handler();
		
		/**JSONArray declared to parse in the JSONResponseTask*/
		JSONArray result = null;
		try {
			/**Getting the results of the JSONParser*/
			result = new JSONResponseTask().execute(queryURL).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		finally{
			try {
				/**After data is fetched, everything will tableResultsTable method */
				buildResultsTable(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		/**Putting the country flag and name to be displayed*/
		Integer flagResId = Util.getFlag(getApplicationContext(), query.getCodeCountryPair().getCode());
		/**Validator to make sure the Flag ID does exist*/
		if(flagResId !=0){
			/**Setting the flag to be displayed*/
			imageViewFlag.setImageResource(flagResId);
		}
		else{
			/**If not available display no flag*/
			imageViewFlag.setImageResource(R.drawable.none);
		}
		/**Setting Country view to country name by calling methods from query Object*/
		textViewCountry.setText(query.getCodeCountryPair().getName());
		/**Setting Indicator View to indicator name by calling methods from query object */
		textViewIndicator.setText(query.getCodeIndicatorPair().getName());		
	}
	
	public void bindViews(){	
		imageViewFlag = (ImageView) findViewById(R.id.imageView_flag);textViewCountry = (TextView) findViewById(R.id.textView_country);
		textViewCountry = (TextView) findViewById(R.id.textView_country);
		textViewIndicator = (TextView) findViewById(R.id.textView_indicator);
		tableLayoutResult = (TableLayout) findViewById(R.id.table_layout_result);
		
		textViewCompareCountries = (TextView) findViewById(R.id.textView_compare_countries);
		textViewCompareCountries.setMovementMethod(new ScrollingMovementMethod());
		textViewCompareCountries.setVisibility(View.GONE);
		textViewCompareCountriesVs = (TextView) findViewById(R.id.textView_compare_countries_vs);
		textViewCompareCountriesVs.setVisibility(View.GONE);
	}

	public Query getQuery(){
		return query;
	}
	
	/**Allowing multiple selections for countryPairs, it then queries the URL once all selection is complete*/
	public void setCheckedCodeCouuntryPairs(ArrayList<CodeCountryPair> checkedCodeCouuntryPairs){
		this.checkedCodeCouuntryPairs = checkedCodeCouuntryPairs;
		
		String countryIDs = ";";
		String countryNames = "";
		/**Looping through the list of codeCountryPair, and adding notation to be compatible with JSON Parser*/
		for(CodeCountryPair codeCountryPair : checkedCodeCouuntryPairs){
			
			countryIDs = countryIDs + codeCountryPair.getCode()+";";
			countryNames = countryNames + codeCountryPair.getName()+", ";
		}
		/**Viewing all the countries in the list*/
		textViewCompareCountries.setText(countryNames.subSequence(0, countryNames.length()-2));
		textViewCompareCountries.setVisibility(View.VISIBLE);
		textViewCompareCountriesVs.setVisibility(View.VISIBLE);
		/**Removing a semicolon to disallow the URL from not working*/
		countryIDs = countryIDs.substring(0, countryIDs.length()-1);
		/**URL is built based on the list*/
		queryURL = "http://api.worldbank.org/countries/"+query.getCodeCountryPair().getCode()+countryIDs+"/indicators/"+query.getCodeIndicatorPair().getCode()+"?per_page="+MAX_QUERY_RESULTS+"&date="+query.getStartYear()+":"+query.getFinishYear()+"&format=json";
		System.out.println("queryURL: "+queryURL);
		JSONArray result = null;
		try {
			/**Execute the Query and returning the result from the JSONResponseTask*/
			result = new JSONResponseTask().execute(queryURL).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		finally{
			try {
				/**Repeating the stage of displaying everything in a table*/
				buildResultsTable(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	}
	/**
	 * This method will display all the contents to a table
	 * Paramater being a JSONArray allowing the contents to be broken separately  then added accordingly 
	 * @param jsonResponse
	 * @throws JSONException
	 */
	public void buildResultsTable(JSONArray jsonResponse) throws JSONException{		
		/**Removing the view as this method is used for displaying indvidual countries and multiple*/
		tableLayoutResult.removeAllViews();
		/**Declaring SparseArray of type ArrayList which contains TextViews*/
		rowTextViews = new SparseArray<ArrayList<TextView>>();
		graphData = "";
		graphDataArray = new SparseArray<String>();
		
		/**Looping through the size of the JSONArray*/
		for(int j = 1; j < jsonResponse.length(); j++){
			/**Allowing the contents of the JSONArray to be accessed through the next loop*/
			JSONArray indicatorResults = (JSONArray) jsonResponse.get(j);
			/**Looping through the results of the indicatorResults*/
			for(int i = 0; i < indicatorResults.length(); i++){
				JSONObject rowData = indicatorResults.getJSONObject(i);
				
				System.out.println("Country: "+(rowData.getJSONObject("country")).getString("value"));
				
				/**Adding all the contents of the rowData to TextViews*/
				TextView textViewYear = new TextView(this);
				TextView textViewValue = new TextView(this);		
				textViewYear.setText(rowData.getString("date"));
				textViewYear.setPadding(5, 5, 10, 5); //Temp padding to align columns
				textViewValue.setPadding(5, 5, 10, 5);
				/**Declaring a String to remove all spaces via REGEX to improve the display*/
				String value = rowData.getString("value").toString().replaceAll("\\s","");
				/**Displaying data as long as it isn't null*/
				if(!value.equals("null")){
					/**Formating the values accordingly*/
					textViewValue.setText(Util.formatNumber(rowData.getString("value")));
					/**Building the Graph according to the JSONResult*/
					graphData = graphData + "["+rowData.getString("date")+", "+value+"],";
					
					String yearData = graphDataArray.get(Integer.valueOf(rowData.getString("date")));
					if(yearData != null){
						graphDataArray.put(Integer.valueOf(rowData.getString("date")), yearData+value+", ");
					}
					else{
						graphDataArray.put(Integer.valueOf(rowData.getString("date")), value+", ");
					}
					
				}
				else{
					/** If null then replace it with  - */
					textViewValue.setText("-");
				}
				
				textViewValue.setPadding(5, 5, 5, 5);
				/**Fetching the year from rowData*/
				int year = Integer.parseInt(rowData.getString("date"));
				/**  */
				if(rowTextViews.get(year) == null){
					ArrayList<TextView> textViews = new ArrayList<TextView>();
					textViews.add(textViewYear);
					textViews.add(textViewValue);
					
					rowTextViews.put(year, textViews);
				}
				
				/** */
				else{
					ArrayList<TextView> textViews = rowTextViews.get(year);
					textViews.add(textViewValue);
					rowTextViews.put(year, textViews);
					
				}				
				
			}
		}
		/**Adding table rows*/
		TableRow rowLabels = new TableRow(this);
		/**Declaring TextView*/
		TextView yearTextViewLabel = new TextView(this);
		yearTextViewLabel.setPadding(5, 5, 10, 5);
		/**Setting the font settings*/
		yearTextViewLabel.setTypeface(yearTextViewLabel.getTypeface(), Typeface.BOLD);
		yearTextViewLabel.setText("Year");
		/**Adding row to tableRow*/
		rowLabels.addView(yearTextViewLabel);
		
		/**Same procedure as above but for country*/
		TextView textViewCountryLabel = new TextView(this);
		textViewCountryLabel.setPadding(5, 5, 10, 5);
		textViewCountryLabel.setTypeface(yearTextViewLabel.getTypeface(), Typeface.BOLD);
		textViewCountryLabel.setText(query.getCodeCountryPair().getName());
		rowLabels.addView(textViewCountryLabel);		
		/**Adding all checked countries, and displaying them to TableRow once added*/
		if(checkedCodeCouuntryPairs != null){
			for(CodeCountryPair codeCountryPair : checkedCodeCouuntryPairs){
				textViewCountryLabel = new TextView(this);
				textViewCountryLabel.setText(codeCountryPair.getName());
				textViewCountryLabel.setPadding(5, 5, 10, 5);
				textViewCountryLabel.setTypeface(yearTextViewLabel.getTypeface(), Typeface.BOLD);
				rowLabels.addView(textViewCountryLabel);
			}
		}
		/**Adding the rows to the tableLayout */
		tableLayoutResult.addView(rowLabels, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));	
		/**Looping through years that were selected and adding them to the TextView in reverse format so latest will be on first */
		for(int k = query.getFinishYear(); k >= query.getStartYear(); k--){
			TableRow row = new TableRow(this);
			
			System.out.println("rowTextViews.get(k) Size: "+rowTextViews.get(k).size());
			
			for(TextView textView : rowTextViews.get(k)){
				row.addView(textView);				  	
			}
			/**Adding the result to the Table Layout*/
			tableLayoutResult.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));	
		}
	}
	/** 
	 * Method returns a BitMap According to the imageFullUrl 
	 * @param imageFullURL
	 * @return
	 */
	
	public String getGraphData(){
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
	        bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0,
	                baf.toByteArray().length);
	    } catch (IOException e) {
	        Log.d("ImageManager", "Error: " + e);
	    }
	    return bm;
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ITEM_COMPARE, 0, "Compare")
		.setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
		
		
		menu.add(0, MENU_ITEM_GRAPH, 0, "Graph")
		.setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);		

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
						CountryPickerDialogFragment.newInstance(QueryResultActivity.this).show(fragmentManager, null);
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
						GraphDialogFragment.newInstance(QueryResultActivity.this).show(fragmentManager, null);
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