package com.owo.ui;

import android.view.View;
import android.widget.PopupWindow;

import com.msjf.fentuan.R;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;

public class ConfigurablePopupWindow extends PopupWindow implements ThemeObserver, LanguageObserver {
	private View mInterceptedContentView;

	@Override
	public void setContentView(View contentView) {
		// in constructor of PopupWindow, #setContentView will be invoked
		// some known existing bug in 2.x devices may lead to crash
		// TODO: read source code of #PopupWindow of SDK 2.X
		if (contentView == null) {
			return;
		}
		setOutsideTouchable(false);
		setFocusable(true);
		setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.popup_bg));
		mInterceptedContentView = contentView;
		super.setContentView(contentView);
		onContentViewSetted();
		onThemeChanged();
	}

	private void onContentViewSetted() {
		Singleton.of(Language.class).addObserver(new LanguageObserver() {
			@Override
			public void onLanguageChanged() {
				ConfigurablePopupWindow.this.onLanguageChanged();
				Language.notifyChanged(mInterceptedContentView);
			}
		});
		Singleton.of(Theme.class).addObserver(new ThemeObserver() {

			@Override
			public void onThemeChanged() {
				ConfigurablePopupWindow.this.onThemeChanged();
				Theme.notifyChanged(mInterceptedContentView);
			}
		});
	}

	@Override
	public void onLanguageChanged() {
	}

	@Override
	public void onThemeChanged() {
	}

}
