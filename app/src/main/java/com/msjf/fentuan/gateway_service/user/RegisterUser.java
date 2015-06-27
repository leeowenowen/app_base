package com.msjf.fentuan.gateway_service.user;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.QiNiuClient;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.user.MobUser;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.Sex;
import com.msjf.fentuan.user.User;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;

public class RegisterUser {
    private static final String TAG = "RegisterUser";
    private static final String SERVICE = "/v1/user/register_user";

    public static void start(final Bitmap photoBmp,//
                             final String phone,//
                             final String password,//
                             final String userName,//
                             final String starName,//
                             final Sex sex,//
                             String cityId,//
                             final String cityName,//
                             final String remark,
                             final JsonResponseHandler handler) {
        Singleton.of(Self.class).getUser().getPhoto().setBmp(photoBmp);
        if (photoBmp != null) {
            QiNiuClient.uploadImage(photoBmp, new Callback<QiNiuClient.ImageUploadResponseItem>() {
                @Override
                public void run(QiNiuClient.ImageUploadResponseItem param) {
                    Singleton.of(Self.class).getUser().setAvatar(param.key);
                    JSONObject obj = new JSONObject();
                    String cityId_ = "28";
                    obj.put("phone", phone);
                    //    obj.put("password", password);
                    obj.put("userName", userName);
                    obj.put("fansGroupName", userName);
                    obj.put("starName", starName);
                    obj.put("sex", "" + sex.ordinal());
                    obj.put("cityId", cityId_);
                    obj.put("cityName", cityName);
                    obj.put("remark", remark);

                    JSONObject userImage = new JSONObject();
                    userImage.put("fileName", param.key);
                    userImage.put("oldFileName", "");
                    userImage.put("suffix", "bmp");
                    userImage.put("path", param.key);//
                    userImage.put("qiniuKey", param.key);//来自七牛
                    userImage.put("fileHash", param.hash);//来自七牛

                    obj.put("userImage", userImage);
                    RequestCenter.getInstance().post(SERVICE, obj, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject json) {
                            JSONObject jsonUser = json.getJSONObject("userInfo");
                            JSONObject jsonMobUser = json.getJSONObject("easemob_user");
                            Self self = Singleton.of(Self.class);
                            self.setUser(User.fromJson(jsonUser));
                            self.getUser().setMobUser(MobUser.fromJson(jsonMobUser));

                            handler.onSuccess(json);
                        }

                        @Override
                        public void onFailure(JSONObject jsonObject) {

                            handler.onFailure(jsonObject);
                        }
                    });
                }
            });
        } else {
            JSONObject obj = new JSONObject();
            String cityId_ = "28";
            obj.put("phone", phone);
            //    obj.put("password", password);
            obj.put("userName", userName);
            obj.put("fansGroupName", userName);
            obj.put("starName", starName);
            obj.put("sex", "" + sex.ordinal());
            obj.put("cityId", cityId_);
            obj.put("cityName", cityName);
            obj.put("remark", remark);

            RequestCenter.getInstance().post(SERVICE, obj, new JsonResponseHandler() {
                @Override
                public void onSuccess(JSONObject json) {
                    JSONObject jsonUser = json.getJSONObject("userInfo");
                    JSONObject jsonMobUser = json.getJSONObject("easemob_user");
                    Self self = Singleton.of(Self.class);
                    self.setUser(User.fromJson(jsonUser));
                    self.getUser().setMobUser(MobUser.fromJson(jsonMobUser));

                    handler.onSuccess(json);
                }

                @Override
                public void onFailure(JSONObject jsonObject) {

                    handler.onFailure(jsonObject);
                }
            });
        }
    }
}
