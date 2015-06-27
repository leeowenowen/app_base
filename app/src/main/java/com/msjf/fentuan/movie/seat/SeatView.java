package com.msjf.fentuan.movie.seat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.QuoteSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.movie.order.OrderActivity;
import com.msjf.fentuan.movie.seat.SeatData.Coorinate;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class SeatView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private TextView mMovieName;
	private TextView mDateTime;

	private TextView mChangeHallText;
	private ImageView mChangeHallIcon;
	private LinearLayout mChangeHall;
	private View mDivider;

	private TextView mHallTitle;
	private GridView mSeats;
	private SeatAdapter mSeatsAdapter;
	private TextView mSeatsDescription;
	private BottomButtonTextView mBtnBuy;
	private AbsListView.LayoutParams mSeatItemViewLayoutParams;

	public SeatView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mMovieName = new TextView(context);
		mDateTime = new TextView(context);
		mChangeHallText = new TextView(context);
		mChangeHallIcon = new ImageView(context);
		mChangeHall = new LinearLayout(context);
		mChangeHall.addView(mChangeHallText);
		mChangeHall.addView(mChangeHallIcon);
		mChangeHall.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

		mDivider = new View(context);

		mHallTitle = new TextView(context);
		// HorizontalScrollView horizontalScrollView = new
		// HorizontalScrollView(context);
		mSeats = new GridView(context);
		// horizontalScrollView.addView(mSeats);
		mSeats.setHorizontalSpacing(DimensionUtil.w(5));
		mSeats.setVerticalSpacing(DimensionUtil.h(5));

		mSeatsAdapter = new SeatAdapter();
		mSeats.setAdapter(mSeatsAdapter);

		mSeatsDescription = new TextView(context);
		mBtnBuy = new BottomButtonTextView(context);

		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout title = new LinearLayout(context);
		LinearLayout titleLeft = new LinearLayout(context);
		titleLeft.setOrientation(VERTICAL);
		titleLeft.addView(mMovieName);
		titleLeft.addView(mDateTime);
		title.addView(titleLeft);
		title.addView(mChangeHall, LP.L0M1);

		setGravity(Gravity.CENTER_HORIZONTAL);
		addView(title);
		LU.setMargin(title, 18, 26, 18, 24);
		addView(mDivider, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(2)));
		mHallTitle.setGravity(Gravity.CENTER);
		addView(mHallTitle, LP.lp(DimensionUtil.w(320), DimensionUtil.h(55)));
		LU.setMargin(mHallTitle, 0, 25, 0, 25);
		// addView(horizontalScrollView);
		addView(mSeats);
		addView(mSeatsDescription);
		mSeatsDescription.setGravity(Gravity.CENTER);
		LU.setMargin(mSeatsDescription, 0, 40, 0, 40);
		addView(mBtnBuy);

		mSeatItemViewLayoutParams = new AbsListView.LayoutParams(0, 0);
	}

	private void setupListeners() {
		mBtnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContextManager.activity(), OrderActivity.class);
				ContextManager.activity().startActivity(intent);
			}
		});
	}

	public void setData(SeatData data) {
		int width = DimensionUtil.perW(data.rowMaxCount());
		mSeatItemViewLayoutParams.width = width;
		mSeatItemViewLayoutParams.height = width;
		mSeats.setNumColumns(data.rowMaxCount());
		mSeatsAdapter.update(data);
	}

	@Override
	public void onLanguageChanged() {
		mMovieName.setText("狼图腾");
		mDateTime.setText("今天04:02  10:46");
		mHallTitle.setText("2号厅屏幕方向");
		mBtnBuy.setText("立即购票");
		mChangeHallText.setText("改场次");
		mSeatsDescription.setText("共75个座位，剩余75个座位");
		hightlightNum(mSeatsDescription);
	}

	private void hightlightNum(TextView textView) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(textView.getText());
		Pattern regexPattern = Pattern.compile("[0-9]+");
		Matcher matcher = regexPattern.matcher(builder.toString());
		while (matcher.find()) {
			builder.setSpan(
					new ForegroundColorSpan(Singleton.of(Theme.class)
							.color(ColorId.highlight_color)), matcher.start(), matcher.end(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		textView.setText(builder);
	}

	@Override
	public void onThemeChanged() {
		TU.setTextSize(36, mMovieName);
		TU.setTextSize(32, mSeatsDescription);
		TU.setTextSize(27, mDateTime, mChangeHallText, mHallTitle);

		TU.setTextColor(ColorId.main_text_color, mMovieName, mSeatsDescription);
		TU.setTextColor(ColorId.main_text_inverse_color, mHallTitle);
		TU.setTextColor(ColorId.gray_text_color, mDateTime);
		TU.setTextColor(ColorId.highlight_color, mChangeHallText);
		TU.setBGColor(ColorId.view_divider, mDivider);
		TU.setImageBitmap(BitmapId.common_right_arow, mChangeHallIcon);
		Theme theme = Singleton.of(Theme.class);
		mHallTitle.setBackgroundColor(theme.color(ColorId.hall_screen_direction_bg));
	}

	private class SeatAdapter extends BaseAdapter {
		private SeatData mData;

		public void update(SeatData data) {
			mData = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.count();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemView view = null;
			if (convertView == null) {
				view = new ItemView(getContext());
			} else {
				view = (ItemView) convertView;
			}

			Coorinate coorinate = mData.coordinate(position);
			view.setCoordinate(coorinate.mRow, coorinate.mCol);
			view.take(mData.take(position));
			// view.setLayoutParams(mSeatItemViewLayoutParams);
			return view;
		}
	}

	private class ItemView extends FrameLayout {
		private ImageView mImageView;

		@SuppressWarnings("deprecation")
		public ItemView(Context context) {
			super(context);
			mImageView = new ImageView(context);
			addView(mImageView);
			Theme theme = Singleton.of(Theme.class);
			mImageView.setImageDrawable(theme.drawable(DrawableId.seat_item));
		}

		private int mCol;
		private int mRow;

		public void setCoordinate(int row, int col) {
			mCol = col;
			mRow = row;
		}

		public void take(boolean flag) {
			setSelected(!isSelected());
		}
	}

}
