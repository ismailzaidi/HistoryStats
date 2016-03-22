package com.iz.rootfeeder.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.util.Log;

public class GraphModel {
	private HashMap<Float, Integer> data;
	private String countryName;
	private int colour_counter = 0;

	public GraphModel(HashMap<Float, Integer> data, String countryName) {
		this.data = data;
		this.countryName = countryName;
	}

	public ArrayList<String> getXValueList() {
		ArrayList<String> xValueList = new ArrayList<String>();
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			String xValue = String.valueOf(tempMap.getValue());
			xValueList.add(xValue);
		}
		Collections.sort(xValueList);
		return xValueList;
	}

	public ArrayList<String> getXValueListForPie() {
		ArrayList<String> xValueList = new ArrayList<String>();
		for (int i = 1960; i <= 2010; i += 5) {
			if (i != 2010) {
				xValueList.add(String.valueOf(i) + ":" + String.valueOf(i + 4));
			} else {
				xValueList.add(String.valueOf(i-5)+":"+String.valueOf(i));

			}
		}
		Collections.sort(xValueList);
		return xValueList;
	}

	public ArrayList<Entry> getyValueListLine() {
		ArrayList<Entry> yValueListLine = new ArrayList<Entry>();
		int counter = 1;
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			float yValue = tempMap.getKey();
			yValueListLine.add(new Entry(yValue, counter));
			counter++;
		}
		return yValueListLine;
	}

	public ArrayList<Entry> getyValueListPie() {
		ArrayList<Entry> yValueListPie = new ArrayList<Entry>();
		int counter = 1;
		int yearCounter = 1960;
		float yValue;
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			yValue = +tempMap.getKey();
			if (yearCounter % 5 == 0 && yearCounter <= 2010) {
				yValueListPie.add(new Entry(yValue, counter));
				yValue=0;
				counter++;
			}
			yearCounter += 5;
		}
		return yValueListPie;
	}

	public ArrayList<BarEntry> getyValueListBar() {
		ArrayList<BarEntry> yValueListBar = new ArrayList<BarEntry>();
		int counter = 1;
		int yearCounter = 1960;
		float yValue;
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			yValue = +tempMap.getKey();
			if (yearCounter % 5 == 0 && yearCounter <= 2010) {
				yValueListBar.add(new BarEntry(yValue, counter));
				yValue=0;
				counter++;
			}
			yearCounter += 5;
		}
		return yValueListBar;
	}

	public ArrayList<BubbleEntry> getyValueListBubble() {
		ArrayList<BubbleEntry> yValueListBar = new ArrayList<BubbleEntry>();
		int counter = 1;
		Random random = new Random();
		for (Map.Entry<Float, Integer> tempMap : data.entrySet()) {
			float yValue = tempMap.getKey();
			float rand = (float) random.nextFloat() * 100000000;
			yValueListBar.add(new BubbleEntry(counter, yValue, rand));
			counter++;
		}
		return yValueListBar;
	}

	public LineDataSet getDataSetForLineChart() {
		LineDataSet dataSet = new LineDataSet(getyValueListLine(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		dataSet.setColor(ColorTemplate.JOYFUL_COLORS[colour_counter]);
		return dataSet;
	}

	public BubbleDataSet getDataSetForBubbleChart() {
		BubbleDataSet dataSet = new BubbleDataSet(getyValueListBubble(), countryName);
		return dataSet;
	}

	public BarDataSet getDataSetForBarChart() {
		BarDataSet dataSet = new BarDataSet(getyValueListBar(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		return dataSet;
	}

	public RadarDataSet getDataSetForRadarChart() {
		RadarDataSet dataSet = new RadarDataSet(getyValueListPie(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		return dataSet;
	}

	public ScatterDataSet getDataSetForScatterChart() {
		ScatterDataSet dataSet = new ScatterDataSet(getyValueListLine(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		return dataSet;
	}

	public PieDataSet getDataSetForPieChart() {
		PieDataSet dataSet = new PieDataSet(getyValueListPie(), countryName);
		dataSet.setAxisDependency(AxisDependency.LEFT);
		return dataSet;
	}
}
