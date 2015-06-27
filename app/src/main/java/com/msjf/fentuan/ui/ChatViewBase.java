package com.msjf.fentuan.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ThemeObserver;
import com.owo.ui.utils.LP;

public abstract class ChatViewBase extends LinearLayout implements ThemeObserver, LanguageObserver {
	public LinearLayout mLeftLayout;
	public ImageView mLeftIcon;
	public TextView mLeftText;
	public ImageView mSeperator;
	public EditText mTalkEdit;
	public ImageView mFaceIcon;
	public ImageView mBtnSend;

	public ChatViewBase(Context context) {
		super(context);
		setGravity(Gravity.CENTER_VERTICAL);
		mLeftIcon = new ImageView(context);
		mLeftText = new TextView(context);
		mSeperator = new ImageView(context);
		mFaceIcon = new ImageView(context);
		mBtnSend = new ImageView(context);
		mTalkEdit = new EditText(context);

		mLeftLayout = new LinearLayout(context);
		mLeftLayout.setGravity(Gravity.CENTER_VERTICAL);
		mLeftLayout.setOrientation(VERTICAL);
		mLeftLayout.addView(mLeftIcon);
		mLeftLayout.addView(mLeftText);

        setGravity(Gravity.CENTER_VERTICAL);
		addView(mLeftLayout);
		addView(mSeperator);
		addView(mTalkEdit, LP.L0W1);
		addView(mFaceIcon);
		addView(mBtnSend);

		mBtnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDelegate.onSendText(mTalkEdit.getText().toString());
			}
		});
		mFaceIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDelegate.onSelectFace();
			}
		});
		initComponents(context);
		onLanguageChanged();
		onThemeChanged();
	}

	protected void initComponents(Context context)
	{}

	public static interface Delegate {
		void onSendText(String text);

		void onSelectFace();
	}

	private Delegate mDelegate;

	public void setDelegate(Delegate delegate) {
		mDelegate = delegate;
	}

}
