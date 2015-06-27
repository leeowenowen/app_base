package com.msjf.fentuan.hongbao;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;

public class HongBaoTipView extends LinearLayout implements ThemeObserver, LanguageObserver {

    private ImageView mIcon;
    private TextView mCenterText;
    private Button mOpen;
    private Double mAmount;

    public HongBaoTipView(Context context, Double amount) {
        super(context);

        mAmount = amount;

        mIcon = new ImageView(context);
        mCenterText = new TextView(context);
        mOpen = new Button(context);

        LU.setPadding(this, 25, 0, 25, 0);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        addView(mIcon);
        addView(mCenterText);
        addView(mOpen);
        LayoutParams openParams = (LinearLayout.LayoutParams) mOpen.getLayoutParams();
        openParams.height = DimensionUtil.h(100);

        LU.setMargin(mIcon, 0, 120, 0, 25);
        LU.setMargin(mOpen, 0, 40, 0, 0);
        mCenterText.setGravity(Gravity.CENTER);
        mOpen.setGravity(Gravity.CENTER);

        onLanguageChanged();
        onThemeChanged();
    }

    @Override
    public void onLanguageChanged() {
        mCenterText.setText("恭喜!\n您获得粉团专场红包" + mAmount + "元!");
        mOpen.setText("打开红包");
    }

    @Override
    public void onThemeChanged() {
        TU.setTextColor(ColorId.main_text_inverse_color, mCenterText);
        TU.setTextSize(26, mCenterText);
        TU.setTextSize(40, mOpen);
        TU.setTextColor(ColorId.highlight_color, mOpen);
        TU.setImageBitmap(BitmapId.hongbao_fentuan_icon, mIcon);
        setBackgroundResource(R.drawable.hongbao_bg);
        mOpen.setBackgroundResource(R.drawable.hongbao_yellow);
    }

    public void setOpenCallback(final Runnable r) {
        mOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                r.run();
            }
        });
    }
}
