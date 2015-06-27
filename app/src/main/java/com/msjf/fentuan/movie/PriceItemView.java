package com.msjf.fentuan.movie;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.theme.BitmapId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;

public class PriceItemView extends LinearLayout implements ThemeObserver {

	public boolean mIsValid = true;
	public ImageView mIcon;
	public TextView mPrice;

	public PriceItemView(Context context) {
		super(context);
		mIcon = new ImageView(context);
		mPrice = new TextView(context);

		addView(mIcon);
		addView(mPrice);
	}

	public void setText(String price) {
		mPrice.setText(price);
	}

	public void setTextColor(int color) {
		mPrice.setTextColor(color);
	}
	
	

	public void setIsValid(boolean flag) {
		mIsValid = flag;
	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		mIcon.setImageBitmap(theme.bitmap(mIsValid ? BitmapId.hall_price_valid : BitmapId.hall_price_invalid));
	}

}
