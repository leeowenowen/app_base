package com.owo.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.owo.app.common.DLog;

/**
 * Url management utils.
 */
public class UrlUtil {
	private static final String TAG = "UrlUtils";
	public static final String URL_ABOUT_BLANK = "about:blank";
	public static final String URL_ABOUT_START = "about:start";
	private static final Pattern PATTERN_FOR_MYNAVIDATA = Pattern
			.compile("(^(http(s)?)://)|(/([0-9a-zA-Z])?$)");

	public static final Pattern ACCEPTED_URI_SCHEMA = Pattern.compile("(?i)" + // switch
																				// on
																				// case
																				// insensitive
																				// matching
			"(" + // begin group for schema
			"(?:http|https|file):\\/\\/" + "|(?:inline|data|about|javascript):" + ")" + "(.*)");

	private final static String EXT_PREFIX = "ext:";
	private final static String DATA_PREFIX = "data:";

	private final static Pattern URL_VALID_PATTERN = Pattern.compile("^" //
			+ "((inline|data|about|javascript):.*)" // 特殊schema
			+ "|" //
			+ "(((https|http|ftp|rtsp|mms)?://)?" // 协议schema
			+ "(([0-9a-zA-Z_!~*'()\\.&=+$%-]+: )?[0-9a-zA-Z_!~*'()\\.&=+$%-]+@)?" // ftp的user@
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-zA-Z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-zA-Z][0-9a-zA-Z-_~]{0,61})?[0-9a-zA-Z]" // 二级域名
			+ "\\.[a-zA-Z][a-zA-Z]{1,5})" // first level domain .com etc
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn’t required if there is no file name
			+ "(/[0-9a-zA-Z_!~*'()\\.;?:/@&=+$,%#-]+)+/?))" //
			+ "$");

	/**
	 * Check if a url is valid
	 * 
	 * @param url
	 *            The url to check.
	 * @return True if the url is valid
	 */
	public static boolean isUrl(String url) {
		url = correctUrl(url);

		if (null == url || url.length() == 0) {
			return false;
		}
		return URL_VALID_PATTERN.matcher(url).matches();
	}

	public static boolean isUrlWithHttpsHead(String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}

		if (url.startsWith("https://")) {
			return true;
		}
		return false;
	}

	/**
	 * Check en url. Add http:// before if missing.
	 * 
	 * @param url
	 *            The url to check.
	 * @return The modified url if necessary.
	 */
	public static String checkToAddHttpPrefix(String url) {
		if ((url != null) && (url.length() > 0)) {
			url = correctUrl(url);
			if ((!url.startsWith("http://")) && (!url.startsWith("https://"))
					&& (!url.startsWith("file://")) && (!url.startsWith("ext:"))
					&& (!url.startsWith(DATA_PREFIX)) && (!url.startsWith(URL_ABOUT_BLANK))
					&& (!url.startsWith(URL_ABOUT_START))) {

				url = "http://" + url;

			}
		}

		return url;
	}

	public static String cutHttpOrHttps(String url) {
		if (url.startsWith("http://")) {
			url = url.substring(7);
		} else if (url.startsWith("https://")) {
			url = url.substring(8);
		}

		return url;
	}

	public static String cutHttpOrHttpsAndTrimTailSlash(String url) {
		if (url == null) {
			return null;
		}

		String ret = cutHttpOrHttps(url);
		if (ret.endsWith("/")) {
			ret = ret.substring(0, ret.length() - 1);
		}

		return ret;
	}

	public static String smartUrlFilter(Uri inUri) {
		if (inUri != null) {
			return smartUrlFilter(inUri.toString());
		}
		return null;
	}

	/**
	 * Attempts to determine whether user input is a URL or search terms.
	 * Anything with a space is passed to search.
	 * 
	 * Converts to lowercase any mistakenly uppercased schema (i.e., "Http://"
	 * converts to "http://"
	 * 
	 * @return Original or modified URL
	 * 
	 */
	public static String smartUrlFilter(String url) {
		return smartUrlFilter(url, true);
	}

	/**
	 * Attempts to determine whether user input is a URL or search terms.
	 * Anything with a space is passed to search if canBeSearch is true.
	 * 
	 * Converts to lowercase any mistakenly uppercased schema (i.e., "Http://"
	 * converts to "http://"
	 * 
	 * @param canBeSearch
	 *            If true, will return a search url if it isn't a valid URL. If
	 *            false, invalid URLs will return null
	 * @return Original or modified URL
	 * 
	 */
	public static String smartUrlFilter(String url, boolean canBeSearch) {
		String inUrl = url.trim();
		boolean hasSpace = inUrl.indexOf(' ') != -1;

		Matcher matcher = ACCEPTED_URI_SCHEMA.matcher(inUrl);
		if (matcher.matches()) {
			// force scheme to lowercase
			String scheme = matcher.group(1);
			String lcScheme = scheme.toLowerCase();
			if (!lcScheme.equals(scheme)) {
				inUrl = lcScheme + matcher.group(2);
			}
			if (hasSpace && Patterns.WEB_URL.matcher(inUrl).matches()) {
				inUrl = inUrl.replace(" ", "%20");
			}
			return inUrl;
		}
		if (!hasSpace) {
			if (Patterns.WEB_URL.matcher(inUrl).matches()) {
				return URLUtil.guessUrl(inUrl);
			}
		}
		if (canBeSearch) {
			// TODO
			/*
			 * return URLUtil.composeSearchUrl(inUrl, QUICKSEARCH_G,
			 * QUERY_PLACE_HOLDER);
			 */

			return inUrl;
		}
		return null;
	}

	/**
	 * now is used for barcode url fix
	 * 
	 * @param strSrc
	 * @param strKeyword
	 * @param isKeepOneKeyword
	 * @return
	 */
	static public String fixURL(String strSrc, String strKeyword, boolean isKeepOneKeyword) {
		String strReturnValue = strSrc;
		String strTempLastCutString = "";
		int tmpIndex = -1;

		// Check whether the String still contains the keyword again.
		while (strReturnValue.startsWith(strKeyword)) {
			strTempLastCutString = strReturnValue;
			tmpIndex = strReturnValue.indexOf(strKeyword);
			strReturnValue = strReturnValue.substring(tmpIndex + strKeyword.length());
		}

		if (isKeepOneKeyword && !strReturnValue.startsWith("http://")
				&& !strReturnValue.startsWith("https://")) {
			strReturnValue = strTempLastCutString;
		}

		return strReturnValue;
	}

	static public String getCheckUrl(String url) {
		String strUri = url.toLowerCase();

		String strHttp = "http://";
		String strHttps = "https://";
		String strUrl = "url:";

		if (strUri.startsWith(strHttp)) {
			strUri = fixURL(strUri, strHttp, true);
		} else if (strUri.startsWith(strHttps)) {
			strUri = fixURL(strUri, strHttps, true);
		} else if (strUri.startsWith(strUrl)) {
			strUri = fixURL(strUri, strUrl, false);
		}

		if (url.length() != strUri.length()) {
			strUri = url.substring(url.length() - strUri.length());
		} else {
			strUri = url;
		}
		return strUri;
	}

	public static String fixUrl(String inUrl) {
		// FIXME: Converting the url to lower case
		// duplicates functionality in smartUrlFilter().
		// However, changing all current callers of fixUrl to
		// call smartUrlFilter in addition may have unwanted
		// consequences, and is deferred for now.
		int colon = inUrl.indexOf(':');
		boolean allLower = true;
		for (int index = 0; index < colon; index++) {
			char ch = inUrl.charAt(index);
			if (!Character.isLetter(ch)) {
				break;
			}
			allLower &= Character.isLowerCase(ch);
			if (index == colon - 1 && !allLower) {
				inUrl = inUrl.substring(0, colon).toLowerCase() + inUrl.substring(colon);
			}
		}
		if (inUrl.startsWith("http://") || inUrl.startsWith("https://"))
			return inUrl;
		if (inUrl.startsWith("http:") || inUrl.startsWith("https:")) {
			if (inUrl.startsWith("http:/") || inUrl.startsWith("https:/")) {
				inUrl = inUrl.replaceFirst("/", "//");
			} else
				inUrl = inUrl.replaceFirst(":", "://");
		}
		return inUrl;
	}

	public static String correctUrl(String url) {
		if (null == url) {
			return null;
		}

		String strRet = url;
		char cColon = 65306;
		char cPeriod = 12290;
		strRet = strRet.replace(cColon, ':');
		strRet = strRet.replace(cPeriod, '.');

		return strRet;
	}

	public static String processUrlBeforeCompare(String url) {
		String retStr = PATTERN_FOR_MYNAVIDATA.matcher(url).replaceAll("");
		return retStr;
	}

	/**
	 * To avoid connecting to internet(which will take time) to get the title
	 * behind the url, here we just cut the unnessary information in the url to
	 * generate the title.
	 * 
	 * @return
	 */
	public static String getTitleFromUrl(String url) {
		String result = null;
		String orginUrl = url;
		if (null != url) {

			int index = -1;
			if (orginUrl.contains(".com")) {
				index = orginUrl.indexOf(".com");
				orginUrl = orginUrl.substring(0, index);
				DLog.i(TAG, "After cut .com---->" + orginUrl);
			}

			if (orginUrl.startsWith("http://")) {
				index = orginUrl.indexOf("http://");
				orginUrl = orginUrl.substring(7);
				DLog.i(TAG, "After cut http://--->" + orginUrl);
			}
			if (orginUrl.contains("www.")) {
				index = orginUrl.indexOf("www.");
				orginUrl = orginUrl.substring(4);
				DLog.i(TAG, "After cut www.---->" + orginUrl);
			}
			if (orginUrl.endsWith(".net/")) {
				index = orginUrl.indexOf(".net/");
				orginUrl = orginUrl.substring(0, index);
				DLog.i(TAG, "After cut .net suffix---->" + orginUrl);
			}

			if (orginUrl.endsWith(".cn/")) {
				index = orginUrl.indexOf(".cn/");
				orginUrl = orginUrl.substring(0, index);
				DLog.i(TAG, "After cut .net suffix---->" + orginUrl);
			}
		}
		result = orginUrl;
		return result;
	}

	public static boolean isUrlEqual(String first, String second) {
		if (first == null && second == null) {
			// if both if null, we regard they are equal.
			return true;
		}

		if (first == null || second == null) {
			return false;
		}

		first = cutHttpOrHttpsAndTrimTailSlash(first);
		second = cutHttpOrHttpsAndTrimTailSlash(second);

		if (first.equalsIgnoreCase(second)) {
			return true;
		}

		return false;
	}

	public static String trimAnchorPart(String url) {
		int lastPartStartPos = url.lastIndexOf('/');
		int hashPos = url.indexOf('#', lastPartStartPos);
		if (hashPos != -1) {
			url = url.substring(0, hashPos);
		}
		return url;
	}

	static final String mPicturePostfix[] = { ".bmp", ".jpg", ".jpeg", ".png", ".gif" };

	public static boolean isPictureUrl(String url) {
		boolean ret = false;
		if (url != null) {
			url = url.toLowerCase().trim();
			for (int i = 0; i < mPicturePostfix.length; i++) {
				if (url.endsWith(mPicturePostfix[i])) {
					ret = true;
					break;
				}
			}
		}

		return ret;
	}

	public static String getDomain(String url) {
		if (url == null) {
			// if url is null, Uri.parse method called will crash the app
			return "";
		}

		Uri uri = Uri.parse(url);
		if (uri == null) {
			return "";
		}

		String domain = uri.getHost();
		if (domain == null) {
			// if the parameter url is invalid
			return "";
		}

		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static boolean isDataUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}

		return url.startsWith(DATA_PREFIX);
	}
}