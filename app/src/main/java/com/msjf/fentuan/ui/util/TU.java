package com.msjf.fentuan.ui.util;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

import org.w3c.dom.Text;

/*
 * Theme UTIL
 */
public class TU {
	public static void setTextSize(int size, TextView... tViews) {
		for (TextView tView : tViews) {
			tView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(size));
		}
	}

	public static void setTextColor(ColorId id, TextView... tViews) {
		for (TextView tView : tViews) {
			tView.setTextColor(Singleton.of(Theme.class).color(id));
		}
	}

	public static void setBold(TextView tv)
	{
		tv.getPaint().setFakeBoldText(true);
	}

	public static void setBGColor(ColorId id, View... views) {
		for (View v : views) {
			v.setBackgroundColor(Singleton.of(Theme.class).color(id));
		}
	}

	public static void setImageBitmap(BitmapId id, ImageView... imageViews) {
		for (ImageView imageView : imageViews) {
			imageView.setImageBitmap(Singleton.of(Theme.class).bitmap(id));
		}
	}

	@SuppressWarnings("deprecation")
	public static void setBgDrawable(DrawableId id, View... views) {
		for (View v : views) {
			v.setBackgroundDrawable((Singleton.of(Theme.class).drawable(id)));
		}
	}

	public static void setImageDrawable(DrawableId id, ImageView... views) {
		for (ImageView v : views) {
			v.setImageDrawable((Singleton.of(Theme.class).drawable(id)));
		}
	}

	@SuppressWarnings("deprecation")
	public static Drawable makeBtnPressedBG(BitmapId normal, BitmapId pressed) {
		Theme theme = Singleton.of(Theme.class);
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { android.R.attr.state_pressed }, pressed == null ? null
				: new BitmapDrawable(theme.bitmap(pressed)));
		drawable.addState(new int[] { -android.R.attr.state_pressed }, normal == null ? null
				: new BitmapDrawable(theme.bitmap(normal)));
		drawable.addState(new int[] {},
				normal == null ? null : new BitmapDrawable(theme.bitmap(normal)));
		return drawable;
	}

	@SuppressWarnings("deprecation")
	public static Drawable makeBtnSelectedBG(BitmapId normal, BitmapId selected) {
		Theme theme = Singleton.of(Theme.class);
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { android.R.attr.state_selected }, selected == null ? null
				: new BitmapDrawable(theme.bitmap(selected)));
		drawable.addState(new int[] { -android.R.attr.state_selected }, normal == null ? null
				: new BitmapDrawable(theme.bitmap(normal)));
		drawable.addState(new int[] {},
				normal == null ? null : new BitmapDrawable(theme.bitmap(normal)));
		return drawable;
	}

	@SuppressWarnings("deprecation")
	public static Drawable makeBtnCheckBg(BitmapId normal, BitmapId selected) {
		Theme theme = Singleton.of(Theme.class);
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { android.R.attr.state_checked }, selected == null ? null
				: new BitmapDrawable(theme.bitmap(selected)));
		drawable.addState(new int[] { -android.R.attr.state_checked }, normal == null ? null
				: new BitmapDrawable(theme.bitmap(normal)));
		drawable.addState(new int[] {},
				normal == null ? null : new BitmapDrawable(theme.bitmap(normal)));
		return drawable;
	}
}
