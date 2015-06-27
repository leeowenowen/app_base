package com.owo.app.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

public class ContextManager {
	private static Context sContext;
	private static Context sAppContext;

	/**
	 * initialize Context Manager before any usage
	 * 
	 * @param context
	 *            this context is share the same life cycle with activity.
	 */
	public static void init(Context context) {
		sContext = context;

		if (sAppContext == null) {
			sAppContext = context.getApplicationContext();
		}
	}

	public static void destroy() {
		sContext = null;
		// keep the AppContext after destory for app scope invoke.
	}

	// app scope

	public static Context appContext() {
		return sAppContext;
	}

	public static Resources resources() {
		return sAppContext.getResources();
	}

	public static AssetManager assetManager() {
		return sAppContext.getAssets();
	}

	public static ContentResolver contentResolver() {
		return sAppContext.getContentResolver();
	}

	public static Object systemService(String name) {
		if (!TextUtils.isEmpty(name)) {
			return sAppContext.getSystemService(name);
		}
		return null;
	}

	public static SharedPreferences sharedPreferences(String name, int mode) {
		return sAppContext.getSharedPreferences(name, mode);
	}

	public static PackageManager packageManager() {
		return sAppContext.getPackageManager();
	}

	public static ActivityManager activityManager() {
		return (ActivityManager) sAppContext.getSystemService(Context.ACTIVITY_SERVICE);
	}

	public static ApplicationInfo appInfo() {
		return sAppContext.getApplicationInfo();
	}

	public static Configuration config() {
		return resources().getConfiguration();
	}

	// activity/service scope

	public static Context context() {
		return sContext;
	}

	public static Activity activity() {
		return (Activity) sContext;
	}

	public static Window window() {
		return activity().getWindow();
	}

	public static WindowManager windowManager() {
		return activity().getWindowManager();
	}

	public static WindowManager systemWindowManager() {
		return (WindowManager) sAppContext.getSystemService(Context.WINDOW_SERVICE);
	}

	public static DownloadManager downloadManager() {
		return (DownloadManager) sAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public static Service service() {
		return (Service) sContext;
	}

	public static boolean startActivity(Intent intent) {
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context().startActivity(intent);
		return true;
	}

	public static void registerReceiver(BroadcastReceiver receiver, String... actions) {
		Check.d(receiver != null && actions != null && actions.length > 0);
		IntentFilter intentFilter = new IntentFilter();
		for (String action : actions) {
			intentFilter.addAction(action);
		}
		context().registerReceiver(receiver, intentFilter);
	}
}