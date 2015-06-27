package com.msjf.fentuan.app.com.msjf.fentuan.app.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.owo.app.common.ContextManager;

/**
 * Created by wangli on 15-5-27.
 */
public class SystemSettings {
    private static final String NAME = "com.msjf.fentuan.system_setting";

    public static String getPhone() {
        SharedPreferences sp = ContextManager.context().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString("phone_num", "");
    }

    public static void setPhone(String phone) {
        SharedPreferences sp = ContextManager.context().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString("phone_num", phone).commit();
    }

    public static String getUserId()
    {
        SharedPreferences sp = ContextManager.context().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString("user_id", "");
    }

    public static void setUserId(String id)
    {
        SharedPreferences sp = ContextManager.context().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString("user_id", id).commit();
    }
}
