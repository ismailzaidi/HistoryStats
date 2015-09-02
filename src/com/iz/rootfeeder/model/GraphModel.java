package com.iz.rootfeeder.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import android.util.Log;

public class GraphModel {
	private ArrayList<String> xValueList;
	private ArrayList<Entry> yValueList;
	private HashMap<Float, Integer> data;
	private String countryName;

	public GraphModel(HashMap<Float, Integer> data, String countryName) {
		this.data = data;
		this.countryName = countryName;
		yValueList = new ArrayList<Entry>();
		xValueList = new ArrayList<String>();
	}

	public ArrayList<String> getXValueList() {
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			String xValue = String.valueOf(tempMap.getValue());
			Log.v("getXValueList", xValue);
			xValueList.add(xValue);
		}
		Collections.sort(xValueList);
		return xValueList;
	}

	public ArrayList<Entry> getyValueList() {
		int counter = 1;
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			float yValue = tempMap.getKey();
			yValueList.add(new Entry(yValue, counter));
			Log.v("counter getYValueList", "Counter: " + counter);
			counter++;
		}
		return yValueList;
	}

	public LineDataSet getDataSet() {
		LineDataSet dataSet = new LineDataSet(getyValueList(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		return dataSet;
	}
}
