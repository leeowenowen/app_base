package com.msjf.fentuan.app.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.owo.app.common.ContextManager;

public class SdkPreference {

	private static final String CLASS_NAME = SdkPreference.class.getSimpleName();

	private static final String PREF_MSJF_FENTUAN = "com.msjf.fentuan.pref";

	private static final String PREF_DOWNLOAD_IDS = "com.msjf.fentuan.download.ids";

	private static SharedPreferences mSdkPreferences = null;

	// TODO: check this warning and make sure we really need the create flag
	// @Context.MODE_WORLD_WRITEABLE
	// and add @SuppressLint to this method
	private static synchronized void ensureInit() {
		if (mSdkPreferences == null && ContextManager.context() != null)
			mSdkPreferences = ContextManager.context().getSharedPreferences(PREF_MSJF_FENTUAN,
					Context.MODE_WORLD_WRITEABLE + Context.MODE_WORLD_READABLE);
	}

	public static void setDownloadIds(String ids) {
		ensureInit();
		mSdkPreferences.edit().putString(PREF_DOWNLOAD_IDS, ids).commit();
	}

	public static String getDownloadIds() {
		ensureInit();
		return mSdkPreferences.getString(PREF_DOWNLOAD_IDS, "");
	}
}