package com.owo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Display;

import com.owo.app.common.ContextManager;
import com.owo.base.util.CompatHelper;

public class UiData {
	protected UiData() {
		// 1) density
		DisplayMetrics dm = ContextManager.resources().getDisplayMetrics();
		mDensity = dm.density;
		mDensityDpi = dm.densityDpi;

		// 2) screen size
		mScreenPixel = computeScreenSize();
		mScreenInch = computeScreenInch();

		// 3) orientation
		mOrientation = ContextManager.config().orientation;
	}

	// Events

	public static final int EVENT_ORIENTATION_CHANGED = 1;
	public static final int EVENT_WINDOW_SIZE_CHANGED = 2;

	// Density

	private final float mDensity;
	private final float mDensityDpi;

	public float density() {
		return mDensity;
	}

	public float densityDpi() {
		return mDensityDpi;
	}

	// Orientation

	public static final int ORIENTATION_PORTRAIT = Configuration.ORIENTATION_PORTRAIT;
	public static final int ORIENTATION_LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;

	private int mOrientation;

	public boolean isPortrait() {
		return mOrientation == ORIENTATION_PORTRAIT;
	}

	public int orientation() {
		return mOrientation;
	}

	/** The RootView of app should remember to update this property. */
	public void updateOrientation() {
		int newOrien = ContextManager.config().orientation;
		if (mOrientation != newOrien) {
			mOrientation = newOrien;
		}
	}

	// Lock Orientation

	private boolean mIsOrientationLockedPermanently = false;

	public void lockOrientationPermanent(int orientation) {
		mIsOrientationLockedPermanently = true;
		int lockOrien = orientation == ORIENTATION_PORTRAIT ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
				: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		ContextManager.activity().setRequestedOrientation(lockOrien);
	}

	public void lockOrientation() {
		lockOrientation(mOrientation);
	}

	public void lockOrientation(int orientation) {
		if (mIsOrientationLockedPermanently) {
			return;
		}

		if (CompatHelper.sdk(9)) {
			int lockOrien = orientation == ORIENTATION_PORTRAIT ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
					: ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
			ContextManager.activity().setRequestedOrientation(lockOrien);
		}
	}

	public void unlockOrientation() {
		if (mIsOrientationLockedPermanently) {
			return;
		}

		ContextManager.activity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	// Screen Size

	private final Point mScreenPixel;
	private final float mScreenInch;

	public float screenInch() {
		return mScreenInch;
	}

	/** The length of the shorter side of screen in pixels */
	public int screenShortSide() {
		return Math.min(mScreenPixel.x, mScreenPixel.y);
	}

	/** The length of the longer side of screen in pixels */
	public int screenLongSide() {
		return Math.max(mScreenPixel.x, mScreenPixel.y);
	}

	// Window Size

	private int mWinWidth;
	private int mWinHeight;

	/** Window horizontal size in pixel */
	public int winWidth() {
		return mWinWidth;
	}

	/** Window vertical size in pixel */
	public int winHeight() {
		return mWinHeight;
	}

	/** Only available for {@link #RootViewBase}. */
	public void winSize(int width, int height) {
		if (mWinWidth != width || mWinHeight != height) {
			mWinWidth = width;
			mWinHeight = height;
		}
	}

	// Calculation of Screen Size

	@SuppressLint("NewApi")
	private static Point computeScreenSize() {
		Activity activity = ContextManager.activity();
		Display dp = activity.getWindowManager().getDefaultDisplay();
		Point outSize = new Point();

		if (CompatHelper.sdk(13)) {
			try {
				if (CompatHelper.sdk(16)) {
					dp.getRealSize(outSize);
				} else {
					outSize.x = (Integer) Display.class.getMethod("getRawWidth").invoke(dp);
					outSize.y = (Integer) Display.class.getMethod("getRawHeight").invoke(dp);
				}
			} catch (Exception e) {
			}
		}
		if (outSize.x <= 0 || outSize.y <= 0) {
			DisplayMetrics dm = ContextManager.resources().getDisplayMetrics();
			outSize.x = dm.widthPixels;
			outSize.y = dm.heightPixels;
		}

		return outSize;
	}

	private float computeScreenInch() {
		float dpi = selectDpi();
		float widthInch = mScreenPixel.x / dpi;
		float heightInch = mScreenPixel.y / dpi;

		return FloatMath.sqrt(widthInch * widthInch + heightInch * heightInch);
	}

	/**
	 * Select correct dpi value from original DisplayMetrics, The value is used
	 * for calculating screen inches.
	 * <p>
	 * we pick up densityDpi, xdpi, ydpi from original DisplayMetrics, and the
	 * selection strategy is, densityDpi will be preferred as
	 * 
	 * <pre>
	 *          1) all they are regular value;
	 *          2) all they are NOT regular value;
	 *          3) densityDpi is regular, but the other two are both malformed(like 50 or 80 whatever)
	 * </pre>
	 * 
	 * </p>
	 * <p>
	 * xdpi or ydpi will be preferred as
	 * 
	 * <pre>
	 *          1) densityDpi is not regular and one of them is;
	 *          2) densityDpi is regular and one of them is not regular but correct.
	 * </pre>
	 * 
	 * </p>
	 * */
	private static float selectDpi() {
		final DisplayMetrics dm = ContextManager.resources().getDisplayMetrics();
		final float dDpi = dm.densityDpi;
		final float xDpi = dm.xdpi;
		final float yDpi = dm.ydpi;
		final boolean isXdpiRegular = isRegularDpi(xDpi);
		final boolean isYdpiReqular = isRegularDpi(yDpi);

		if (isRegularDpi(dDpi)) {
			boolean xyNearlySame = Math.abs(xDpi - yDpi) < XYDPI_DIFF_THRESHOLDS;
			if (xyNearlySame) {
				if (!isXdpiRegular && !isDpiMalformed(xDpi)) {
					return xDpi;
				} else if (!isYdpiReqular && !isDpiMalformed(yDpi)) {
					return yDpi;
				}
			}
		} else {
			if (isXdpiRegular) {
				return xDpi;
			} else if (isYdpiReqular) {
				return yDpi;
			}
		}
		return dDpi;
	}

	/**
	 * Define all supported dpi values. 120 ldpi, 160 mdpi, 240 hdpi, 320 xhdpi,
	 * 480 xxhdpi. And define all it's tolerance also.
	 * */
	private static final int[] REGULAR_DPI = new int[] { 120, 160, 240, 320, 480 };
	private static final int[] REGULAR_DPI_TOL = new int[] { 10, 20, 40, 40, 60 };
	private static final int XYDPI_DIFF_THRESHOLDS = 20;

	/**
	 * Test the specified dpi value is regular. Regular dpi is define in
	 * REGULAR_DPI array.
	 * 
	 * @return True if the dpi value is near to Regular value
	 * */
	private static boolean isRegularDpi(float dpi) {
		for (int i = 0; i < REGULAR_DPI.length; ++i) {
			if (Math.abs(dpi - REGULAR_DPI[i]) < REGULAR_DPI_TOL[i]) {
				return true;
			}
		}
		return false;
	}

	private static boolean isDpiMalformed(float dpi) {
		return dpi < REGULAR_DPI[0] || dpi > REGULAR_DPI[REGULAR_DPI.length - 1];
	}
}
