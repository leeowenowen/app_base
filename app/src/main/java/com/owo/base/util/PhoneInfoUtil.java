package com.owo.base.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class PhoneInfoUtil {

	private static final String CLASS_NAME = PhoneInfoUtil.class.getSimpleName();
	public static final String OS = "android";
	public static final String FR = "API Level-" + android.os.Build.VERSION.SDK + " - "
			+ android.os.Build.MANUFACTURER + "-" + android.os.Build.VERSION.RELEASE;

	// add by cbl 2013.7.12
	// 删除原getResX方法，因为该方法的功能与ResolutionUtil类的getScreenWidth方法，功能相同

	// 删除原getResY方法，因为该方法的功能与ResolutionUtil类的getScreenHeight方法，功能相同

	/**
	 * 获取手机号码。<br>
	 * 该方法由于一些特殊原因，现在已经不去获取手机号码，直接返回空串。
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getMobileNum(Context ctx) {
		// 因隐私数据敏感性问题，据需求变更，在 2.2.1 中去除获取手机号功能，直接返回空串。 wangcj 2012-12-26
		String phoneNum = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) ctx
					.getSystemService(Context.TELEPHONY_SERVICE);
			String mobileNumber = telephonyManager.getLine1Number();
			if (mobileNumber != null && mobileNumber.length() > 11) {
				phoneNum = mobileNumber
						.substring(mobileNumber.length() - 11, mobileNumber.length());
			}
		} catch (Exception e) {
		}

		return phoneNum;
	}

	/**
	 * 获取imei
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getIMEI(Context ctx) {
		String imei = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) ctx
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
		}
		return imei == null ? "" : imei;
	}

	/**
	 * 获取imsi
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getIMSI(Context ctx) {
		String imsi = "";
		try {

			TelephonyManager telephonyManager = (TelephonyManager) ctx
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = telephonyManager.getSubscriberId();

		} catch (Exception e) {

		}
		return imsi;
	}

	/**
	 * 获取mac地址
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getMAC(Context ctx) {
		String macAddress = "";
		try {
			WifiManager wifiMgr = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) {
				macAddress = info.getMacAddress();
			}
		} catch (Exception e) {

		}
		return macAddress;
	}

	/**
	 * 获取sim卡的状态
	 * 
	 * @param ctx
	 * @return true，SIM卡良好可以正常methodName使用；false 其它状态
	 */
	public static boolean getSimState(Context ctx) {
		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		int simState = telephonyManager.getSimState();
		return (simState == TelephonyManager.SIM_STATE_READY) ? true : false;
	}

	/**
	 * 获取android_id
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getAndroidId(Context ctx) {
		String androidId = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
		return androidId == null ? "" : androidId;
	}

	/**
	 * 获取安装标识 2.3以上获取安装时间 ，2.3以下获取data文件夹下的lib目录的iNode值
	 * 
	 * @param ctx
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getInstallId(Context ctx) {
		String installId = "";
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			try {
				installId = String.valueOf(ctx.getPackageManager().getPackageInfo(
						ctx.getPackageName(), 0).firstInstallTime);
			} catch (NameNotFoundException e) {
			}
		} else {
			installId = String.valueOf(PhoneInfoUtil.getiNode(ctx.getFilesDir().getParent()
					+ File.separator + "lib"));
		}

		return installId;
	}

	/**
	 * 获取文件或文件夹的inode
	 * 
	 * @param path
	 * @return
	 */
	public static int getiNode(String path) {

		int inode = -1;

		try {
			Class<?> fileUtilClass = Class.forName("android.os.FileUtils");
			Class<?> fileStatusClass = Class.forName("android.os.FileUtils$FileStatus");

			Method getFileStatus = fileUtilClass.getMethod("getFileStatus", String.class,
					fileStatusClass);

			Object status = fileStatusClass.newInstance();
			boolean invokeSuc = (Boolean) getFileStatus.invoke(fileUtilClass.newInstance(), path,
					status);

			if (invokeSuc) {
				Field field = fileStatusClass.getField("ino");

				inode = field.getInt(status);
			}
		} catch (Exception e) {
		}

		return inode;
	}

	/**
	 * 获取手机运营商，目前只支持移动、联通、电信 说明：移动：46000/46002/46007;联通：46001；电信：46003
	 * 
	 * @return
	 */
	public static String getOperator(Context ctx) {
		String type = "";
		if (getSimState(ctx)) {
			TelephonyManager telephonyManager = (TelephonyManager) ctx
					.getSystemService(Context.TELEPHONY_SERVICE);
			String operator = telephonyManager.getSimOperator();
			if (operator != null) {
				type = operator;
			}
		}

		return type;
	}
}