package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class DividerView extends View implements ThemeObserver {

	public DividerView(Context context, int hPix) {
		super(context);
		ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, DimensionUtil.h(hPix));
		setLayoutParams(lParams);
	}

	public DividerView(Context context) {
		this(context, 2);
	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		setBackgroundColor(theme.color(ColorId.view_divider));
	}

}
