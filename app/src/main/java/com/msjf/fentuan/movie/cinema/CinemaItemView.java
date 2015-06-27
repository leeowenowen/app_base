package com.msjf.fentuan.movie.cinema;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.msjf.fentuan.movie.PriceItemView;
import com.msjf.fentuan.movie.hall.HallData;
import com.msjf.fentuan.movie.hall.HallDataItem;
import com.msjf.fentuan.movie.hall.HallActivity;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class CinemaItemView extends TableLayout implements ThemeObserver, LanguageObserver {

	private TextView mName;
	private ImageView mType;
	private ImageView mSeat;
	private TextView mAddress;
	private TextView mDistance;
	private PriceItemView mPrice;
	private TextView mLeftToday;

	private LinearLayout mRow1;
	private LinearLayout mRow2;
	private LinearLayout mRow3;
	private FrameLayout mZuoContainer;
	private FrameLayout mDistanceContainer;

	public CinemaItemView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mName = new TextView(context);
		mType = new ImageView(context);
		mSeat = new ImageView(context);
		mAddress = new TextView(context);
		mDistance = new TextView(context);
		mPrice = new PriceItemView(context);
		mLeftToday = new TextView(context);
		mZuoContainer = new FrameLayout(context);
		mZuoContainer.addView(mSeat, LP.FWWR);
		mDistanceContainer = new FrameLayout(context);
		mDistanceContainer.addView(mDistance, LP.FWWR);

		mRow1 = new LinearLayout(context);
		mRow1.addView(mName);
		mRow1.addView(mType);
		LU.setMargin(mType, 30, 0, 0, 0);
		mRow1.addView(mZuoContainer, LP.L0W1);
		addView(mRow1);

		mRow2 = new LinearLayout(context);
		mRow2.addView(mAddress);
		mRow2.addView(mDistanceContainer, LP.L0W1);
		addView(mRow2);
		LU.setMargin(mRow2, 0, 20, 0, 0);

		mRow3 = new LinearLayout(context);
		mRow3.addView(mPrice);
		mRow3.addView(mLeftToday);
		LU.setMargin(mLeftToday, 100, 0, 0, 0);
		addView(mRow3);
		LU.setMargin(mRow3, 0, 20, 0, 0);
		setPadding(DimensionUtil.w(19), DimensionUtil.h(21), DimensionUtil.w(34),
				DimensionUtil.h(21));
	}

	private void makeHallData() {
		HallData hallData = Singleton.of(HallData.class);
		for (int i = 0; i < 20; ++i) {
			HallDataItem item = new HallDataItem();
			item.mId = "" + i;
			item.mStartTime = "11:30";
			item.mEndTime = "13:36结束";
			item.mLanguage = "英语/3D";
			item.mType = "3D";
			item.mOriginalPrice = "￥100";
			item.mCurrentPrice = "￥55";
			item.mHasFansSpecialPerformance = false;
			item.mHallNum = "3号厅";
			if (i == 2 || i == 4) {
				item.mLimitInfo = "限200人";
				item.mHasFansSpecialPerformance = true;
			}
			hallData.add(item);
		}
	}

	private void setupListeners() {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String id = mDataItem.mId;
				// get data with id from server and notify
				makeHallData();
				Intent intent = new Intent(ContextManager.activity(), HallActivity.class);
				ContextManager.activity().startActivity(intent);
			}
		});
	}

	private CinemaDataItem mDataItem;

	public void setData(CinemaDataItem item) {
		mDataItem = item;
		mName.setText(item.mName);
		mAddress.setText(item.mAddress);
		mDistance.setText(item.mDistance);
		mPrice.setText(item.mPrice);
		mLeftToday.setText(item.mLeftToday);
	}

	@Override
	public void onLanguageChanged() {

	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		mSeat.setImageBitmap(theme.bitmap(BitmapId.cinema_zuo));
		mPrice.setTextColor(theme.color(ColorId.highlight_color));
		mPrice.mPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(25));
		mAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(21));
		mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(30));
		mLeftToday.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(23));
		TU.setTextColor(ColorId.gray_text_color, mAddress, mLeftToday, mDistance);
		TU.setTextColor(ColorId.main_text_color, mName);
		mType.setImageBitmap(theme.bitmap(BitmapId.cinema_3d));
	}

}
