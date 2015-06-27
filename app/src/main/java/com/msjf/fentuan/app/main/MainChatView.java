package com.msjf.fentuan.app.main;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.msjf.fentuan.ui.ChatViewBase;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class MainChatView extends ChatViewBase {

	public MainChatView(Context context) {
		super(context);
		LU.setPadding(this, 20, 20, 20, 20);
		LU.setMargin(mTalkEdit, 30, 0, 30, 0);
		LU.setMargin(mBtnSend, 30, 0, 0, 0);
	}

	@Override
	public void onLanguageChanged() {
		mTalkEdit.setHint("说吧");
		TU.setTextSize(25, mTalkEdit);
	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		mLeftIcon.setImageBitmap(theme.bitmap(BitmapId.main_chat_small));
		TU.setImageDrawable(DrawableId.main_face, mFaceIcon);
		mBtnSend.setImageBitmap(theme.bitmap(BitmapId.main_chat_send));
		mSeperator.setBackgroundColor(theme.color(ColorId.talk_sep));
		TU.setTextColor(ColorId.main_text_color, mTalkEdit);
		mTalkEdit.setBackgroundColor(Color.WHITE);
		setBackgroundColor(theme.color(ColorId.main_page_bottom_bg));
	}

}
