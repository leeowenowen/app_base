package com.msjf.fentuan.app.logic;

public class ParamKeys {
	public static final String SERVICE = "service";
	public static final String JSON_DATA = "jsonData";

	public static final String RES_CODE = "responseCode";
	public static final String JSON_RESPONSE = "jsonResponse";
	public static final String INT_RESPONSE = "intResponse";
	public static final String BOOLEAN_RESPONSE = "booleanResponse";
	public static final String STRING_RESPONSE = "booleanResponse";

	public static final String PKG_NAME = "pkgName";
	public static final String VER = "ver";
	public static final String UPDATE_FLAG = "updateFlag";

	// download
	public static final String DOWNLOAD_URL = "download_url";
	// 下载文件保存地址
	public static final String DOWNLOAD_SAVE_PATH = "download_save_path";
	// 非静默下载时需提供
	public static final String DOWNLOAD_MIME_TYPE = "download_mimeType";
	// 不提供此参数默认为静默下载,此参数为true为“可感知”下载(通过系统的DownloadManager下载,或通过第三方app下载)
	public static final String DOWNLOAD_SILENT = "download_silent";
	// 调用下载模块返回状态,参照DownloadState中各值的定义
	public static final String DOWNLOAD_STATE = "download_state";
	// 下载不成功时携带的"原因状态码"
	public static final String DOWNLOAD_REASON = "download_reason";
	// 下载进度更新,文件总size
	public static final String DOWNLOAD_FILE_SIZE = "download_file_size";
	// 下载进度更新,当前已下载size
	public static final String DOWNLOAD_CURRENT_SIZE = "download_current_size";

}
