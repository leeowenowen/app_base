package com.msjf.fentuan.movie.hall;

import android.R.menu;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.movie.PriceItemView;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class HallItemView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private class UpDownItemView extends LinearLayout {
		public UpDownItemView(Context context, View up, View down) {
			super(context);

			setOrientation(VERTICAL);
			addView(up);
			addView(down);
			LinearLayout.LayoutParams downParams = (LinearLayout.LayoutParams) down
					.getLayoutParams();
			downParams.topMargin = DimensionUtil.h(20);

		}

	}

	private TextView mStartTime;
	private TextView mEndTime;
	private TextView mLanguageType;
	private TextView mHallNum;
	private PriceItemView mCurPrice;
	private PriceItemView mOriginalPrice;
	private TextView mFansSpecialPerformance;

	private TextView mNumLimit;
	private FrameLayout mNumLimitOrOriginalPrice;
	private FrameLayout mFansSpecialPerformanceContainer;

	public HallItemView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mStartTime = new TextView(context);
		mEndTime = new TextView(context);
		mLanguageType = new TextView(context);
		mHallNum = new TextView(context);
		mCurPrice = new PriceItemView(context);
		mOriginalPrice = new PriceItemView(context);
		mOriginalPrice.setIsValid(false);
		mFansSpecialPerformance = new TextView(context);
		mFansSpecialPerformanceContainer = new FrameLayout(context);
		mFansSpecialPerformanceContainer.addView(mFansSpecialPerformance, LP.FWWC);

		mNumLimit = new TextView(context);
		mNumLimitOrOriginalPrice = new FrameLayout(context);
		mNumLimitOrOriginalPrice.addView(mOriginalPrice);
		mNumLimitOrOriginalPrice.addView(mNumLimit);

		setPadding(DimensionUtil.w(30), DimensionUtil.h(20), DimensionUtil.w(20),
				DimensionUtil.h(25));
		addView(new UpDownItemView(context, mStartTime, mEndTime));
		mLanguageType.setGravity(Gravity.CENTER);
		mHallNum.setGravity(Gravity.CENTER);
		addView(new UpDownItemView(context, mLanguageType, mHallNum), LP.L0W1);

		mFansSpecialPerformance.setGravity(Gravity.CENTER);
		addView(mFansSpecialPerformanceContainer);
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFansSpecialPerformance
				.getLayoutParams();
		params.leftMargin = DimensionUtil.w(10);
		params.rightMargin = DimensionUtil.w(10);
		mFansSpecialPerformance.setPadding(DimensionUtil.w(5), DimensionUtil.h(12),
				DimensionUtil.w(5), DimensionUtil.h(12));

		addView(new UpDownItemView(context, mCurPrice, mNumLimitOrOriginalPrice));

		setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
	}

	private void setupListeners() {
		// setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	private HallDataItem mDataItem;

	public void setData(HallDataItem dataItem) {
		mDataItem = dataItem;
		mStartTime.setText(dataItem.mStartTime);
		mEndTime.setText(dataItem.mEndTime);
		mLanguageType.setText(dataItem.mLanguage);
		mHallNum.setText(dataItem.mHallNum);
		mCurPrice.setText(dataItem.mCurrentPrice);
		mOriginalPrice.setText(dataItem.mOriginalPrice);
		mNumLimit.setText(dataItem.mLimitInfo);

		if (dataItem.mHasFansSpecialPerformance) {
			mFansSpecialPerformance.setVisibility(VISIBLE);
			mNumLimit.setVisibility(VISIBLE);
			mOriginalPrice.setVisibility(INVISIBLE);
		} else {
			mFansSpecialPerformance.setVisibility(INVISIBLE);
			mNumLimit.setVisibility(INVISIBLE);
			mOriginalPrice.setVisibility(VISIBLE);
		}
	}

	@Override
	public void onLanguageChanged() {
		mFansSpecialPerformance.setText("粉丝专场");
	}

	@Override
	public void onThemeChanged() {
		mStartTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
		mEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		mLanguageType.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
		mHallNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		mCurPrice.mPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
		mOriginalPrice.mPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		mFansSpecialPerformance.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(23));
		mNumLimit.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		Theme theme = Singleton.of(Theme.class);
		mFansSpecialPerformance.setBackgroundColor(theme
				.color(ColorId.hall_item_view_fenSiZhuanChange_bg));

		TU.setTextColor(ColorId.gray_text_color, mEndTime, mHallNum, mOriginalPrice.mPrice);
		TU.setTextColor(ColorId.main_text_color, mStartTime, mLanguageType);
		mCurPrice.setTextColor(theme.color(ColorId.highlight_color));
	}

}
