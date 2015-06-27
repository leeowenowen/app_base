package com.msjf.fentuan.app.main;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;

public class MarkInfoView extends TextView implements ThemeObserver {

	public MarkInfoView(Context context) {
		super(context);
		onThemeChanged();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onThemeChanged() {
		setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.main_mark_info));
		TU.setTextColor(ColorId.main_text_inverse_color, this);
		TU.setTextSize(27, this);
		setGravity(Gravity.TOP);
	}

}
