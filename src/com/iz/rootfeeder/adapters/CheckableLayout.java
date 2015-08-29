package com.iz.rootfeeder.adapters;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLayout extends LinearLayout implements Checkable {
	private boolean mChecked;

	public CheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }	
	
	@SuppressWarnings("deprecation")
	public void setChecked(boolean checked) {
		mChecked = checked;
	}

	public boolean isChecked() {
		return mChecked;
	}

	public void toggle() {
		setChecked(!mChecked);
	}
}