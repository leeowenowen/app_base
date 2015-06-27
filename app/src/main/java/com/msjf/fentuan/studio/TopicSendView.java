package com.msjf.fentuan.studio;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.msjf.fentuan.ui.ChatViewBase;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class TopicSendView extends ChatViewBase {

	public TopicSendView(Context context) {
		super(context);
		LinearLayout.LayoutParams editLayoutParams = (LinearLayout.LayoutParams) mTalkEdit
				.getLayoutParams();
		editLayoutParams.height = DimensionUtil.h(115);
		LU.setMargin(mTalkEdit, 23, 0, 18, 0);
		LU.setMargin(mBtnSend, 25, 0, 0, 0);

		LinearLayout.LayoutParams myLayoutParams = (LinearLayout.LayoutParams) mTalkEdit
				.getLayoutParams();
		myLayoutParams.height = DimensionUtil.h(160);
	}

	@Override
	public void onLanguageChanged() {
		mTalkEdit.setHint("#说个话题把．140个字以内#");
		mLeftText.setText("今日话题");
		mLeftText.setEms(2);
	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		mLeftIcon.setImageBitmap(theme.bitmap(BitmapId.studio_today_topic_icon));
		TU.setImageBitmap(BitmapId.studio_today_topic_add, mFaceIcon);
		TU.setImageBitmap(BitmapId.main_chat_send, mBtnSend);
		mSeperator.setBackgroundColor(theme.color(ColorId.talk_sep));
		TU.setTextColor(ColorId.main_text_color, mTalkEdit, mLeftText);
		TU.setTextSize(27, mTalkEdit, mLeftText);
		mTalkEdit.setHintTextColor(theme.color(ColorId.main_text_color));
		mTalkEdit.setBackgroundColor(Color.WHITE);
	}

}
