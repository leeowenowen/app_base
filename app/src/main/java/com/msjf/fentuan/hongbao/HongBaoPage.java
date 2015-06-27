package com.msjf.fentuan.hongbao;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.share.ShareView;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.owo.app.theme.ColorId;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.utils.LP;

public class HongBaoPage extends BasePage {

    private TextView mTopText;
    private ImageView mImage;
    private TextView mHightLight;
    private TextView mTips;
    private BottomButtonTextView mShare;
    private LinearLayout mContent;
    private double mAmount;

    public HongBaoPage(Context context, double amount) {
        super(context);
        mAmount = amount;
    }

    private void updateHongBaoAmount() {
        mHightLight.setText("您获得粉团专场红包" + mAmount + "元");
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mTopText = new TextView(context);
        mImage = new ImageView(context);
        mHightLight = new TextView(context);
        mTips = new TextView(context);
        mShare = new BottomButtonTextView(context);
        mContent = new LinearLayout(context);
        mContent.setOrientation(VERTICAL);
        mContent.setGravity(Gravity.CENTER_HORIZONTAL);
        mContent.addView(mTopText);
        mContent.addView(mImage,
                LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(600)));
        mContent.addView(mHightLight);
        mContent.addView(mTips);
        mContent.addView(mShare);
        setContentView(mContent);

        LU.setPadding(mContent, 20, 20, 20, 0);
        LU.setMargin(mImage, 0, 15, 0, 30);
        LU.setMargin(mTips, 0, 24, 0, 30);

        mTopText.setGravity(Gravity.CENTER);
        mHightLight.setGravity(Gravity.CENTER);
        mTips.setGravity(Gravity.CENTER);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                ShareView view = new ShareView(getContext());
                view.setCancelCallback(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(700);
                param.height = DimensionUtil.h(600);

                dialog.getWindow().setAttributes(param);
                dialog.show();
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("粉团红包");
        mTopText.setText("粉团粉丝专场红包");
        mTips.setText("红包一经放入您的账户" + Singleton.of(Self.class).getUser().getPhone());
        mShare.setText("惊喜分享");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        mImage.setBackgroundResource(R.drawable.title_image);
        mTopText.setBackgroundResource(R.drawable.hongbao_green);
        TU.setTextColor(ColorId.main_text_color, mTips);
        TU.setTextColor(ColorId.main_text_inverse_color, mTopText);
        TU.setTextColor(ColorId.highlight_color, mHightLight);
        TU.setTextSize(32, mHightLight, mTips);
        TU.setTextSize(25, mTopText);

    }

}
