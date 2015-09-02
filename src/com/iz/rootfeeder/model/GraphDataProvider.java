package com.iz.rootfeeder.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.util.Log;

public class GraphDataProvider extends GraphModel{
	public GraphDataProvider(HashMap<Float, Integer> data, String countryName) {
		super(data, countryName);
	}
}
