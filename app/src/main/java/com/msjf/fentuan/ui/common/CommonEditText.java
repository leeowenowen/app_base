package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.ui.utils.LP;

public class CommonEditText extends FrameLayout implements ThemeObserver {

	public TextView mText;
	private ImageView mArrow;

	public CommonEditText(Context context, boolean showArrow, boolean canEdit) {
		super(context);

		mText = canEdit ? new EditText(context) : new TextView(context);
		mArrow = new ImageView(context);

		addView(mText, LP.FMM);
		addView(mArrow, LP.FWWRCV);
		LU.setMargin(mArrow, 0, 0, 0, 3);
		mArrow.setVisibility(showArrow ? VISIBLE : GONE);
		onThemeChanged();
	}

	public void setText(String text) {
		mText.setText(text);
	}
	
	public String getText()
	{
		return mText.getText().toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onThemeChanged() {
		TU.setImageBitmap(BitmapId.common_down_arow, mArrow);
		mText.setBackgroundDrawable(null);
		TU.setTextSize(25, mText);
		Theme theme = Singleton.of(Theme.class);
		mText.setTextColor(theme.color(ColorId.main_text_inverse_color));
		mText.setHintTextColor(theme.color(ColorId.register_edit_text_hint));
	}

}
