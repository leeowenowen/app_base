package com.msjf.fentuan.app;

import java.util.Map;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.domain.User;
import com.msjf.fentuan.log.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;

public class App extends DemoApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // initialize
        BaseHandler.initialize();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheSize(1024 * 1024 * 4).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        BaseHandler.destroy();
        ContextManager.destroy();
        Singleton.destroy();
        super.onTerminate();
    }

    class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            Log.e("Master", "Uncaught exception met from thread " + t.getName(), e);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    ;
}
