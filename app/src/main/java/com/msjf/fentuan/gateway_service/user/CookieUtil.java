package com.msjf.fentuan.gateway_service.user;

/**
 * Created by wangli on 15-5-25.
 */
public class CookieUtil {
    public static final String SESSIONID = "JSESSIONID";
    public static String getValue(String cookieLine, String key) {
        String[] cookies = cookieLine.split(";");
        for (String cookie : cookies) {
            String[] kv = cookie.split("=");
            if (kv[0].equals(key)) {
                return kv[1];
            }
        }
        return null;
    }
}
