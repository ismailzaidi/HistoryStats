package com.iz.rootfeeder.fragments.charts;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.iz.rootfeeder.R;
import com.iz.rootfeeder.model.GraphDataProvider;
import com.iz.rootfeeder.model.JSONHandler;

import android.R.color;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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

	private FragmentManager fm;
	private GraphDataProvider graphProvider;
	private static String line_tag = "com.iz.feeder.linechart";

	public static LineChartFragment instanceOf(ArrayList<HashMap<Float, Integer>> data) {
		LineChartFragment fragment = new LineChartFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(line_tag, data);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.line_chart_fragment, container, false);
		// this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		LineChart lineChart = (LineChart) view.findViewById(R.id.lineChart);
		ArrayList<HashMap<Float, Integer>> hashData = (ArrayList<HashMap<Float, Integer>>) getArguments()
				.getSerializable(line_tag);
		graphProvider = new GraphDataProvider(getActivity().getApplicationContext(), hashData);
		ArrayList<LineDataSet> dataSet = graphProvider.getLineDataSet();
		LineData lineChartData = new LineData(graphProvider.getXValues(), dataSet);
		lineChartData.setDrawValues(false);
		lineChart.setData(lineChartData);
		setupChart(lineChart);
		return view;
	}

	public void changeFragment(Fragment fragment, String fragmentTag) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		transaction.addToBackStack(fragmentTag);
		transaction.commit();
	}

	public void setupChart(LineChart chart) {
		Typeface font = Typeface.createFromAsset(this.getActivity().getAssets(), "Roboto-Regular.ttf");
		chart.setDescription("");
		chart.setHighlightEnabled(true);
		chart.setScaleEnabled(true);
		chart.setPinchZoom(false);
		chart.setDrawGridBackground(false);
		chart.getAxisRight().setEnabled(false);
		Legend l = chart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART_INSIDE);
		l.setTypeface(font);
		l.setTextSize(8f);
		XAxis x = chart.getXAxis();
		x.setPosition(XAxisPosition.TOP_INSIDE);
		x.setTypeface(font);
		x.setTextSize(2f);
		YAxis leftAxis = chart.getAxisLeft();
		leftAxis.setTypeface(font);
		leftAxis.setValueFormatter(new LargeValueFormatter());
		leftAxis.setDrawGridLines(false);
		leftAxis.setSpaceTop(30f);
		chart.animateX(3000);
		chart.animateY(3000);
		chart.invalidate();

	}
}