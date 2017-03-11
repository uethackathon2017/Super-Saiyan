package com.supersaiyan.englock.view.cardswipe;

import android.content.Context;
import android.util.AttributeSet;

import com.supersaiyan.englock.view.customview.CardFrameLayout;


public class BaseCardLayout extends CardFrameLayout {

	public BaseCardLayout(Context context) {
		this(context, null);
	}

	public BaseCardLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreateView(context);
	}

	private void onCreateView(final Context context) {
		findViews();
		setViewsContent();
		setViewsListener();
	}

	protected void findViews() {
	}
	protected void setViewsContent() {
	}
	protected void setViewsListener() {
	}
}
