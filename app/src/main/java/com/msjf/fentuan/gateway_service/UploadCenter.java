package com.msjf.fentuan.gateway_service;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * Created by Esimrop on 15/5/24.
 */
public class UploadCenter {
    private final static UploadCenter uploadCenter = new UploadCenter();
    private UploadManager uploadManager;

    private UploadCenter() {
        uploadManager = new UploadManager();

    }

    private void getUploadToken(JsonResponseHandler handler) {
        //TODO 请求Token


    }

    public void uploadImageByPath(final String imagePath, final UploadCallBack callBack) {
        getUploadToken(new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                final String key = "";
                String token = "";
                //TODO 生成文件Key 从服务器返回值中获取token
                uploadManager.put(imagePath, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String s, ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
                        boolean success = true;
                        //TODO 判断是否上传成功

                        if (success) {
                            callBack.onUploadSuccess(key);
                        } else {
                            callBack.onUploadFailure();
                        }
                    }
                }, null);
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                callBack.onUploadFailure();
            }
        });
    }


    public interface UploadCallBack {

        void onGetTokenError();

        void onUploadFailure();

        void onUploadSuccess(String key);
    }

}
