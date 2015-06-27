package com.owo.app.language;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.view.View;
import android.view.ViewGroup;

import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.system_settings.SystemSettingsData.Observer;
import com.owo.base.pattern.Singleton;

/**
 * <pre>
 * SystemSettingsData --> Language(reload)-->Activity --> ContentView
 * </pre>
 * 
 * @author leeowenowen@gmail.com
 * 
 */
public class Language implements ILanguageProvider {
	private ILanguageProvider mProvider;

	private Language() {
		// 1) initialize providers
		addProvider("zh-CN", new zh_CN_Provider());
		addProvider("en-US", new en_US_Provider());
		initProvider();
		// 2) register observer
		Singleton.of(SystemSettingsData.class).addObserver(SystemSettingKeys.Language, new Observer() {
			@Override
			public void onDataChanged(String key, String oldValue, String newValue) {
				mProvider = provider(newValue);
				assert (mProvider != null);
				for (LanguageObserver observer : mObservers) {
					observer.onLanguageChanged();
				}
			}
		});
	}

	private void initProvider() {
		mProvider = mProviders.get("zh-CN");
	}

	@Override
	public String get(LanguageResourceKeys key) {
		return mProvider.get(key);
	}

	public static LanguageResourceKeys toLanguageResourceKey(String language) {
		if ("zh-CN".equals(language)) {
			return LanguageResourceKeys.ZH_CN;
		} else if ("en-US".equals(language)) {
			return LanguageResourceKeys.EN_US;
		}
		return LanguageResourceKeys.None;
	}

	public static String toLanguageString(LanguageResourceKeys key) {
		switch (key) {
		case ZH_CN:
			return "zh-CN";
		case EN_US:
			return "en-US";
		default:
			return "";
		}
	}

	private Map<String, ILanguageProvider> mProviders = new HashMap<String, ILanguageProvider>();

	public void addProvider(String key, ILanguageProvider provider) {
		mProviders.put(key, provider);
	}

	public void removeProvider(String key) {
		mProviders.remove(key);
	}

	public ILanguageProvider provider(String key) {
		return mProviders.get(key);
	}

	private Set<LanguageObserver> mObservers = new HashSet<LanguageObserver>();

	public void addObserver(LanguageObserver observer) {
		mObservers.add(observer);
	}

	public void removeObserver(LanguageObserver observer) {
		mObservers.remove(observer);
	}

	public static void notifyChanged(View v) {
		if (v instanceof LanguageObserver) {
			((LanguageObserver) v).onLanguageChanged();
		}
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); ++i) {
				notifyChanged(vg.getChildAt(i));
			}
		}
	}
}
