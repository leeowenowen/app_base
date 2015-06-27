package com.msjf.fentuan.ui.util;

import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import com.owo.base.util.DimensionUtil;

/*
 * Layout UTIL
 */
public class LU {

	public static void setPadding(View v, int left, int top, int right, int bottom) {
		v.setPadding(left == 0 ? 0 : DimensionUtil.w(left), top == 0 ? 0 : DimensionUtil.h(top),
				right == 0 ? 0 : DimensionUtil.w(right), bottom == 0 ? 0 : DimensionUtil.h(bottom));
	}

	public static void setMargin(View v, int left, int top, int right, int bottom) {
		MarginLayoutParams lParams = (MarginLayoutParams) v.getLayoutParams();
		lParams.leftMargin = (left == 0 ? 0 : DimensionUtil.w(left));
		lParams.topMargin = (top == 0 ? 0 : DimensionUtil.w(top));
		lParams.rightMargin = (right == 0 ? 0 : DimensionUtil.w(right));
		lParams.bottomMargin = (bottom == 0 ? 0 : DimensionUtil.w(bottom));
	}
}
