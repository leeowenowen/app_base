package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;

public class CommonPopupText extends TextView implements ThemeObserver {

	public CommonPopupText(Context context) {
		super(context);
		onThemeChanged();
	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.highlight_color, this);
		TU.setTextSize(27, this);
		setLineSpacing(DimensionUtil.h(10), 1);
		LU.setPadding(this, 55, 10, 10, 10);
	}

}
