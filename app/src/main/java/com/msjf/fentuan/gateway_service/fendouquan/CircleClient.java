package com.msjf.fentuan.gateway_service.fendouquan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.LayerJsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.LogHelper;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.user.Self;
import com.owo.base.pattern.Singleton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangli on 15-5-26.
 */
public class CircleClient {
    private static final String TAG = "CircleClient";
    //获取粉逗圈消息列表（同时获取前5条评论信息和所有的图片信息）
    private static final String get_circle_list = "/v1/circle/get_circle_list";
    //获取粉逗圈单条消息 （同时获取前5条评论信息和所有的图片信息）
    private static final String get_circle_info = "/v1/circle/get_circle_info";
    //发布粉逗圈消息
    private static final String send_circle_message = "/v1/circle/send_circle_message";
    //删除粉逗圈消息
    private static final String delete_circle_message = "/v1/circle/delete_circle_message";
    //粉逗圈消息浏览次数+1
    private static final String circle_browse_add = "/v1/circle/circle_browse_add";
    //粉逗圈点赞
    private static final String send_circle_praise = "/v1/circle/send_circle_praise";
    //粉逗圈取消点赞
    private static final String send_circle_cancel_praise = "/v1/circle/send_circle_cancel_praise";
    //发表粉逗圈消息评论
    private static final String send_circle_reply = "/v1/circle/send_circle_reply";
    //删除粉逗圈消息评论
    private static final String delete_circle_reply = "/v1/circle/delete_circle_reply";
    //获取粉逗圈消息评论列表
    private static final String get_circle_reply_list = "/v1/circle/get_circle_reply_list";


    public static void getCircleList(int pageNum,//
                                     int pageSize,//
                                     JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", Singleton.of(Self.class).getUser().getId());
        fields.put("pageNum", "" + pageNum);
        fields.put("pageSize", "" + pageSize);
        RequestCenter.getInstance().post(get_circle_list, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void getCircleInfo(String circleId,//
                                     JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("circleId", circleId);

        RequestCenter.getInstance().post(get_circle_info, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleInfo success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleInfo failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void sendCircleMessage(String userId, //
                                         String content,//
                                         boolean isAnonymous,//
                                         List<String> qiniuKeys,//
                                         JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", userId);
        fields.put("content", content);
        //0 为匿名, 1为非匿名
        fields.put("anonymousFlag", isAnonymous ? "0" : "1");

        if (null != qiniuKeys) {
            JSONArray circleImageList = new JSONArray();
            for (String key : qiniuKeys) {
                JSONObject image = new JSONObject();
                image.put("qiniuKey", key);
                circleImageList.add(image);
            }
            fields.put("circleImageList", circleImageList);
        }


        RequestCenter.getInstance().post(send_circle_message, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleMessage success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleMessage failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void deleteCircleMessage(String circleId,//
                                           JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("circleId", circleId);

        RequestCenter.getInstance().post(delete_circle_message, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "deleteCircleMessage success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "deleteCircleMessage failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void circleBrowserAdd(String circleId,//
                                        JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("circleId", circleId);
        RequestCenter.getInstance().post(circle_browse_add, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "circleBrowserAdd success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "circleBrowserAdd failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void sendCirclePraise(String circleId,//
                                        String userId,//
                                        JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("circleId", circleId);
        fields.put("userId", userId);
        RequestCenter.getInstance().post(send_circle_praise, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCirclePraise success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCirclePraise failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }


    public static void sendCircleCanclePraise(String circleId,//
                                              String userId,//
                                              JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("circleId", circleId);
        fields.put("userId", userId);
        RequestCenter.getInstance().post(send_circle_cancel_praise, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleCanclePraise success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleCanclePraise failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void sendCircleReply(String circleId,//
                                       String userId,//
                                       String replayId,//
                                       String content,//
                                       JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("circleId", circleId);
        fields.put("userId", userId);
        fields.put("replyId", replayId);
        fields.put("content", content);
        RequestCenter.getInstance().post(send_circle_reply, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleReply success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleReply failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void deleteCircleReply(
            String replayId,//
            JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("replyId", replayId);
        RequestCenter.getInstance().post(delete_circle_reply, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleReply success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "sendCircleReply failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void getCircleReplyList(int pageNum,//
                                          int pageSize,//
                                          String circleId,//
                                          JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();

        fields.put("pageNum", "" + pageNum);
        fields.put("pageSize", "" + pageSize);
        fields.put("circleId", circleId);
        RequestCenter.getInstance().post(get_circle_reply_list, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleReplyList success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleReplyList failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }


    public static void testMain() {
//        getCircleList(1, 5, null);
//        getCircleInfo("d34467abf37c45b382634016f40f8a87", null);
//        sendCircleMessage("860a7ecfb46145d2905f1462b0d1a1e6",//
//                "测试粉逗圈消息",//
//                false, Arrays.asList("key1", "key2", "key3"), null);
//        deleteCircleMessage("95d5a8bac13243ee893a0de4a6e0c9bc", null);
//        circleBrowserAdd("acb66a7feb01420fb23618a8c07ab14f", null);
//        sendCirclePraise("95d5a8bac13243ee893a0de4a6e0c9bc", "860a7ecfb46145d2905f1462b0d1a1e6", null);
//        sendCircleCanclePraise("c446b696c36744bf8bc69dfe48fd4ab7", "0001", null);
//        sendCircleReply("95d5a8bac13243ee893a0de4a6e0c9bc", //
//                "860a7ecfb46145d2905f1462b0d1a1e6", "", "测试回复", null);
//        deleteCircleReply("3f8622489e0c46bcb238c09c34e41535", null);
//        getCircleReplyList(1, 5, "c446b696c36744bf8bc69dfe48fd4ab7", null);
    }

}
