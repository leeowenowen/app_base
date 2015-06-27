package com.msjf.fentuan.fendouquan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.app.image.ImageSelectActivity;
import com.msjf.fentuan.gateway_service.QiNiuClient;
import com.msjf.fentuan.gateway_service.fendouquan.CircleClient;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.UIUtil;
import com.owo.ui.utils.LP;

import java.util.ArrayList;
import java.util.List;

public class DouYiDuanPage extends BasePage {
    private ImageView mSend;
    private LinearLayout mContainer;
    private EditText mEditText;
    private GridView mImagesGridView;
    private ImageView mAddImageView;
    private ImgAdapter mImgAdapter;
    private TextView mSyncTo;
    private LinearLayout mSyncToLayout;
    private GridView mShareGridView;
    private ShareAdapter mShareAdapter;

    private TextView mAnonymousSend;
    private LinearLayout mAddImageLayout;
    private PageType mPageType;

    public DouYiDuanPage(Context context) {
        super(context, true);
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mContainer = new LinearLayout(context);
        mEditText = new EditText(context);
        mEditText.setGravity(Gravity.LEFT | Gravity.TOP);
        mImagesGridView = new GridView(context);
        mImagesGridView.setNumColumns(2);
        mImgAdapter = new ImgAdapter();
        mImagesGridView.setAdapter(mImgAdapter);
        mAddImageView = new ImageView(context);
        mSyncTo = new TextView(context);
        mSyncToLayout = new LinearLayout(context);
        mShareGridView = new GridView(context);
        mShareAdapter = new ShareAdapter();
        mShareGridView.setAdapter(mShareAdapter);
        mShareGridView.setNumColumns(3);

        mSyncToLayout.setGravity(Gravity.CENTER_VERTICAL);
        mSyncToLayout.addView(mSyncTo);
        mSyncToLayout.addView(mShareGridView);

        mAddImageLayout = new LinearLayout(context);
        mAddImageLayout.setGravity(Gravity.CENTER_VERTICAL);
        mAnonymousSend = new TextView(context);
        mAnonymousSend.setGravity(Gravity.RIGHT);
        mAddImageLayout.addView(mAddImageView);
        mAddImageLayout.addView(mAnonymousSend, LP.L0W1);

        mContainer.setOrientation(VERTICAL);
        mContainer.setGravity(Gravity.LEFT);
        mContainer.addView(mEditText,
                LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(200)));
        mContainer.addView(mImagesGridView);
        mContainer.addView(mAddImageLayout, LP.LMW());
        mContainer.addView(mSyncToLayout);


        LU.setPadding(mContainer, 40, 20, 40, 0);
        LU.setMargin(mAddImageLayout, 0, 20, 0, 120);
        LU.setMargin(mSyncToLayout, 0, 0, 0, 30);
        UIUtil.setGridViewHeightBasedOnChildren(mShareGridView);

        mSend = new ImageView(context);
        mSend.setLayoutParams(LP.fp(DimensionUtil.w(50), DimensionUtil.w(50)));
        setTitleRightExtension(mSend);
        setContentView(mContainer);
        setPageType(PageType.DouYiQuan);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mAddImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), ImageSelectActivity.class);
                ContextManager.activity().startActivityForResult(intent, 100);
            }
        });
        mSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //  check params
                final CharSequence cs = mEditText.getText();
                if (cs == null || TextUtils.isEmpty(cs.toString())) {
                    Toast.makeText(ContextManager.context(), "请输入内容！", Toast.LENGTH_LONG).show();
                    return;
                }

                // show loading progress
                final String text = mPageType.equals(PageType.DouYiQuan) ? "粉逗" : "爆料";
                LoadingCtrl.start("正在发送" + text + "信息...");
                // store images to qiniu
                if (mImgAdapter.getCount() == 0) {
                    sendCircleMessage(text,mPageType.equals(PageType.MiBao), null);
                } else {
                    QiNiuClient.uploadImage(mImgAdapter.getImages(), new Callback<List<QiNiuClient.ImageUploadResponseItem>>() {
                        @Override
                        public void run(List<QiNiuClient.ImageUploadResponseItem> responseItems) {
                            List<String> keys = new ArrayList<String>();
                            for (QiNiuClient.ImageUploadResponseItem item : responseItems) {
                                keys.add(item.key);
                            }
                            sendCircleMessage(text,mPageType.equals(PageType.MiBao), keys);
                        }
                    });
                }
                // send fendouquan
            }
        });
    }

    private void sendCircleMessage(final String content, boolean isAnonymous, List<String> imgKeys) {
        CircleClient.sendCircleMessage(Singleton.of(Self.class).getUser().getId(),//
                mEditText.getText().toString(),//
                isAnonymous, imgKeys, new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // hide loading progress
                        LoadingCtrl.stop();
                        // update data
//                        JSONObject circleJson = jsonObject.getJSONObject("circleInfo");
//                        CircleInfo info = CircleInfo.fromJson(circleJson);
//                        Singleton.of(FenDouQuanData.class).add(info);
                        ContextManager.activity().setResult(105);
                        ContextManager.activity().finish();
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        LoadingCtrl.stop();
                        Toast.makeText(ContextManager.context(), "发送" + content + "消息失败！", Toast.LENGTH_LONG).show();
                        //ContextManager.activity().finish();
                    }
                });
    }

    public void notifyPhotoData(ArrayList<String> paths) {
        if (paths == null || (paths.size() == 0)) {
            return;
        }
        mImgAdapter.add(paths);
    }

    public void setPageType(PageType type) {
        mPageType = type;
        if (mPageType.equals(PageType.DouYiQuan)) {
            mEditText.setHint("一切以逗明星为乐趣,你就大胆秀一段");
            setTitle("逗一段");
            mAnonymousSend.setVisibility(INVISIBLE);
        } else {
            mEditText.setHint("来吧,大胆匿名爆出明星猛料");
            setTitle("神秘爆料");
            mAnonymousSend.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();

        mSyncTo.setText("同步到");
        mAnonymousSend.setText("匿名");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        Theme theme = Singleton.of(Theme.class);
        mEditText.setBackgroundColor(Color.WHITE);
        mEditText.setHintTextColor(theme.color(ColorId.gray_text_color));
        TU.setTextColor(ColorId.main_text_color, mEditText, mSyncTo, mAnonymousSend);
        TU.setTextSize(25, mEditText, mAnonymousSend);
        TU.setTextSize(27, mSyncTo);
        TU.setImageBitmap(BitmapId.fendouquan_add_image, mAddImageView);
        TU.setImageBitmap(BitmapId.douyiduan_send, mSend);
    }

    private class ImgAdapter extends BaseAdapter {
        private ArrayList<Bitmap> mBmpsArrayList = new ArrayList<Bitmap>();
        private ArrayList<String> mPathsArrayList = new ArrayList<String>();
        private ArrayList<DownloadState> mBmpStateList = new ArrayList<>();

        public void add(ArrayList<String> paths) {
            for (String path : paths) {
                mPathsArrayList.add(path);
                mBmpsArrayList.add(null);
                mBmpStateList.add(DownloadState.None);
            }
            notifyDataSetChanged();
        }

        public List<Bitmap> getImages() {
            return mBmpsArrayList;
        }

        @Override
        public int getCount() {
            return mPathsArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
        private boolean mHasBmp = false;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView view = null;
            if (convertView == null) {
                view = new ImageView(getContext());

            } else {
                view = (ImageView) convertView;
            }
            view.setTag(position);
            final ImageView itemViewMark = view;

            Bitmap bmp = mBmpsArrayList.get(position);
            DownloadState state = mBmpStateList.get(position);
            if (state == DownloadState.None) {
                mBmpStateList.set(position, DownloadState.Downloading);
                ImageLoader.getInstance().loadImage("file://" + mPathsArrayList.get(position), new ImageSize(DimensionUtil.w(300), DimensionUtil.w(300)), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        if ((int) (itemViewMark.getTag()) == position) {
                            itemViewMark.setImageBitmap(loadedImage);
                            mBmpsArrayList.set(position, loadedImage);
                            mBmpStateList.set(position, DownloadState.Downloaded);
                            if (!mHasBmp) {
                                UIUtil.setGridViewHeightBasedOnChildren(mImagesGridView, DimensionUtil.h(300));
                                mHasBmp = true;
                            }

                        }
                    }
                });
            } else {
                view.setImageBitmap(bmp);
            }
            view.setLayoutParams(new AbsListView.LayoutParams(DimensionUtil.w(300), DimensionUtil
                    .h(300)));
            return view;
        }

    }

    public class ShareAdapter extends BaseAdapter {
        private BitmapId[] mIds = new BitmapId[]{BitmapId.login_sina_blog,
                BitmapId.hongbao_share_weixinquan, BitmapId.share_qq_space};

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mIds.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view = null;
            if (convertView == null) {
                view = new ImageView(getContext());

            } else {
                view = (ImageView) convertView;
            }
            TU.setImageBitmap(mIds[position], view);
            return view;
        }

    }
}
