package com.msjf.fentuan.ui.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.owo.app.common.ContextManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangli on 15-5-27.
 */
public class LoadingCtrl {
    public static Map<Context, ProgressDialog> mProgressDialogs = new HashMap<>();

    public static void start(String message) {
        Context context = ContextManager.context();
        ProgressDialog dialog = mProgressDialogs.get(context);
        if (dialog == null) {
            dialog = new ProgressDialog(ContextManager.context());
            mProgressDialogs.put(context, dialog);
        }
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        dialog.show();
    }

    public static void stop() {
        Context context = ContextManager.context();
        ProgressDialog dialog = mProgressDialogs.get(context);
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
        }
    }

    public static void dismiss(Context context){
        ProgressDialog dialog = mProgressDialogs.get(context);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            mProgressDialogs.remove(context);
        }
    }
}

