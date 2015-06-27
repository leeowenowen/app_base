package com.msjf.fentuan.gateway_service.fans_group;

import android.text.TextUtils;
import android.view.TextureView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.LayerJsonResponseHandler;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.user.Self;
import com.owo.base.pattern.Singleton;

/**
 * Created by wangli on 15-5-28.
 */
public class FansGroupClient {
    private static final String TAG = "FansGroupCient";
    //获取粉团列表
    public static final String get_fangroup_list = "/v1/fangroup/get_fangroup_list";
    //获取粉团详细信息
    public static final String get_fangroup_info = "/v1/fangroup/get_fangroup_info";
    //关注粉团
    public static final String focus_fangroup = "/v1/fangroup/focus_fangroup";
    //取消关注粉团
    public static final String cancel_focus_fangroup = "/v1/fangroup/cancel_focus_fangroup";

    //获取用户关注粉团列表
    public static final String get_user_fangroup_list = "/v1/fangroup/get_user_fangroup_list";
    //粉团说吧发表内容
    public static final String send_fangroup_said = "/v1/fangroup/send_fangroup_said";


    public static void getFansGroupList(int pageNum, int pageSize, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("pageNum", pageNum);
        fields.put("pageSize", pageSize);
        fields.put("userId", Self.user().getId());
        RequestCenter.getInstance().post(get_fangroup_list, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getFansGroupList success !" + jsonObject.toJSONString());
                JSONArray array = jsonObject.getJSONArray("fanGroupInfoList");
                FansGroupData.instance().clear();
                String currentSelecte = null;
                for (int i = 0; i < array.size(); ++i) {
                    FansGroupItem item = FansGroupItem.fromJson(array.getJSONObject(i));
                    FansGroupData.instance().add(item);

                    if(TextUtils.isEmpty(currentSelecte) && item.isFocusFlag())
                    {
                        currentSelecte = item.getTid();
                    }
                }
                FansGroupData.instance().setCurrentFanGroupId(currentSelecte);
                FansGroupData.instance().notifyDataSetChanged();
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getFansGroupList failed !" + jsonObject.toJSONString());
            }
        });
    }

    public static void getFansGroupInfo(String fanGroupId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("fanGroupId", fanGroupId);
        fields.put("userId", Self.user().getId());
        RequestCenter.getInstance().post(get_fangroup_info, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getFansGroupInfo success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getFansGroupInfo failed !" + jsonObject.toJSONString());
            }
        });
    }

    public static void focusFanGroup(String fanGroupId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("fanGroupId", fanGroupId);
        fields.put("userId", Self.user().getId());
        RequestCenter.getInstance().post(focus_fangroup, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "focusFanGroup success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "focusFanGroup failed !" + jsonObject.toJSONString());
            }
        });
    }

    public static void cancelFocusFanGroup(String fanGroupId, JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("fanGroupId", fanGroupId);
        fields.put("userId", Self.user().getId());
        RequestCenter.getInstance().post(cancel_focus_fangroup, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "cancelFocusFanGroup success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "cancelFocusFanGroup failed !" + jsonObject.toJSONString());
            }
        });
    }

    public static void getUserFanGroupList(JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", Self.user().getId());
        RequestCenter.getInstance().post(get_user_fangroup_list, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getUserFanGroupList success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "getUserFanGroupList failed !" + jsonObject.toJSONString());
            }
        });
    }


    public static void send_fangroup_said(String fanGroupId,//
                                          String content,//
                                          JsonResponseHandler handler) {
        JSONObject fields = new JSONObject();
        fields.put("userId", Singleton.of(Self.class).getUser().getId());
        fields.put("fanGroupId", fanGroupId);
        fields.put("content", content);
        RequestCenter.getInstance().post(send_fangroup_said, fields, new LayerJsonResponseHandler(handler) {
            @Override
            protected void onSuccessImpl(JSONObject jsonObject) {
                Logger.v(TAG, "send_fangroup_said success !" + jsonObject.toJSONString());
            }

            @Override
            protected void onFailureImpl(JSONObject jsonObject) {
                Logger.v(TAG, "send_fangroup_said failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }
}
