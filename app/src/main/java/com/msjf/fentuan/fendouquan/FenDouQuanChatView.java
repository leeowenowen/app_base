package com.msjf.fentuan.fendouquan;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.msjf.fentuan.ui.ChatViewBase;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;


public class FenDouQuanChatView extends ChatViewBase {

    public FenDouQuanChatView(Context context) {
        super(context);
        setPadding(DimensionUtil.w(20), DimensionUtil.h(20), DimensionUtil.w(20),
                DimensionUtil.h(20));
        LinearLayout.LayoutParams editLayoutParams = (LinearLayout.LayoutParams) mTalkEdit
                .getLayoutParams();
        editLayoutParams.leftMargin = DimensionUtil.w(30);
        editLayoutParams.rightMargin = DimensionUtil.w(30);

        LinearLayout.LayoutParams sendLayoutParams = (LinearLayout.LayoutParams) mBtnSend
                .getLayoutParams();
        sendLayoutParams.leftMargin = DimensionUtil.w(30);
    }

    @Override
    public void onLanguageChanged() {
        mTalkEdit.setHint("评论");
        TU.setTextSize(25, mTalkEdit);
    }

    @Override
    public void onThemeChanged() {
        Theme theme = Singleton.of(Theme.class);
        TU.setImageDrawable(DrawableId.main_face, mFaceIcon);
        mBtnSend.setImageBitmap(theme.bitmap(BitmapId.main_chat_send));
        mSeperator.setBackgroundColor(theme.color(ColorId.talk_sep));
        TU.setTextColor(ColorId.main_text_color, mTalkEdit);
        mTalkEdit.setBackgroundColor(Color.WHITE);
        setBackgroundColor(theme.color(ColorId.main_page_bottom_bg));
    }

}
