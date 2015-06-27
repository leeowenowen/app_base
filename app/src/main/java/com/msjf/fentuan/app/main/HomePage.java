package com.msjf.fentuan.app.main;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.com.msjf.fentuan.app.setting.SystemSettings;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.crowdfunding.ListCrowdfundingActivity;
import com.msjf.fentuan.fendouquan.FenDouQuanPage;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.user.UserClient;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.base.ActivityLifeCycleListener;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ScrollTabControl;
import com.owo.ui.utils.LP;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by wangli on 15-5-23.
 */
public class HomePage extends FrameLayout implements LanguageObserver, ThemeObserver, ActivityLifeCycleListener {
    private static final String TAG = "HomePage";
    private static final String TAG_FENTUAN = "FenTuan";
    private static final String TAG_FENDOUQUAN = "FenDouQuan";
    private static final String TAG_ZHONGCHOU = "ZhongChou";
    private ScrollTabControl mScrollTabControl;
    private TitleLayout[] mTitles = new TitleLayout[3];
    private ArrayList<TitleItem> mItems = new ArrayList<>();
    private ActivityGroup mParent;

    public HomePage(Context context, ActivityGroup ag) {
        super(context);
        mParent = ag;
        mMainFrame = new MainFrame(context);
        mScrollTabControl = new ScrollTabControl(context) {
            // override this method to change layout

            @Override
            protected ViewPager createViewPager(Context context) {
                return new ViewPager(context) {
                    @Override
                    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//                        if(mScrollTabControl.getCurrentTabTag().equals(TAG_FENTUAN ))
//                        {
//                            return true;
//                        }
//                        return super.canScroll(v, checkV, dx, x, y);
                        //禁止滑屏
                        return true;
                    }
                };
            }

            protected void setupLayout() {
                setOrientation(LinearLayout.VERTICAL);
                addView(mViewPager, LP.LM01);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 5);
                addView(mSeperator, layoutParams);
                addView(mTabWidget);
            }
        };
        mScrollTabControl.setOnTabChangedListener(new ScrollTabControl.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });

        mItems.add(new TitleItem(TAG_FENTUAN, DrawableId.main_tab_fentuan, "粉团"));
        mItems.add(new TitleItem(TAG_FENDOUQUAN, DrawableId.main_tab_fendouquan, "粉逗圈"));
        mItems.add(new TitleItem(TAG_ZHONGCHOU, DrawableId.main_tab_crowdfunding, "粉筹"));

        for (int i = 0; i < 3; ++i) {
            mTitles[i] = new TitleLayout(context);
            mTitles[i].updateData(mItems.get(i));
            mScrollTabControl
                    .addTab(mScrollTabControl.newTabSpec(mItems.get(i).mTag)
                            .setIndicator(new TitleIndicator(mTitles[i]))
                            .setContent(mTabContentFactory));
        }

        addView(mScrollTabControl);
        onLanguageChanged();
        onThemeChanged();

        if (Self.user() == null) {
            String userId = SystemSettings.getUserId();
            if (userId != null) {
                final User destUser = new User();
                Singleton.of(Self.class).setUser(destUser);
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
    }

    @Override
    public void onCreate(Activity a, Bundle savedInstanceState) {
        mMainFrame.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy(Activity a) {
        mMainFrame.onDestroy();
    }

    @Override
    public void onPause(Activity a) {
        mMainFrame.onPause();
    }

    @Override
    public void onResume(Activity a) {
        mMainFrame.onResume();
        if (mFendouQuanPage != null) {
            mFendouQuanPage.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(Activity a, Bundle outState) {
        mMainFrame.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Activity a, Bundle savedInstanceState) {
        mMainFrame.onRestoreInstanceState(savedInstanceState);

    }

    private class TitleLayout extends LinearLayout implements ThemeObserver {
        ImageView mImage;
        TextView mText;
        View mLine;

        public TitleLayout(Context context) {
            super(context);
            mImage = new ImageView(context);
            mText = new TextView(context);
            mLine = new View(context);
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
            addView(mLine, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(4)));
            addView(mImage, LP.lp(DimensionUtil.w(50), DimensionUtil.w(50)));
            mText.setGravity(Gravity.CENTER);
            addView(mText);
            LU.setMargin(mLine, 0, 5, 0, 0);
            LU.setMargin(mImage, 0, 10, 0, 10);
            mLine.setVisibility(INVISIBLE);
            onThemeChanged();
        }

        public void updateData(TitleItem item) {
            TU.setImageDrawable(item.mId, mImage);
            mText.setText(item.mText);
        }

        @Override
        public void onThemeChanged() {
            TU.setTextSize(25, mText);
            TU.setBGColor(ColorId.highlight_color, mLine);
        }

    }

    private class TitleIndicator implements ScrollTabControl.IndicatorView {
        private TitleLayout mTitleLayout;

        public TitleIndicator(TitleLayout title) {
            mTitleLayout = title;
        }

        @Override
        public View getView() {
            return mTitleLayout;
        }

        @Override
        public void onTabClosed() {
            mTitleLayout.mText.setTextColor(Singleton.of(Theme.class).color(
                    ColorId.main_page_inverse_text_color));
            mTitleLayout.mLine.setVisibility(INVISIBLE);
            if (mScrollTabControl.getCurrentTabTag().equals(TAG_FENTUAN)) {
                mMainFrame.onPause();
            }

        }

        @Override
        public void onSetAsCurrentTab() {
            mTitleLayout.mText.setTextColor(Singleton.of(Theme.class)
                    .color(ColorId.highlight_color));
            mTitleLayout.mLine.setVisibility(VISIBLE);
            if (mScrollTabControl.getCurrentTabTag().equals(TAG_FENTUAN)) {
                mMainFrame.onResume();
            } else if (mScrollTabControl.getCurrentTabTag().equals(TAG_FENDOUQUAN)) {

            }
        }
    }

    private MainFrame mMainFrame;
    private FenDouQuanPage mFendouQuanPage;

    private ScrollTabControl.TabContentFactory mTabContentFactory = new ScrollTabControl.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
            View v = null;
            if (TAG_FENTUAN.equals(tag)) {
                v = mMainFrame;
            } else if (TAG_FENDOUQUAN.equals(tag)) {
                mFendouQuanPage = new FenDouQuanPage(ContextManager.context());
                v = mFendouQuanPage;
            } else if (TAG_ZHONGCHOU.equals(tag)) {
                Intent intent = new Intent(ContextManager.activity(), ListCrowdfundingActivity.class);
                return mParent.getLocalActivityManager().startActivity("TAG_ZHONGCHOU", intent).getDecorView();
            }
            return v;
        }
    };


    @Override
    public void onLanguageChanged() {

    }

    @Override
    public void onThemeChanged() {

    }

    private class TitleItem {
        String mTag;
        DrawableId mId;
        String mText;

        public TitleItem(String tag, DrawableId id, String text) {
            mTag = tag;
            mId = id;
            mText = text;
        }
    }


}
