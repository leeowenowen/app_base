package com.msjf.fentuan.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public class CircleShape extends RectShape {
	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float x = rectF.left + rectF.width() / 2;
		float y = rectF.top + rectF.height() / 2;
		float max = (rectF.width() > rectF.height()) ? rectF.width() : rectF.height();
		float r = max / 2;
		canvas.drawCircle(x, y, r, paint);
	}
}
