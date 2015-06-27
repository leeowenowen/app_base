package com.msjf.fentuan.movie.order;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class OrderView extends LinearLayout implements ThemeObserver, LanguageObserver {
	private TitleTextView mMovieTitle;
	private TextView mMovieName;
	private TextView mCinemaName;
	private TextView mDateTime;
	private TextView mSeatInfo;

	private TitleTextView mPriceTitle;
	private TextView mTotalPrice;
	private TextView mPriceDescription;
	private TextView mShouldPayInfo;

	private TitleTextView mPhoneTitle;
	private TextView mPhoneNum;

	private TextView mFenTuanTip;
	private BottomButtonTextView mButtonPay;

	private LinearLayout mPayInfoLayout;

	public OrderView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mMovieTitle = new TitleTextView(context);
		mMovieName = new TextView(context);
		mCinemaName = new TextView(context);
		mDateTime = new TextView(context);
		mSeatInfo = new TextView(context);

		mPriceTitle = new TitleTextView(context);
		mTotalPrice = new TextView(context);
		mPriceDescription = new TextView(context);
		mShouldPayInfo = new TextView(context);

		mPhoneTitle = new TitleTextView(context);
		mPhoneNum = new TextView(context);
		mFenTuanTip = new TextView(context);
		mButtonPay = new BottomButtonTextView(context);

		mPayInfoLayout = new LinearLayout(context);
		mPayInfoLayout.addView(mTotalPrice);
		mPayInfoLayout.addView(mPriceDescription, LP.L0W1);
		mPriceDescription.setGravity(Gravity.RIGHT);

		setOrientation(VERTICAL);
		LinearLayout.LayoutParams titleParams = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
				DimensionUtil.h(60));
		addView(mMovieTitle, titleParams);
		addView(mMovieName);
		addView(mCinemaName);
		addView(mDateTime);
		addView(mSeatInfo);
		addView(mPriceTitle, titleParams);
		addView(mPayInfoLayout);
		addView(mShouldPayInfo);
		addView(mPhoneTitle, titleParams);
		addView(mPhoneNum);
		addView(mFenTuanTip, LP.LMW());
		addView(mButtonPay, LP.LMW());
		LU.setMargin(mMovieName, 0, 20, 0, 20);
		LU.setMargin(mDateTime, 0, 20, 0, 20);
		LU.setMargin(mPriceTitle, 0, 20, 0, 20);
		LU.setMargin(mShouldPayInfo, 0, 20, 0, 20);
		LU.setMargin(mPhoneNum, 0, 20, 0, 20);
		LU.setMargin(mButtonPay, 35, 0, 35, 0);

		mFenTuanTip.setLineSpacing(DimensionUtil.h(20), 1);
		mFenTuanTip.setSingleLine(false);
	}

	private void setupListeners() {
		mButtonPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "请使用支付宝支付", Toast.LENGTH_LONG).show();
			}
		});
	}

	public void setData(OrderData data) {
		mMovieName.setText("片名:" + data.mMovieName);
		mCinemaName.setText("影院:" + data.mCinemaName);
		mDateTime.setText("日期:" + data.mDataTime);
		mSeatInfo.setText("座位:" + data.mSeatInfo);
		mTotalPrice.setText("共计:" + data.mTotalPrice + "元");
		mPriceDescription.setText(data.mPriceDescription);
		mShouldPayInfo.setText("应付金额:" + data.mTotalPrice + "元");
		mPhoneNum.setText("手机号码:" + data.mPhoneNum);
		mFenTuanTip.setText("粉团提示：请确认订单信息，订座票一经售出不可退换!");
		mButtonPay.setText("用支付宝付款");
	}

	@Override
	public void onLanguageChanged() {
		mMovieTitle.setText("影票信息");
		mPriceTitle.setText("结算信息");
		mPhoneTitle.setText("手机号码");
	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.gray_text_color, mMovieName, mCinemaName, mDateTime, mSeatInfo,
				mTotalPrice, mPhoneNum, mShouldPayInfo, mFenTuanTip);
		TU.setTextColor(ColorId.highlight_color, mPriceDescription);
		TU.setTextSize(27, mMovieName, mCinemaName, mDateTime, mSeatInfo, mTotalPrice,
				mShouldPayInfo, mPhoneNum, mFenTuanTip);
		TU.setTextSize(21, mPriceDescription);
		LU.setPadding(mMovieTitle, 20, 0, 0, 0);
		LU.setPadding(mPriceTitle, 20, 0, 0, 0);
		LU.setPadding(mPhoneTitle, 20, 0, 0, 0);
		LU.setPadding(mMovieName, 20, 0, 0, 0);
		LU.setPadding(mCinemaName, 20, 0, 0, 0);
		LU.setPadding(mDateTime, 20, 0, 0, 0);
		LU.setPadding(mSeatInfo, 20, 0, 0, 0);
		LU.setPadding(mShouldPayInfo, 20, 0, 0, 0);
		LU.setPadding(mPayInfoLayout, 20, 0, 20, 0);
		LU.setPadding(mPhoneNum, 20, 0, 0, 0);
		LU.setPadding(mFenTuanTip, 40, 0, 40, 0);
	}

	private class TitleTextView extends TextView implements ThemeObserver {
		public TitleTextView(Context context) {
			super(context);
			setGravity(Gravity.CENTER_VERTICAL);
			onThemeChanged();
		}

		@Override
		public void onThemeChanged() {
			TU.setBGColor(ColorId.cinema_group_title_bg, this);
			TU.setTextColor(ColorId.main_text_color, this);
			setTextColor(Color.BLACK);
			TU.setTextSize(27, this);
		}

	}

}
