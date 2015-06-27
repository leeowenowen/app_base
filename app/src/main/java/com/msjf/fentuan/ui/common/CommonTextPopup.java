package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.WindowManager;

import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;

public class CommonTextPopup extends ConfigurableDialog {

	private CommonPopupText mText;

	public CommonTextPopup(Context context, boolean hasTitle) {
		super(context, hasTitle);
		mText = new CommonPopupText(context);
		mText.setSingleLine(false);
		WindowManager.LayoutParams param = getWindow().getAttributes();
		param.width = DimensionUtil.w(620);
		param.height = DimensionUtil.h(260);
		getWindow().setAttributes(param);
		setContentView(mText);
	}

	public CommonTextPopup text(String text) {
		mText.setText(text);
		return this;
	}

}
