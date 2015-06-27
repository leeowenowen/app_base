package com.msjf.fentuan.me.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jauker.widget.BadgeView;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.user.UserClient;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.me.chat.OneOneChatActivity;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.common.LeftTextRightTextLayout;
import com.msjf.fentuan.ui.common.OkCancelDialogView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MessagePage_bkup extends BasePage {
    private static final String TAG = "MessagePage";
    private ListViewBase mListView;
    private MessageAdapter mAdapter;

    public MessagePage_bkup(Context context) {
        super(context, false);
        final MessageData data = Singleton.of(MessageData.class);
        data.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                mAdapter.updateData(data.getData());
            }
        });
//        MessageData messageData = Singleton.of(MessageData.class);
//        for (int i = 0; i < 20; ++i) {
//            MessageDataItem item = new MessageDataItem();
//            item.mDateTime = "2015/03/05 10:03" + i;
//            if (i % 2 == 0) {
//                item.mIsTouTiao = true;
//                item.mName = "星闻头条";
//                item.mContent = "乱七八糟随便写";
//                item.mFrom = "发自粉团官方媒体";
//            } else {
//                item.mName = "奇偶比妹";
//                item.mContent = "冯绍峰结婚了？";
//                item.mFrom = "发自冯绍峰粉丝团";
//            }
//            messageData.put(item.mDateTime, item);
//        }
//        ArrayList<MessageDataItem> datas = new ArrayList<MessageDataItem>();
//        for (MessageDataItem item : messageData.values()) {
//            datas.add(item);
//        }
//        mAdapter.updateData(datas);
    }



    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mListView = new ListViewBase(context);
        mAdapter = new MessageAdapter();
        mListView.setAdapter(mAdapter);
        FrameLayout contentView = new FrameLayout(context);
        contentView.addView(mListView, LP.FMM);
        setContentView(contentView);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDataItem item = (MessageDataItem) mAdapter.getItem(position);
                if (item.getMessageType() == 1) {
                    final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                    StarNewsDialogContentView dialogView = new StarNewsDialogContentView(getContext());
                    dialog.setContentView(dialogView);
                    dialogView.setClient(new OkCancelDialogView.Client() {
                        @Override
                        public void onClose() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onOk() {
                            dialog.dismiss();
                        }
                    });
                    WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                    param.width = DimensionUtil.w(650);
                    param.height = DimensionUtil.h(900);
                    dialog.getWindow().setAttributes(param);
                    dialog.show();
                } else {
                    Intent intent = new Intent(ContextManager.activity(), OneOneChatActivity.class);
                    intent.putExtra("userId", ((MessageDataItem)mAdapter.getItem(position)).getSenderId());
                    ContextManager.activity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("消息");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
    }

    private class MessageAdapter extends BaseAdapter {

        private List<MessageDataItem> mData;

        public void updateData(List<MessageDataItem> data) {
            mData = data;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemView view = null;
            if (convertView == null) {
                view = new ItemView(getContext());
            } else {
                view = (ItemView) convertView;
            }
            view.update(mData.get(position));
            return view;
        }
    }

    private class ItemView extends LinearLayout implements ThemeObserver {

        private ShapeImageView mPhoto;//头像
        private TextView mName;//名称
        private TextView mContent;//内容
        private TextView mDateTime;//时间
        private TextView mFrom;//来自粉团**
        private FrameLayout mPhotoContainer;
        private LinearLayout mInfos;
        private LeftTextRightTextLayout mInfoTop;
        private LeftTextRightTextLayout mInfoBottom;
        private BadgeView mBadgeView;//[官网信息]标记

        public ItemView(Context context) {
            super(context);
            mPhoto = new ShapeImageView(context, ShapeImageView.TYPE_CIRCLE);
            mName = new TextView(context);
            mContent = new TextView(context);
            mDateTime = new TextView(context);
            mFrom = new TextView(context);

            mPhotoContainer = new FrameLayout(context);
            mInfos = new LinearLayout(context);
            mInfoTop = new LeftTextRightTextLayout(context);
            mInfoBottom = new LeftTextRightTextLayout(context);
            mBadgeView = new BadgeView(context);

            mPhotoContainer.addView(mPhoto, LP.fp(DimensionUtil.w(100), DimensionUtil.w(100)));

            mName = mInfoTop.mLeft;
            mDateTime = mInfoTop.mRight;
            mContent = mInfoBottom.mLeft;
            mFrom = mInfoBottom.mRight;

            mInfos.setOrientation(VERTICAL);
            mInfos.addView(mInfoTop);
            mInfos.addView(mInfoBottom);

            addView(mPhotoContainer);
            addView(mInfos, LP.LMM());
            LU.setPadding(this, 20, 20, 20, 30);
            LU.setMargin(mInfos, 20, 0, 0, 0);
            LU.setMargin(mInfoBottom, 0, 20, 0, 0);
            onThemeChanged();
        }
        private User mUserTag;
        private MessageDataItem mItem;
        public void update(MessageDataItem item) {
            mItem = item;
            mName.setText(mUserTag.getUserName());
            mContent.setText(mItem.getTitle());
            mDateTime.setText(mItem.getSendTime());

            if (mItem.getMessageType() == 0) {
                mBadgeView.setTargetView(mPhoto);
                mBadgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
                mBadgeView.setBadgeCount(13);
            }

            String userId = mItem.getSenderId();
            User user = UserData.getUser(userId);
            if (user != null) {
                mUserTag = user;
                if (user.getDownloadState() == DownloadState.Downloaded) {
                    updateUserInfo(user);
                } else {
                    Logger.v("FenDouQuanLog", "无图片[id:" + user.getId() + "]");
                    mPhoto.setImageBitmap(user.getPhoto().getBmp());
                    return;
                }
            } else {
                user = new User();
                user.setId(userId);
                user.setDownloadState(DownloadState.Downloading);
                UserData.addUser(user);
                mUserTag = user;
                mPhoto.setImageBitmap(user.getPhoto().getBmp());
                final User destUser = user;
                UserClient.get_user_info(//
                        userId,//
                        userId, //
                        new JsonResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Logger.v(TAG, "get_user_info success !" + jsonObject.toJSONString());
                                JSONObject jsonUser = jsonObject.getJSONObject("userInfo");
                                User.parserJson(destUser, jsonUser);
                                destUser.setDownloadState(DownloadState.Downloaded);
                                update(mItem);
                                String url = destUser.getAvatarUrl();
                                if (url != null) {
                                    try {
                                        url = URLDecoder.decode(url, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            destUser.getPhoto().setBmp(loadedImage);
                                            destUser.getPhoto().setDownloadState(DownloadState.Downloaded);
                                            update(mItem);
                                            Logger.v("FenDouQuanLog", "下载到图片[id:" + destUser.getId() + "]");
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Logger.v(TAG, "get_user_info Failed !");
                                destUser.setDownloadState(DownloadState.None);
                            }
                        });
            }
        }

        private void updateUserInfo(User user) {
            mFrom.setText(user.getUserName());
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mName, mContent);
            TU.setTextColor(ColorId.gray_text_color, mDateTime, mFrom);
            TU.setTextSize(32, mName);
            TU.setTextSize(27, mContent);
            TU.setTextSize(21, mDateTime, mFrom);
            mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.star_photo));
        }
    }

}
