package com.msjf.fentuan.movie.hall;

import java.util.BitSet;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.movie.seat.SeatData;
import com.msjf.fentuan.movie.seat.SeatActivity;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;

public class HallView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private TextView mName;
	private TextView mAddress;
	private ImageView mRightArow;
	private LinearLayout mTopLayout;
	private LinearLayout mTopLeftLayout;

	private TextView mDateTitle;
	private ListViewBase mHallListView;
	private HallAdapter mAdapter;

	public HallView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mName = new TextView(context);
		mAddress = new TextView(context);
		mRightArow = new ImageView(context);
		mTopLeftLayout = new LinearLayout(context);
		mTopLeftLayout.setOrientation(VERTICAL);
		mTopLeftLayout.addView(mName);
		mTopLeftLayout.addView(mAddress);
		LinearLayout.LayoutParams addressLayoutParams = (LinearLayout.LayoutParams) mAddress
				.getLayoutParams();
		addressLayoutParams.topMargin = DimensionUtil.h(25);

		mTopLayout = new LinearLayout(context);
		mTopLayout.setGravity(Gravity.CENTER_VERTICAL);
		mTopLayout.addView(mTopLeftLayout, LP.L0W1);
		mTopLayout.addView(mRightArow);
		mTopLayout.setPadding(DimensionUtil.w(20), DimensionUtil.h(31), DimensionUtil.w(20),
				DimensionUtil.h(24));

		mDateTitle = new TextView(context);
		mDateTitle.setGravity(Gravity.CENTER);
		mHallListView = new ListViewBase(context);
		mAdapter = new HallAdapter();
		mHallListView.setAdapter(mAdapter);

		setOrientation(VERTICAL);
		mDateTitle.setGravity(Gravity.CENTER);
		addView(mTopLayout);
		addView(mDateTitle, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(70)));
		addView(mHallListView);
	}

	private void makeSeatsInfo() {
		SeatData seatData = Singleton.of(SeatData.class);
		for (int i = 0; i < 10; ++i) {
			int columnNum = new Random().nextInt(10);
			columnNum = columnNum < 5 ? 5 : columnNum;
			BitSet flagSet = new BitSet();
			for (int j = 0; j < columnNum; ++j) {
				int value = new Random().nextInt();
				boolean flag = ((value % 2) == 1);
				flagSet.set(j, flag);
			}
			seatData.addRow(columnNum, flagSet);
		}
	}

	private void setupListeners() {
		mHallListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				makeSeatsInfo();
				Intent intent = new Intent(ContextManager.activity(), SeatActivity.class);
				ContextManager.activity().startActivity(intent);
			}
		});
	}

	public void setData(String date, HallData data) {
		mDateTitle.setText(date);
		mAdapter.updateData(data);
	}

	@Override
	public void onLanguageChanged() {
		mName.setText("北京唐阁影院亦庄店");
		mAddress.setText("北京市大兴区荣华中路１８号院１号楼四层f4-01");
	}

	@Override
	public void onThemeChanged() {
		mDateTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(30));
		mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(35));
		mAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(21));
		Theme theme = Singleton.of(Theme.class);
		mDateTitle.setBackgroundColor(theme.color(ColorId.hall_view_title_bg_color));
		mDateTitle.setTextColor(theme.color(ColorId.main_text_inverse_color));
		mName.setTextColor(theme.color(ColorId.main_text_color));
		mRightArow.setImageBitmap(theme.bitmap(BitmapId.common_right_arow));
	}

	private class HallAdapter extends BaseAdapter {

		private HallData mData;

		public void updateData(HallData data) {
			mData = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HallItemView view = null;
			if (convertView != null) {
				view = (HallItemView) convertView;
			} else {
				view = new HallItemView(getContext());
			}
			view.setData(mData.get(position));
			return view;
		}
	}

}
