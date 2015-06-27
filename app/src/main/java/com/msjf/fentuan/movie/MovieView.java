package com.msjf.fentuan.movie;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msjf.fentuan.hongbao.HongBaoActivity;
import com.msjf.fentuan.hongbao.HongBaoTipView;
import com.msjf.fentuan.movie.BaoXViewBase.Client;
import com.msjf.fentuan.movie.Movie.CreatorItem;
import com.msjf.fentuan.movie.cinema.CinemaActivity;
import com.msjf.fentuan.movie.cinema.CinemaData;
import com.msjf.fentuan.movie.cinema.CinemaDataItem;
import com.msjf.fentuan.movie.cinema.CinemaDatas;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.common.CommonTextPopup;
import com.msjf.fentuan.ui.common.ContentNormalTextView;
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
import com.owo.base.util.ImageBlurUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.utils.LP;

public class MovieView extends LinearLayout implements ThemeObserver, LanguageObserver {
	private ImageView mMoviePoster;
	private ContentNormalTextView mMovieType;
	private ContentNormalTextView mMovieFansBuyCount;
	private ContentNormalTextView mMovieLength;
	private ContentNormalTextView mMoviePlayTime;
	private ContentNormalTextView mMovieArea;
	private ContentNormalTextView mHasFansSpecialPerformanceText;
	private LinearLayout mMovieAttributes;
	private LinearLayout mMovieInfos;// movie attributes + image

	private ItemView mZan;
	private ItemView mBaoDianYing;
	private ItemView mBaoFansSpecialPerformance;
	private LinearLayout mItems;

	private ImageView mDescriptionTitle;
	private ScrollView mDescriptionContainer;
	private TextView mDescription;
	private LinearLayout mDescriptionLayout;
	private ImageView mCreatorTitle;
	private GridView mCreator;
	private LinearLayout mCreatorLayout;
	private BottomButtonTextView mBtnBuy;
	private LinearLayout mTopLayout;
	private LinearLayout mBottomLayout;

	private CreatorAdapter mAdapter = new CreatorAdapter();

	public MovieView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mMoviePoster = new ImageView(context);
		mMovieType = new ContentNormalTextView(context);
		mMovieFansBuyCount = new ContentNormalTextView(context);
		mMovieLength = new ContentNormalTextView(context);
		mMoviePlayTime = new ContentNormalTextView(context);
		mMovieArea = new ContentNormalTextView(context);
		mHasFansSpecialPerformanceText = new ContentNormalTextView(context);
		mMovieAttributes = new LinearLayout(context);
		mMovieInfos = new LinearLayout(context);

		mMovieAttributes.setOrientation(VERTICAL);
		mMovieAttributes.addView(mMovieType, LP.LM01);
		mMovieAttributes.addView(mMovieFansBuyCount, LP.LM01);
		mMovieAttributes.addView(mMovieLength, LP.LM01);
		mMovieAttributes.addView(mMoviePlayTime, LP.LM01);
		mMovieAttributes.addView(mMovieArea, LP.LM01);
		mHasFansSpecialPerformanceText.setGravity(Gravity.CENTER_VERTICAL);
		mMovieAttributes.addView(mHasFansSpecialPerformanceText, LP.LM01);

		mMovieInfos.addView(mMoviePoster, LP.lp(DimensionUtil.w(209), DimensionUtil.h(318)));
		mMovieInfos.addView(mMovieAttributes, LP.LMM());
		LU.setMargin(mMovieAttributes, 39, 0, 0, 0);

		mZan = new ItemView(context);
		mBaoDianYing = new ItemView(context);
		mBaoFansSpecialPerformance = new ItemView(context);
		mItems = new LinearLayout(context);
		mItems.addView(mZan, LP.L0W1);
		mItems.addView(mBaoDianYing, LP.L0W1);
		mItems.addView(mBaoFansSpecialPerformance, LP.L0W1);

		mDescriptionTitle = new ImageView(context);
		mDescription = new TextView(context);

		mDescriptionContainer = new ScrollView(context);
		mDescriptionContainer.addView(mDescription);

		mDescriptionLayout = new LinearLayout(context);
		mDescriptionLayout.addView(mDescriptionTitle);
		mDescriptionLayout.addView(mDescriptionContainer);

		mCreatorTitle = new ImageView(context);
		mCreator = new GridView(context);
		mCreator.setAdapter(mAdapter);
		mCreator.setNumColumns(3);
		mCreator.setHorizontalSpacing(DimensionUtil.w(20));
		mCreatorLayout = new LinearLayout(context);
		mCreatorLayout.addView(mCreatorTitle);
		mCreatorLayout.addView(mCreator);
		mBtnBuy = new BottomButtonTextView(context);

		mTopLayout = new LinearLayout(context);
		mTopLayout.setOrientation(VERTICAL);
		LU.setPadding(mTopLayout, 30, 32, 30, 32);
		mTopLayout.addView(mMovieInfos);
		mTopLayout.addView(mItems);

		LU.setMargin(mItems, 0, 32, 0, 0);

		mBottomLayout = new LinearLayout(context);
		mBottomLayout.setOrientation(VERTICAL);
		mBottomLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LU.setPadding(mBottomLayout, 20, 20, 20, 0);
		mBottomLayout.addView(mDescriptionLayout);
		mBottomLayout.addView(mCreatorLayout);
		LU.setMargin(mCreatorLayout, 0, 29, 0, 0);
		mBottomLayout.addView(mBtnBuy);
		LU.setMargin(mBtnBuy, 0, 29, 0, 0);

		setOrientation(VERTICAL);
		addView(mTopLayout, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(490)));
		addView(mBottomLayout, LP.LMM());
		LU.setMargin(mBottomLayout, 0, 0, 0, 20);
	}

	private void makeCinemaData() {
		CinemaDatas cinemaDatas = Singleton.of(CinemaDatas.class);
		for (int i = 0; i < 4; ++i) {
			CinemaData data = new CinemaData();
			for (int j = 0; j < 3; ++j) {
				for (int k = 0; k < 5; ++k) {
					CinemaDataItem item = new CinemaDataItem();
					item.mName = "北京第一电影院";
					item.mAddress = "中国北京市北京路北京胡同河南路18号院1号楼";
					item.mId = "id" + j;
					item.mDistance = "1.0km";
					item.mPrice = "57起";
					item.mLeftToday = "今天剩余13场";
					item.mType = "3D";
					data.add("常去影院" + j, item);
				}
			}
			cinemaDatas.put("03月0" + i + "号", data);
		}
	}

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mHasFansSpecialPerformanceText)) {
				String text = "粉丝专场是粉团联合片商,影院为服务该电影的一线明星粉丝做的影院O2O互动活动.目前在"
						+ "测试运营期.单片报名人数超过150人,该专场就正式运营.";
				new CommonTextPopup(getContext(), false).text(text).show();
			} else if (v.equals(mZan)) {
			} else if (v.equals(mBaoDianYing)) {
				final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
				BaoDianYingTipView view = new BaoDianYingTipView(getContext());
				view.setClient(new Client() {
					
					@Override
					public void onOk() {
						dialog.dismiss();
					}
					
					@Override
					public void onClose() {
						dialog.dismiss();
					}
				});
				dialog.setContentView(view);
				WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
				param.width = DimensionUtil.w(600);
				param.height = DimensionUtil.h(800);
				dialog.getWindow().setAttributes(param);
				dialog.show();
			} else if (v.equals(mBaoFansSpecialPerformance)) {
				final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
				BaoFenSiZhuanChangTipView view = new BaoFenSiZhuanChangTipView(getContext());
				view.setClient(new Client() {
					
					@Override
					public void onOk() {
						dialog.dismiss();
					}
					
					@Override
					public void onClose() {
						dialog.dismiss();
					}
				});
				dialog.setContentView(view);
				WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
				param.width = DimensionUtil.w(600);
				param.height = DimensionUtil.h(900);
				dialog.getWindow().setAttributes(param);
				dialog.show();
			} else if (v.equals(mBtnBuy)) {
				makeCinemaData();
				Intent intent = new Intent(ContextManager.activity(), CinemaActivity.class);
				intent.putExtra("movie_id", mMovie.mId);
				ContextManager.activity().startActivity(intent);
			}
		}
	};

	private void setupListeners() {
		mHasFansSpecialPerformanceText.setOnClickListener(mOnClickListener);
		mZan.setOnClickListener(mOnClickListener);
		mBaoDianYing.setOnClickListener(mOnClickListener);
		mBaoFansSpecialPerformance.setOnClickListener(mOnClickListener);
		mBtnBuy.setOnClickListener(mOnClickListener);
		// grid view on click listener
	}

	private Movie mMovie = null;

	@SuppressWarnings("deprecation")
	public void setData(Movie movie) {
		mMovie = movie;

		Theme theme = Singleton.of(Theme.class);
		mMoviePoster.setImageBitmap(movie.mPoster);
		mMovieType.setText("类型:" + movie.mType);
		mMovieFansBuyCount.setText("冯粉购票数" + movie.mFansBuyNumber);
		mMovieLength.setText("片长" + movie.mLength);
		mMoviePlayTime.setText("上映" + movie.mPlayTime);
		mMovieArea.setText("地区" + movie.mArea);
		mZan.setItemIcon(theme.bitmap(BitmapId.movie_zan));
		mZan.setItemNum("" + movie.mZanNum);
		mBaoDianYing.setItemIcon(theme.bitmap(BitmapId.movie_bao_dian_ying));

		mBaoDianYing.setItemNum("" + movie.mZanNum);
		mBaoFansSpecialPerformance.setItemIcon(theme.bitmap(BitmapId.movie_bao_fans));
		mBaoFansSpecialPerformance.setItemNum("" + movie.mZanNum);
		mDescription.setText(movie.mDescription);
		mAdapter.updateData(movie.mCreators);

		Bitmap bmp = ImageBlurUtil.fastblur(getContext(), movie.mPoster, 25);
		mTopLayout.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), bmp));
	}

	@Override
	public void onLanguageChanged() {
		mHasFansSpecialPerformanceText.setText("该片可报粉丝专场");
		mZan.setItemText("赞");
		mBaoDianYing.setItemText("报点映");
		mBaoFansSpecialPerformance.setItemText("报粉丝专场");
		mDescription.setSingleLine(false);
		mBtnBuy.setText("支持偶像,马上购");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		mDescriptionTitle.setImageBitmap(theme.bitmap(BitmapId.movie_story_content));
		mCreatorTitle.setImageBitmap(theme.bitmap(BitmapId.movie_main_actor));
		mBottomLayout.setBackgroundColor(theme.color(ColorId.main_bg));
		mHasFansSpecialPerformanceText.setBackgroundDrawable(new BitmapDrawable(theme
				.bitmap(BitmapId.movie_bao_fans_special_performance)));
		TU.setTextColor(ColorId.main_text_color, mDescription);
	}

	private class ItemView extends LinearLayout {
		private FrameLayout mTop;
		private TextView mNumber;
		private ImageView mIcon;
		private TextView mText;

		public ItemView(Context context) {
			super(context);
			mNumber = new TextView(context);
			mIcon = new ImageView(context);
			mText = new TextView(context);
			mTop = new FrameLayout(context);
			mTop.addView(mIcon, LP.FWWC);
			mTop.addView(mNumber, LP.FWWTR);
			mNumber.setGravity(Gravity.TOP);

			setOrientation(VERTICAL);
			addView(mTop, LP.LM01);
			addView(mText);
			mText.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimensionUtil.w(200),
					DimensionUtil.h(120));
			setLayoutParams(params);
		}

		public void setItemIcon(Bitmap bitmap) {
			mIcon.setImageBitmap(bitmap);
		}

		public void setItemText(String text) {
			mText.setText(text);
		}

		public void setItemNum(String number) {
			mNumber.setText(number);
		}
	}

	private class ListItemView extends LinearLayout {
		private ImageView mIcon;
		private TextView mText;

		public ListItemView(Context context) {
			super(context);
			mIcon = new ImageView(context);
			mText = new TextView(context);

			setOrientation(VERTICAL);
			addView(mIcon);
			addView(mText);
			mText.setGravity(Gravity.CENTER);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(DimensionUtil.w(172),
					DimensionUtil.h(172));
			setLayoutParams(params);
		}

		public void setIcon(Bitmap bitmap) {
			mIcon.setImageBitmap(bitmap);
		}

		public void setText(String text) {
			mText.setText(text);
		}
	}

	private class CreatorAdapter extends BaseAdapter {

		private List<CreatorItem> mItems;

		public void updateData(List<CreatorItem> items) {
			mItems = items;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mItems == null ? 0 : mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ListItemView view = null;
			if (convertView != null) {
				view = (ListItemView) convertView;
			} else {
				view = new ListItemView(getContext());
			}

			view.setIcon(mItems.get(position).mPhoto);
			view.setText(mItems.get(position).mName);
			return view;
		}
	}

}
