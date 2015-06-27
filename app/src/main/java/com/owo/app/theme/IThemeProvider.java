package com.owo.app.theme;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface IThemeProvider {
	int color(ColorId id);

	Drawable drawable(DrawableId id);

	Bitmap bitmap(BitmapId id);
}
