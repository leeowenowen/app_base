package com.msjf.fentuan.gateway_service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.me.hong_bao.HongBaoData;
import com.msjf.fentuan.user.Self;
import com.owo.base.pattern.Singleton;

/**
 * Created by wangli on 15-6-5.
 */
public class HongBaoClient {
    private static String TAG = "HongBaoClient";
    //    #接口说明：
//    获取下一条红包记录
//    /v1/redenvelope/status
//    不需要传递参数。
//    返回结果分两种情况：
//    //未在抢红包时间内，返回下一条红包的时间
//    {"request_success":"true","service_result":{"service_success":"true","next":65396}}
//    //在抢红包时间内，返回当前红包的id
//    {"request_success":"true","service_result":{"service_success":"true","redEnvelopeId":"6c25514409a711e5a4a500163e0031f2"
    private static final String status = "/v1/redenvelope/status";

    //    #抢红包
//    /v1/redenvelope/snatch
//    传递参数：redEnvelopeId:红包id，userId:用户id
//            返回结果
//    如果该用户已抢过红包，则返回exists，如果没抢过，则没有exists
//    {"request_success":"true","service_result":{"service_success":"true","exists":true,"subRedEnvelopeId":"6c71f53009a711e5a4a500163e0031f2","amount":5.39}}
    private static final String snatch = "/v1/redenvelope/snatch";

    private static final String get_user_red_envelope = "/v1/user/get_user_red_envelope";

    public static void status(JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        RequestCenter.getInstance().post(status, fields, handler);
    }

    public static void snatch(String hongbaoId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("redEnvelopeId", hongbaoId);
        fields.put("userId", Singleton.of(Self.class).getUser().getId());
        RequestCenter.getInstance().post(snatch, fields, handler);
    }

    public static void get_user_red_envelope(int pageNum,//
                                             int pageSize) {
        JSONObject fields = new JSONObject();
        fields.put("userId", Self.user().getId());
        fields.put("pageNum", "" + pageNum);
        fields.put("pageSize", "" + pageSize);

        RequestCenter.getInstance().post(get_user_red_envelope, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                JSONArray array = jsonObject.getJSONArray("redEnvelopeUserList");
                if(array != null)
                {
                    HongBaoData.instance().clear();
                    for(int i = 0; i < array.size(); ++i)
                    {
                        JSONObject object = array.getJSONObject(i);
                        HongBaoData.HongBaoDataItem item = HongBaoData.HongBaoDataItem.fromJson(object);
                        HongBaoData.instance().add(item, false);
                    }
                    HongBaoData.instance().notifyDataSetChanged();
                }
                Logger.v(TAG, "get_user_red_envelope success !" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "get_user_red_envelope Failed !");
            }
        });
    }
}
