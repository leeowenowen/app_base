package com.owo.base.util;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import com.owo.app.common.ContextManager;

/** General immutable app information. */
public class AppInfoHelper {
	// Info

	private static String mPackageName;

	public static String packageName() {
		if (mPackageName == null) {
			mPackageName = ContextManager.appContext().getPackageName();
		}
		return mPackageName;
	}

	private static String mVersionName;

	public static String versionName() {
		if (mVersionName == null) {
			try {
				mVersionName = ContextManager.packageManager().getPackageInfo(packageName(), 0).versionName;
			} catch (NameNotFoundException e) {
			}
			mVersionName = TextHelper.ensureNotNull(mVersionName);
		}
		return mVersionName;
	}

	public static final int INVALID_VERSION_CODE = -1;
	private static int mVersionCode = INVALID_VERSION_CODE;

	public static int versionCode() {
		if (mVersionCode == INVALID_VERSION_CODE) {
			try {
				mVersionCode = ContextManager.packageManager().getPackageInfo(packageName(), 0).versionCode;
			} catch (Exception e) {
			}
		}
		return mVersionCode;
	}

	public static String processName() {
		return ContextManager.appInfo().processName;
	}

	public static int uid() {
		return ContextManager.appInfo().uid;
	}

	private static String byteToHexStr(byte[] input) {
		if (input == null) {
			return "";
		}
		String output = "";
		String tmp = "";
		for (int n = 0; n < input.length; n++) {
			tmp = Integer.toHexString(input[n] & 0xFF);
			if (tmp.length() == 1) {
				output = output + "0" + tmp;
			} else {
				output = output + tmp;
			}
		}
		return output.toUpperCase(Locale.ENGLISH);
	}

	public static String signature() {
		String ret = "";
		try {
			Signature[] sigs = ContextManager.packageManager().getPackageInfo(packageName(),//
					PackageManager.GET_SIGNATURES).signatures;

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

			X509Certificate X509Cert = (X509Certificate) certificateFactory.generateCertificate(//
					new ByteArrayInputStream(sigs[0].toByteArray()));

			ret = byteToHexStr(md5.digest(X509Cert.getSignature()));
		} catch (NameNotFoundException | NoSuchAlgorithmException | CertificateException e) {

		}

		return ret;
	}
}