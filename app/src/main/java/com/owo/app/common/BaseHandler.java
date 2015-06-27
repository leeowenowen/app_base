package com.owo.app.common;

import android.os.Handler;
import android.os.SystemClock;

/**
 * {@link #UiHandler} only works between {@link #initialize()} and
 * {@link #destroy()}.
 */
public class BaseHandler {
	private static Handler sHandler;

	/**
	 * Must ensure this is called on UI thread.
	 */
	public static void initialize() {
		if (sHandler == null) {
			sHandler = new Handler();
		}
	}

	public static void clear() {
		if (sHandler != null) {
			// If token is null, all callbacks and messages will be removed.
			sHandler.removeCallbacksAndMessages(null);
		}
	}

	public static void destroy() {
		if (sHandler != null) {
			// If token is null, all callbacks and messages will be removed.
			sHandler.removeCallbacksAndMessages(null);
			sHandler = null;
		}
	}

	public static Handler get() {
		return sHandler;
	}

	public static void post(Runnable r) {
		post(r, false);
	}

	public static void post(Runnable r, boolean repost) {
		if (sHandler != null) {
			if (repost) {
				sHandler.removeCallbacks(r);
			}
			sHandler.post(r);
		}
	}

	public static void postDelayed(Runnable r, long delayMillis) {
		postDelayed(r, delayMillis, false);
	}

	public static void postDelayed(Runnable r, long delayMillis, boolean repost) {
		if (sHandler != null) {
			if (repost) {
				sHandler.removeCallbacks(r);
			}
			sHandler.postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
		}
	}

	public static void postAtFront(Runnable r) {
		postAtFront(r, false);
	}

	public static void postAtFront(Runnable r, boolean repost) {
		if (sHandler != null) {
			if (repost) {
				sHandler.removeCallbacks(r);
			}
			sHandler.postAtFrontOfQueue(r);
		}
	}

	public static void removeCallbacks(Runnable r) {
		if (sHandler != null) {
			sHandler.removeCallbacks(r);
		}
	}
}