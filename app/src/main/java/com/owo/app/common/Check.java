package com.owo.app.common;

import com.msjf.fentuan.BuildConfig;

/**
 * The built-in assert() has no effect on android devices unless you run: adb
 * shell setprop debug.assert 1 for you device. So we build our own
 * {@link #Check} to do assertion things.
 * 
 * @see http 
 *      ://stackoverflow.com/questions/2364910/can-i-use-assert-on-android-devices
 */
public class Check {
	/** Check condition for DEBUG */
	public static void d(boolean condition) {
		d(condition, "");
	}

	/** Check condition with hint for DEBUG */
	public static void d(boolean condition, String hint) {
		if (condition == false) {
			if (BuildConfig.DEBUG) {
				throw new AssertionError(hint);
			}
		}
	}

	/** Check condition with target object for DEBUG */
	public static void d(boolean condition, Object errorTarget) {
		if (condition == false) {
			if (BuildConfig.DEBUG) {
				throw new AssertionError(errorTarget.toString());
			}
		}
	}

	/** Throw the throwable for DEBUG */
	public static void d(Throwable throwable) {
		if (BuildConfig.DEBUG) {
			throw new RuntimeException(getRootCause(throwable));
		}
	}

	/** Check condition for RELEASE/DEBUG */
	public static void r(boolean condition) {
		r(condition, "");
	}

	/** Check condition for RELEASE/DEBUG */
	public static void r(boolean condition, String hint) {
		if (condition == false) {
			throw new AssertionError(hint);
		}
	}

	/** Check condition with target object for RELEASE/DEBUG */
	public static void r(boolean condition, Object errorTarget) {
		if (condition == false) {
			throw new AssertionError(errorTarget.toString());
		}
	}

	/** Throw the throwable for RELEASE/DEBUG */
	public static void r(Throwable throwable) {
		throw new RuntimeException(getRootCause(throwable));
	}

	private static Throwable getRootCause(Throwable throwable) {
		while (throwable.getCause() != null && throwable != throwable.getCause()) {
			throwable = throwable.getCause();
		}
		return throwable;
	}
}
