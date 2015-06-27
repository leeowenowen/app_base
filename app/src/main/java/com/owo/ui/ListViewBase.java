package com.owo.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;

public class ListViewBase extends ListView {

	public ListViewBase(Context context) {
		super(context);
		setDivider(new ColorDrawable(Singleton.of(Theme.class).color(ColorId.view_divider)));
		setCacheColorHint(0);
		setDividerHeight(2);
	}

}
