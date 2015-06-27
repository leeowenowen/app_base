package com.owo.base.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.owo.app.common.Check;

public class StreamUtil {

	private static final int DEFAULT_BUFFER_SIZE = 8192;
	public static final String CLASS_NAME = "StreamUtil";

	public static void close(Closeable obj) {
		if (obj != null) {
			try {
				obj.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 从输入流读取内容, 写入到输出流中. 此方法使用大小为8192字节的默认的缓冲区.
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static void io(InputStream in, OutputStream out) throws IOException {
		io(in, out, -1);
	}

	/**
	 * 从输入流读取内容, 写入到输出流中. 使用指定大小的缓冲区.
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * @param bufferSize
	 *            缓冲区大小(字节数)
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static void io(InputStream in, OutputStream out, int bufferSize) throws IOException {
		if (bufferSize == -1) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}

		byte[] buffer = new byte[bufferSize];
		int amount;
		while ((amount = in.read(buffer)) > 0) {
			out.write(buffer, 0, amount);
		}
	}

	public interface SerialCallback {
		// 如果返回true,表示需要停止
		boolean onWrite(int bytes);
	}

	/**
	 * 将InputStream序列化到指定的OutputStream
	 * 
	 * @return false 发生文件IO异常
	 * */
	public static boolean io(OutputStream out, InputStream in, SerialCallback callback) {
		Check.d(callback != null, "如果不需要回调,请使用无callback参数版重载方法");
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int amount;
		boolean ret = false;
		try {
			while ((amount = in.read(buffer)) > 0) {
				out.write(buffer, 0, amount);
				if (callback.onWrite(amount)) {
					break;
				}
			}
			ret = true;
		} catch (IOException e) {
		}
		return ret;
	}

	/**
	 * 从输入流读取内容, 写入到输出流中. 此方法使用大小为4096字符的默认的缓冲区.
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static void io(Reader in, Writer out) throws IOException {
		io(in, out, -1);
	}

	/**
	 * 从输入流读取内容, 写入到输出流中. 使用指定大小的缓冲区.
	 * 
	 * @param in
	 *            输入流
	 * @param out
	 *            输出流
	 * @param bufferSize
	 *            缓冲区大小(字符数)
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static void io(Reader in, Writer out, int bufferSize) throws IOException {
		if (bufferSize == -1) {
			bufferSize = DEFAULT_BUFFER_SIZE >> 1;
		}

		char[] buffer = new char[bufferSize];
		int amount;

		while ((amount = in.read(buffer)) >= 0) {
			out.write(buffer, 0, amount);
		}
	}

	/**
	 * 取得同步化的输出流.
	 * 
	 * @param out
	 *            要包裹的输出流
	 * 
	 * @return 线程安全的同步化输出流
	 */
	public static OutputStream synchronizedOutputStream(OutputStream out) {
		return new SynchronizedOutputStream(out);
	}

	/**
	 * 取得同步化的输出流.
	 * 
	 * @param out
	 *            要包裹的输出流
	 * @param lock
	 *            同步锁
	 * 
	 * @return 线程安全的同步化输出流
	 */
	public static OutputStream synchronizedOutputStream(OutputStream out, Object lock) {
		return new SynchronizedOutputStream(out, lock);
	}

	/**
	 * 将指定输入流的所有文本全部读出到一个字符串中.
	 * 
	 * @param in
	 *            要读取的输入流
	 * 
	 * @return 从输入流中取得的文本
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String readText(InputStream in) throws IOException {
		return readText(in, null, -1);
	}

	/**
	 * 将指定输入流的所有文本全部读出到一个字符串中.
	 * 
	 * @param in
	 *            要读取的输入流
	 * @param encoding
	 *            文本编码方式
	 * 
	 * @return 从输入流中取得的文本
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String readText(InputStream in, String encoding) throws IOException {
		return readText(in, encoding, -1);
	}

	/**
	 * 将指定输入流的所有文本全部读出到一个字符串中.
	 * 
	 * @param in
	 *            要读取的输入流
	 * @param encoding
	 *            文本编码方式
	 * @param bufferSize
	 *            缓冲区大小(字符数)
	 * 
	 * @return 从输入流中取得的文本
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String readText(InputStream in, String encoding, int bufferSize)
			throws IOException {
		Reader reader = (encoding == null) ? new InputStreamReader(in) : new InputStreamReader(in,
				encoding);

		return readText(reader, bufferSize);
	}

	/**
	 * 将指定<code>Reader</code>的所有文本全部读出到一个字符串中.
	 * 
	 * @param reader
	 *            要读取的<code>Reader</code>
	 * 
	 * @return 从<code>Reader</code>中取得的文本
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String readText(Reader reader) throws IOException {
		return readText(reader, -1);
	}

	/**
	 * 将指定<code>Reader</code>的所有文本全部读出到一个字符串中.
	 * 
	 * @param reader
	 *            要读取的<code>Reader</code>
	 * @param bufferSize
	 *            缓冲区的大小(字符数)
	 * 
	 * @return 从<code>Reader</code>中取得的文本
	 * 
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String readText(Reader reader, int bufferSize) throws IOException {
		StringWriter writer = new StringWriter();

		io(reader, writer, bufferSize);
		return writer.toString();
	}

	/**
	 * 同步化的输出流包裹器.
	 */
	private static class SynchronizedOutputStream extends OutputStream {
		private OutputStream out;
		private Object lock;

		SynchronizedOutputStream(OutputStream out) {
			this(out, out);
		}

		SynchronizedOutputStream(OutputStream out, Object lock) {
			this.out = out;
			this.lock = lock;
		}

		public void write(int datum) throws IOException {
			synchronized (lock) {
				out.write(datum);
			}
		}

		public void write(byte[] data) throws IOException {
			synchronized (lock) {
				out.write(data);
			}
		}

		public void write(byte[] data, int offset, int length) throws IOException {
			synchronized (lock) {
				out.write(data, offset, length);
			}
		}

		public void flush() throws IOException {
			synchronized (lock) {
				out.flush();
			}
		}

		public void close() throws IOException {
			synchronized (lock) {
				out.close();
			}
		}
	}

	// read toByteArray
	// -----------------------------------------------------------------------
	/**
	 * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @return the requested byte array
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * Copy bytes from an <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * Large streams (over 2GB) will return a bytes copied value of
	 * <code>-1</code> after the copy has completed since the correct number of
	 * bytes cannot be returned as an int. For large streams use the
	 * <code>copyLarge(InputStream, OutputStream)</code> method.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ArithmeticException
	 *             if the byte count is too large
	 * @since Commons IO 1.1
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.3
	 */
	public static long copyLarge(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}
