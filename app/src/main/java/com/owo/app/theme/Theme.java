package com.owo.app.theme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.system_settings.SystemSettingsData.Observer;
import com.owo.base.pattern.Singleton;

/**
 * <pre>
 * SystemSettingsData --> Theme(reload)-->Activity --> ContentView
 * </pre>
 * 
 * @author leeowenowen@gmail.com
 * 
 */

public class Theme implements IThemeProvider {
	private IThemeProvider mCurThemeProvider;
	private final Map<String, IThemeProvider> mThemeProviders = new HashMap<String, IThemeProvider>();

	private Theme() {
		// 1) initialize provider
		mThemeProviders.put("Default", new DefaultThemeProvider());
		// mThemeProviders.put(
		// String.valueOf(Color.BLACK),
		// new
		// DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
		// .bgColor(Color.BLACK));
		// mThemeProviders.put(String.valueOf(Color.RED),
		// new
		// DefaultThemeProvider().textColor(Color.YELLOW).paintColor(Color.YELLOW)
		// .bgColor(Color.RED));
		// mThemeProviders.put(
		// String.valueOf(Color.BLUE),
		// new
		// DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
		// .bgColor(Color.BLUE));
		// mThemeProviders.put(
		// String.valueOf(Color.GREEN),
		// new
		// DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
		// .bgColor(Color.GREEN));
		initProvider();

		// 3) register observer
		Singleton.of(SystemSettingsData.class).addObserver(SystemSettingKeys.Theme, new Observer() {
			@Override
			public void onDataChanged(String key, String oldValue, String newValue) {
				mCurThemeProvider = mThemeProviders.get(newValue);
				assert (mCurThemeProvider != null);

				// TODO: ADD THEME CHANGE LOGIC HERE

				for (ThemeObserver observer : mObservers) {
					observer.onThemeChanged();
				}
			}
		});
	}

	private void initProvider() {
		// TODO: make theme provider type adaptable
		mCurThemeProvider = mThemeProviders.get("Default");
	}

	private Set<ThemeObserver> mObservers = new HashSet<ThemeObserver>();

	public void addObserver(ThemeObserver observer) {
		mObservers.add(observer);
	}

	public void removeObserver(ThemeObserver observer) {
		mObservers.remove(observer);
	}

	public static void notifyChanged(View v) {
		if (v instanceof ThemeObserver) {
			((ThemeObserver) v).onThemeChanged();
		}
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); ++i) {
				notifyChanged(vg.getChildAt(i));
			}
		}
	}

	public static void updateTheme(View v) {
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); ++i) {
				updateTheme(vg.getChildAt(i));
			}
		}
	}

	@Override
	public int color(ColorId id) {
		return mCurThemeProvider.color(id);
	}

	@Override
	public Drawable drawable(DrawableId id) {
		return mCurThemeProvider.drawable(id);
	}

	@Override
	public Bitmap bitmap(BitmapId id) {
		return mCurThemeProvider.bitmap(id);
	}
}
