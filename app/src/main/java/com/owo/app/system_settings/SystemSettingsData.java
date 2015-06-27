package com.owo.app.system_settings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;

import com.owo.base.util.TextHelper;

public class SystemSettingsData {
	Map<String, String> mMap = new HashMap<String, String>();

	private SystemSettingsData() {
		set(SystemSettingKeys.SupportedLanguage, "zh-CN##en-US");
		set(SystemSettingKeys.Language, "zh-CN");
	}

	public String get(String key) {
		return mMap.get(key);
	}

	public void set(String key, String value) {
		String oldValue = mMap.get(key);
		if (TextUtils.equals(oldValue, value)) {
			return;
		}
		mMap.put(key, value);
		Set<Observer> keyObservers = mObservers.get(key);
		if (keyObservers != null) {
			for (Observer observer : keyObservers) {
				observer.onDataChanged(key, oldValue, value);
			}
		}
	}

	public void append(String key, String value) {
		String old = get(key);
		if (!TextHelper.isEmptyOrSpaces(old)) {
			value = old + "##" + value;
		}
		set(key, value);
	}

	public static interface Observer {
		void onDataChanged(String key, String oldValue, String newValue);
	}

	private Map<String, Set<Observer>> mObservers = new HashMap<String, Set<Observer>>();

	public void addObserver(String key, Observer observer) {
		Set<Observer> keyObservers = mObservers.get(key);
		if (keyObservers == null) {
			keyObservers = new HashSet<Observer>();
			mObservers.put(key, keyObservers);
		}
		keyObservers.add(observer);
	}

	public void removeObserver(String key, Observer observer) {
		Set<Observer> keyObservers = mObservers.get(key);
		if (keyObservers != null) {
			keyObservers.remove(observer);
		}
	}
}
