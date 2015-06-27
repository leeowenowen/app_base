package com.msjf.fentuan.me;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.msjf.fentuan.R;

import com.msjf.fentuan.hongbao.HongBaoActivity;
import com.msjf.fentuan.me.hong_bao.MyHongBaoActivity;
import com.msjf.fentuan.me.message.MessageActivity;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.common.LeftTextRightImageLayout;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.Sex;
import com.msjf.fentuan.user.User;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;

public class MePage extends BasePage {

    private TextView mMessage;
    private ImageView mPhoto;
    private TextView mName;
    private ImageView mSex;
    private MeItemView mFenTuan;
    private MeItemView mFenYou;
    private MeItemView mFenTuanHao;
    private MeItemView mFenDouQuan;
    private View mDivider1;
    private ListView mShareListView;
    private View mDivider2;
    private View mSubDivider1;
    private TextView mAccount;
    private TextView mAccountLeft;
    private ImageView mReCharge;
    private ImageView mBind;
    private View mSubDivider2;
    private View mDivider3;
    private BottomButtonTextView mBtnBottom;
    private LeftTextRightImageLayout mBarcode;
    private View mSubDivider3;
    private LeftTextRightImageLayout mMySuggestion;
    private ShareItemView mMyHongBao;

    private ShareAdapter mShareAdapter;

    public MePage(Context context) {
        super(context, true);
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mMessage = new TextView(context);
        mPhoto = new ImageView(context);
        mName = new TextView(context);
        mSex = new ImageView(context);
        mFenTuan = new MeItemView(context);
        mFenYou = new MeItemView(context);
        mFenTuanHao = new MeItemView(context);
        mFenDouQuan = new MeItemView(context);
        mDivider1 = new View(context);
        mShareListView = new ListView(context);
        mDivider2 = new View(context);

        mBarcode = new LeftTextRightImageLayout(context);

        mSubDivider1 = new View(context);
        mAccount = new TextView(context);
        mAccountLeft = new TextView(context);
        mReCharge = new ImageView(context);
        mBind = new ImageView(context);
        mSubDivider2 = new View(context);

        mMySuggestion = new LeftTextRightImageLayout(context);
        mMyHongBao = new ShareItemView(context);
        mSubDivider3 = new View(context);

        mDivider3 = new View(context);
        mBtnBottom = new BottomButtonTextView(context);

        mShareAdapter = new ShareAdapter();
        mShareListView.setAdapter(mShareAdapter);

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
        LU.setPadding(items, 30, 0, 30, 0);

        LinearLayout accountCenter = new LinearLayout(context);
        accountCenter.addView(mAccountLeft);
        accountCenter.addView(mReCharge);
        LU.setMargin(mAccountLeft, 15, 0, 0, 0);
        LinearLayout account = new LinearLayout(context);
        account.addView(mAccount);
        account.addView(accountCenter, LP.L0W1);
        account.addView(mBind);

        LinearLayout.LayoutParams itemParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(55));
        LinearLayout.LayoutParams subDividerParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(2));
        LinearLayout.LayoutParams dividerParam = LP.lp(LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionUtil.h(20));
        LinearLayout barcodeAccountSuggestion = new LinearLayout(context);
        barcodeAccountSuggestion.setOrientation(VERTICAL);
        barcodeAccountSuggestion.addView(mBarcode, itemParam);
        barcodeAccountSuggestion.addView(mSubDivider1, subDividerParam);
        LU.setMargin(mSubDivider1, 0, 14, 0, 12);
        barcodeAccountSuggestion.addView(account, itemParam);
        barcodeAccountSuggestion.addView(mSubDivider2, subDividerParam);
        LU.setMargin(mSubDivider2, 0, 14, 0, 12);
        barcodeAccountSuggestion.addView(mMyHongBao, itemParam);
        barcodeAccountSuggestion.addView(mSubDivider3, subDividerParam);
        LU.setMargin(mSubDivider3, 0, 14, 0, 12);
        barcodeAccountSuggestion.addView(mMySuggestion, itemParam);

        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(VERTICAL);
        contentView.setGravity(Gravity.CENTER_HORIZONTAL);
        contentView.addView(mPhoto, LP.lp(DimensionUtil.w(180), DimensionUtil.w(180)));
        contentView.addView(nameSex, LP.LWW());
        LU.setMargin(nameSex, 0, 10, 0, 13);
        contentView.addView(items);
        contentView.addView(mDivider1, dividerParam);
        LU.setMargin(mDivider1, 0, 33, 0, 18);
        contentView.addView(mShareListView,
                LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(432)));
        LU.setPadding(mShareListView, 20, 0, 20, 0);
        contentView.addView(mDivider2, dividerParam);
        LU.setMargin(mDivider2, 0, 15, 0, 18);
        contentView.addView(barcodeAccountSuggestion);
        LU.setPadding(barcodeAccountSuggestion, 20, 0, 20, 0);
        contentView.addView(mDivider3, dividerParam);
        LU.setMargin(mDivider3, 0, 15, 0, 18);
        contentView.addView(mBtnBottom);
        LU.setMargin(mBtnBottom, 0, 0, 0, 50);

        mMessage.setGravity(Gravity.CENTER);
        setTitleRightExtension(mMessage);
        LU.setPadding(mMessage, 30, 10, 30, 10);
        setContentView(contentView);


    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mMySuggestion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), SuggestionActivity.class);
                ContextManager.activity().startActivity(intent);
            }
        });
        mMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), MessageActivity.class);
                // Intent intent = new Intent(ContextManager.activity(), MainActivity.class);
                ContextManager.activity().startActivity(intent);
            }
        });

        mBtnBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                android.os.Process.killProcess(Process.myPid());
            }
        });
        mBarcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                MyBarcodeInfoView view = new MyBarcodeInfoView(getContext(), Self.user());
                popupWindow.setContentView(view);
                popupWindow.setWidth(DimensionUtil.w(620));
                popupWindow.setHeight(DimensionUtil.h(700));
                popupWindow.showAtLocation(mBarcode, Gravity.CENTER, 0, 0);
            }
        });
        mMyHongBao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContextManager.activity(), MyHongBaoActivity.class);
                ContextManager.activity().startActivity(intent);
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();

        User me = Self.user();
        setTitle("我");
        mMessage.setText("  消 息  ");
        mName.setText(me.getUserName());
        mFenTuan.setText("粉团", "" + me.getFocusFanGroupCount());
        mFenYou.setText("粉友", "" + me.getFanFriendsCount());
        mFenTuanHao.setText("粉团号", "" + me.getUserName());
        mFenDouQuan.setText("粉逗圈", "" + me.getFocusFanGroupCount());
        mBarcode.mLeft.setText("我的二维码名片");
        mAccount.setText("我的账户");
        mAccountLeft.setText("余额１２元");
        mMySuggestion.mLeft.setText("我的意见");
        mBtnBottom.setText("我先撤");
        mMyHongBao.mLeft.setText("我的红包");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        User user = Singleton.of(Self.class).getUser();
        Theme theme = Singleton.of(Theme.class);

        mPhoto.setImageBitmap(user.getPhoto().getBmp());

        mMySuggestion.mRight.setImageBitmap(theme.bitmap(BitmapId.pencile));
        mReCharge.setImageBitmap(theme.bitmap(BitmapId.me_recharge));
        mBind.setImageBitmap(theme.bitmap(BitmapId.me_bind));
        boolean male = (user.getSex() == null || user.getSex().equals(Sex.Female));
        mSex.setImageBitmap(theme.bitmap(male ? BitmapId.register_female : BitmapId.register_male));
        TU.setTextColor(ColorId.main_text_inverse_color, mMessage);
        TU.setTextSize(27, mBarcode.mLeft, mAccount, mMySuggestion.mLeft, mMessage);
        TU.setTextSize(21, mAccountLeft);
        TU.setTextColor(ColorId.main_text_color, mBarcode.mLeft, mAccount, mMySuggestion.mLeft,
                mName);
        TU.setTextColor(ColorId.gray_text_color, mAccountLeft);
        TU.setTextSize(32, mName);
        TU.setBGColor(ColorId.view_divider, mDivider1, mDivider2, mDivider3, mSubDivider1,
                mSubDivider2,mSubDivider3);
        mMessage.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.login_get_verify_code));
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

    enum ShareItem {
        TONG_XUN_LU, // 通讯录好友
        WEI_XIN_HAO_YOU, // 微信好友
        QQ, // qq好友
        MING_XING_TIE_BA, // 明星贴吧
        WEI_XIN_PENG_YOU_QUAN, // 微信朋友圈
        SINA_WEIBO, // 新浪微博
        QQ_KONGJIAN, // QQ空间

    }

    private class ShareAdapter extends BaseAdapter {
        private final String[] mStrings = new String[]{"邀请通讯录好友",//
                "邀请微信好友",//
                "邀请QQ好友",//
                "分享到明星贴吧",//
                "分享到微信朋友圈",//
                "分享到新浪微博",//
                "分享到QQ空间",//
        };

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public Object getItem(int position) {
            return mStrings[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShareItemView view = null;
            if (convertView == null) {
                view = new ShareItemView(getContext());
            } else {
                view = (ShareItemView) convertView;
            }
            view.mLeft.setText(mStrings[position]);
            return view;
        }

    }

    class ShareItemView extends LeftTextRightImageLayout {
        public ShareItemView(Context context) {
            super(context);
        }

        @Override
        public void onThemeChanged() {
            Theme theme = Singleton.of(Theme.class);
            mLeft.setTextColor(theme.color(ColorId.main_text_color));
            TU.setTextSize(27, mLeft);
            mRight.setImageBitmap(theme.bitmap(BitmapId.common_right_arow_black));
        }

    }
}
