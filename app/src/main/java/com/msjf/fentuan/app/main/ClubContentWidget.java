package com.msjf.fentuan.app.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.R;
import com.msjf.fentuan.crowdfunding.ListCrowdfundingActivity;
import com.msjf.fentuan.gateway_service.HongBaoClient;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupClient;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.hongbao.HongBaoActivity;
import com.msjf.fentuan.hongbao.HongBaoTipView;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.movie.Movie;
import com.msjf.fentuan.movie.MovieActivity;
import com.msjf.fentuan.movie.MovieData;
import com.msjf.fentuan.studio.StudioActivity;
import com.msjf.fentuan.ui.ChatViewBase.Delegate;
import com.msjf.fentuan.ui.common.ContentNormalTextView;
import com.msjf.fentuan.ui.shape.CircleShape;
import com.msjf.fentuan.ui.util.LU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.DataTimeFormatterUtil;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClubContentWidget extends LinearLayout implements ThemeObserver, LanguageObserver {
    private static final String TAG = "ClubContentWidget";
    // top
    private ImageView mStarPhoto;
    private MainTextView mStarMovie;
    private ImageView mBtnMovie;
    private MainTextView mHongbaoCounter;
    // center-left
    private ImageView mKiss;
    private MainTextView mGroupKissText;
    private LinearLayout mGroupKissLayout;
    private ImageView mGroupKissIcon;

    private ImageView mClub;
    // private BadgeView mFenDouQuanCount;

    private ImageView mHeart;
    private MainTextView mWannaInText;
    private ImageView mBtnTalk;
    private LinearLayout mWannaInLayout;

    // center right
    // private CircleMainTextView mGroupMemberCount;
    private ImageView mGroupMemberCount;
    private MainTextView mGroupDescription;
    private MainTextView mStudio;

    private LinearLayout mTopLayout;
    private FrameLayout mCenterLayout;
    private LinearLayout mCenterLeftLayout;
    private LinearLayout mCenterRightLayout;
    private MainChatView mChatView;
    private long mNextHongBaoTime;
    private String mHongBaoId;

    private Runnable mCounterRunnable = new Runnable() {
        @Override
        public void run() {
            mNextHongBaoTime--;
            mHongbaoCounter.setText("离本轮抢红包还剩" + DataTimeFormatterUtil.formatTime(mNextHongBaoTime));
            hightlightNum(mHongbaoCounter.mTextView);
            if (mNextHongBaoTime == 0l) {
                // Toast.makeText(getContext(), "可以抢红包啦!", Toast.LENGTH_LONG).show();
                tipToQiangHongBao();
            } else {
                postDelayed(mCounterRunnable, 1000);
            }
        }
    };

    private void tipQiangHongBao(final double amount) {
        HongBaoTipView view = new HongBaoTipView(getContext(), amount);
        view.setOpenCallback(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ContextManager.activity(),
                        HongBaoActivity.class);
                intent.putExtra("amount", amount);
                ContextManager.activity().startActivity(intent);
            }
        });
        ConfigurableDialog.show(574, 700, view);
    }

    private void tipToQiangHongBao() {
//        TextView view = new TextView(getContext());
//        view.setGravity(Gravity.CENTER);
//        view.setText("现在正是抢红包时间,赶紧来抢吧!");
//        ConfigurableDialog.show(600, 200, view);
        mHongbaoCounter.setText("现在正是抢红包时间,赶紧来抢吧!");
    }

    private void getHongBaoAmount(String hongBaoId) {
        HongBaoClient.snatch(hongBaoId, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                double amount = jsonObject.getDoubleValue("amount");
                tipQiangHongBao(amount);
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v("Main", "QiangHongBao failed:" + jsonObject.toJSONString());
            }
        });
    }

    private void startQiangHongBao(final boolean init) {
        HongBaoClient.status(new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                String redEnvelopeId = jsonObject.getString("redEnvelopeId");
                if (!TextUtils.isEmpty(redEnvelopeId)) {
                    //抢中红包
                    if (init) {
                        tipToQiangHongBao();
                    } else {
                        getHongBaoAmount(redEnvelopeId);
                    }
                } else {
                    long next = jsonObject.getLongValue("next");
                    mNextHongBaoTime = next;
                    postDelayed(mCounterRunnable, 1000);
                }
                Logger.v("Main", "QiangHongBao succeed:" + jsonObject.toJSONString());
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                Logger.v("Main", "QiangHongBao failed:" + jsonObject.toJSONString());
            }
        });
    }

    public ClubContentWidget(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onLanguageChanged();
        onThemeChanged();

        startQiangHongBao(true);
    }

    private void initComponents(Context context) {
        // top
        mStarPhoto = new ImageView(context);
        mStarMovie = new MainTextView(context);
        mStarMovie.mTextView.setGravity(Gravity.CENTER);
        mBtnMovie = new ImageView(context);
        mBtnMovie.setVisibility(GONE);
        mHongbaoCounter = new MainTextView(context);
        // center-left
        mKiss = new ImageView(context);
        mGroupKissText = new MainTextView(context);
        mGroupKissIcon = new ImageView(context);
        mGroupKissLayout = new LinearLayout(context);
        mGroupKissLayout.setGravity(Gravity.CENTER_VERTICAL);
        mGroupKissLayout.addView(mGroupKissIcon);
        mGroupKissLayout.addView(mGroupKissText);
        LU.setMargin(mGroupKissText, 23, 0, 10, 0);

        mClub = new ImageView(context);
        //mFenDouQuanCount = new BadgeView(context);

        mHeart = new ImageView(context);
        mBtnTalk = new ImageView(context);
        mWannaInText = new MainTextView(context);
        mWannaInLayout = new LinearLayout(context);
        mWannaInLayout.setGravity(Gravity.CENTER_VERTICAL);
        mWannaInLayout.addView(mHeart);
        mWannaInLayout.addView(mWannaInText);
        LU.setMargin(mWannaInText, 23, 0, 10, 0);
        // center-right
        mGroupMemberCount = new ImageView(context);
        mGroupDescription = new MainTextView(context);
        mStudio = new MainTextView(context);

        // layout
        mTopLayout = new LinearLayout(context);
        mCenterLeftLayout = new LinearLayout(context);
        mCenterRightLayout = new LinearLayout(context);
        mChatView = new MainChatView(context);

        mTopLayout.setGravity(Gravity.CENTER_VERTICAL);
        // mTopLayout.addView(mStarPhoto, LP.lp(DimensionUtil.w(72),
        // DimensionUtil.w(72)));
        mTopLayout.addView(mStarMovie, LP.L0W1);
        mTopLayout.addView(mBtnMovie);

        mCenterLeftLayout.setOrientation(VERTICAL);
        mCenterLeftLayout.setGravity(Gravity.LEFT);
        mCenterLeftLayout.addView(mKiss, LP.LWW());
        LU.setMargin(mKiss, 0, 100, 0, 23);
        mCenterLeftLayout.addView(mGroupKissLayout, LP.LWW());
        mCenterLeftLayout.addView(mClub, LP.LWW());
        LU.setMargin(mClub, 0, 123, 0, 16);
        mCenterLeftLayout.addView(mWannaInLayout);
        FrameLayout btnTalkContainer = new FrameLayout(context);
        btnTalkContainer.addView(mBtnTalk, LP.FWWBCH());
        mCenterLeftLayout.addView(btnTalkContainer, LP.LW01);
        //mCenterLeftLayout.setGravity(Gravity.CENTER_HORIZONTAL);


        mCenterRightLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mCenterRightLayout.setOrientation(VERTICAL);
        mCenterRightLayout.addView(mGroupMemberCount,
                new LinearLayout.LayoutParams(DimensionUtil.w(100), DimensionUtil.w(100)));
        LU.setMargin(mGroupMemberCount, 0, 30, 0, 0);

        mCenterRightLayout.addView(mGroupDescription, LP.LWW());
        mCenterRightLayout.addView(mStudio, LP.LWW());
        LU.setMargin(mStudio, 0, 90, 0, 0);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        addView(mTopLayout, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(100)));
        addView(mHongbaoCounter, LP.lp(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mHongbaoCounter.mTextView.setGravity(Gravity.CENTER);
        LU.setMargin(mHongbaoCounter, 0, 10, 0, 23);
        mCenterLayout = new FrameLayout(context);
        mCenterLayout.setClipChildren(false);
        mCenterLayout.addView(mCenterLeftLayout, LP.fpWM(Gravity.LEFT));
        mCenterLayout.addView(mCenterRightLayout, LP.fpWM(Gravity.RIGHT));

        FrameLayout centerContainer = new FrameLayout(getContext());
        centerContainer.addView(mCenterLayout, LP.FMM());
        qiangHongBaoAnimationView = new QiangHongBaoAnimationView(getContext());
        Theme theme = Singleton.of(Theme.class);
        qiangHongBaoAnimationView.setBitmap(new Bitmap[]{
                theme.bitmap(BitmapId.qianghongbao_animation1),
                theme.bitmap(BitmapId.qianghongbao_animation2),
                theme.bitmap(BitmapId.qianghongbao_animation3),
                theme.bitmap(BitmapId.qianghongbao_animation4),

        });
        centerContainer.addView(qiangHongBaoAnimationView, LP.FMM());

        addView(centerContainer, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        addView(mChatView);
        mChatView.setVisibility(INVISIBLE);
        LU.setMargin(mChatView, 17, 0, 17, 0);
        LU.setMargin(mTopLayout, 17, 17, 17, 0);
        LU.setMargin(mCenterLayout, 17, 0, 10, 0);

        // mFenDouQuanCount.setTargetView(mClub);
        // mFenDouQuanCount.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        mGroupDescription.setVisibility(INVISIBLE);
        mStudio.setVisibility(INVISIBLE);
        mTopLayout.setVisibility(INVISIBLE);
        mGroupKissLayout.setVisibility(INVISIBLE);
        mWannaInLayout.setVisibility(INVISIBLE);
        mClub.setVisibility(INVISIBLE);
        // mFenDouQuanCount.setVisibility(INVISIBLE);

    }

    private QiangHongBaoAnimationView qiangHongBaoAnimationView;

    private Movie makeMovie() {
        Movie movie = new Movie();
        movie.mId = "id";
        movie.mName = "<狼图腾>";
        movie.mPoster = BitmapHelper.getResourceBitmap(R.drawable.title_image,
                DimensionUtil.w(400), DimensionUtil.h(600));
        movie.mType = "动作,冒险";
        movie.mFansBuyNumber = 231;
        movie.mLength = 100;
        movie.mPlayTime = "2015年03月06日";
        movie.mArea = "大陆";
        movie.mHasFanSpecialPerformance = true;
        movie.mZanNum = 100;
        movie.mBaoDianYingNum = 209;
        movie.mBaoFansSpecialPerformanceNum = 380;
        movie.mDescription = "在好莱坞有超过半个世纪的历史，。它包括预演和小规模放映两类，预演有市场调查场、影评人场和媒体场，它们的共同特点就是非公开和不收钱；小规模放映，可以是一两家影院，也可以是上百家影院，“奥斯卡放映”就属于小规模放映的一种。《黑鹰坠落》是为奥斯卡“点映”的典型：它抢在元旦前选择了洛杉矶一家影院点映，满足了奥斯卡报名条件后，《黑鹰坠落》就冷藏起来没有继续公映，直到1月18日快要接近提名的时候才开始大面积公映，这样一来，即便片方最终得不到奥斯卡奖，至少也能让票房占一占奥斯卡提名的便宜。";
        Bitmap dummyCreatorPhoto = BitmapHelper.getResourceBitmap(R.drawable.title_image,
                DimensionUtil.w(250), DimensionUtil.h(250));
        movie.addCreator(dummyCreatorPhoto, "冯绍峰");
        movie.addCreator(dummyCreatorPhoto, "窦饶");
        movie.addCreator(dummyCreatorPhoto, "昂哈尼马");
        return movie;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(mStarMovie)) {
                Intent intent = new Intent(ContextManager.activity(), MovieActivity.class);
                Movie movie = makeMovie();
                Singleton.of(MovieData.class).put(movie.mId, movie);
                intent.putExtra("movie", movie.mId);
                ContextManager.activity().startActivity(intent);
            } else if (v.equals(mBtnTalk)) {
                mChatView.setVisibility(mChatView.getVisibility() == View.VISIBLE ? INVISIBLE : VISIBLE);
            } else if (v.equals(mGroupDescription)) {
//				String text = "此群是冯绍峰粉丝群．大家可以讨论男神任何话题．目前冯囧囧新电影<狼图腾>上映，大家支持偶像票房过亿!";
//				new CommonTextPopup(getContext(), false).text(text).show();
                Intent intent = new Intent(ContextManager.activity(), ListCrowdfundingActivity.class);
                ContextManager.activity().startActivity(intent);
            } else if (v.equals(mKiss)) {
                qiangHongBaoAnimationView.setFinishCallback(new Runnable() {
                    @Override
                    public void run() {
                        startQiangHongBao(false);
                    }
                });
                qiangHongBaoAnimationView.start();
                // start animation
//                int[] point = new int[2];
//                v.getLocationOnScreen(point);
//                float wIFactor = (DimensionUtil.screenWidth() - point[0]) / (float) v.getWidth();
//                AnimationSet animationSet = new AnimationSet(true);
//                ScaleAnimation scaleAnimation = new ScaleAnimation(1, wIFactor, 1, wIFactor);
//                scaleAnimation.setDuration(1000);
//                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.0f);
//                alphaAnimation.setDuration(1000);
//                animationSet.addAnimation(alphaAnimation);
//                animationSet.addAnimation(scaleAnimation);
//                animationSet.setDuration(1000);
//                animationSet.setInterpolator(new BounceInterpolator());
//                animationSet.setAnimationListener(new AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                       startQiangHongBao(false);
//                    }
//                });
//                v.startAnimation(animationSet);


            } else if (v.equals(mGroupKissText)) {
                // request server

            } else if (v.equals(mWannaInLayout)) {
                // request server

            } else if (v.equals(mClub)) {
                // if the UI becomes complicated, make this TextView as a member
                // of @ClubContentWidget
                // String text = "粉丝俱乐部/粉团为粉丝精心定制粉丝专属偶像粉团专利,粉丝专利,明星情感"
                // + "互动活动,明星极致单品,明星时尚产品购买等一站式服务.行星饭可以申请全职粉团" + "行官边追星边服务粉丝.";
                // new CommonTextPopup(getContext(), false).text(text).show();
                Intent intent = new Intent(ContextManager.activity(), StudioActivity.class);
                ContextManager.activity().startActivity(intent);

            } else if (v.equals(mStudio)) {
                Intent intent = new Intent(ContextManager.activity(), StudioActivity.class);
                ContextManager.activity().startActivity(intent);
            } else if (mChatView.getVisibility() == VISIBLE) {
                mChatView.setVisibility(INVISIBLE);
            }
        }
    };

    private Callback<String> mShuoBaSendCallback;

    public void setShuoBaSendCallback(Callback<String> callback) {
        mShuoBaSendCallback = callback;
    }

    //隐藏虚拟键盘
    public static void hideIM(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }

    //显示虚拟键盘
    public static void showIM(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

    }

    private void setupListeners() {
        mStarMovie.setOnClickListener(mOnClickListener);

        mKiss.setOnClickListener(mOnClickListener);
        mGroupKissText.setOnClickListener(mOnClickListener);
        mClub.setOnClickListener(mOnClickListener);
        mWannaInLayout.setOnClickListener(mOnClickListener);
        mBtnTalk.setOnClickListener(mOnClickListener);

        mGroupDescription.setOnClickListener(mOnClickListener);
        mStudio.setOnClickListener(mOnClickListener);

        // mCenterLayout.setOnClickListener(mOnClickListener);
        mChatView.setDelegate(new Delegate() {
            @Override
            public void onSendText(final String text) {
                FansGroupClient.send_fangroup_said(FansGroupData.instance().getCurrentFanGroupId(), text, new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Logger.v(TAG, "send_fangroup_said success !" + jsonObject.toJSONString());
                        mChatView.mTalkEdit.setText("");
                        // 隐藏输入法
                        hideIM(mChatView.mTalkEdit);
                        if (mShuoBaSendCallback != null) {
                            mShuoBaSendCallback.run(text);
                        }
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        Logger.v(TAG, "send_fangroup_said success !" + jsonObject.toJSONString());
                        Toast.makeText(getContext(), "发送失败,请检查网络!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSelectFace() {
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                FaceSelectView view = new FaceSelectView(getContext());
                view.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(view);
                popupWindow.setWidth(DimensionUtil.w(600));
                popupWindow.setHeight(DimensionUtil.h(400));
                popupWindow.showAsDropDown(mChatView.mFaceIcon);
            }
        });

        FansGroupData.instance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                FansGroupItem item = FansGroupData.instance().getCurrentFansGroupItem();
                if (item == null) {
                    mGroupMemberCount.setBackgroundResource(R.drawable.me_default_photo);
                } else {
                    mGroupMemberCount.setBackgroundResource(R.drawable.me_default_photo);
                }

            }
        });
    }


    @Override
    public void onLanguageChanged() {
        mStarMovie.setText("冯绍峰最新电影 <<狼图腾>> 粉团预售  >");
        mGroupKissText.setText("抢红包 320");
        mWannaInText.setText("星话 320");
        // mGroupMemberCount.setText("冯粉群\n1万");
        mGroupDescription.setEms(1);
        mStudio.setEms(1);
        mGroupDescription.setText("亲密众筹");
        mStudio.setText("星话直播间");
        //   mHongbaoCounter.setText("离本轮抢红包还剩1小时02分");
        hightlightNum(mHongbaoCounter.mTextView);

        //    mFenDouQuanCount.setBadgeCount(3);
    }

    private void hightlightNum(TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(textView.getText());
        Pattern regexPattern = Pattern.compile("[0-9]+");
        Matcher matcher = regexPattern.matcher(builder.toString());
        while (matcher.find()) {
            builder.setSpan(
                    new ForegroundColorSpan(Singleton.of(Theme.class)
                            .color(ColorId.highlight_color)), matcher.start(), matcher.end(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        textView.setText(builder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onThemeChanged() {
        Theme theme = Singleton.of(Theme.class);
        FansGroupItem item = FansGroupData.instance().getCurrentFansGroupItem();
        if (item == null) {
            mGroupMemberCount.setBackgroundResource(R.drawable.me_default_photo);
        } else {
            mGroupMemberCount.setBackgroundResource(R.drawable.me_default_photo);
        }
        mGroupMemberCount.setBackgroundResource(R.drawable.me_default_photo);
        mStarPhoto.setImageBitmap(theme.bitmap(BitmapId.star_photo));
        mKiss.setImageBitmap(theme.bitmap(BitmapId.main_kiss));
        mClub.setImageBitmap(theme.bitmap(BitmapId.main_studio));
        mHeart.setImageBitmap(theme.bitmap(BitmapId.main_heart));
        mBtnMovie.setImageBitmap(theme.bitmap(BitmapId.common_right_arow));
        mBtnTalk.setImageBitmap(theme.bitmap(BitmapId.main_chat_big));
        mChatView.setBackgroundColor(theme.color(ColorId.main_page_bottom_bg));
        mTopLayout.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.main_top));

        mGroupKissIcon.setImageBitmap(theme.bitmap(BitmapId.hongbao_small));
        mGroupKissLayout.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.main_center_text_bg));
        mWannaInLayout.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.main_center_text_bg));
        mGroupDescription.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.main_center_text_bg));
        mStudio.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.main_center_text_bg));
        mHongbaoCounter.setBackgroundColor(theme.color(ColorId.gray_text_color));
//
        //   TU.setTextColor(ColorId.main_text_inverse_color, mFenDouQuanCount);
        //     mFenDouQuanCount.setCircleBg(theme.color(ColorId.highlight_color));
    }

    public static class MainTextView extends FrameLayout {
        protected ContentNormalTextView mTextView;

        public MainTextView(Context context) {
            super(context);
            mTextView = new ContentNormalTextView(context);
            addView(mTextView);
        }

        public void setText(String text) {
            mTextView.setText(text);
        }

        public void setEms(int ems) {
            mTextView.setEms(ems);
        }
    }

    private static class CircleMainTextView extends TextView implements ThemeObserver {
        private ShapeDrawable mDrawable;

        public CircleMainTextView(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            mDrawable = new ShapeDrawable(new CircleShape());
            onThemeChanged();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onThemeChanged() {
            Theme theme = Singleton.of(Theme.class);
            mDrawable.getPaint().setColor(theme.color(ColorId.highlight_color));
            setBackgroundDrawable(mDrawable);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
            setTextColor(theme.color(ColorId.main_page_text_color));
        }
    }
}
