package com.iz.rootfeeder.fragments;

import com.iz.rootfeeder.QueryResultActivity;
import com.iz.rootfeeder.R;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class GraphDialogFragment extends DialogFragment{
	
	public static DialogFragment newInstance(Context context){
		DialogFragment graphDialogFragment = new GraphDialogFragment();
		return graphDialogFragment;		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_graph, container);
		getDialog().setTitle("Graph");		
		
		WebView webView = (WebView) view.findViewById(R.id.webView_graph);
		QueryResultActivity queryResultActivity = (QueryResultActivity) getActivity();
		
		String content = "<html>"
				+ "  <head>"
				+ "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
				+ "    <script type=\"text/javascript\">"
				+ "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
				+ "      google.setOnLoadCallback(drawChart);"
				+ "      function drawChart() {"
				+ "        var data = google.visualization.arrayToDataTable(["
				+ "          ['Year', '"+queryResultActivity.getQuery().getCodeCountryPair().getName()+"'],"
				+ queryResultActivity.getGraphData()
				+ "        ]);"
				+ "        var options = {"
				+ "          title: '"+queryResultActivity.getQuery().getCodeIndicatorPair().getName()+"',"
				+ "        };"
				+ "        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));"
				+ "        chart.draw(data, options);"
				+ "      }"
				+ "    </script>"
				+ "  </head>"
				+ "  <body>"
				+ "    <div id=\"chart_div\" style=\"width: 1000px; height: 500px;\"></div>"
				+ "	   <img style=\"padding: 0; margin: 0 0 0 330px; display: block;\" src=\"charts.png\"/>"
				+ "  </body>" + "</html>";

		
		/**Allowing Javascript to be excuted settings*/
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.requestFocusFromTouch();
		/**Setting path and loading the Javascript contents via android assets folder*/
		webView.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );
		webView.getSettings().setUseWideViewPort(true);
		webView.setInitialScale(1);
		
		return view;
	}
}
