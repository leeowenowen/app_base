package com.owo.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.text.TextUtils;

import com.owo.app.common.Check;
import com.owo.app.common.ContextManager;

public final class FileHelper {
	// Sync IO

	public static final int TYPE_FILE_SYSTEM = 1;
	public static final int TYPE_ASSET = 2;

	public static final String ASSETS = "assets";

	public static InputStream open(String path) {
		return open(TYPE_FILE_SYSTEM, path);
	}

	/**
	 * @param type
	 *            {@link #TYPE_FILE_SYSTEM} or {@link #TYPE_ASSET}
	 */
	public static InputStream open(int type, String path) {
		try {
			switch (type) {
			case TYPE_FILE_SYSTEM:
				return new FileInputStream(path);

			case TYPE_ASSET:
				return ContextManager.assetManager().open(path);

			default:
				break;
			}
		} catch (Throwable e) {
		}
		return null;
	}

	public static FileOutputStream write(String path) {
		return write(path, false);
	}

	public static FileOutputStream write(String path, boolean lockFile) {
		// 1) ensure dir
		String[] parts = splitFileName(path);
		if (!TextUtils.isEmpty(parts[0])) {
			if (!ensureDir(parts[0])) {
				return null;
			}
		}

		// 2) open stream;
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(path);
			if (lockFile) {
				while (true) {
					try {
						if (stream.getChannel().tryLock() != null) {
							break;
						}
					} catch (Exception ex) {
					}
					Thread.sleep(100);
				}
			}
		} catch (Throwable e) {
			return null;
		}

		return stream;
	}

	public static boolean delete(String path) {
		return new File(path).delete();
	}

	/**
	 * If dir is not existed, create it.
	 */
	public static boolean ensureDir(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}

	public static XmlResourceParser openResourceXml(String path) throws IOException {
		AssetManager assetMgr = ContextManager.assetManager();

		return assetMgr.openXmlResourceParser(path);
	}

	// Path API

	public static final String INTERNAL_PATH = ContextManager.appContext().getFilesDir().getPath();
	public static final String EXTERNAL_PATH = Environment.getExternalStorageDirectory().getPath();

	/** Build a dir path */
	public static String dirPath(String... parts) {
		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			sb.append(part);
			sb.append(File.separator);
		}
		return sb.toString();
	}

	/** Build a file path */
	public static String filePath(String... parts) {
		if (parts.length == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		int end = parts.length - 1;
		for (int i = 0; i < end; ++i) {
			sb.append(parts[i]);
			sb.append(File.separator);
		}
		sb.append(parts[end]);
		return sb.toString();
	}

	/**
	 * Split to get the dir/file parts.
	 * 
	 * @return [dir,filename]
	 */
	public static String[] splitFileName(String filePath) {
		if (filePath.endsWith(File.separator)) {
			return new String[] { filePath, "" };
		}

		int index = filePath.lastIndexOf(File.separator);
		if (index < 0) {
			return new String[] { "", filePath };
		}
		return new String[] { filePath.substring(0, index), filePath.substring(index + 1) };
	}

	/** Split file path string to parts. */
	public static String[] splitPath(String path) {
		return TextHelper.split(path, File.separator, false, true);
	}

	public static void ensurePath(String path) {
		File dir = new File(path);
		if (dir.isFile()) {
			dir.delete();
		}

		if (!dir.exists())
			dir.mkdirs();
	}

	public static boolean ensureFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file.isFile();
		}

		boolean ret = false;
		try {
			ret = file.createNewFile();
		} catch (IOException e) {
		}
		return ret;
	}

	/**
	 * 确保创建一个新文件 如果文件已存在,则删除旧文件 注意:必须确保路径指定的不是一个文件夹,
	 * */
	public static boolean ensureNewFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			Check.d(!file.isDirectory(), filePath);
			if (file.isFile()) {
				file.delete();
			}
		} else {
			// 检查目录是否已存在
			String parent = file.getParent();
			if (!exists(parent)) {
				// 创建文件夹
				ensurePath(parent);
			}
		}

		boolean ret = false;
		try {
			ret = file.createNewFile();
		} catch (IOException e) {
		}
		return ret;
	}

	public static boolean exists(String path) {
		return new File(path).exists();
	}
}