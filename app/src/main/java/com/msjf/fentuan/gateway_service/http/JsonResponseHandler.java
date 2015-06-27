package com.msjf.fentuan.gateway_service.http;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.TextHttpResponseHandler;
import com.msjf.fentuan.log.Logger;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

/**
 * Created by Esimrop on 15/5/24.
 */
public abstract class JsonResponseHandler extends TextHttpResponseHandler {
    private static final String TAG = "JsonResponseHandler";

    @Override
    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
        //TODO 拼装错误信息调用onFailure
//        Log.e("back", s);
        onFailure(new JSONObject());
    }

    @Override
    public void onSuccess(int i, Header[] headers, String s) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(s);
            boolean success = true;
            //TODO 检查请求是否成功
            String status = jsonObject.getString("request_success");
            if (status == null || !status.equalsIgnoreCase("true")) {
                String errorCode = jsonObject.getString("error_code");
                String errorMsg = jsonObject.getString("message");
                Logger.e(TAG, "[errorCode:" + errorCode + "]response status is not ok. msg:" + errorMsg);
                success = false;
            }

            // String responseSecureMode = json.getString("secure_mode");
            JSONObject serviceResult = null;
            if (status.equalsIgnoreCase("true")) {
                serviceResult = jsonObject.getJSONObject("service_result");
                String serviceSuccess = serviceResult.getString("service_success");

                if ("false".equals(serviceSuccess)) {
                    String serviceCode = serviceResult.getString("service_code");
                    String serviceMsg = serviceResult.getString("service_message");
                    Logger.e(TAG, "service error:" + serviceMsg);
                    success = false;
                }
            }
            if (success) {
                onSuccess(serviceResult);
            } else {
                //TODO 拼装错误信息并调用onFailure
                onFailure(new JSONObject());
            }

        } catch (Exception e) {
            //TODO 提示服务器返回异常
            onFailure(new JSONObject());
        }
    }

    public abstract void onSuccess(JSONObject jsonObject);

    public abstract void onFailure(JSONObject jsonObject);

}
