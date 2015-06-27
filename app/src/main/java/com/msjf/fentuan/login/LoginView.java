package com.msjf.fentuan.login;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.app.com.msjf.fentuan.app.setting.SystemSettings;
import com.msjf.fentuan.app.main.MainTabActivity;
import com.msjf.fentuan.login.verify_code.VerifyCodeUtil;
import com.msjf.fentuan.register.RegisterActivity;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.common.CommonEditText;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.PhoneInfoUtil;
import com.owo.base.util.TextHelper;
import com.owo.ui.utils.LP;

public class LoginView extends LinearLayout implements ThemeObserver, LanguageObserver {

    private ImageView mFen;
    private TextView mFenTip;
    private ImageView mPhoneNumIcon;
    private TextView mPhoneNumTitle;
    private CommonEditText mPhoneNum;
    private ImageView mVerifyCodeIcon;
    private TextView mVerifyCodeTitle;
    private CommonEditText mVerifyCode;
    private TextView mGetVerifyCode;
    private BottomButtonTextView mNext;
    private TextView mThirdPartyLoginTips;
    private GridView mThirdPartyLoginGridView;

    private LinearLayout mPhoneNumLayout;
    private LinearLayout mVerifyCodeLayout;
    private LinearLayout mCenterLayout;
    private View mSeperator;


    private ThirdPartyLoginAdapter mThirdPartyLoginAdapter;

    public LoginView(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onLanguageChanged();
        onThemeChanged();
        initData();
    }

    private void initData() {
        mPhoneNum.setText(SystemSettings.getPhone());
    }

    private void initComponents(Context context) {
        mFen = new ImageView(context);
        mFenTip = new TextView(context);
        mPhoneNumIcon = new ImageView(context);
        mPhoneNumTitle = new TextView(context);
        mPhoneNum = new CommonEditText(context, false, true);

        mVerifyCodeIcon = new ImageView(context);
        mVerifyCodeTitle = new TextView(context);
        mVerifyCode = new CommonEditText(context, false, true);

        mGetVerifyCode = new TextView(context);

        mNext = new BottomButtonTextView(context);
        mThirdPartyLoginTips = new TextView(context);
        mThirdPartyLoginGridView = new GridView(context);

        mPhoneNumLayout = new LinearLayout(context);
        mPhoneNumLayout.setGravity(Gravity.CENTER_VERTICAL);
        mPhoneNumLayout.addView(mPhoneNumIcon);
        mPhoneNumLayout.addView(mPhoneNumTitle);
        LU.setMargin(mPhoneNumTitle, 25, 0, 10, 0);
        mPhoneNumLayout.addView(mPhoneNum, LP.L0W1);
        LU.setPadding(mPhoneNumLayout, 10, 0, 10, 0);
        mPhoneNum.mText.setInputType(InputType.TYPE_CLASS_PHONE);

        mVerifyCodeLayout = new LinearLayout(context);
        mVerifyCodeLayout.setGravity(Gravity.CENTER_VERTICAL);
        mVerifyCodeLayout.addView(mVerifyCodeIcon);
        mVerifyCodeLayout.addView(mVerifyCodeTitle);
        LU.setMargin(mVerifyCodeTitle, 25, 0, 10, 0);
        mVerifyCodeLayout.addView(mVerifyCode, LP.L0W1);
        mVerifyCodeLayout.addView(mGetVerifyCode);
        LU.setMargin(mGetVerifyCode, 25, 0, 0, 0);
        LU.setPadding(mVerifyCodeLayout, 10, 0, 10, 0);
        mGetVerifyCode.setGravity(Gravity.CENTER);

        mSeperator = new View(context);
        mCenterLayout = new LinearLayout(context);
        mCenterLayout.setOrientation(VERTICAL);
        mCenterLayout.addView(mPhoneNumLayout,
                LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        mCenterLayout.addView(mSeperator, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        mCenterLayout.addView(mVerifyCodeLayout,
                LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        addView(mFen, LP.lp(DimensionUtil.w(170), DimensionUtil.w(170)));
        LU.setMargin(mFen, 0, 40, 0, 0);
        mFenTip.setGravity(Gravity.CENTER);
        addView(mFenTip);
        LU.setMargin(mFenTip, 0, 35, 0, 80);
        addView(mCenterLayout, LP.lp(DimensionUtil.w(650), LinearLayout.LayoutParams.WRAP_CONTENT));
        addView(mNext);
        LU.setMargin(mNext, 0, 140, 0, 100);
        addView(mThirdPartyLoginTips);
        mThirdPartyLoginTips.setGravity(Gravity.CENTER);
        addView(mThirdPartyLoginGridView);
        LU.setMargin(mThirdPartyLoginGridView, 0, 50, 0, 0);


        mThirdPartyLoginAdapter = new ThirdPartyLoginAdapter();
        mThirdPartyLoginGridView.setNumColumns(3);
        mThirdPartyLoginGridView.setAdapter(mThirdPartyLoginAdapter);
        mThirdPartyLoginGridView.setHorizontalSpacing(DimensionUtil.w(70));
        mThirdPartyLoginGridView.requestFocus();
    }

    private void setupListeners() {
        mNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.of(VerifyCodeUtil.class).login();
            }
        });
        mGetVerifyCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.of(VerifyCodeUtil.class).startVerify(
                        mPhoneNum.mText.getText().toString(), mGetVerifyCode, mVerifyCode.mText);
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        mFenTip.setText("粉团");
        mGetVerifyCode.setText("获取验证码");
        mThirdPartyLoginTips.setText("使用以下社交账号快速登陆");
        mNext.setText("下一步");
        mPhoneNumTitle.setText("手机号: ");
        mVerifyCodeTitle.setText("验证码: ");
        mPhoneNum.mText.setHint("请输入手机号码");
        mVerifyCode.mText.setHint("请输入验证码");
        String phoneNumber = PhoneInfoUtil.getMobileNum(ContextManager.context());
        if (!TextHelper.isEmptyOrSpaces(phoneNumber)) {
            mPhoneNum.mText.setText(phoneNumber);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onThemeChanged() {
        TU.setTextColor(ColorId.main_text_inverse_color, mGetVerifyCode, mPhoneNumTitle,
                mVerifyCodeTitle, mThirdPartyLoginTips, mFenTip);

        TU.setTextSize(32, mPhoneNumTitle, mVerifyCodeTitle, mThirdPartyLoginTips, mFenTip);
        TU.setTextSize(21, mGetVerifyCode);
        TU.setImageBitmap(BitmapId.login_fen, mFen);
        TU.setImageBitmap(BitmapId.login_phone_num, mPhoneNumIcon);
        TU.setImageBitmap(BitmapId.login_lock, mVerifyCodeIcon);
        mGetVerifyCode.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.login_get_verify_code));
        // mCenterLayout.setBackgroundDrawable(ThemeUtil
        // .getNinePatchDrawable(R.drawable.register_login_bg));
        mSeperator.setBackgroundResource(R.drawable.register_divider);
        mCenterLayout.setBackgroundDrawable(ThemeUtil
                .getNinePatchDrawable(R.drawable.register_bg));

    }

    private class ThirdPartyLoginAdapter extends BaseAdapter {

        private final BitmapId[] mIds = new BitmapId[]{BitmapId.login_weixin,//
                BitmapId.login_qq,//
                BitmapId.login_sina_blog,//
        };

        @Override
        public int getCount() {
            return mIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mIds[position];
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
            view.setImageBitmap(Singleton.of(Theme.class).bitmap(mIds[position]));
            return view;
        }
    }
}
