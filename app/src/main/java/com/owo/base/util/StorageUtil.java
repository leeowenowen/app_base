package com.owo.base.util;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

import com.owo.app.common.ContextManager;

public class StorageUtil {
	public static final int INTERNAL = 1;
	public static final int SDCARD = 2;

	/**
	 * 计算指定路径所在存储器的剩余空间大小
	 * 
	 * @return 单位mb
	 * */
	private static double calcAvailableSpace(String path) {
		StatFs statFs = new StatFs(path);
		long blockSize = statFs.getBlockSize();
		long blocks = statFs.getAvailableBlocks();
		double availableSpare = (blocks * blockSize) / (1024d * 1024d);
		return availableSpare;
	}

	/**
	 * 判断外部存储是否mounted
	 * */
	public static boolean isSDCardMouted() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && //
				Environment.getExternalStorageDirectory().canWrite();
	}

	/**
	 * 获取外部存储路径
	 * */
	public static String getSDCardPath() {
		File file = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
				&& file.canWrite()) {
			return file.getAbsolutePath();
		} else {
			return "";
		}
	}

	/**
	 * 获取外部存储剩余空间大小,单位mb
	 * */
	public static double sdCardLeftSpace() {
		String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
		return calcAvailableSpace(sdCard);
	}

	/**
	 * 判断外部存储是否满足要求
	 * 
	 * @param mb
	 *            单位m
	 * */
	public static boolean sdcardEnough(int mb) {
		boolean isHasSpace = false;
		File sdCard = Environment.getExternalStorageDirectory();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
				&& sdCard.canWrite()) {
			if (mb < sdCardLeftSpace()) {
				isHasSpace = true;
			}
		}
		return isHasSpace;
	}

	/**
	 * 判断内部存储空间是否满足要求
	 * 
	 * @param sizeMb
	 *            单位m
	 * */
	public static boolean internalEnough(int sizeMb) {
		boolean ishasSpace = false;
		if (internalLeftSpace() > sizeMb) {
			ishasSpace = true;
		}
		return ishasSpace;
	}

	/**
	 * 获取内部存储剩余空间大小,单位mb
	 * */
	public static double internalLeftSpace() {
		String path = ContextManager.context().getFilesDir().getPath();
		return calcAvailableSpace(path);
	}
}
