package com.iz.rootfeeder.adapters;

import java.util.ArrayList;

import com.iz.rootfeeder.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomGridViewAdapter extends ArrayAdapter implements OnClickListener {
	private Context context;
	private ImageView imageView;
	private ArrayList<Integer> imageList;
	private AppCompatActivity activity;
	private int parent_layout;
	public CustomGridViewAdapter(AppCompatActivity activity,int parent_layout,Context context, int resource, ArrayList objects) {
		super(context, resource, objects);
		this.context = context;
		this.activity=activity;
		this.parent_layout=parent_layout;
		this.imageList = objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		itemView = inflater.inflate(R.layout.custom_country_item, parent,false);
		imageView = (ImageView) itemView.findViewById(R.id.imageView_flag);
		imageView.setImageResource(imageList.get(position));
		imageView.setOnClickListener(this);
		return itemView;
	}
	@Override
	public void onClick(View v) {
		Toast.makeText(context, "Pressed", Toast.LENGTH_SHORT).show();
	}
}
