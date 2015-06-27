package com.msjf.fentuan.gateway_service.user;


import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.user.Self;
import com.owo.base.pattern.Singleton;

public class Login {
	private static final String TAG = "GatewayClient";
	private static final String SERVICE = "/v1/user/do_login";

    public static void start(final String phone, final String password, final JsonResponseHandler handler)
    {
        JSONObject obj = new JSONObject();
        obj.put("phone", phone);
        obj.put("password", password);
        RequestCenter.getInstance().post(SERVICE, obj, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject jsonObject) {
                Singleton.of(Self.class).getUser().setId(jsonObject.getString("tid"));
                Singleton.of(Self.class).getUser().setPhone(phone);
                Logger.v(TAG, jsonObject.toString());
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {

            }
        });
    }
}
