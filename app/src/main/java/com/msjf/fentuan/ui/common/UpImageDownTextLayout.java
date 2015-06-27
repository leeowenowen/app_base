package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wangli on 15-5-23.
 */
public class UpImageDownTextLayout extends LinearLayout {
    public ImageView mImage;
    public TextView mText;

    public UpImageDownTextLayout(Context context) {
        super(context);

        mImage = new ImageView(context);
        mText = new TextView(context);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        addView(mImage);
        addView(mText);
    }
}
