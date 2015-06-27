package com.owo.app.theme;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.TypedValue;

import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.UiData;

public class ThemeUtil {
	public static final int WRAP_CONTENT = -1;
	private static final Rect OUT_PADDINGS;
	static {
		OUT_PADDINGS = new Rect();
	}
	private static final int NINE_PATCH_DPI = 320;
	private static float NINE_PATCH_SCALE = 1;

	static {
		NINE_PATCH_SCALE = Singleton.of(UiData.class).densityDpi() / NINE_PATCH_DPI;
	}

	public static Drawable getNinePatchDrawable(int id) {
		return loadDrawable(id, WRAP_CONTENT, WRAP_CONTENT);
	}

	private static final ThreadLocal<Options> sDecodeOptionsLocal = new ThreadLocal<Options>();

	public static Options getOptions() {
		Options op = sDecodeOptionsLocal.get();
		if (op == null) {
			op = new Options();
			op.inDither = false;
			op.inScaled = false;
			op.inSampleSize = 1;
			op.inTempStorage = new byte[16 * 1024];
			sDecodeOptionsLocal.set(op);
		}
		return op;
	}

	public static Bitmap decodeResource(Resources res, int id, Options opts, Rect paddings) {
		Bitmap bm = null;
		InputStream is = null;

		try {
			final TypedValue value = new TypedValue();
			is = res.openRawResource(id, value);

			bm = BitmapFactory.decodeResourceStream(res, value, is, paddings, opts);
		} catch (Exception e) {
			/*
			 * do nothing. If the exception happened on open, bm will be null.
			 * If it happened on close, bm is still valid.
			 */
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				// Ignore
			}
		}

		return bm;
	}

	private static Drawable loadDrawable(int id, int width, int height) {
		Bitmap bmp = decodeResource(ContextManager.resources(), id, getOptions(), OUT_PADDINGS);
		if (bmp == null) {
			return null;
		}
		if (OUT_PADDINGS.left > 0 || OUT_PADDINGS.right > 0 || OUT_PADDINGS.top > 0
				|| OUT_PADDINGS.bottom > 0) {
			if (NINE_PATCH_SCALE != 1f) {
				// OUT_PADDINGS.left *= NINE_PATCH_SCALE;
				// OUT_PADDINGS.right *= NINE_PATCH_SCALE;
				// OUT_PADDINGS.top *= NINE_PATCH_SCALE;
				// OUT_PADDINGS.bottom *= NINE_PATCH_SCALE;
				OUT_PADDINGS.left = DimensionUtil.w(OUT_PADDINGS.left);
				OUT_PADDINGS.right = DimensionUtil.w(OUT_PADDINGS.right);
				OUT_PADDINGS.top = DimensionUtil.h(OUT_PADDINGS.top);
				OUT_PADDINGS.bottom = DimensionUtil.h(OUT_PADDINGS.bottom);
			}
		}

		byte[] chunk = bmp.getNinePatchChunk();
		if (NinePatch.isNinePatchChunk(chunk)) {
			return new NinePatchDrawable(null, bmp, chunk, OUT_PADDINGS, null);
		}
		return null;
	}
}
