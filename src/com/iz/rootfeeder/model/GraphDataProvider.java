package com.iz.rootfeeder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.content.Context;
import android.util.Log;

public class GraphDataProvider {
	private String countryDetails;
	private GraphModel graphModel;
	private Context context;
	private ArrayList<HashMap<Float, Integer>> data;

	public GraphDataProvider(Context context, ArrayList<HashMap<Float, Integer>> data) {
		this.context = context;
		this.data = data;
		fetchDetails();
	}

	private void fetchDetails() {
		Util utils = new Util(context);
		countryDetails = utils.loadSavedPreferencesForCountryPair();

	}

	public ArrayList<RadarDataSet> getRadarDataSet() {
		ArrayList<RadarDataSet> dataSet = new ArrayList<RadarDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			RadarDataSet data = graphModel.getDataSetForRadarChart();
			data.setColor(ColorTemplate.JOYFUL_COLORS[i]);
			data.setDrawFilled(true);
			data.setLineWidth(2f);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<BubbleDataSet> getBubbleDataSet() {
		ArrayList<BubbleDataSet> dataSet = new ArrayList<BubbleDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			BubbleDataSet data = graphModel.getDataSetForBubbleChart();
			data.setColor(ColorTemplate.JOYFUL_COLORS[i], 130);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<ScatterDataSet> getScatterDataSet() {
		ArrayList<ScatterDataSet> dataSet = new ArrayList<ScatterDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		int random = new Random().nextInt(4);
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			ScatterDataSet data = graphModel.getDataSetForScatterChart();
			if (i == 0) {
				data.setScatterShape(ScatterShape.TRIANGLE);
			} else if (i == 1) {
				data.setScatterShape(ScatterShape.CIRCLE);
			} else if (i == 2) {
				data.setScatterShape(ScatterShape.CROSS);
			}else{
				data.setScatterShape(ScatterShape.SQUARE);
			}
			data.setColor(ColorTemplate.COLORFUL_COLORS[i]);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<PieDataSet> getPieDataSet() {
		ArrayList<PieDataSet> dataSet = new ArrayList<PieDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			PieDataSet data = graphModel.getDataSetForPieChart();
			data.setColors(ColorTemplate.JOYFUL_COLORS);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<BarDataSet> getBarDataSet() {
		ArrayList<BarDataSet> dataSet = new ArrayList<BarDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			BarDataSet data = graphModel.getDataSetForBarChart();
			data.setHighLightColor(ColorTemplate.JOYFUL_COLORS[i]);
			data.setColor(ColorTemplate.JOYFUL_COLORS[i]);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<LineDataSet> getLineDataSet() {
		ArrayList<LineDataSet> dataSet = new ArrayList<LineDataSet>();
		ArrayList<HashMap<Float, Integer>> global_data = data;
		int size = global_data.size();
		Log.v("getDataLine", "Size: " + size);
		for (int i = 0; i < size; i++) {
			graphModel = new GraphModel(data.get(i), countryDetails.split(";")[i].split(",")[1]);
			LineDataSet data = graphModel.getDataSetForLineChart();
			data.setDrawCubic(true);
			data.setCubicIntensity(0.1f);
			data.setDrawCircles(false);
			data.setLineWidth(1f);
			data.setCircleSize(3f);
			data.setHighLightColor(ColorTemplate.JOYFUL_COLORS[i]);
			data.setColor(ColorTemplate.JOYFUL_COLORS[i]);
			data.setFillColor(ColorTemplate.JOYFUL_COLORS[i]);
			data.setDrawHorizontalHighlightIndicator(false);
			dataSet.add(data);
		}
		return dataSet;

	}

	public ArrayList<String> getXValuesForPie() {
		int position = selectHighestSize();
		graphModel = new GraphModel(data.get(position), countryDetails.split(";")[0].split(",")[1]);
		ArrayList<String> list = graphModel.getXValueListForPie();
		Log.v("Pie Fragment", list.toString());
		return list;

	}

	public ArrayList<String> getXValues() {
		int position = selectHighestSize();
		graphModel = new GraphModel(data.get(position), countryDetails.split(";")[0].split(",")[1]);
		ArrayList<String> list = graphModel.getXValueList();
		return list;

	}
	private int selectHighestSize(){
		int position = 0;
		int current_size = data.get(0).size();
		for(int i=0;i<data.size();i++){
			if(current_size<data.get(i).size()){
				current_size = data.get(i).size();
				position = i;
			}
		}
		return position;
	}
}
