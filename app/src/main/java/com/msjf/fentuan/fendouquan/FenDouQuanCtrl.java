package com.msjf.fentuan.fendouquan;

import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.fendouquan.CircleClient;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;

/**
 * Created by wangli on 15-5-27.
 */
public class FenDouQuanCtrl {
    private FenDouQuanCtrl() {
    }

    public void startCheck() {
        LoadingCtrl.start("正在加载粉逗圈信息...");
        CircleClient.getCircleList(1, FenDouQuanData.NUM_PER_PAGE, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                com.alibaba.fastjson.JSONArray cicleList = jsonObject.getJSONArray("circleInfoList");
                for (int i = 0; i < cicleList.size(); ++i) {
                    JSONObject circleJson = cicleList.getJSONObject(i);
                    CircleInfo info = CircleInfo.fromJson(circleJson);
                    Singleton.of(FenDouQuanData.class).add(info, false);
                    Logger.v("FenDouQuanLog", "getCircleItem[userId:" + info.getUserId() + "]");
                    if (i == 0) {
                        Logger.v("TestUserId", "first:" +  info.getUserId());
                    }
                }
                Singleton.of(FenDouQuanData.class).notifyDataSetChanged();
                LoadingCtrl.stop();
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Toast.makeText(ContextManager.context(), "Failed:" + jsonObject.toJSONString(), Toast.LENGTH_LONG).show();
                LoadingCtrl.stop();
            }
        });
    }

}
