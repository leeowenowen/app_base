package com.msjf.fentuan.gateway_service.http;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;
import com.msjf.fentuan.gateway_service.user.CookieUtil;
import com.msjf.fentuan.net.util.MD5Util;
import com.msjf.fentuan.user.Self;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;

import org.apache.http.Header;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;

/**
 * Created by Esimrop on 15/5/24.
 */
public class RequestCenter {
    private static final RequestCenter center = new RequestCenter();
    private AsyncHttpClient client;

    private final String BASE_URL = "http://182.92.1.109:9080/fan_group";
    // private final String BASE_URL = "http://172.23.9.9:9080/fan_group";
    private final String appid = "qweasd321123";
    private final String key = "abcd123456";
    private final int version = 1;
    private final String source = "android";
    private final String ext = "";

    private RequestCenter() {
        client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            AllSSLSocketFactory sf = new AllSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static RequestCenter getInstance() {
        return center;
    }


    public void get(String uri, ResponseHandlerInterface handler) {
        client.get(uri, handler);
    }

    public void testHttps() {
        client.get("https://192.168.0.188:8443/fan_group/v1/user/send_validate_code?key=abcd123456&timestamp=1431355998555&method=doLogin&fields=%7B%20%22phone%22:%20%2218623149838%22,%20%22password%22:%20%22123456%22%20%7D&sign=103DE05DA877FEA02E46122A915182DB", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.e("test", "onFailure:" + s + i);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("test", "onSuccess:" + s);
            }
        });
    }

    public void get(String uri, RequestParams params, TextHttpResponseHandler handler) {

    }

//    public void postWithoutSession(String uri, JSONObject object, ResponseHandlerInterface handler) {
//        long timeStep = System.currentTimeMillis();
//        RequestParams params = jsonObjectToRequestParams(object, getSign(uri, object, timeStep), timeStep);
//        client.post(getFullUrl(uri), params, handler);
//    }

    public void post(String uri, JSONObject object, ResponseHandlerInterface handler) {
        Header[] headers = new Header[]{new BasicHeader(CookieUtil.SESSIONID, Singleton.of(Self.class).getVerifyCodeSessionId())};
        long timeStep = System.currentTimeMillis();
        RequestParams params = jsonObjectToRequestParams(object, getSign(uri, object, timeStep), timeStep);
        client.post(ContextManager.context(), getFullUrl(uri), headers, params, "", handler);
    }

    private RequestParams jsonObjectToRequestParams(JSONObject object, String sign, long timeStep) {
        RequestParams params = new RequestParams();
        String requestData = toURLEncoded(object.toString());

        params.put("fields", requestData);
        params.put("sign", toURLEncoded(sign));
        params.put("timestamp", timeStep);
        params.put("appid", toURLEncoded(appid));
        params.put("version", version);
        params.put("source", toURLEncoded(source));

        Log.e("params", params.toString());
        return params;
    }

    private String getFullUrl(String uri) {
        return BASE_URL + uri;
    }

    private String getSign(String uri, JSONObject object, long timeStep) {
        StringBuffer str = new StringBuffer();
        str.append(toURLEncoded(appid));
        str.append(toURLEncoded(key));
        str.append(toURLEncoded(uri));
        str.append(toURLEncoded(object.toString()));
        str.append(timeStep);
        str.append(version);
        str.append(toURLEncoded(source));
        str.append(toURLEncoded(ext));
        return MD5Util.getMD5(str.toString());
    }

    private String toURLEncoded(String paramString) {
        String enc = "UTF-8";
        try {
            return URLEncoder.encode(paramString, enc);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
