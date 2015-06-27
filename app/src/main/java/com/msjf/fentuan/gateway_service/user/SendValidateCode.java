package com.msjf.fentuan.gateway_service.user;

import com.loopj.android.http.TextHttpResponseHandler;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.user.Self;
import com.owo.base.pattern.Singleton;

import org.apache.http.Header;


public class SendValidateCode {
    private static final String TAG = "SendValidateCode";

    public static void start(final String phone, final JsonResponseHandler handler) {
        JSONObject obj = new JSONObject();
        obj.put("phone", phone);
        RequestCenter.getInstance().post("/v1/user/send_validate_code", obj, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Logger.e(TAG, "SendValidateCode failed");
                handler.onFailure(null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                for (Header header : headers) {
                    if (header.getName().equals("Set-Cookie")) {
                        String sessionId = CookieUtil.getValue(header.getValue(), CookieUtil.SESSIONID);
                        Singleton.of(Self.class).setVerifyCodeSessionId(sessionId);
                    }
                }
                handler.onSuccess(null);
            }
        });
    }
}
