package com.msjf.fentuan.movie;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;

public class MoviePage extends BasePage {

	private TextView mTitleText;
	private MovieView mMovieView;

	public MoviePage(Context context) {
		super(context, true);
	}

	protected void initComponents(Context context) {
		super.initComponents(context);
		mTitleText = new TextView(context);
		mTitleText.setGravity(Gravity.CENTER);
		setTitleRightExtension(mTitleText);

		mMovieView = new MovieView(context);
		setContentView(mMovieView);
		makeDummyData();
	}

	public void setData(Movie movie) {
		mMovieView.setData(movie);
	}

	protected void setupListeners() {
		super.setupListeners();
		mTitleText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
				ListViewBase view = new ListViewBase(getContext());
				view.setAdapter(new StarMovieRankListAdapter());
				view.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						dialog.dismiss();
					}
				});
				dialog.setContentView(view);
				WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
				param.width = DimensionUtil.w(600);
				param.height = DimensionUtil.h(400);
				dialog.getWindow().setAttributes(param);
				dialog.show();
			}
		});
	}

	private void makeDummyData() {
		StarMovieData data = Singleton.of(StarMovieData.class);
		for (int i = 0; i < 5; ++i) {
			StarMovieDataItem item = new StarMovieDataItem();
			item.mStarId = "" + i;
			item.mStarMovie = "冯绍峰 <新电影>";
			item.mStarPhoto = Singleton.of(Theme.class).bitmap(BitmapId.login_fen);
			data.add(item);
		}
	}

	@Override
	public void onLanguageChanged() {
		super.onLanguageChanged();
		mTitleText.setText(" 偶像票房破亿榜 ");
	}

	@Override
	public void onThemeChanged() {
		super.onThemeChanged();
		TU.setTextColor(ColorId.highlight_color, mTitleText);
		TU.setTextSize(27, mTitleText);
		TU.setBGColor(ColorId.main_bg, mTitleText);
	}

	private class StarMovieRankListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Singleton.of(StarMovieData.class).size();
		}

		@Override
		public Object getItem(int position) {
			return Singleton.of(StarMovieData.class).get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			StarMovieRankListItemView view = null;
			if (convertView == null) {
				view = new StarMovieRankListItemView(getContext());
			} else {
				view = (StarMovieRankListItemView) convertView;
			}
			view.setData(Singleton.of(StarMovieData.class).get(position), position);
			return view;
		}
	}

	private class StarMovieRankListItemView extends LinearLayout implements ThemeObserver {
		private ImageView mPhoto;
		private TextView mMovie;
		private TextView mIndex;

		public StarMovieRankListItemView(Context context) {
			super(context);

			mPhoto = new ImageView(context);
			mMovie = new TextView(context);
			mIndex = new TextView(context);
			setGravity(Gravity.CENTER_VERTICAL);
			addView(mPhoto);
			addView(mMovie, LP.L0M1);
			addView(mIndex);
			LU.setMargin(mMovie, 10, 0, 10, 0);
			LU.setPadding(this, 10, 10, 30, 10);
			mIndex.setGravity(Gravity.CENTER);
			mMovie.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					AbsListView.LayoutParams.MATCH_PARENT, DimensionUtil.h(90));
			setLayoutParams(params);
			onThemeChanged();
		}

		public void setData(StarMovieDataItem item, int index) {
			mPhoto.setImageBitmap(item.mStarPhoto);
			mMovie.setText(item.mStarMovie);
			mIndex.setText("" + index);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onThemeChanged() {
			TU.setTextColor(ColorId.main_text_color, mMovie);
			TU.setTextSize(20, mMovie);
			TU.setTextColor(ColorId.main_text_inverse_color, mIndex);
			TU.setTextSize(27, mIndex);
			mIndex.setBackgroundDrawable(new BitmapDrawable(Singleton.of(Theme.class).bitmap(
					BitmapId.movie_popup_circle_bg)));
		}
	}
}
