package com.msjf.fentuan.movie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.util.LU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class BasePage extends LinearLayout implements ThemeObserver, LanguageObserver {
	protected ImageView mBack;
	protected TextView mTitle;
	private FrameLayout mTitleLeftExtensionContainer;
	private LinearLayout mTitleLayout;
	private FrameLayout mTitleLayoutContainer;
	private FrameLayout mContentView;
	private FrameLayout mTitleRightExtensionContainer;
	private boolean mScroll = false;

	public BasePage(Context context) {
		this(context, true);
	}

	public BasePage(Context context, boolean scroll) {
		super(context);
		mScroll = scroll;
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	protected void setupListeners() {
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activity activity = ContextManager.activity();
				if (activity != null) {
					activity.finish();
				}
			}
		});
	}

	public void setTitleLeftExtension(View extension) {
		mTitleLeftExtensionContainer.addView(extension, LP.FMM());
	}

	public void setTitleRightExtension(View extension) {
		mTitleRightExtensionContainer.addView(extension, LP.FMM());
	}

	protected void initComponents(Context context) {
		mBack = new ImageView(context);
		mTitle = new TextView(context);
		mTitle.setGravity(Gravity.CENTER);
		mTitleLayout = new LinearLayout(context);
		mTitleLayout.addView(mBack);
		mTitleLeftExtensionContainer = new FrameLayout(context);
		mTitleLayout.addView(mTitleLeftExtensionContainer);
		mTitleLayout.setGravity(Gravity.CENTER_VERTICAL);

		mTitleRightExtensionContainer = new FrameLayout(context);

		mContentView = mScroll ? new ScrollView(context) : new FrameLayout(context);
		mTitleLayoutContainer = new FrameLayout(context);
		mTitleLayoutContainer.addView(mTitleLayout, LP.FMM);
		mTitleLayoutContainer.addView(mTitle, LP.FWWC);
		mTitleLayoutContainer.addView(mTitleRightExtensionContainer, LP.FWWRCV);
		LU.setMargin(mTitleRightExtensionContainer, 0, 0, 30, 0);
		LU.setMargin(mBack, 20, 0, 0, 0);

		setOrientation(VERTICAL);
		addView(mTitleLayoutContainer,
				LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(100)));
		addView(mContentView, LP.LMM());
	}

	public void setBackListener(OnClickListener listener) {
		mBack.setOnClickListener(listener);
	}

	public void setTitle(String title) {
		mTitle.setText(title);
	}

	public void setContentView(View contentView) {
		if (mScroll) {
			mContentView.addView(contentView);
		} else {
			mContentView.addView(contentView, LP.FMM);
		}
	}

	@Override
	public void onLanguageChanged() {
	}

	@Override
	public void onThemeChanged() {
		mBack.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.common_left_arow));
		mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
		Theme theme = Singleton.of(Theme.class);

		mTitle.setTextColor(theme.color(ColorId.highlight_color));
		mTitleLayout.setBackgroundColor(theme.color(ColorId.base_page_title_bg));
		setBackgroundColor(theme.color(ColorId.main_bg));
	}

}
