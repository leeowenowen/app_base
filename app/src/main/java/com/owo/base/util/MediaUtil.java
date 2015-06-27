package com.owo.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.util.Log;

public class MediaUtil {

	public static Bitmap createImageThumbnail(String filePath, int targetWidth,
			int targetHeight, Rect outPaddings, Config config) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (stream == null) {
			return null;
		}
		Options op = BitmapHelper.getOptions();
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = config;
		Bitmap bitmap = BitmapFactory.decodeStream(stream, outPaddings, op);
		try {
			stream.close();
		} catch (IOException e) {
		}
		// Scale down the bitmap if it's too large.
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		targetHeight = (int) ((float) targetWidth * height / width);
		Log.v("xxx", "[w:" + width + "][h:" + height + "][tw:" + targetWidth
				+ "][th:" + targetHeight + "]");

		if (width != targetWidth || height != targetHeight) {
			bitmap = Bitmap.createScaledBitmap(bitmap, targetWidth,
					targetHeight, true);
		}

		return bitmap;
	}

	@SuppressLint("NewApi")
	public static Bitmap createVideoThumbnail(String filePath) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			return retriever.getFrameAtTime(-1);
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}
		return null;
	}

	/**
	 * Create a video thumbnail for a video. May return null if the video is
	 * corrupt or the format is not supported.
	 * 
	 * @param filePath
	 *            the path of video file
	 * @param kind
	 *            could be MINI_KIND or MICRO_KIND
	 */
	@SuppressLint("NewApi")
	public static Bitmap createVideoThumbnail(String filePath, int targetWidth,
			int targetHeight, Rect outPaddings, Config config) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime(-1);
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}

		if (bitmap == null)
			return null;

		// Scale down the bitmap if it's too large.
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
//		targetHeight = (int) ((float) targetWidth * height / width);
//		Log.v("xxx", "[w:" + width + "][h:" + height + "][tw:" + targetWidth
//				+ "][th:" + targetHeight + "]");

		if (width != targetWidth || height != targetHeight) {
			bitmap = Bitmap.createScaledBitmap(bitmap, targetWidth,
					targetHeight, true);
		}

		// bitmap = Bitmap.createScaledBitmap(bitmap, 500, 3000, true);

		return bitmap;
	}

	private static StringBuilder mFormatBuilder = new StringBuilder();
	private static Formatter mFormatter = new Formatter(mFormatBuilder,
			Locale.getDefault());

	public static String format(int timeMs) {
		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	private static final int kM = 1000 * 1000;
	private static final int kK = 1000;

	public static String size(long bytes) {
		mFormatBuilder.setLength(0);
		if (bytes > kM) {
			return mFormatter.format("%dM", bytes / kM).toString();
		} else if (bytes > kK) {
			return mFormatter.format("%dK", bytes / kK).toString();
		} else {
			return mFormatter.format("%dByte", bytes).toString();
		}
	}
}
