package com.owo.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.owo.base.util.DimensionUtil;

public class ShapeImageView extends View {
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND_RECT = 1;

	private final int mType;

	public ShapeImageView(Context context) {
		this(context, TYPE_CIRCLE);
	}

	public ShapeImageView(Context context, int type) {
		super(context);
		mType = type;
	}

	private Bitmap mBgBitmap;
	private Rect mBitmapRect = new Rect();
	private static Paint sPaint;
	static {
		sPaint = new Paint();
		sPaint.setAntiAlias(true);
		sPaint.setFilterBitmap(true);
		sPaint.setDither(true);
	}
	private static PorterDuffXfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

	public void setImageBitmap(Bitmap bitmap) {
		mBgBitmap = bitmap;
	}

	public static Bitmap makeDst(Bitmap src, Rect r, int type) {
		final int w = r.width();
		final int h = r.height();
		Bitmap bg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bg);
		canvas.drawARGB(0, 0, 0, 0);

		switch (type) {
		case TYPE_CIRCLE:
			canvas.drawOval(new RectF(0, 0, w, h), sPaint);
			break;
		case TYPE_ROUND_RECT:
			canvas.drawRoundRect(new RectF(0, 0, w, h), DimensionUtil.w(10), DimensionUtil.w(10),
					sPaint);
			break;
		default:
			break;
		}
		sPaint.setXfermode(sXfermode);
		canvas.drawBitmap(src, null, r, sPaint);
		sPaint.setXfermode(null);
		return bg;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (null == mBgBitmap) {
			return;
		}
		mBitmapRect.set(0, 0, getWidth(), getHeight());
		Bitmap dstBmp = makeDst(mBgBitmap, mBitmapRect, mType);
		canvas.drawBitmap(dstBmp, 0, 0, null);
	}
}
