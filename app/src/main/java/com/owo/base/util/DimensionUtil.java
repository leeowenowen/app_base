package com.owo.base.util;

import android.util.DisplayMetrics;

import com.owo.app.common.ContextManager;

public class DimensionUtil {

	private static final int RELATIVE_WIDTH = 720;
	private static final int RELATIVE_HEIGHT = 1280;

	/*
	 * Screen info
	 */
	public static int rowHeight() {
		return w(150);
	}

	public static int iconSize() {
		return w(200);
	}

	public static int perW(int num) {
		return w(RELATIVE_WIDTH / num);
	}

	public static int perH(int num) {
		return h(RELATIVE_HEIGHT / num);
	}

	public static int w(int w) {
		if (displayMetrics().widthPixels == RELATIVE_WIDTH) {
			return w;
		}
		return w * displayMetrics().widthPixels / RELATIVE_WIDTH;
	}

	public static int h(int h) {
		if (displayMetrics().heightPixels == RELATIVE_HEIGHT) {
			return h;
		}
		return h * displayMetrics().heightPixels / RELATIVE_HEIGHT;
	}

	public static int rw(int w) {
		if (displayMetrics().widthPixels == RELATIVE_WIDTH) {
			return w;
		}
		return w * RELATIVE_WIDTH / displayMetrics().widthPixels;
	}

	public static int rh(int h) {
		if (displayMetrics().heightPixels == RELATIVE_HEIGHT) {
			return h;
		}
		return h * RELATIVE_HEIGHT / displayMetrics().heightPixels;
	}

	public static float wFactor(float w) {
		return w / RELATIVE_WIDTH;
	}

	public static float hFactor(float h) {
		return h / RELATIVE_HEIGHT;
	}

	public static float wIFactor(float w) {
		return RELATIVE_WIDTH / w;
	}

	public static float hIFactor(float h) {
		return RELATIVE_HEIGHT / h;
	}

	public static DisplayMetrics sDisplayMetrics = null;

	public static DisplayMetrics displayMetrics() {
		if (sDisplayMetrics == null) {
			sDisplayMetrics = new DisplayMetrics();
			ContextManager.activity().getWindowManager().getDefaultDisplay()
					.getMetrics(sDisplayMetrics);
		}
		return sDisplayMetrics;
	}

	public static int screenWidth() {
		return sDisplayMetrics.widthPixels;
	}

	public static int screenHeight() {
		return sDisplayMetrics.heightPixels;
	}

	public static int dip2Pixel(int dip) {
		return (int) displayMetrics().density * dip;
	}

	public static int sp2Pixel(int sp) {
		return (int) (sp * displayMetrics().scaledDensity);
	}

	public static float pointToPixel(float point) {
		double xdpi = displayMetrics().xdpi;
		return (float) (point * xdpi * (1.0f / 72));
	}

	public static float millimeterToPixel(float mm) {
		double xdpi = displayMetrics().xdpi;
		return (float) (mm * xdpi * (1.0f / 25.4f));
	}
}
