package com.owo.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.R;
import com.msjf.fentuan.hongbao.HongBaoActivity;
import com.msjf.fentuan.hongbao.HongBaoTipView;
import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class ConfigurableDialog extends Dialog implements ThemeObserver, LanguageObserver {
	public ConfigurableDialog(Context context, boolean hasTitle) {
		super(context);
		setCanceledOnTouchOutside(true);
		setCancelable(true);
		if (!hasTitle) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
	}

	private View mInterceptedContentView;

	@SuppressWarnings("deprecation")
	@Override
	public void setContentView(View contentView) {
		if (contentView == null) {
			return;
		}

		mInterceptedContentView = contentView;
		Drawable d = contentView.getBackground();
		if (d == null) {
			contentView.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.popup_bg));
		}
		super.setContentView(contentView);
		onContentViewSetted();
		onThemeChanged();
	}

	@SuppressWarnings("deprecation")
	public void setBg(int id) {
		mInterceptedContentView.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(id));
	}

	public void setBgDrawableId(int id) {
		mInterceptedContentView.setBackgroundResource(id);
	}

	private void onContentViewSetted() {
		Singleton.of(Language.class).addObserver(new LanguageObserver() {
			@Override
			public void onLanguageChanged() {
				ConfigurableDialog.this.onLanguageChanged();
				Language.notifyChanged(mInterceptedContentView);
			}
		});
		Singleton.of(Theme.class).addObserver(new ThemeObserver() {

			@Override
			public void onThemeChanged() {
				ConfigurableDialog.this.onThemeChanged();
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


	public static void show(int width, int height, View contentView)
	{
		ConfigurableDialog dialog = new ConfigurableDialog(ContextManager.context(), false);
		dialog.setContentView(contentView);
		WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
		param.width = DimensionUtil.w(width);
		param.height = DimensionUtil.h(height);
		dialog.getWindow().setAttributes(param);
		dialog.show();
	}

}