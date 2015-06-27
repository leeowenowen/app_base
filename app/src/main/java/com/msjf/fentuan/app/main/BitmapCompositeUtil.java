package com.msjf.fentuan.app.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BitmapCompositeUtil {
	public static Bitmap composite(Bitmap bg, Bitmap fg, int xOff, int yOff) {
		Canvas canvas = new Canvas(bg);
		canvas.translate(xOff, yOff);
		canvas.drawBitmap(fg, 0, 0, null);
		return bg;
	}
}
