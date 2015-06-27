package com.msjf.fentuan.member_info;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class InviteHostView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private ImageView mTitleLeft;
	private ImageView mContentLeft;
	private TextView mTitleRight;
	private TextView mContentRight;
	private ImageView mSend;
	private View mDividerHorizental, mDividerVertical;
	private LinearLayout mTitle, mContent;

	public InviteHostView(Context context) {
		super(context);

		mTitleLeft = new ImageView(context);
		mContentLeft = new ImageView(context);
		mTitleRight = new TextView(getContext());
		mContentRight = new TextView(getContext());
		mSend = new ImageView(context);
		mDividerHorizental = new View(context);
		mDividerVertical = new View(context);

		mTitle = new LinearLayout(context);
		mTitle.setGravity(Gravity.CENTER_VERTICAL);
		mTitle.addView(mTitleLeft);
		mTitle.addView(mTitleRight);

		mContent = new LinearLayout(context);
		mContent.setGravity(Gravity.CENTER_VERTICAL);
		mContent.addView(mContentLeft);
		mContent.addView(mContentRight);

		LinearLayout left = new LinearLayout(context);
		left.setOrientation(VERTICAL);
		left.addView(mTitle, LP.LM01);
		left.addView(mDividerVertical,
				LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(1)));
		left.addView(mContent, LP.LM01);

		setGravity(Gravity.CENTER_VERTICAL);
		addView(left, LP.lp(DimensionUtil.w(500), LinearLayout.LayoutParams.MATCH_PARENT));
		addView(mDividerHorizental,
				LP.lp(DimensionUtil.w(1), LinearLayout.LayoutParams.MATCH_PARENT));
		addView(mSend);

		LU.setPadding(mTitle, 22, 0, 0, 0);
		LU.setPadding(mContent, 22, 0, 0, 0);
		LU.setMargin(mSend, 20, 0, 20, 0);
		LU.setMargin(mTitleRight, 20, 0, 0, 0);
		LU.setMargin(mContentRight, 20, 0, 0, 0);
		onLanguageChanged();
		onThemeChanged();
		select(0);
		setupListeners();
	}

	private void setupListeners() {
		mTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				select(0);
			}
		});
		mContent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				select(1);
			}
		});
	}

	@Override
	public void onLanguageChanged() {
		mTitleRight.setText("有请大湿胸主持星话题!");
		mContentRight.setText("有请大湿兄主持星话题!");
	}

	private int mSelected;

	private void select(int index) {
		mSelected = index;
		if (index == 0) {
			TU.setBGColor(ColorId.highlight_color, mTitle);
			mContent.setBackgroundColor(Color.TRANSPARENT);
			TU.setImageBitmap(BitmapId.me_member_invite_mic_selected, mTitleLeft);
			TU.setImageBitmap(BitmapId.me_member_invite_mic, mContentLeft);
		} else {
			TU.setBGColor(ColorId.highlight_color, mContent);
			mTitle.setBackgroundColor(Color.TRANSPARENT);
			TU.setImageBitmap(BitmapId.me_member_invite_mic_selected, mContentLeft);
			TU.setImageBitmap(BitmapId.me_member_invite_mic, mTitleLeft);
		}
	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.main_text_color, mContentRight);
		TU.setTextColor(ColorId.main_text_inverse_color, mTitleRight);
		Theme theme = Singleton.of(Theme.class);
		mContentRight.setHintTextColor(theme.color(ColorId.gray_text_color));
		TU.setTextSize(27, mTitleRight, mContentRight);
		TU.setImageBitmap(BitmapId.main_chat_send, mSend);
		TU.setBGColor(ColorId.main_text_color, mDividerHorizental, mDividerVertical);
	}

}
