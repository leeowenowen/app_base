package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.ui.utils.LP;

/**
 * Created by wangli on 15-5-24.
 */
public class OkCancelDialogView extends LinearLayout implements ThemeObserver, LanguageObserver{
    protected TextView mTitle;
    protected ImageView mClose;
    protected ImageView mOk;
    protected LinearLayout mTitleLayout;
    protected FrameLayout mContentView;

    public OkCancelDialogView(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onThemeChanged();
        onLanguageChanged();

    }

    protected void initComponents(Context context)
    {
        mTitle = new TextView(context);
        mTitle.setGravity(Gravity.CENTER);
        mClose = new ImageView(context);
        mOk = new ImageView(context);

        mTitleLayout = new LinearLayout(context);
        mTitleLayout. setGravity(Gravity.CENTER_VERTICAL);
        mTitleLayout.addView(mClose);
        mTitleLayout.addView(mTitle, LP.L0W1);
        mTitleLayout.addView(mOk);

        mContentView = new FrameLayout(context);

        setOrientation(VERTICAL);
        addView(mTitleLayout);
        addView(mContentView);
        LU.setPadding(mTitleLayout, 20, 10, 20, 10);
        LU.setMargin(mContentView, 0, 20, 0, 0);
        LU.setPadding(mContentView, 20, 10, 20, 0);
    }


    protected void setupListeners() {
        mOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mClient.onClose();
            }
        });
        mClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mClient.onOk();
            }
        });
    }

    @Override
    public void onThemeChanged() {
        TU.setTextColor(ColorId.highlight_color, mTitle);
        TU.setTextSize(27, mTitle);
        TU.setImageBitmap(BitmapId.movie_ok, mOk);
        TU.setImageBitmap(BitmapId.movie_cancel, mClose);
        TU.setBold(mTitle);
    }

    public void setContentView(View v)
    {
        mContentView.removeAllViews();
        mContentView.addView(v);
    }

    private Client mClient;

    @Override
    public void onLanguageChanged() {

    }

    public static interface Client {
        void onClose();

        void onOk();
    }

    public void setClient(Client client) {
        mClient = client;
    }

}
