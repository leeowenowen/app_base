package com.msjf.fentuan.gateway_service;

import android.graphics.Bitmap;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.http.RequestCenter;
import com.msjf.fentuan.log.Logger;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.Theme;
import com.owo.base.async.AllTask;
import com.owo.base.async.Task;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.BitmapHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangli on 15-5-27.
 */
public class QiNiuClient {
    private static final String get_upload_token = "/v1/qiniu/get_upload_token";
    private static final String get_download_token = "/v1/qiniu/get_download_token";
    static final String TAG = "QINIUTEST";

    public static class ImageUploadRequestItem {
        public Bitmap bmp;
        public String key;
        public String token;

        public ImageUploadRequestItem(Bitmap bmp, String key, String token) {
            this.bmp = bmp;
            this.key = key;
            this.token = token;
        }
    }

    public static class ImageUploadResponseItem {
        public String key;
        public String hash;

        public ImageUploadResponseItem(String key, String hash) {
            this.key = key;
            this.hash = hash;
        }
    }

    public static void uploadImage(final Bitmap bmp, final Callback<ImageUploadResponseItem> callback) {
        List<Bitmap> bmps = new ArrayList<Bitmap>();
        bmps.add(bmp);
        uploadImage(bmps, new Callback<List<ImageUploadResponseItem>>() {
            @Override
            public void run(List<ImageUploadResponseItem> param) {
                callback.run(param.get(0));
            }
        });
    }


    public static void uploadImage(final List<Bitmap> bmps, final Callback<List<ImageUploadResponseItem>> callback) {

        JSONArray array = new JSONArray();
        for (int i = 0; i < bmps.size(); ++i) {

            JSONObject obj = new JSONObject();
            obj.put("module", "circle");
            obj.put("uploadId", UUID.randomUUID());
            obj.put("suffix", "png");
            array.add(obj);
        }
        JSONObject fields = new JSONObject();
        fields.put("fileList", array);
        RequestCenter.getInstance().post(get_upload_token, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "uploadImage success !");
                JSONArray fileList = jsonObject.getJSONArray("fileList");
                List<ImageUploadRequestItem> items = new ArrayList<ImageUploadRequestItem>();
                for (int i = 0; i < fileList.size(); ++i) {
                    JSONObject obj = fileList.getJSONObject(i);
                    String key = obj.getString("key");
                    String token = obj.getString("uploadToken");
                    items.add(new ImageUploadRequestItem(bmps.get(i), key, token));
                }

                new AllTask<ImageUploadRequestItem, ImageUploadResponseItem>(new Task<ImageUploadRequestItem, ImageUploadResponseItem>() {
                    @Override
                    public void run(ImageUploadRequestItem item, final Callback<ImageUploadResponseItem> itemCallback) {
                        byte[] data = BitmapHelper.toByteArray(item.bmp);
                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(data, item.key, item.token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String s, final ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
                                        Log.i("qiniu", responseInfo.toString());
                                        String hash = jsonObject.optString("hash");
                                        String key = jsonObject.optString("key");
                                        String url = jsonObject.optString("url");
                                        itemCallback.run(new ImageUploadResponseItem(key, hash));
                                    }
                                }, new UploadOptions(null, null, false,
                                        new UpProgressHandler() {
                                            public void progress(String key, double percent) {
                                                Log.i("qiniu", key + ": " + percent);
                                            }
                                        }, null));
                    }
                }).run(items, new Callback<List<ImageUploadResponseItem>>() {
                    @Override
                    public void run(final List<ImageUploadResponseItem> param) {
                        ContextManager.activity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.run(param);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "uploadImage success !");
            }
        });
    }


    public static void downloadImage(final String url, final Callback<Bitmap> callback) {
        RequestCenter.getInstance().get(url, new DataAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap bmp = BitmapHelper.toBitmap(responseBody);
                Logger.v(TAG, "getCircleList success !");
                callback.run(bmp);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Logger.v(TAG, "getCircleList success !" + error.getMessage());
                callback.run(null);
            }
        });
    }

    public static void getImageDownloadUrl(List<String> keys, List<String> urls) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < keys.size(); ++i) {

            JSONObject obj = new JSONObject();
            obj.put("key", keys.get(i));
            array.add(obj);
        }
        JSONObject fields = new JSONObject();
        fields.put("fileList", array);
        RequestCenter.getInstance().post(get_download_token, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "getImageDownloadUrl success !");
                JSONArray fileList = jsonObject.getJSONArray("fileList");
                List<String> keys = new ArrayList<String>();
                for (int i = 0; i < fileList.size(); ++i) {
                    JSONObject obj = fileList.getJSONObject(i);

                    String key = obj.getString("key");
                    String url = obj.getString("url");

                }
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "getImageDownloadUrl success !");
            }
        });
    }

    private static void test2(List<String> keys) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < keys.size(); ++i) {

            JSONObject obj = new JSONObject();
            obj.put("key", keys.get(i));
            array.add(obj);
        }
        JSONObject fields = new JSONObject();
        fields.put("fileList", array);
        RequestCenter.getInstance().post(get_download_token, fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList success !");
                JSONArray fileList = jsonObject.getJSONArray("fileList");
                List<String> keys = new ArrayList<String>();
                for (int i = 0; i < fileList.size(); ++i) {
                    JSONObject obj = fileList.getJSONObject(i);

                    String key = obj.getString("key");
                    String url = obj.getString("url");
                    RequestCenter.getInstance().get(url, new DataAsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Bitmap bmp = BitmapHelper.toBitmap(responseBody);
                            Logger.v(TAG, "getCircleList success !");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Logger.v(TAG, "getCircleList success !" + error.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList success !");
            }
        });
    }

    public static void startUpload(long deadline) {
        final String TAG = "QINIUTEST";
        com.alibaba.fastjson.JSONObject fields = new com.alibaba.fastjson.JSONObject();

        fields.put("scope", "test-image");
        fields.put("deadline", deadline);

        JSONObject returnBody = new JSONObject();
        returnBody.put("name", "$(fname)");
        returnBody.put("size", "$(fsize)");
        returnBody.put("w", "$(imageInfo.width)");
        returnBody.put("h", "$(imageInfo.height)");
        //returnBody.put("hash", "$(etag)");

        fields.put("returnBody", returnBody);
        RequestCenter.getInstance().post("/v1/qiniu/get_upload_token", fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList success !" + jsonObject.toJSONString());
                String token = jsonObject.getString("uploadToken");
                Bitmap bmp = Singleton.of(Theme.class).bitmap(BitmapId.fendouquan_douyiduan);
                byte[] data = BitmapHelper.toByteArray(bmp);
                String key = "douyiquan.png";
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(data, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String s, final ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
                                Log.i("qiniu", responseInfo.toString());
                            }
                        }, new UploadOptions(null, null, false,
                                new UpProgressHandler() {
                                    public void progress(String key, double percent) {
                                        Log.i("qiniu", key + ": " + percent);
                                    }
                                }, null));
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void getUploadToken(long deadline, Callback<String> uploadToken) {
        final String TAG = "QINIUTEST";
        com.alibaba.fastjson.JSONObject fields = new com.alibaba.fastjson.JSONObject();

        fields.put("scope", "test-image");
        fields.put("deadline", deadline);

        JSONObject returnBody = new JSONObject();
        returnBody.put("name", "$(fname)");
        returnBody.put("size", "$(fsize)");
        returnBody.put("w", "$(imageInfo.width)");
        returnBody.put("h", "$(imageInfo.height)");
        //returnBody.put("hash", "$(etag)");

        fields.put("returnBody", returnBody);
        RequestCenter.getInstance().post("/v1/qiniu/get_upload_token", fields, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList success !" + jsonObject.toJSONString());
                String token = jsonObject.getString("uploadToken");
                Bitmap bmp = Singleton.of(Theme.class).bitmap(BitmapId.fendouquan_douyiduan);
                byte[] data = BitmapHelper.toByteArray(bmp);
                String key = "douyiquan.png";
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(data, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String s, final ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
                                Log.i("qiniu", responseInfo.toString());
                            }
                        }, new UploadOptions(null, null, false,
                                new UpProgressHandler() {
                                    public void progress(String key, double percent) {
                                        Log.i("qiniu", key + ": " + percent);
                                    }
                                }, null));
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "getCircleList failed !" + (jsonObject == null ? "" : jsonObject.toJSONString()));
            }
        });
    }

    public static void test() {

        {
            JSONArray array = new JSONArray();
            for (int i = 0; i < 3; ++i) {

                JSONObject obj = new JSONObject();
                obj.put("module", "circle");
                obj.put("uploadId", UUID.randomUUID());
                obj.put("suffix", "bmp");
                array.add(obj);
            }
            JSONObject fields = new JSONObject();
            fields.put("fileList", array);
            RequestCenter.getInstance().post(get_upload_token, fields, new JsonResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    Logger.v(TAG, "getCircleList success !");
                    JSONArray fileList = jsonObject.getJSONArray("fileList");
                    List<String> keys = new ArrayList<String>();
                    for (int i = 0; i < fileList.size(); ++i) {
                        JSONObject obj = fileList.getJSONObject(i);

                        String key = obj.getString("key");

                        String token = obj.getString("uploadToken");
                        Bitmap bmp = Singleton.of(Theme.class).bitmap(BitmapId.fendouquan_douyiduan);
                        byte[] data = BitmapHelper.toByteArray(bmp);
                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(data, key, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String s, final ResponseInfo responseInfo, org.json.JSONObject jsonObject) {
                                        Log.i("qiniu", responseInfo.toString());
                                        String hash = jsonObject.optString("hash");
                                        String key = jsonObject.optString("key");
                                        String url = jsonObject.optString("url");
                                        List<String> keys = new ArrayList<String>();
                                        keys.add(key);
                                        test2(keys);
                                    }
                                }, new UploadOptions(null, null, false,
                                        new UpProgressHandler() {
                                            public void progress(String key, double percent) {
                                                Log.i("qiniu", key + ": " + percent);
                                            }
                                        }, null));
                        keys.add(key);
                        test2(keys);
                    }
                }

                @Override
                public void onFailure(JSONObject jsonObject) {
                    Logger.v(TAG, "getCircleList success !");
                }
            });
        }
        {

        }
    }

}
