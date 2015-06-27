package com.msjf.fentuan.member_info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.google.zxing.WriterException;
import com.msjf.fentuan.R;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.user.UserClient;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.me.MyBarcodeInfoView;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.common.CommonTextPopup;
import com.msjf.fentuan.ui.common.LeftTextRightImageLayout;
import com.msjf.fentuan.ui.common.LeftTextRightTextLayout;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.DLog;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.ImageBlurUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;
import com.zxing.encoding.EncodingHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MemberInfoPage extends BasePage {
    private static final String TAG = "MemberInfoPage";
    private ImageView mPhoto;
    private TextView mName;
    private ImageView mSex;
    private MeItemView mFenTuan;
    private MeItemView mFenYou;
    private MeItemView mFenTuanHao;
    private MeItemView mFenDouQuan;
    private View mDivider0;
    private View mDivider1;
    private View mDivider2;
    private View mSubDivider1;
    private View mSubDivider2;

    private LeftTextRightTextLayout mShenFen;
    private LeftTextRightTextLayout mConsumeLevel;
    private LeftTextRightImageLayout mBarcode;
    private ImageView mAsk;
    private LinearLayout mTopLayout;

    private TextView mBtnSendNews;
    private TextView mBtnInviteHost;
    private View mBottomDivider;
    private LinearLayout mBottomBtnLayout;

    public MemberInfoPage(Context context) {
        super(context, false);
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mPhoto = new ImageView(context);
        mName = new TextView(context);
        mSex = new ImageView(context);
        mFenTuan = new MeItemView(context);
        mFenYou = new MeItemView(context);
        mFenTuanHao = new MeItemView(context);
        mFenDouQuan = new MeItemView(context);
        mDivider0 = new View(context);
        mDivider1 = new View(context);
        mDivider2 = new View(context);

        mBarcode = new LeftTextRightImageLayout(context);
        mConsumeLevel = new LeftTextRightTextLayout(context);
        mShenFen = new LeftTextRightTextLayout(context);

        mSubDivider1 = new View(context);
        mSubDivider2 = new View(context);

        mBtnSendNews = new TextView(context);
        mBtnInviteHost = new TextView(context);
        mBottomDivider = new View(context);

        mAsk = new ImageView(context);

        LinearLayout nameSex = new LinearLayout(context);
        nameSex.setGravity(Gravity.CENTER_VERTICAL);
        nameSex.addView(mName);
        nameSex.addView(mSex);
        LU.setMargin(mSex, 22, 0, 0, 0);

        LinearLayout items = new LinearLayout(context);
        items.addView(mFenTuan, LP.L0W1);
        items.addView(mFenYou, LP.L0W1);
        items.addView(mFenTuanHao, LP.L0W1);
        items.addView(mFenDouQuan, LP.L0W1);
        LU.setPadding(items, 50, 0, 50, 0);

        LinearLayout mConsumeLevelLayout = new LinearLayout(context);
        mConsumeLevelLayout.setGravity(Gravity.CENTER_VERTICAL);
        mConsumeLevelLayout.addView(mConsumeLevel, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        mConsumeLevelLayout.addView(mAsk);
        LU.setMargin(mAsk, 10, 0, 0, 0);

        LinearLayout.LayoutParams itemParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(55));
        LinearLayout.LayoutParams subDividerParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(2));
        LinearLayout.LayoutParams dividerParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(20));
        LinearLayout centerLayout = new LinearLayout(context);
        centerLayout.setOrientation(VERTICAL);
        centerLayout.addView(mDivider1, dividerParam);
        LU.setMargin(mDivider1, 0, 33, 0, 18);

        LinearLayout centerContentLayout = new LinearLayout(context);
        centerContentLayout.setOrientation(VERTICAL);
        centerContentLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        centerContentLayout.addView(mShenFen, itemParam);
        centerContentLayout.addView(mSubDivider1, subDividerParam);
        LU.setMargin(mSubDivider1, 0, 14, 0, 12);
        centerContentLayout.addView(mConsumeLevelLayout, itemParam);
        centerContentLayout.addView(mSubDivider2, subDividerParam);
        LU.setMargin(mSubDivider2, 0, 14, 0, 12);
        centerContentLayout.addView(mBarcode, itemParam);

        centerLayout.addView(centerContentLayout);
        LU.setMargin(centerContentLayout, 20, 0, 20, 0);
        centerLayout.addView(mDivider2, dividerParam);
        LU.setMargin(mDivider2, 0, 15, 0, 18);

        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(VERTICAL);
        contentView.setGravity(Gravity.CENTER_HORIZONTAL);

        mTopLayout = new LinearLayout(context);
        mTopLayout.setOrientation(VERTICAL);
        mTopLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mTopLayout.addView(mPhoto, LP.lp(DimensionUtil.w(180), DimensionUtil.w(180)));
        mTopLayout.addView(nameSex, LP.LWW());
        LU.setMargin(nameSex, 0, 10, 0, 13);
        mTopLayout.addView(items);
        contentView.addView(mTopLayout);
        LinearLayout.LayoutParams divider0Param = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(20));
        contentView.addView(mDivider0, divider0Param);

        LU.setMargin(mDivider0, 0, 33, 0, 100);
        contentView.addView(centerLayout, LP.LM01);

        mBottomBtnLayout = new LinearLayout(context);
        mBottomBtnLayout.addView(mBtnSendNews, LP.L0W1);
        ViewGroup.LayoutParams bottomDividerLayoutParams = new ViewGroup.LayoutParams(
                DimensionUtil.w(2), DimensionUtil.h(40));
        mBottomBtnLayout.addView(mBottomDivider, bottomDividerLayoutParams);
        mBottomBtnLayout.addView(mBtnInviteHost, LP.L0W1);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DimensionUtil.h(80));
        mBottomBtnLayout.setLayoutParams(layoutParams);
        mBottomBtnLayout.setGravity(Gravity.CENTER);

        mBtnSendNews.setGravity(Gravity.CENTER);
        mBtnInviteHost.setGravity(Gravity.CENTER);

        contentView.addView(mBottomBtnLayout);
        LU.setMargin(mBottomBtnLayout, 30, 30, 30, 40);

        setContentView(contentView);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mBtnSendNews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                SendNewsView view = new SendNewsView(getContext());
                view.setDelegate(new SendNewsView.Delegate() {
                    @Override
                    public void onSend(String title, String content) {
                        if (content.length() > 0) {
                            EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);

                            TextMessageBody txtBody = new TextMessageBody(content);
                            // 设置消息body
                            message.addBody(txtBody);
                            // 设置要发给谁,用户username或者群聊groupid
                            String userName = mUser.getMobUser().getUsername();
                            message.setReceipt(userName);
                            // 把messgage加到conversation中
                            EMConversation conversation = EMChatManager.getInstance().getConversation(userName);
                            if(conversation != null) {
                                conversation.addMessage(message);
                            }
                            // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                            // 调用sdk发送异步发送方法
                            EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

                                @Override
                                public void onSuccess() {
                                    post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发送成功!", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onError(int code, String error) {
                                    post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发送失败!", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                }

                            });
                        }
                    }
                });
                dialog.setContentView(view);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(660);
                param.height = DimensionUtil.h(500);
                dialog.getWindow().setAttributes(param);
                dialog.show();
            }
        });
        mBtnInviteHost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                InviteHostView view = new InviteHostView(getContext());
                dialog.setContentView(view);
                dialog.setBg(R.drawable.popup_bg_no_pading);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(660);
                param.height = DimensionUtil.h(300);
                dialog.getWindow().setAttributes(param);
                dialog.show();
            }
        });

        mBarcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUser == null){return ;}
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                MyBarcodeInfoView view = new MyBarcodeInfoView(getContext(), mUser);
                popupWindow.setContentView(view);
                popupWindow.setWidth(DimensionUtil.w(620));
                popupWindow.setHeight(DimensionUtil.h(700));
                popupWindow.showAtLocation(mBarcode, Gravity.CENTER, 0, 0);
            }
        });

        mConsumeLevel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "明星消费等级,累计计算,总计消费总额达到10000以上,为土豪粉丝,300-10000元之间为尊贵粉,200-300之间为普通粉,100-200之间为屌丝粉,"
                        + "等级身份越高,按身份高低优先参与粉丝专场/点映/明星简明辉.该身份等级解释权归属粉团产品.";
                new CommonTextPopup(getContext(), false).text(text).show();
            }
        });
        mShenFen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                ShenFenView view = new ShenFenView(getContext());
                dialog.setContentView(view);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(620);
                param.height = DimensionUtil.h(1000);
                dialog.getWindow().setAttributes(param);
                dialog.show();
            }
        });
    }
    private User mUser;
    public void setUserId(String userId) {
        User user = UserData.getUser(userId);
        mUser = user;
        if (user != null && user.getDownloadState() == DownloadState.Downloaded) {
            updateUserInfo(user);
        } else {
            LoadingCtrl.start("正在加载用户信息...");
            user = new User();
            user.setId(userId);
            user.setDownloadState(DownloadState.Downloading);
            UserData.addUser(user);
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
                            User.parserJson(destUser, jsonUser);
                            destUser.setDownloadState(DownloadState.Downloaded);
                            updateUserInfo(destUser);
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
                                        updateUserInfo(destUser);
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
        Theme theme = Singleton.of(Theme.class);
        setTitle(user.getUserName());
        mName.setText(user.getUserName());
        mFenTuan.setText("粉团", user.getFocusFanGroupCount() + "");
        mFenYou.setText("粉友", user.getFanFriendsCount() + "");
        mFenTuanHao.setText("粉团号", user.getPhone());
        mFenDouQuan.setText("粉逗圈", user.getFocusFanGroupCount() + "");
        mShenFen.mRight.setText(user.getShenFenString());
        Bitmap photo = user.getPhoto().getBmp();
        mPhoto.setImageBitmap(photo);
        Bitmap bmp = ImageBlurUtil.fastblur(getContext(), photo, 25);
        mTopLayout.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), bmp));
        mConsumeLevel.mRight.setText(user.getConsumeLevelString());
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();

        //    setTitle("奇偶比妹");
        //   mName.setText("奇偶比妹");
        //   mFenTuan.setText("粉团", "1");
        //  mFenYou.setText("粉友", "12");
        //   mFenTuanHao.setText("粉团号", "df213");
        //   mFenDouQuan.setText("粉逗圈", "12");
        mShenFen.mLeft.setText("她的身份");
        //  mShenFen.mRight.setText("首席新闻官");
        mConsumeLevel.mLeft.setText("她的消费等级");
        mBarcode.mLeft.setText("他的二维码名片");

        mBtnSendNews.setText("发她星闻");
        mBtnInviteHost.setText("请她做星话主持");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onThemeChanged() {
        super.onThemeChanged();

        Theme theme = Singleton.of(Theme.class);
//        Bitmap photo = theme.bitmap(BitmapId.me_default_photo);
//        mPhoto.setImageBitmap(theme.bitmap(BitmapId.me_default_photo));
        mSex.setImageBitmap(theme.bitmap(BitmapId.me_femail));
        mAsk.setImageBitmap(theme.bitmap(BitmapId.me_member_help));
        TU.setTextSize(32, mName);
        TU.setTextColor(ColorId.main_text_inverse_color, mName);
        TU.setTextSize(27, mBarcode.mLeft, mShenFen.mLeft, mShenFen.mRight, mConsumeLevel.mLeft,
                mConsumeLevel.mRight);
        TU.setTextColor(ColorId.main_text_color, mBarcode.mLeft, mShenFen.mLeft, mShenFen.mRight,
                mConsumeLevel.mLeft, mConsumeLevel.mRight);
        TU.setBGColor(ColorId.view_divider, mDivider0, mDivider1, mDivider2, mSubDivider1,
                mSubDivider2);


        mBottomDivider.setBackgroundColor(Color.WHITE);
        TU.setBgDrawable(DrawableId.common_bottom_btn, mBottomBtnLayout);

        try {
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode("奇偶比妹", 100);
            mBarcode.mRight.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            DLog.e(TAG, "生成二维码失败", e);
            e.printStackTrace();
        }
    }

    private class MeItemView extends LinearLayout implements ThemeObserver {
        private TextView mTop;
        TextView mBottom;

        public MeItemView(Context context) {
            super(context);
            mTop = new TextView(context);
            mBottom = new TextView(context);
            setOrientation(VERTICAL);
            mTop.setGravity(Gravity.CENTER_HORIZONTAL);
            mBottom.setGravity(Gravity.CENTER_HORIZONTAL);
            addView(mTop);
            addView(mBottom);
            onThemeChanged();
        }

        public void setText(String top, String bottom) {
            mTop.setText(top);
            mBottom.setText(bottom);
        }

        @Override
        public void onThemeChanged() {
            TU.setTextSize(32, mTop, mBottom);
            TU.setTextColor(ColorId.main_text_color, mTop);
            TU.setTextColor(ColorId.highlight_color, mBottom);
        }

    }
}
