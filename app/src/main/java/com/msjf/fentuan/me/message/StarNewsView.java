package com.msjf.fentuan.me.message;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.ChatViewBase;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

/**
 * Created by wangli on 15-5-24.
 */
public class StarNewsView extends ScrollView implements ThemeObserver, LanguageObserver {
    private TextView mTitle;
    private ImageView mImage;
    private TextView mContent;
    private LinearLayout mContainer;
    private SendMessageView mSendMessageView;

    public StarNewsView(Context context) {
        super(context);

        mTitle = new TextView(context);
        mImage = new ImageView(context);
        mContent = new TextView(context);
        mSendMessageView = new SendMessageView(context);
        mTitle.setGravity(Gravity.CENTER);

        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        mContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        mContainer.addView(mTitle);
        mContainer.addView(mImage, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(200)));
        mContainer.addView(mContent);
        mContainer.addView(mSendMessageView);
        mSendMessageView.setDelegate(mDelegate);
        addView(mContainer);

        onLanguageChanged();
        onThemeChanged();
    }

    private ChatViewBase.Delegate mDelegate = new ChatViewBase.Delegate() {
        @Override
        public void onSendText(String text) {

        }

        @Override
        public void onSelectFace() {

        }
    };

    @Override
    public void onLanguageChanged() {
        TU.setTextSize(25, mContent);
        TU.setTextSize(25, mTitle);

        mTitle.setText("粉团星闻头条手法/全明星探度假提供");
        mContent.setText("新浪娱乐讯 近日有消息称黄晓明[微博]baby近期赴法国拍摄婚纱照。5月21日，有网友偶遇黄晓明和Baby在巴黎某名牌店挑家具，并在微博中曝光两人在家具店的照片。照片中，黄晓明和angelababy[微博]穿着黑色情侣装，在店员的介绍下认真交谈，黄晓明似乎全程都跟在baby身后，由baby决定，一副好老公的姿态，baby笑容满面，心情看上去很好。此微博曝光后，留言祝福声一片，称：“教主真要婚了？祝福女神！”\n" +
                "\n" +
                "　　近日有网友爆料黄晓明和baby将于今年10月完婚，婚宴地点在上海，更曝二人已在黄晓明的家乡青岛领证，目前双方已经飞往法国巴黎拍摄婚纱照。但据知情人透露，二人确实是今年准备完婚，但至于已领结婚证一说并未肯定。(我是弥尔)");

    }

    @Override
    public void onThemeChanged() {
        Theme theme = Singleton.of(Theme.class);
        TU.setTextColor(ColorId.main_text_color, mContent);
        TU.setTextColor(ColorId.highlight_color, mTitle);
        mImage.setBackgroundResource(R.drawable.title_image);
    }

    public class SendMessageView extends ChatViewBase {
        private TextView mShareText;
        private ImageView mShareIcon;

        public SendMessageView(Context context) {
            super(context);
        }

        @Override
        protected void initComponents(Context context) {
            super.initComponents(context);
            LU.setPadding(this, 20, 0, 20, 20);
            LU.setMargin(mTalkEdit, 30, 0, 30, 0);
            LU.setMargin(mBtnSend, 30, 0, 0, 0);

            mShareText = new TextView(context);
            mShareIcon = new ImageView(context);

            addView(mShareText);
            addView(mShareIcon, LP.lp(DimensionUtil.w(45), DimensionUtil.w(45)));
            LU.setMargin(mShareIcon, 20, 0, 0, 0);
        }

        @Override
        public void onLanguageChanged() {
            mTalkEdit.setHint("说吧");
            TU.setTextSize(25, mTalkEdit, mShareText);
        }

        @Override
        public void onThemeChanged() {
            Theme theme = Singleton.of(Theme.class);
            TU.setTextColor(ColorId.main_text_color, mTalkEdit, mShareText);
            TU.setTextColor(ColorId.highlight_color);
            mTalkEdit.setBackgroundColor(Color.WHITE);
            setBackgroundColor(theme.color(ColorId.main_page_bottom_bg));
            TU.setImageBitmap(BitmapId.hongbao_share_weixinquan, mShareIcon);
            TU.setImageDrawable(DrawableId.main_face,mLeftIcon);
        }

    }
}
