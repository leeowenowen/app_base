package com.owo.app.theme;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;

import com.owo.app.common.Check;
import com.owo.base.util.DimensionUtil;

public class ScaledNinePatchDrawable extends NinePatchDrawable {
	private final float mDisplayScale;

	private String mTraceContent;

	public ScaledNinePatchDrawable(Bitmap bitmap, byte[] chunk, Rect padding, float scale) {
		super(null, bitmap, chunk, padding, null);

		mDisplayScale = scale;
		if (scale != 1f) {
			/**
			 * Check the source code and you will see setFilterBitmap(true)
			 * won't take effect on ROM 2.x.
			 */
			getPaint().setFilterBitmap(true);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		final float scale = mDisplayScale;
		boolean isRecycled = false;
		if (scale != 1f) {
			canvas.save();
			canvas.scale(scale, scale);

			try {
				super.draw(canvas);
			} catch (Exception ex) {
				isRecycled = true;
			}

			canvas.restore();
		} else {
			try {
				super.draw(canvas);
			} catch (Exception ex) {
				isRecycled = true;
			}
		}

		Check.d(!isRecycled, mTraceContent);
	}

	public void setBounds(int left, int top, int right, int bottom) {
		final float scale = mDisplayScale;
		if (scale != 1f) {
			// super.setBounds((int) FloatMath.floor(left / scale),
			// (int) FloatMath.floor(top / scale), (int) FloatMath.ceil(right /
			// scale),
			// (int) FloatMath.ceil(bottom / scale));
			super.setBounds(DimensionUtil.rw(left), DimensionUtil.rh(top), DimensionUtil.rw(right),
					DimensionUtil.rh(bottom));
		} else {
			super.setBounds(left, top, right, bottom);
		}
	}

	@Override
	public int getIntrinsicWidth() {
		// return (int) FloatMath.ceil(super.getIntrinsicWidth() *
		// mDisplayScale);
		return DimensionUtil.w(super.getIntrinsicWidth());
	}

	@Override
	public int getIntrinsicHeight() {
		// return (int) FloatMath.ceil(super.getIntrinsicHeight() *
		// mDisplayScale);
		return DimensionUtil.h(super.getIntrinsicHeight());
	}

	@Override
	public int getMinimumHeight() {
		// return (int) FloatMath.ceil(super.getMinimumHeight() *
		// mDisplayScale);
		return DimensionUtil.h(super.getMinimumHeight());
	}

	@Override
	public int getMinimumWidth() {
		// return (int) FloatMath.ceil(super.getMinimumWidth() * mDisplayScale);
		return DimensionUtil.w(super.getMinimumWidth());
	}
}
