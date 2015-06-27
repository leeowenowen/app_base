package com.owo.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.owo.app.common.ContextManager;

public class BitmapHelper {
	// Decode

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

	public static final int WRAP_CONTENT = -1;

	/**
	 * @param maxWidth
	 *            the maximum width size or {@link #WRAP_CONTENT}
	 * @param maxHeight
	 *            the maximum height size or {@link #WRAP_CONTENT}
	 * @param config
	 *            {@link Config#RGB_565} or {@link Config#ARGB_8888}
	 */
	public static Bitmap decodeBitmap(int pathType, String filePath, int maxWidth, int maxHeight,
			Rect outPaddings, Config config) {
		InputStream stream = FileHelper.open(pathType, filePath);
		if (stream == null) {
			return null;
		}

		Bitmap bitmap = decodeBitmap(stream, maxWidth, maxHeight, outPaddings, config);

		try {
			stream.close();
		} catch (IOException e) {
		}
		return bitmap;
	}

	public static Bitmap decodeBitmapThumbnail(int pathType, String filePath, int maxWidth,
			int maxHeight, Rect outPaddings, Config config) {
		InputStream stream = FileHelper.open(pathType, filePath);
		if (stream == null) {
			return null;
		}

		Bitmap bitmap = decodeBitmap(stream, maxWidth, maxHeight, outPaddings, config);

		try {
			stream.close();
		} catch (IOException e) {
		}
		return bitmap;
	}

	/**
	 * @param maxWidth
	 *            the maximum width size or {@link #WRAP_CONTENT}
	 * @param maxHeight
	 *            the maximum height size or {@link #WRAP_CONTENT}
	 * @param config
	 *            {@link Config#RGB_565} or {@link Config#ARGB_8888}
	 */
	public static Bitmap decodeBitmap(InputStream stream, int maxWidth, int maxHeight,
			Rect outPaddings, Config config) {
		// 1) decode
		Options op = getOptions();
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = config;
		Bitmap bitmap = BitmapFactory.decodeStream(stream, outPaddings, op);
		bitmap.setDensity(Bitmap.DENSITY_NONE);

		// 2) ensure size
		return ensureBitmapSize(bitmap, maxWidth, maxHeight);
	}

	/**
	 * @param maxWidth
	 *            the maximum width size or {@link #WRAP_CONTENT}
	 * @param maxHeight
	 *            the maximum height size or {@link #WRAP_CONTENT}
	 * @param config
	 *            {@link Config#RGB_565} or {@link Config#ARGB_8888}
	 */
	public static Bitmap decodeBitmap(byte[] data, int offset, int length, int maxWidth,
			int maxHeight, Config config) {
		// 1) decode
		Options op = getOptions();
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = config;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, op);
		bitmap.setDensity(Bitmap.DENSITY_NONE);

		// 2) ensure size
		return ensureBitmapSize(bitmap, maxWidth, maxHeight);
	}

	private static Bitmap ensureBitmapSize(Bitmap bitmap, int maxWidth, int maxHeight) {
		if (maxWidth != WRAP_CONTENT || maxHeight != WRAP_CONTENT) {
			// 1) calc size
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			float scale = Math.max(//
					maxWidth != WRAP_CONTENT ? ((float) maxWidth / width) : 0, //
					maxHeight != WRAP_CONTENT ? ((float) maxHeight / height) : 0);
			if (maxWidth == WRAP_CONTENT) {
				width = Math.round(width * scale);
			} else if (maxHeight == WRAP_CONTENT) {
				height = Math.round(height * scale);
			}

			// 2) do scale
			if (scale != 1f) {
				Bitmap tmp = bitmap;
				bitmap = createScaledBitmap(tmp, width, height);
				tmp.recycle();
			}
		}
		return bitmap;
	}

	/**
	 * Read bitmap info (size, mimetype) without create Bitmap object.<br/>
	 * ATTENTION: The returned value is readonly.
	 */
	public static Options readBitmapInfo(int pathType, String filePath) {
		InputStream stream = FileHelper.open(pathType, filePath);
		if (stream == null) {
			return null;
		}
		Options op = BitmapHelper.readBitmapInfo(stream);
		try {
			stream.close();
		} catch (IOException e) {
		}

		return op;
	}

	/**
	 * Read bitmap info (size, mimetype) without create Bitmap object.<br/>
	 * ATTENTION:<br/>
	 * 1) The stream need to be re-opened after this.<br/>
	 * 2) The returned value is readonly.<br/>
	 */
	public static Options readBitmapInfo(InputStream stream) {
		Options op = getOptions();
		op.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(stream, null, op);
		return op;
	}

	// Scale

	private static final Matrix SCALE_MATRIX;
	private static final Paint SCALE_PAINT;
	static {
		SCALE_MATRIX = new Matrix();
		SCALE_PAINT = new Paint();
		SCALE_PAINT.setFilterBitmap(true);
		SCALE_PAINT.setAntiAlias(true);
	}

	public static Bitmap createScaledBitmap(Bitmap source, int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
		bitmap.setDensity(Bitmap.DENSITY_NONE);

		/**
		 * The only way to detach a bitmap from a canvas is to invoke
		 * Canvas.setBitmap(null), but it only works on 4.x (throw
		 * NullPointerException otherwise). So we can't reuse the canvas,
		 * otherwise the bitmap will be kept referenced.
		 */
		Canvas canvas = new Canvas();
		int oldWidth = source.getWidth();
		int oldHeight = source.getHeight();
		if (width != oldWidth || height != oldHeight) {
			SCALE_MATRIX.setScale((float) width / oldWidth, (float) height / oldHeight);
			canvas.setMatrix(SCALE_MATRIX);
		} else {
			canvas.setMatrix(null);
		}

		canvas.setBitmap(bitmap);
		canvas.drawBitmap(source, 0, 0, SCALE_PAINT);

		return bitmap;
	}

	public static Bitmap toBitmap(byte[] data) {
		Bitmap bitmap = null;

		try {
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		} catch (OutOfMemoryError e) {
		}

		return bitmap;
	}

	public static byte[] toByteArray(Bitmap bmp) {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, ostream);
		return ostream.toByteArray();
	}

	static public Bitmap drawableToBitmap(Drawable drawable) {
		if (null == drawable) {
			return null;
		}
		if (drawable instanceof BitmapDrawable) {
			try {
				BitmapDrawable thisDrawable = (BitmapDrawable) drawable;
				return thisDrawable.getBitmap();
			} catch (Exception e) {
			}
		}
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	public static void saveBitmap(Bitmap b, String path) {
		FileOutputStream fos = null;
		try {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}

			fos = new FileOutputStream(f);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap loadBitmap(String path) {
		return decodeBitmap(FileHelper.TYPE_FILE_SYSTEM, path, WRAP_CONTENT, WRAP_CONTENT, null,
				null);
	}

	@SuppressWarnings("deprecation")
	public static BitmapDrawable getResourceBmpDrawable(int id, int width, int height) {
		Bitmap bmp = getResourceBitmap(id, width, height);
		return bmp == null ? null : new BitmapDrawable(bmp);
	}

	public static Bitmap getResourceBitmap(int id, int width, int height) {
		Bitmap source = BitmapFactory.decodeResource(ContextManager.resources(), id, null);
		if (source == null) {
			return null;
		}
		return createScaledBitmap(source, width, height);
	}

	public static Bitmap getScaledResourceBitmap(int id) {
		Bitmap source = BitmapFactory.decodeResource(ContextManager.resources(), id, getOptions());
		if (source == null) {
			return null;
		}
		int width = source.getWidth();
		int height = source.getHeight();
		return createScaledBitmap(source, DimensionUtil.w(width), DimensionUtil.h(height));
	}
}