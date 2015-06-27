package com.msjf.fentuan.gateway_service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.LayerJsonResponseHandler;
import com.msjf.fentuan.gateway_service.ShuoBaData;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.user.Self;

/**
 * Created by wangli on 15-5-31.
 */
public class UserClient {
    private static final String TAG = "User";
    //发送短信验证码
    private static final String send_validate_code = "/v1/user/send_validate_code";
    //校验验证码
    private static final String check_validate_code = "/v1/user/check_validate_code";
    //新注册用户
    private static final String register_user = "/v1/user/register_user";
    //用户登录
    private static final String do_login = "/v1/user/do_login";
    //注销登录
    private static final String do_logout = "/v1/user/do_logout";

    //获取用户单条消息
    private static final String get_user_info = "/v1/user/get_user_info";
    //获取用户交易记录
    private static final String get_user_trade_record = "/v1/user/get_user_trade_record";
    //关注用户
    private static final String focus_user = "/v1/user/focus_user";
    //取消关注用户
    private static final String cancel_focus_user = "/v1/user/cancel_focus_user";
    //用户发送消息
    private static final String user_send_message = "/v1/user/user_send_message";
    //获取用户的会话列表
    private static final String get_user_dialogue_list = "/v1/user/get_user_dialogue_list";
    //获取用户的消息列表
    private static final String get_user_message_list = "/v1/user/get_user_message_list";
    //用户删除消息
    private static final String user_delete_message = "/v1/user/user_delete_message";
    //用户删除会话
    private static final String user_delete_dialogue = "/v1/user/user_delete_dialogue";
    //更改用户头像
    private static final String change_user_avator = "/v1/user/change_user_avator";
    //更新地理位置信息
    public static final String geo_update = "/v1/user/geo_update";
    //获取附近用户消息
    private static final String get_nearby_user_msg = "/v1/user/get_nearby_user_msg";


    public static void get_user_info(String userId, String focusId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("userId", userId);
        fields.put("focusId", focusId);

        RequestCenter.getInstance().post(get_user_info, fields, handler);
    }

    public static void get_user_info_by_phone(String phone, String focusId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("phone", phone);
        fields.put("focusId", focusId);

        RequestCenter.getInstance().post(get_user_info, fields, handler);
    }


    public static void focus_user(String userId, String focusId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("userId", userId);
        fields.put("fanUserId", focusId);

        RequestCenter.getInstance().post(focus_user, fields, new LayerJsonResponseHandler(handler) {
            @Override
            public void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "focus_user success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "focus_user Failed !");
            }
        });
    }

    public static void cancel_focus_user(String userId, String focusId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("userId", userId);
        fields.put("fanUserId", focusId);

        RequestCenter.getInstance().post(cancel_focus_user, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "cancel_focus_user success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "cancel_focus_user Failed !");
            }
        });
    }

    public static void user_send_message(String sendId, String recvId, String content, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("sendId", sendId);
        fields.put("receiveId", recvId);
        fields.put("content", content);

        RequestCenter.getInstance().post(user_send_message, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "user_send_message success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "user_send_message Failed !");
            }
        });
    }

    public static void get_user_dialogue_list(String userId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", userId);

        RequestCenter.getInstance().post(get_user_dialogue_list, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "get_user_dialogue_list success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "get_user_dialogue_list Failed !");
            }
        });
    }

    public static void get_user_message_list(String sendId, String recvId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("sendId", sendId);
        fields.put("receiveId", recvId);

        RequestCenter.getInstance().post(get_user_message_list, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "get_user_message_list success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "get_user_message_list Failed !");
            }
        });
    }

    public static void user_delete_message(String messageId, boolean sendReceiveFlag, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("messageId", messageId);
        fields.put("sendReceiveFlag", sendReceiveFlag ? 1 : 0);

        RequestCenter.getInstance().post(user_delete_message, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "user_delete_message success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "user_delete_message Failed !");
            }
        });
    }

    public static void user_delete_dialogue(String sendId, String receiveId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("sendId", sendId);
        fields.put("receiveId", receiveId);

        RequestCenter.getInstance().post(user_delete_dialogue, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "user_delete_dialogue success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "user_delete_dialogue Failed !");
            }
        });
    }

    public static void change_user_avator(String userId, String qiniuKey, String path, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("userId", userId);
        fields.put("qiniuKey", qiniuKey);
        fields.put("path", path);

        RequestCenter.getInstance().post(change_user_avator, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "change_user_avator success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "change_user_avator Failed !");
            }
        });
    }

    public static void geo_update(String latitude, String longitude) {
        JSONObject fields = new JSONObject();

        fields.put("userId", Self.user().getId());
        fields.put("latitude", latitude);
        fields.put("longitude", longitude);

        RequestCenter.getInstance().post(geo_update, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "geo_update success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "geo_update Failed !");
            }
        });
    }

    public static void get_nearby_user_msg(JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", Self.user().getId());
        fields.put("precision", 1500);
        RequestCenter.getInstance().post(get_nearby_user_msg, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "get_nearby_user_msg success !" + jsonObject.toJSONString());
                JSONArray array = jsonObject.getJSONArray("users");
                ShuoBaData.instance().clear();
                for (int i = 0; i < array.size(); ++i) {
                    JSONObject obj = array.getJSONObject(i);
                    ShuoBaData.ShuoBaItem item = ShuoBaData.ShuoBaItem.fromJson(obj);
                    ShuoBaData.instance().add(item);
                }
                ShuoBaData.instance().notifyDataSetChanged();
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "get_nearby_user_msg failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    /*
    粉友在:地理位置上的右键单击弹出“添加”“锤击”这是其一，其二，在粉逗圈，添加关注，即可关注粉友
     */

//    public static void change_user_avator() {
//        JSONObject fields = new JSONObject();
//
//        fields.put("userId", "ea32653c34a849288c97aea294298615");
//        fields.put("qiniuKey", "data/image/circle/20150530/f38bfd4a772c4b32b81aae1e6830ea01.bmp");
//        fields.put("path", "data/image/circle/20150530/f38bfd4a772c4b32b81aae1e6830ea01.bmp");
//
//        ClientServer.clientServer("/v1/user/change_user_avator", fields);
//    }

//    public static void get_user_red_envelope() {
//        JSONObject fields = new JSONObject();
//
//
//        ClientServer.clientServer("/v1/user/get_user_red_envelope", fields);
//    }

//
//    public static void set_user_oauth() {
//        JSONObject fields = new JSONObject();
//
//        ClientServer.clientServer("/v1/user/set_user_oauth", fields);
//    }
//
//    public static void get_user_focus_list() {
//        JSONObject fields = new JSONObject();
//
//        ClientServer.clientServer("/v1/user/get_user_focus_list", fields);
//    }
//
//    public static void get_user_fan_friends_list() {
//        JSONObject fields = new JSONObject();
//
//        ClientServer.clientServer("/v1/user/get_user_fan_friends_list", fields);
//    }
}
