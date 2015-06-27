package com.msjf.fentuan.gateway_service.user;


import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.hx.HXUtil;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupClient;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.user.MobUser;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CheckValidateCode {
    private static final String TAG = "CheckValidateCode";

    public static void start(final String phone, final String validateCode, final JsonResponseHandler handler) {
        com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
        obj.put("phone", phone);
        obj.put("validateCode", validateCode);
        RequestCenter.getInstance().post("/v1/user/check_validate_code", obj, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject json) {
                int isRegistered = json.getIntValue("is_register");
                JSONObject jsonUser = json.getJSONObject("userInfo");
                JSONObject jsonMobUser = json.getJSONObject("easemob_user");
                final Self self = Singleton.of(Self.class);
                self.getUser().setPhone(phone);
                self.setRegistered(isRegistered == 0);
                if (isRegistered == 0) {
                    self.setUser(User.fromJson(jsonUser));
                    MobUser mobUser = MobUser.fromJson(jsonMobUser);
                    self.getUser().setMobUser(mobUser);
                    HXUtil.login(mobUser.getUsername(), mobUser.getPassword(), new Callback<Boolean>() {
                        @Override
                        public void run(Boolean param) {
                            Logger.e(TAG, "login hx " + param);
                        }
                    });
                    String url = self.getUser().getAvatarUrl();
                    if (!TextUtils.isEmpty(url)) {
                        try {
                            url = URLDecoder.decode(url, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                self.getUser().getPhoto().setBmp(loadedImage);
                                self.getUser().getPhoto().setDownloadState(DownloadState.Downloaded);
                            }
                        });
                    }
                }
                Logger.v(TAG, json.toString());
                handler.onSuccess(json);
                FansGroupClient.getFansGroupList(1, 100, null);//获取所有
                FansGroupClient.getUserFanGroupList(null);
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {
                handler.onFailure(jsonObject);
            }
        });
    }
}
