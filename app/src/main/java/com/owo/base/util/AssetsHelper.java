package com.owo.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;

import com.owo.app.common.ContextManager;

public class AssetsHelper {
	/**
	 * Copy assets to specified path.
	 * 
	 * @param assetFolder
	 *            Asset folder name, can be "" or path ends with "/".
	 * @param destPath
	 *            An exists path that can be accessed, ends with "/" or not.
	 * */
	public static boolean copyAssetFolderTo(String assetFolder, String destPath) {
		boolean bRet = true;
		if (assetFolder.endsWith("/")) {
			assetFolder = assetFolder.substring(0, assetFolder.length() - 1);
		}

		String[] files = listAssetsFrom(assetFolder);
		if (!destPath.endsWith("/")) {
			destPath += "/";
		}

		for (String file : files) {
			if (!new File(destPath + file).exists()) {
				String assetFile;
				if (assetFolder.equals("")) {
					assetFile = file;
				} else {
					assetFile = assetFolder + "/" + file;
				}
				bRet &= copyAssetIfNotExist(assetFile, destPath + file);
			}
		}

		return bRet;
	}

	public static boolean copyAssetIfNotExist(String assetPath, String destFile) {
		boolean bRet = true;
		if (!new File(destFile).exists()) {
			try {
				AssetManager assetManager = ContextManager.assetManager();
				InputStream inputStream = assetManager.open(assetPath);
				OutputStream outputStream = new FileOutputStream(destFile);
				copyTo(inputStream, outputStream);

				inputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				bRet = false;
			}
		}

		return bRet;
	}

	/**
	 * List all file names in the specified asset folder.
	 * 
	 * @param folder
	 *            Asset folder name, can not ends with "/".
	 * @return Asset file name array.
	 * */
	public static String[] listAssetsFrom(String folder) {
		AssetManager assetManager = ContextManager.assetManager();
		String[] files = null;
		try {
			files = assetManager.list(folder);
		} catch (Exception e) {
		}
		return files;
	}

	private static void copyTo(InputStream input, OutputStream output) {
		byte[] buffer = new byte[1024 * 4];
		int read;
		try {
			while ((read = input.read(buffer)) != -1) {
				output.write(buffer, 0, read);
			}
		} catch (IOException e) {
		}
	}
}