package com.owo.ui.utils;

import android.graphics.Color;

public class ColorUtil {
	public static int alpha(int color, int alpha) {
		return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
	}
}
