package com.msjf.fentuan.fendouquan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.R;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.gateway_service.fendouquan.CircleClient;
import com.msjf.fentuan.gateway_service.fendouquan.CircleImage;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.msjf.fentuan.gateway_service.fendouquan.CircleReply;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.user.UserClient;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.member_info.MemberInfoActivity;
import com.msjf.fentuan.ui.ChatViewBase.Delegate;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.MobUser;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.DataTimeFormatterUtil;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.UIUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.utils.LP;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class ItemView extends LinearLayout implements ThemeObserver, LanguageObserver {
    private static final String TAG = "ItemView";
    private ImageView mPhoto, mEyes, mUnFocus, mFocus;
    private TextView mUserName, mTitle, mShenFen, mTime, mLiuLanCount;
    private CommentItemView mZan, mSendComments;
    private GridView mImagesGridView;
    private ListView mComments;
    private LinearLayout mLiuLanLayout;
    private FrameLayout mLike;


    public ItemView(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onThemeChanged();
        onLanguageChanged();
    }

    private BitmapAdapter mImagesAdapter = new BitmapAdapter();

    private void initComponents(Context context) {
        mPhoto = new ImageView(context);
        mEyes = new ImageView(context);
        mUnFocus = new ImageView(context);
        mZan = new CommentItemView(context);
        mSendComments = new CommentItemView(context);
        mFocus = new ImageView(context);

        mUserName = new TextView(context);
        mTitle = new TextView(context);
        mShenFen = new TextView(context);
        mTime = new TextView(context);
        mUserName = new TextView(context);
        mUserName = new TextView(context);

        mImagesGridView = new GridView(context);
        mImagesGridView.setNumColumns(2);
        mImagesGridView.setAdapter(mImagesAdapter);
        mImagesGridView.setHorizontalSpacing(DimensionUtil.w(20));
        mImagesGridView.setVerticalSpacing(DimensionUtil.h(20));
        mImagesGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        mComments = new ListView(context);
        mComments.setDivider(null);
        mComments.setCacheColorHint(0);
        mLiuLanCount = new TextView(context);
        mLiuLanLayout = new LinearLayout(context);
        mLike = new FrameLayout(context);
        mLike.addView(mUnFocus);
        mLike.addView(mFocus);

        mUnFocus.setVisibility(View.INVISIBLE);
        mFocus.setVisibility(View.INVISIBLE);

        mLiuLanLayout.setOrientation(VERTICAL);
        mLiuLanLayout.addView(mEyes);
        mLiuLanLayout.addView(mLiuLanCount);

        LinearLayout userNameLayout = new LinearLayout(context);
        userNameLayout.addView(mUserName);
        userNameLayout.addView(mLike);

        LinearLayout commentsLayout = new LinearLayout(context);
        commentsLayout.addView(mTime);
        commentsLayout.addView(mShenFen, LP.L0M1);
        commentsLayout.addView(mZan);
        commentsLayout.addView(mSendComments);

        LinearLayout centerLayout = new LinearLayout(context);
        centerLayout.setOrientation(VERTICAL);
        centerLayout.addView(userNameLayout);
        centerLayout.addView(mTitle);
        centerLayout.addView(mImagesGridView, LP.LMW());
        centerLayout.addView(commentsLayout);
        centerLayout.addView(mComments);


        addView(mPhoto, LP.lp(DimensionUtil.w(100), DimensionUtil.w(100)));
        addView(centerLayout, LP.L0W1);
        addView(mLiuLanLayout);


        LU.setMargin(centerLayout, 20, 0, 20, 0);
        LU.setMargin(mLike, 24, 0, 0, 0);
        LU.setMargin(mTitle, 0, 15, 0, 15);
        LU.setMargin(mSendComments, 20, 0, 0, 0);
        LU.setMargin(mTime, 0, 0, 20, 0);
        LU.setMargin(commentsLayout, 0, 20, 0, 0);
        LU.setMargin(mComments, 0, 10, 0, 20);
        LU.setPadding(this, 20, 20, 20, 20);
        refreshComments();
    }

    private void refreshComments() {
        mComments.setAdapter(mCommentsAdapter);
        UIUtil.setListViewHeightBasedOnChildren(mComments);
    }

    private CommentsAdapter mCommentsAdapter = new CommentsAdapter();

    private void hightlightUserName(String userName, String content, TextView tv) {
        if (userName == null) {
            userName = "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(userName);
        builder.append(":");
        builder.append(content);
        builder.setSpan(
                new ForegroundColorSpan(Singleton.of(Theme.class)
                        .color(ColorId.fendouquan_green_text)), 0, userName.length() + 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(builder);
    }

    private class CommentsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mItem == null ? 0 : mItem.getCircleReplyList().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            TextView view = null;
            if (convertView == null) {
                view = new TextView(getContext());

            } else {
                view = (TextView) convertView;
            }
            CircleReply reply = mItem.getCircleReplyList().get(i);
            User user = UserData.getUser(reply.getUserId());
            final String content = reply.getContent();

            if (user != null && user.getDownloadState() == DownloadState.Downloaded) {
                hightlightUserName(user.getUserName(), content, view);
            } else {
                view.setText(content);
            }
            TU.setTextColor(ColorId.gray_text_color, view);
            TU.setTextSize(22, view);
            return view;
        }
    }

    /**
     * @param focus true : 关注
     */
    public void like(boolean focus) {
        mUnFocus.setVisibility(focus ? VISIBLE : INVISIBLE);
        mFocus.setVisibility(focus ? INVISIBLE : VISIBLE);
        Log.v("Like", "[focus:" + focus + "][userId:" + mUserTag.getId() + "][userName:" + mUserTag.getUserName() + "]");
    }

    private void setupListeners() {
        mSendComments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                FenDouQuanChatView view = new FenDouQuanChatView(getContext());
                view.setDelegate(new Delegate() {

                    @Override
                    public void onSendText(String text) {
                        dialog.dismiss();
                        CircleClient.sendCircleReply(mItem.getTid(), Self.user().getId(), "", text, new JsonResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Logger.v(TAG, "sendCircleReply succeed ~!");
                                JSONObject circleReply = jsonObject.getJSONObject("circleReply");
                                CircleReply reply = CircleReply.fromJson(circleReply);
                                mItem.getCircleReplyList().add(reply);
                                doUpdate();

                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Toast.makeText(ContextManager.context(), "发送评论失败!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSelectFace() {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.setContentView(view);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(720);
                param.height = DimensionUtil.h(300);
                dialog.getWindow().setAttributes(param);
                dialog.show();
            }
        });

        mEyes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem.isMeBrowsed) {
                    return;
                }
                CircleClient.circleBrowserAdd(mItem.getTid(), new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Logger.v(TAG, "circleBrowserAdd succeed ~!");
                        int browseCount = jsonObject.getIntValue("browseCount");
                        mItem.setBrowseCount(browseCount);
                        mItem.isMeBrowsed = true;
                        doUpdate();
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        Toast.makeText(ContextManager.context(), "添加浏览次数失败!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mZan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem.isMeZaned) {
                    CircleClient.sendCircleCanclePraise(mItem.getTid(), mItem.getUserId(), new JsonResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) {
                                    Logger.v(TAG, "sendCircleCanclePraise succeed ~!");
                                    mItem.isMeZaned = false;
                                    mZan.mIcon.setSelected(false);
                                    mItem.setPraiseCount(mItem.getPraiseCount() - 1);
                                    doUpdate();
                                }

                                @Override
                                public void onFailure(JSONObject jsonObject) {
                                    mItem.isMeZaned = false;
                                    mZan.mIcon.setSelected(false);
                                    Toast.makeText(ContextManager.context(), "取消点赞失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                } else {
                    CircleClient.sendCirclePraise(mItem.getTid(), mItem.getUserId(), new JsonResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) {
                                    Logger.v(TAG, "sendCirclePraise succeed ~!");
                                    int praiseCount = jsonObject.getIntValue("praiseCount");
                                    mItem.setPraiseCount(praiseCount);
                                    mItem.isMeZaned = true;
                                    mZan.mIcon.setSelected(true);
                                    //praiseId here
                                    doUpdate();
                                }

                                @Override
                                public void onFailure(JSONObject jsonObject) {
                                    int praiseCount = jsonObject.getIntValue("praiseCount");
                                    mItem.setPraiseCount(praiseCount);
                                    mItem.isMeZaned = true;
                                    mZan.mIcon.setSelected(true);
                                    //praiseId here
                                    doUpdate();
                                    Toast.makeText(ContextManager.context(), "已点赞!", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            }
        });

        mFocus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                UserClient.focus_user(Singleton.of(Self.class).getUser().getId(), mItem.getUserId(), new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Logger.v("FocusUser", "关注用户成功!");
                        like(true);
                        User user = UserData.getUser(mItem.getUserId());
                        if (user != null) {
                            user.setFocusFlag(0);
                        }
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        Logger.v("FocusUser", "关注用户失败!");
                        like(true);
                        User user = UserData.getUser(mItem.getUserId());
                        if (user != null) {
                            user.setFocusFlag(0);
                        }
                       // Toast.makeText(ContextManager.context(), "关注用户失败!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mUnFocus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                UserClient.cancel_focus_user(Singleton.of(Self.class).getUser().getId(), mItem.getUserId(), new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Logger.v("FocusUser", "取消关注用户成功!");
                        like(false);
                        User user = UserData.getUser(mItem.getUserId());
                        if (user != null) {
                            user.setFocusFlag(1);
                        }
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        Logger.v("FocusUser", "取消关注用户失败!");
                        like(false);
                        User user = UserData.getUser(mItem.getUserId());
                        if (user != null) {
                            user.setFocusFlag(1);
                        }
                       // Toast.makeText(ContextManager.context(), "取消关注用户失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContextManager.context(), MemberInfoActivity.class);
                intent.putExtra("userId", mItem.getUserId());
                ContextManager.activity().startActivity(intent);
            }
        });
    }

    private void browserAdd() {
        if (mItem.isMeBrowsed) {
            return;
        }
        CircleClient.circleBrowserAdd(mItem.getTid(), new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Logger.v(TAG, "circleBrowserAdd succeed ~!");
                int browseCount = jsonObject.getIntValue("browseCount");
                mItem.setBrowseCount(browseCount);
                mItem.isMeBrowsed = true;
                doUpdate();
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Toast.makeText(ContextManager.context(), "添加浏览次数失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLanguageChanged() {
    }

    private CircleInfo mItem;
    private List<CircleImage> mImages;


    public void updateData(CircleInfo item) {
        mItem = item;
        mImages = mItem.getCircleImageList();
        doUpdate();
    }

    private String formatCreateTime(long createTime) {
        long cur = System.currentTimeMillis();
        return DataTimeFormatterUtil.formatTime((cur - createTime) / 1000);
    }

    private void updateUserInfo(User user) {
        int level = user.getUserLevel();
        if (user.getPhoto().getDownloadState() == DownloadState.Downloaded && mItem.getAnonymousFlag() != 0) {
            Logger.v("FenDouQuanLog", "有图片[id:" + user.getId() + "]");
            mPhoto.setImageBitmap(user.getPhoto().getBmp());
            Logger.v("TestUserId", "updateImage:" + user.getId());
        } else {
            Logger.v("FenDouQuanLog", "无图片[id:" + user.getId() + "]");
            TU.setImageBitmap(BitmapId.me_default_photo, mPhoto);
            return;
        }
        mShenFen.setText(user.getShenFenString());
        if (mItem.getAnonymousFlag() != 0) {
            like(user.isFocused());
        }
        else
        {
            Log.v("Like", "[ 匿名,不显示关注! [userId:" + mUserTag.getId() + "][userName:" + mUserTag.getUserName() + "]");
            mFocus.setVisibility(View.INVISIBLE);
            mUnFocus.setVisibility(View.INVISIBLE);
        }
    }

    private User mUserTag;

    /**
     * Photo: User-->CircleInfo::Photo-->PhotoView
     */
    private void doUpdate() {
        String userId = mItem.getUserId();
        User user = UserData.getUser(userId);
        if (user != null) {
            mUserTag = user;
            if (user.getDownloadState() == DownloadState.Downloaded) {
                updateUserInfo(user);
            } else {
                Logger.v("FenDouQuanLog", "无图片[id:" + user.getId() + "]");
                TU.setImageBitmap(BitmapId.me_default_photo, mPhoto);
                return;
            }
        } else {
            user = new User();
            user.setId(userId);
            user.setDownloadState(DownloadState.Downloading);
            UserData.addUser(user);
            mUserTag = user;
            TU.setImageBitmap(BitmapId.me_default_photo, mPhoto);
            final User destUser = user;
            UserClient.get_user_info(//
                    userId,//
                    userId, //
                    new JsonResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Logger.v(TAG, "get_user_info success !" + jsonObject.toJSONString());
                            JSONObject jsonUser = jsonObject.getJSONObject("userInfo");
                            JSONObject jsonMobUser = jsonObject.getJSONObject("easemob_user");
                            MobUser mobUser = MobUser.fromJson(jsonMobUser);
                            destUser.setMobUser(mobUser);
                            User.parserJson(destUser, jsonUser);
                            destUser.setDownloadState(DownloadState.Downloaded);
                            doUpdate();
                            String url = destUser.getAvatarThumbnail();
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
                                        doUpdate();
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

        mUserName.setText(mItem.getUserName());
        mTitle.setText(mItem.getContent());
        mTime.setText(formatCreateTime(mItem.getCreateTime()));
        // mShenFen.setText(item.mUserShenFen);
        mZan.mText.setText("" + mItem.getPraiseCount());
        mZan.mIcon.setSelected(mItem.isMeZaned);

        List<CircleReply> replyList = mItem.getCircleReplyList();
        mSendComments.mText.setText("" + mItem.getReplyCount());
        mSendComments.mText.setText("" + mItem.getCircleReplyList().size());
        mLiuLanCount.setText("" + mItem.getBrowseCount());
        refreshComments();
        refreshImages();
        if (mItem.getAnonymousFlag() == 0) {
            mUserName.setText("匿名爆料");
            TU.setImageBitmap(BitmapId.me_default_photo, mPhoto);
        }
    }

    @Override
    public void onThemeChanged() {
        Theme theme = Singleton.of(Theme.class);
        TU.setTextColor(ColorId.main_text_color, mTitle);
        TU.setTextColor(ColorId.gray_text_color, mTime, mLiuLanCount);
        TU.setTextColor(ColorId.fendouquan_green_text, mUserName);
        TU.setTextColor(ColorId.highlight_color, mShenFen);

        TU.setTextSize(27, mUserName);
        TU.setTextSize(23, mTitle);
        TU.setTextSize(20, mShenFen);
        TU.setTextSize(16, mTime, mLiuLanCount);

        TU.setImageBitmap(BitmapId.fendouquan_add_gray, mFocus);
        TU.setImageBitmap(BitmapId.fendouquan_liulan, mEyes);
        TU.setImageBitmap(BitmapId.fendouquan_comment, mSendComments.mIcon);
        TU.setImageBitmap(BitmapId.fendouquan_has_add, mUnFocus);
        TU.setImageDrawable(DrawableId.fendouquan_zan, mZan.mIcon);

        mComments.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.fendouquan_comments_bg));
    }

    class CommentItemView extends LinearLayout implements ThemeObserver {
        ImageView mIcon;
        TextView mText;

        public CommentItemView(Context context) {
            super(context);

            mIcon = new ImageView(context);
            mText = new TextView(context);

            addView(mIcon);
            addView(mText);
            mText.setGravity(Gravity.TOP);
            onThemeChanged();
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.gray_text_color, mText);
            TU.setTextSize(16, mText);

        }
    }

    private void refreshImages() {
        UIUtil.setGridViewHeightBasedOnChildren(mImagesGridView);
        mImagesAdapter.notifyDataSetChanged();
    }

    public class BitmapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImages == null ? 0 : mImages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
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
            browserAdd();

            final CircleImage ci = mImages.get(position);
            view.setImageBitmap(ci.getThumbnailBmp());

            if (ci.getDownloadState() == DownloadState.None) {
                ci.setDownloadState(DownloadState.Downloading);
                String url = ci.getThumbnail();
                if (!TextUtils.isEmpty(url)) {
                    try {
                        url = URLDecoder.decode(url, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    String thumbnail = ("imageMogr2/thumbnail/300x300");
//                    int sep = url.indexOf('?');
//                    url = url.substring(0, sep + 1) + thumbnail;//+ url.substring(sep);
                    ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            loadedImage = BitmapHelper.createScaledBitmap(loadedImage, 300, 300);
                            ci.setThumbnailBmp(loadedImage);
                            ci.setDownloadState(DownloadState.Downloaded);
                            refreshImages();
                        }
                    });
                }
            }

            return view;
        }

    }
}