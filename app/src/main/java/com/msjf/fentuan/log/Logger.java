package com.msjf.fentuan.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br>==========================
 * <br> 公司：九游
 * <br> 开发：wangli
 * <br> 版本：1.0
 * <br> 创建时间：2015/05/05
 * <br>==========================
 */
public class Logger {
    public static enum LogLevel {
        VERBOSE,//
        INFO,//
        WARN,//
        ERROR,//
    }

    public static enum LogType {
        LOG,//
        STAT,//
    }

    public static interface Delegate {
        void log(LogType type, LogLevel level, String dateTime, String tag, String msg);
    }

    public static void setDelegate(Delegate delegate) {
        mDelegateWrapper = new DelegateWrapper(delegate);
    }

    private static class DelegateWrapper {
        private Delegate mDelegate;


        public DelegateWrapper(Delegate delegate) {
            mDelegate = delegate;
        }

        public void log(LogType type, LogLevel level, String tag, String msg) {
            Log.v(tag, msg);
            if (mDelegate == null) {
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日" + "hh:mm:ss");
            String dateTime = formatter.format(new Date());
            mDelegate.log(LogType.LOG, LogLevel.VERBOSE, dateTime, tag, msg);
        }
    }

    private static DelegateWrapper mDelegateWrapper = new DelegateWrapper(null);

    public static void v(String tag, String msg) {
        mDelegateWrapper.log(LogType.LOG, LogLevel.VERBOSE, tag, msg);
    }

    public static void i(String tag, String msg) {
        mDelegateWrapper.log(LogType.LOG, LogLevel.INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        mDelegateWrapper.log(LogType.LOG, LogLevel.WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        mDelegateWrapper.log(LogType.LOG, LogLevel.ERROR, tag, msg);
    }

    public static void stat(String key, String value) {
        mDelegateWrapper.log(LogType.LOG, LogLevel.ERROR, key, value);
    }
}
