package com.owo.base.util;

import android.content.Context;
import android.os.PowerManager;

import com.owo.app.common.ContextManager;

public class DeviceUtils {
	private static DeviceUtils sInstance = null;

	private PowerManager.WakeLock mScreenOnLock = null;

	public static DeviceUtils getInstance() {
		if (sInstance == null) {
			sInstance = new DeviceUtils();
		}

		return sInstance;
	}

	private void Destroy() {
		UnlockScreenOn();
	}

	public void LockScreenOn() {
		if (null == mScreenOnLock) {
			PowerManager pm = (PowerManager) ContextManager.systemService(Context.POWER_SERVICE);
			if (null == pm) {
				return;
			}

			mScreenOnLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "screen on lock");
			mScreenOnLock.setReferenceCounted(true);
		}

		if (null != mScreenOnLock) {
			mScreenOnLock.acquire();
		}
	}

	public void UnlockScreenOn() {
		if (null != mScreenOnLock) {
			if (mScreenOnLock.isHeld()) {
				mScreenOnLock.release();
			}
		}
	}
}
