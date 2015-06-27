package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;

public  class LeftTextRightImageLayout extends RelativeLayout implements ThemeObserver {

	public TextView mLeft;
	public ImageView mRight;

	public LeftTextRightImageLayout(Context context) {
		super(context);

		mLeft = new TextView(context);
		mLeft.setGravity(Gravity.CENTER_VERTICAL);
		mRight = new ImageView(context);

		addView(mLeft);
		RelativeLayout.LayoutParams textLayoutParams = (RelativeLayout.LayoutParams) mLeft
				.getLayoutParams();
		textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		addView(mRight);
		RelativeLayout.LayoutParams arowLayoutParams = (RelativeLayout.LayoutParams) mRight
				.getLayoutParams();
		arowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
				DimensionUtil.h(60)));
		setGravity(Gravity.CENTER_VERTICAL);
		onThemeChanged();
	}

	@Override
	public void onThemeChanged() {
	}
}
