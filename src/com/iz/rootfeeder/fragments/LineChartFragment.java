package com.iz.rootfeeder.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.GraphDataProvider;
import com.iz.rootfeeder.model.JSONHandler;

import android.R.color;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LineChartFragment extends Fragment {

	private static String IndicatorTag = "com.iz.fragment.graphfragment.indicator";
	private static String CountryTag = "com.iz.fragment.graphfragment.country";
	private String countryDetails, indicatorDetails;
	private FragmentManager fm;
	private JSONHandler json_data;
	private GraphDataProvider graphProvider;

	public static LineChartFragment instanceOf(String countryDetail, String indicatorDetail) {
		LineChartFragment fragment = new LineChartFragment();
		Bundle bundle = new Bundle();
		bundle.putString(CountryTag, countryDetail);
		bundle.putString(IndicatorTag, indicatorDetail);
		Log.v(IndicatorTag, "Graph Created");
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		countryDetails = getArguments().getString(CountryTag);
		indicatorDetails = getArguments().getString(IndicatorTag);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.line_chart_fragment, container, false);
		// this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		LineChart lineChart = (LineChart) view.findViewById(R.id.lineChart);
		if (countryDetails.equals("0")) {

		}
		graphProvider = new GraphDataProvider(retrieveCountryList().get(0), countryDetails.split(",")[1]);
		ArrayList<LineDataSet> dataSet = getLineDataSet();
		LineData data = new LineData(graphProvider.getXValueList(),dataSet);
		lineChart.setData(data);
		fm = this.getChildFragmentManager();
		return view;
	}
	public ArrayList<LineDataSet> getLineDataSet(){
		ArrayList<LineDataSet> dataSet = new ArrayList<LineDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = retrieveCountryList();
		int size = global_data.size();
		for(int i=0;i<size;i++){
			graphProvider = new GraphDataProvider(retrieveCountryList().get(i), countryDetails.split(";")[i].split(",")[1]);
			LineDataSet data = graphProvider.getDataSet();
			data.setColors(ColorTemplate.COLORFUL_COLORS);
			dataSet.add(data);
		}
		return dataSet;

	}
	public ArrayList<HashMap<Float, Integer>> retrieveCountryList() {
		String[] countryContent = countryDetails.split(";");
		int map_size = countryContent.length;
		ArrayList<HashMap<Float, Integer>> map = new ArrayList<HashMap<Float, Integer>>(map_size);
		for (int i = 0; i < map_size; i++) {
			String countryCode = countryContent[i].split(",")[0];
			String indicatorCode = indicatorDetails.split(",")[0];
			String queryURL = "http://api.worldbank.org/countries/" + countryCode + "/indicators/" + indicatorCode
					+ "?per_page=10000&date=1940:2010&format=json";
			json_data = new JSONHandler(queryURL);
			json_data.generateJSONResponse();
			HashMap<Float, Integer> mapItem = json_data.getCountryList();
			Log.v("RetrieveCountryList", "Country: " + countryCode + " Size: " + mapItem.size() + "  Indicator: "
					+ indicatorDetails.split(",")[1]);
			map.add(mapItem);
		}
		return map;
	}

	public void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}

	public void setupLineChart(LineChart lineChart) {

		Context context = getActivity().getApplicationContext();
		lineChart.setDrawBorders(true);
		lineChart.setBorderWidth(1);
		lineChart.setBorderColor(color.black);
		lineChart.setBackgroundColor(color.transparent);
		lineChart.setDescription(indicatorDetails.split(",")[1]);
		lineChart.setNoDataText("Sync Data");
		lineChart.setTouchEnabled(true);
		lineChart.setDragEnabled(true);
		lineChart.setScaleEnabled(true);
		YAxis leftaxis = lineChart.getAxis(AxisDependency.RIGHT);
		leftaxis.setTextColor(ContextCompat.getColor(context, com.iz.rootfeeder.R.color.blue));
		leftaxis.setTextSize(8);
		leftaxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
		lineChart.setPinchZoom(true);
		lineChart.animateX(1000);
		lineChart.animateX(2000);
		lineChart.invalidate();

	}
}