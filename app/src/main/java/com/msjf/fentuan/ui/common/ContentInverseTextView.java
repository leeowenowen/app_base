package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class ContentInverseTextView extends TextView implements ThemeObserver {

	public ContentInverseTextView(Context context) {
		super(context);
		onThemeChanged();
	}

	@Override
	public void onThemeChanged() {
		setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
		Theme theme = Singleton.of(Theme.class);
		setTextColor(theme.color(ColorId.main_page_text_color));
	}
}
