package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.owo.app.theme.ThemeObserver;

public class LeftTextRightTextLayout extends RelativeLayout implements ThemeObserver {

	public TextView mLeft;
	public TextView mRight;

	public LeftTextRightTextLayout(Context context) {
		super(context);

		mLeft = new TextView(context);
		mLeft.setGravity(Gravity.CENTER_VERTICAL);
		mRight = new TextView(context);

		addView(mLeft);
		RelativeLayout.LayoutParams textLayoutParams = (RelativeLayout.LayoutParams) mLeft
				.getLayoutParams();
		textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		addView(mRight);
		RelativeLayout.LayoutParams arowLayoutParams = (RelativeLayout.LayoutParams) mRight
				.getLayoutParams();
		arowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		setGravity(Gravity.CENTER_VERTICAL);
		onThemeChanged();
	}

	@Override
	public void onThemeChanged() {
	}
}
