package com.msjf.fentuan.me.chat;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.msjf.fentuan.R;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.me.message.MessageDataItem;
import com.msjf.fentuan.me.message.MessagePage;
import com.msjf.fentuan.member_info.MemberInfoActivity;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.ChatViewBase;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import org.w3c.dom.Text;

public class OneOneChatPage extends BasePage {

    private ListViewBase mListView;
    private ChatView mChatView;
    private OneOneChatListAdapter mAdapter;
    private ImageView mAccount;
    private User mUser;

    private EMConversation mConversation;
    private String mUserId;

    public OneOneChatPage(Context context, String userId) {
        super(context, false);

        mUserId = userId;
        refreshUI();
    }

    public void refreshUI() {
        boolean finished = EMChatManager.getInstance().areAllConversationsLoaded();
        mConversation = EMChatManager.getInstance().getConversation(mUserId);
        mConversation.resetUnsetMsgCount();
        mUser = MessagePage.getUserByPhone(mUserId);
        int count = mConversation.getAllMsgCount();
        EMMessage message = mConversation.getLastMessage();
        //加载所有消息
        if (message != null) {
            mConversation.loadMoreMsgFromDB(message.getMsgId(), count - 1);
        }
        mAdapter.updateData(mConversation.getAllMessages());
        mListView.setSelection(mAdapter.getCount() - 1);

        setTitle(mConversation.getUserName());
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mListView = new ListViewBase(context);
        mListView.setDivider(null);
        mAccount = new ImageView(context);
        mAdapter = new OneOneChatListAdapter();
        mListView.setAdapter(mAdapter);
        mChatView = new ChatView(context);
        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(mListView, LP.LM01);
        contentView.addView(mChatView, LP.LMW());
        setTitleRightExtension(mAccount);
        setContentView(contentView);

        mChatView.setDelegate(new ChatViewBase.Delegate() {
            @Override
            public void onSendText(String text) {
                sendText(text);
            }

            @Override
            public void onSelectFace() {

            }
        });
    }

    private void sendText(String content) {

        if (content.length() > 0) {
            EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);

            TextMessageBody txtBody = new TextMessageBody(content);
            // 设置消息body
            message.addBody(txtBody);
            // 设置要发给谁,用户username或者群聊groupid
            message.setReceipt(mUserId);
            // 把messgage加到conversation中
            mConversation.addMessage(message);
            // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
            // 调用sdk发送异步发送方法
            EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

                @Override
                public void onSuccess() {
                    mListView.smoothScrollToPosition(mListView.getCount() - 1);
                    mChatView.clear();
                }

                @Override
                public void onError(int code, String error) {

                }

                @Override
                public void onProgress(int progress, String status) {
                }

            });
            refreshUI();
        }
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), MemberInfoActivity.class);
                ContextManager.activity().startActivity(intent);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContextManager.context(), MemberInfoActivity.class);
                intent.putExtra("userId", mUserId);
                ContextManager.activity().startActivity(intent);
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        TU.setBgDrawable(DrawableId.main_account, mAccount);
    }

    private class OneOneChatListAdapter extends BaseAdapter {

        private List<EMMessage> mData;

        public void updateData(List<EMMessage> data) {
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

        private ShapeImageView mPhoto;
        private TextView mContent;
        private TextView mDateTime;
        private TextView mDistance;
        private ImageView mLocation;
        private FrameLayout mPhotoContainer;
        private LinearLayout mInfos;
        private LinearLayout mLocationLayout;

        public ItemView(Context context) {
            super(context);
            mPhoto = new ShapeImageView(context, ShapeImageView.TYPE_CIRCLE);
            mContent = new TextView(context);
            mDateTime = new TextView(context);
            mDistance = new TextView(context);
            mLocation = new ImageView(context);

            mLocationLayout = new LinearLayout(getContext());
            mLocationLayout.addView(mLocation);
            mLocationLayout.addView(mDistance);

            mPhotoContainer = new FrameLayout(context);
            mPhotoContainer.addView(mPhoto, LP.fp(DimensionUtil.w(100), DimensionUtil.w(100)));

            mInfos = new LinearLayout(context);
            mInfos.setOrientation(VERTICAL);
            mInfos.addView(mDateTime, LP.LMW());
            mInfos.addView(mLocationLayout, LP.LMW());
            FrameLayout contentContainer = new FrameLayout(context);
            contentContainer.addView(mContent, LP.FWWR);
            mInfos.addView(contentContainer, LP.LMM());

            LU.setPadding(this, 20, 20, 20, 20);
            onThemeChanged();
        }

        void layoutByMe(boolean me) {
            removeAllViews();
            if (me) {
                layoutRight();
            } else {
                layoutLeft();
            }
            mLocationLayout.setVisibility(me ? INVISIBLE : VISIBLE);
            int id = (me ? R.drawable.studio_bubble_right : R.drawable.studio_bubble_left);
            mContent.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(id));
        }

        void layoutRight() {
            mDateTime.setGravity(Gravity.LEFT);
            mLocationLayout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            LU.setMargin(mDistance, 12, 9, 0, 5);

            addView(mInfos, LP.L0W1);
            addView(mPhotoContainer);
            mPhoto.setImageBitmap(Self.user().getPhoto().getBmp());
        }

        void layoutLeft() {
            mDateTime.setGravity(Gravity.RIGHT);
            mLocationLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            LU.setMargin(mDistance, 12, 9, 0, 5);
            mContent.setLayoutParams(LP.FWWL);

            addView(mPhotoContainer);
            addView(mInfos, LP.L0W1);
            mPhoto.setImageBitmap(mUser.getPhoto().getBmp());
        }

        public void update(EMMessage message) {
            // set photo by id
            TextMessageBody body = (TextMessageBody) message.getBody();
            mContent.setText(body.getMessage());
            mDateTime.setText(MessagePage.formatCreateTime(message.getMsgTime()));
            mDistance.setText("0km");
            String messageUserName = message.getUserName();
            String selfUserName = Self.user().getMobUser().getUsername();
            layoutByMe(message.direct == EMMessage.Direct.SEND);
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mContent);
            TU.setTextColor(ColorId.gray_text_color, mDateTime, mDistance);
            TU.setTextSize(27, mContent);
            TU.setTextSize(21, mDateTime, mDistance);
            mLocation.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.location));
        }
    }

    public class ChatView extends ChatViewBase {

        public ChatView(Context context) {
            super(context);
            LU.setPadding(this, 20, 20, 20, 20);
            LU.setMargin(mTalkEdit, 30, 0, 30, 0);
            LU.setMargin(mBtnSend, 30, 0, 0, 0);
        }

        public void clear() {
            mTalkEdit.setText("");
        }

        @Override
        public void onLanguageChanged() {
            TU.setTextSize(25, mTalkEdit);
        }

        @Override
        public void onThemeChanged() {
            Theme theme = Singleton.of(Theme.class);
            mLeftIcon.setImageBitmap(theme.bitmap(BitmapId.main_chat_small));
            TU.setImageDrawable(DrawableId.main_face, mFaceIcon);
            mBtnSend.setImageBitmap(theme.bitmap(BitmapId.main_chat_send));
            mSeperator.setBackgroundColor(theme.color(ColorId.talk_sep));
            TU.setTextColor(ColorId.main_text_color, mTalkEdit);
            mTalkEdit.setBackgroundColor(Color.WHITE);
            setBackgroundColor(theme.color(ColorId.main_page_bottom_bg));
        }

    }
}
