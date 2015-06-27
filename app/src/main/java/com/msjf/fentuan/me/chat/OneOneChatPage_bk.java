package com.msjf.fentuan.me.chat;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.me.message.MessageData;
import com.msjf.fentuan.me.message.MessageDataItem;
import com.msjf.fentuan.member_info.MemberInfoActivity;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class OneOneChatPage_bk extends BasePage {

    private ListViewBase mListView;
    private OneOneChatListAdapter mAdapter;
    private ImageView mAccount;
    private User mUser;

    public OneOneChatPage_bk(Context context, String userId) {
        super(context, false);
        mUser = UserData.getUser(userId);
        final MessageData data = Singleton.of(MessageData.class);
        data.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                List<MessageDataItem> items = new ArrayList<MessageDataItem>();
                mAdapter.updateData(data.getData());
            }
        });

//
//        for (int i = 0; i < 20; ++i) {
//            MessageDataItem item = new MessageDataItem();
//            item.mName = "奇偶比妹";
//            item.mContent = "冯绍峰结婚了？";
//            item.mFrom = "发自冯绍峰粉丝团";
//            item.mDateTime = "2015/03/05 10:03" + i;
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
        mAccount = new ImageView(context);
        mAdapter = new OneOneChatListAdapter();
        mListView.setAdapter(mAdapter);
        FrameLayout contentView = new FrameLayout(context);
        contentView.addView(mListView, LP.FMM);
        setTitleRightExtension(mAccount);
        setContentView(contentView);
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
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("欧尼比妹");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        TU.setBgDrawable(DrawableId.main_account, mAccount);
    }

    private class OneOneChatListAdapter extends BaseAdapter {

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

        private ShapeImageView mPhoto;
        private TextView mContent;
        private TextView mDateTime;
        private TextView mDistance;
        private ImageView mLocation;
        private FrameLayout mPhotoContainer;
        private LinearLayout mInfos;

        public ItemView(Context context) {
            super(context);
            mPhoto = new ShapeImageView(context, ShapeImageView.TYPE_CIRCLE);
            mContent = new TextView(context);
            mDateTime = new TextView(context);
            mDistance = new TextView(context);
            mLocation = new ImageView(context);

            mPhotoContainer = new FrameLayout(context);
            mInfos = new LinearLayout(context);
            mPhotoContainer.addView(mPhoto, LP.fp(DimensionUtil.w(100), DimensionUtil.w(100)));

            mDateTime.setGravity(Gravity.RIGHT);

            LinearLayout locationLayout = new LinearLayout(context);
            locationLayout.addView(mLocation);
            locationLayout.addView(mDistance);
            locationLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            LU.setMargin(mDistance, 12, 9, 0, 5);

            mInfos.setOrientation(VERTICAL);
            mInfos.addView(mDateTime, LP.LMW());
            mInfos.addView(locationLayout, LP.LMW());
            mInfos.addView(mContent, LP.LMW());

            addView(mPhotoContainer);
            addView(mInfos, LP.LMW());
            LU.setPadding(this, 20, 20, 20, 20);
            onThemeChanged();
        }

        public void update(MessageDataItem item) {
            // set photo by id
//            mContent.setText(item.mContent);
//            mDateTime.setText(item.mDateTime);
//            mDistance.setText("0km");
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mContent);
            TU.setTextColor(ColorId.gray_text_color, mDateTime, mDistance);
            TU.setTextSize(27, mContent);
            TU.setTextSize(21, mDateTime, mDistance);
            mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.star_photo));
            mLocation.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.location));
        }
    }

}
