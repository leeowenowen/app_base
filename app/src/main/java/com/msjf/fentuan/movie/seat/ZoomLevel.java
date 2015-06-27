package com.msjf.fentuan.movie.seat;

import java.util.ArrayList;
import java.util.List;

import com.owo.base.util.DimensionUtil;

public class ZoomLevel {
	private static class Size {
		public int mW;
		public int mH;

		public Size(int w, int h) {
			mW = w;
			mH = h;
		}
	}

	private List<Size> mSize = new ArrayList<Size>();

	public ZoomLevel(int maxColumn) {
		int size = DimensionUtil.perW(maxColumn);
		mSize.add(new Size(size, size));
		mSize.add(new Size(size, size));
		mSize.add(new Size(size, size));
		mSize.add(new Size(size, size));
		mSize.add(new Size(size, size));
	}
}
