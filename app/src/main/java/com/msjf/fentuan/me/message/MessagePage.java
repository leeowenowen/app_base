package com.msjf.fentuan.me.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Pair;
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
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
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
import com.owo.base.util.DataTimeFormatterUtil;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class MessagePage extends BasePage {
    private static final String TAG = "MessagePage";
    private ListViewBase mListView;
    private MessageAdapter mAdapter;

    public MessagePage(Context context) {
        super(context, false);
    }


    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        emptyContentView = new TextView(context);
        emptyContentView.setGravity(Gravity.CENTER);
        mListView = new ListViewBase(context);
        mAdapter = new MessageAdapter();
        mListView.setAdapter(mAdapter);
        mContentViewContainer = new FrameLayout(context);
        mContentViewContainer.addView(mListView, LP.FMM);
        // mContentViewContainer.addView(emptyContentView, LP.FMM);
        setContentView(mContentViewContainer);

        refreshUI();
    }

    private FrameLayout mContentViewContainer;

    private TextView emptyContentView;

    private void refreshUI() {
//        if(mAdapter.getCount() == 0)
//        {
//            mContentViewContainer.removeAllViews();;
//            mContentViewContainer.addView(emptyContentView, LP.FMM);
//        }
//        else {
//            mContentViewContainer.removeAllViews();;
//            mContentViewContainer.addView(mListView, LP.FMM);
//        }

        mAdapter.updateData();
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //               EMConversation item = (EMConversation) mAdapter.getItem(position);
//                if (item.getMessageType() == 1) {
//                    final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
//                    StarNewsDialogContentView dialogView = new StarNewsDialogContentView(getContext());
//                    dialog.setContentView(dialogView);
//                    dialogView.setClient(new OkCancelDialogView.Client() {
//                        @Override
//                        public void onClose() {
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onOk() {
//                            dialog.dismiss();
//                        }
//                    });
//                    WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
//                    param.width = DimensionUtil.w(650);
//                    param.height = DimensionUtil.h(900);
//                    dialog.getWindow().setAttributes(param);
//                    dialog.show();
//                } else
                {
                    Intent intent = new Intent(ContextManager.activity(), OneOneChatActivity.class);
                    intent.putExtra("userId", ((EMConversation) mAdapter.getItem(position)).getUserName());
                    ContextManager.activity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("消息");
        emptyContentView.setText("会话列表为空!");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        TU.setTextColor(ColorId.main_text_color, emptyContentView);
        TU.setTextSize(32, emptyContentView);
    }

    private EMEventListener mEMEventListener = new EMEventListener() {
        @Override
        public void onEvent(EMNotifierEvent event) {
            switch (event.getEvent()) {
                case EventNewMessage: //普通消息
                {
                    EMMessage message = (EMMessage) event.getData();

                    //提示新消息
                    HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

                    refreshUI();
                    break;
                }

                case EventOfflineMessage: {
                    refreshUI();
                    break;
                }

                default:
                    break;
            }
        }
    };

    public void onStop() {
        EMChatManager.getInstance().unregisterEventListener(mEMEventListener);
    }

    public void onResume() {
        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(mEMEventListener, new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage});
    }

    private class MessageAdapter extends BaseAdapter {

        private List<EMConversation> mConversations;

        private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
            Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
                @Override
                public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                    if (con1.first == con2.first) {
                        return 0;
                    } else if (con2.first > con1.first) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

            });
        }

        private List<EMConversation> loadConversationsWithRecentChat() {
            // 获取所有会话，包括陌生人
            Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
            // 过滤掉messages size为0的conversation
            /**
             * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
             * 影响排序过程，Collection.sort会产生异常
             * 保证Conversation在Sort过程中最后一条消息的时间不变
             * 避免并发问题
             */
            List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
            synchronized (conversations) {
                for (EMConversation conversation : conversations.values()) {
                    if (conversation.getAllMessages().size() != 0) {
                        sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    }
                }
            }
            try {
                // Internal is TimSort algorithm, has bug
                sortConversationByLastChatTime(sortList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<EMConversation> list = new ArrayList<EMConversation>();
            for (Pair<Long, EMConversation> sortItem : sortList) {
                list.add(sortItem.second);
            }
            return list;
        }

        public void updateData() {
            mConversations = loadConversationsWithRecentChat();
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            int count = ((mConversations == null) ? 0 : mConversations.size());
            Logger.v(TAG, "count:" + count);
            return count;
        }

        @Override
        public Object getItem(int position) {
            return mConversations.get(position);
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
            view.update(mConversations.get(position));
            return view;
        }
    }

    public static User getUserByPhone(String phone) {
        UserData data = Singleton.of(UserData.class);
        for (User user : data.values()) {
            if (phone.equals(user.getPhone())) {
                return user;
            }
        }
        return null;
    }


    public static String formatCreateTime(long createTime) {
        long cur = System.currentTimeMillis();
        return DataTimeFormatterUtil.formatTime((cur - createTime) / 1000);
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

        private EMConversation mItem;

        public void update(EMConversation item) {
            mItem = item;
            EMMessage lastMessage = item.getLastMessage();
            TextMessageBody body = (TextMessageBody) lastMessage.getBody();
            mContent.setText(body.getMessage());
            mDateTime.setText(formatCreateTime(lastMessage.getMsgTime()));

            // if (mItem.getMessageType() == 0)
            int count = mItem.getUnreadMsgCount();
            if (count > 0) {
                mBadgeView.setTargetView(mPhoto);
                mBadgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
                mBadgeView.setBadgeCount(count);
            }

            User user = getUserByPhone(mItem.getUserName());
            if (user != null) {
                if (user.getDownloadState() == DownloadState.Downloaded) {
                    updateUserInfo(user);
                } else {
                    Logger.v("FenDouQuanLog", "无图片[id:" + user.getId() + "]");
                    mPhoto.setImageBitmap(user.getPhoto().getBmp());
                    return;
                }
            } else {
                mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.me_default_photo));
                user = new User();
                user.setDownloadState(DownloadState.Downloading);
                UserData data = Singleton.of(UserData.class);
                mPhoto.setImageBitmap(user.getPhoto().getBmp());
                final User destUser = user;
                String phone = mItem.getUserName();
                UserClient.get_user_info_by_phone(//
                        phone,//
                        phone, //
                        new JsonResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Logger.v(TAG, "get_user_info success !" + jsonObject.toJSONString());
                                JSONObject jsonUser = jsonObject.getJSONObject("userInfo");
                                User.parserJson(destUser, jsonUser);
                                UserData.addUser(destUser);
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
            mName.setText(user.getUserName());
            mFrom.setText(user.getUserName());
            if (user.getPhoto().getDownloadState() == DownloadState.Downloaded) {
                Logger.v("FenDouQuanLog", "有图片[id:" + user.getId() + "]");
                mPhoto.setImageBitmap(user.getPhoto().getBmp());
                Logger.v("TestUserId", "updateImage:" + user.getId());
            } else {
                Logger.v("FenDouQuanLog", "无图片[id:" + user.getId() + "]");
                mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.me_default_photo));
                return;
            }
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mName, mContent);
            TU.setTextColor(ColorId.gray_text_color, mDateTime, mFrom);
            TU.setTextSize(32, mName);
            TU.setTextSize(27, mContent);
            TU.setTextSize(21, mDateTime, mFrom);

        }
    }

}
