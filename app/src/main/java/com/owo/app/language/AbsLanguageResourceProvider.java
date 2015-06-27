package com.owo.app.language;

import java.util.HashMap;
import java.util.Map;

public class AbsLanguageResourceProvider implements ILanguageProvider {
	protected Map<LanguageResourceKeys, String> mStrings = new HashMap<LanguageResourceKeys, String>();

	protected void add(LanguageResourceKeys key, String value) {
		mStrings.put(key, value);
	}

	@Override
	public String get(LanguageResourceKeys key) {
		return mStrings.get(key);
	}

}
