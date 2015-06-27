package com.msjf.fentuan.welcome;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.login.LoginActivity;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class WelcomeView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private ImageView mLogo;
	private ImageView mTextLogo;
	private ImageView mBottomText;
	private View mView;

	public WelcomeView(Context context) {
		super(context);
		initComponents(context);
		onLanguageChanged();
		onThemeChanged();
		BaseHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				startAnimation();
			}
		}, 500);
	}

	private void initComponents(Context context) {
		mLogo = new ImageView(context);
		mTextLogo = new ImageView(context);
		mBottomText = new ImageView(context);
		mView = new View(context);

		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		addView(mLogo, LP.lp(DimensionUtil.h(200), DimensionUtil.h(200)));
		addView(mTextLogo);
		addView(mView, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(500)));
		addView(mBottomText);

		LU.setMargin(mLogo, 0, 150, 0, 0);
		LU.setMargin(mTextLogo, 0, 50, 0, 0);
		mLogo.setVisibility(INVISIBLE);
		mTextLogo.setVisibility(INVISIBLE);
	}

	@Override
	public void onLanguageChanged() {
		//mText.setText("每一个粉丝都将走向明星之路");
	}

	@Override
	public void onThemeChanged() {
		mLogo.setBackgroundResource(R.drawable.app_logo);
		TU.setImageBitmap(BitmapId.welcome_text_logo, mTextLogo);
		TU.setImageBitmap(BitmapId.welcome_bottom_text, mBottomText);
		//TU.setTextColor(ColorId.welcome_text, mText);
		//TU.setTextSize(32, mText);
		TU.setBGColor(ColorId.main_bg, this, mView);
	}

	private void startAnimation() {
		AlphaAnimation logoAnimation = new AlphaAnimation(0, 1);
		logoAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				mLogo.setVisibility(VISIBLE);
				mTextLogo.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				BaseHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(ContextManager.activity(), LoginActivity.class);
						ContextManager.activity().startActivity(intent);
						ContextManager.activity().finish();
					}
				}, 500);
			}
		});
		logoAnimation.setDuration(1000);
		TranslateAnimation textLogoAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		textLogoAnimation.setDuration(1000);
		mTextLogo.startAnimation(textLogoAnimation);
		mLogo.startAnimation(logoAnimation);
	}

}
