package com.msjf.fentuan.movie.cinema;

import java.util.Map.Entry;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ScrollTabControl;
import com.owo.ui.ScrollTabControl.IndicatorView;
import com.owo.ui.ScrollTabControl.OnTabChangeListener;
import com.owo.ui.ScrollTabControl.TabContentFactory;
import com.owo.ui.utils.LP;

public class CinemaWidget extends FrameLayout implements ThemeObserver, LanguageObserver {
	private ScrollTabControl mScrollTabControl;

	private class TitleLayout extends LinearLayout implements ThemeObserver {
		TextView mText;
		View mLine;

		public TitleLayout(Context context) {
			super(context);
			mText = new TextView(context);
			mLine = new View(context);
			setOrientation(VERTICAL);
			addView(mText, LP.LMW());
			addView(mLine, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(2)));
			LU.setMargin(mLine, 0, 5, 0, 0);
			mLine.setVisibility(INVISIBLE);
			onThemeChanged();
		}

		@Override
		public void onThemeChanged() {
			TU.setBGColor(ColorId.highlight_color, mLine);
		}

	}

	private TitleLayout[] mTextViews;

	public CinemaWidget(Context context) {
		super(context);

		mScrollTabControl = new ScrollTabControl(context);
		mScrollTabControl.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
			}
		});

		CinemaDatas datas = Singleton.of(CinemaDatas.class);
		mTextViews = new TitleLayout[datas.size()];
		int i = 0;
		for (Entry<String, CinemaData> cinemaData : datas.entrySet()) {
			mTextViews[i] = new TitleLayout(context);
			mTextViews[i]
					.setLayoutParams(new LinearLayout.LayoutParams(0, DimensionUtil.h(100), 1));
			mTextViews[i].setGravity(Gravity.CENTER);
			mTextViews[i].mText.setText(cinemaData.getKey());
			mScrollTabControl
					.addTab(mScrollTabControl.newTabSpec(cinemaData.getKey())
							.setIndicator(new TitleIndicator(mTextViews[i]))
							.setContent(mTabContentFactory));
			i++;
		}

		addView(mScrollTabControl);
		onLanguageChanged();
		onThemeChanged();
	}

	private class TitleIndicator implements IndicatorView {
		private TitleLayout mTitleLayout;

		public TitleIndicator(TitleLayout title) {
			mTitleLayout = title;
			mTitleLayout.mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		}

		@Override
		public View getView() {
			return mTitleLayout;
		}

		@Override
		public void onTabClosed() {
			mTitleLayout.mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
			mTitleLayout.mText.setTextColor(Singleton.of(Theme.class).color(
					ColorId.main_page_inverse_text_color));
			mTitleLayout.mLine.setVisibility(INVISIBLE);

		}

		@Override
		public void onSetAsCurrentTab() {
			mTitleLayout.mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(35));
			mTitleLayout.mText.setTextColor(Singleton.of(Theme.class)
					.color(ColorId.highlight_color));
			mTitleLayout.mLine.setVisibility(VISIBLE);
		}
	}

	private TabContentFactory mTabContentFactory = new TabContentFactory() {
		@Override
		public View createTabContent(String tag) {
			CinemaData data = Singleton.of(CinemaDatas.class).get(tag);
			CinemaView cinemaView = new CinemaView(getContext());
			cinemaView.updateData(data);
			return cinemaView;
		}
	};

	@Override
	public void onLanguageChanged() {

	}

	@Override
	public void onThemeChanged() {
	}
}
