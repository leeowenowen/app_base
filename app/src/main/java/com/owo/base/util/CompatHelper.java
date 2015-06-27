package com.owo.base.util;

import java.util.regex.Pattern;

import android.util.SparseBooleanArray;

public class CompatHelper {
	// SDK
	private static int sMySdk;

	public static boolean sdk(int min) {
		return sMySdk >= min;
	}

	public static boolean sdk(int min, int max) {
		return MathHelper.inRange(sMySdk, min, max);
	}

	public static boolean sdks(int... targets) {
		for (int target : targets) {
			if (sMySdk == target) {
				return true;
			}
		}
		return false;
	}

	// Device Name

	private static SparseBooleanArray sDeviceResultCache;

	public static boolean device(String regex) {
		if (sDeviceResultCache == null) {
			sDeviceResultCache = new SparseBooleanArray();
		}

		int hashCode = regex.hashCode();
		int index = sDeviceResultCache.indexOfKey(hashCode);
		boolean result;
		if (index < 0) {
			result = Pattern.matches(regex, SysInfoHelper.device());
			sDeviceResultCache.put(hashCode, result);
		} else {
			result = sDeviceResultCache.valueAt(index);
		}
		return result;
	}

	// CPU Arch

	private static SparseBooleanArray sCPUResultCache;

	public static boolean cpu(String regex) {
		if (sCPUResultCache == null) {
			sCPUResultCache = new SparseBooleanArray();
		}

		int hashCode = regex.hashCode();
		int index = sCPUResultCache.indexOfKey(hashCode);
		boolean result;
		if (index < 0) {
			result = Pattern.matches(regex, SysInfoHelper.cpuArch());
			sCPUResultCache.put(hashCode, result);
		} else {
			result = sCPUResultCache.valueAt(index);
		}
		return result;
	}

	// Ram Size

	public static boolean ram(int min, int max) {
		return MathHelper.inRange(SysInfoHelper.ramSize(), min, max);
	}
}