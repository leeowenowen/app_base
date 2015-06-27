package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class BottomButtonTextView extends TextView implements ThemeObserver {

	public BottomButtonTextView(Context context) {
		super(context);
		onThemeChanged();
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DimensionUtil.w(650),
				DimensionUtil.h(80));
		setLayoutParams(layoutParams);
		setGravity(Gravity.CENTER);
	}

	@Override
	public void onThemeChanged() {
		setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
		Theme theme = Singleton.of(Theme.class);
		setTextColor(theme.color(ColorId.main_page_text_color));
		TU.setBgDrawable(DrawableId.common_bottom_btn, this);
	}
}
