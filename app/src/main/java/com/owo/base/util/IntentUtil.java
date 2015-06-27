package com.owo.base.util;

import android.content.Intent;
import android.net.Uri;

public class IntentUtil {

	public static Intent installAppIntent(String fileAddress) {
		if (!fileAddress.startsWith("file://")) {
			fileAddress = "file://" + fileAddress;
		}

		Uri downloadedFile = Uri.parse(fileAddress);
		Intent installIntent = new Intent();
		installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		installIntent.setAction(Intent.ACTION_VIEW);
		installIntent.setDataAndType(downloadedFile, "application/vnd.android.package-archive");
		return installIntent;
	}

	public static Intent openBrowserIntent(String url) {
		Intent openExternalBroswer = new Intent();
		openExternalBroswer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		openExternalBroswer.setAction(Intent.ACTION_VIEW);
		openExternalBroswer.setData(Uri.parse(url));
		return openExternalBroswer;
	}
}
