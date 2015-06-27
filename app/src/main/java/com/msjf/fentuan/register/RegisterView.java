package com.msjf.fentuan.register;

import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.R;
import com.msjf.fentuan.app.hx.HXUtil;
import com.msjf.fentuan.app.image.ImageSelectActivity;
import com.msjf.fentuan.app.main.MainTabActivity;
import com.msjf.fentuan.gateway_service.user.RegisterUser;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.register.district.DistrictData;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.common.CommonEditText;
import com.msjf.fentuan.ui.common.TextList;
import com.msjf.fentuan.ui.common.TextList.DataSource;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.MobUser;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.Sex;
import com.msjf.fentuan.user.User;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.TextHelper;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

public class RegisterView extends LinearLayout implements ThemeObserver, LanguageObserver {

    private ImageView mPhoto;
    private TextView mUploadPhotoText;
    private ImageView mNameIcon,//
            mPhoneIcon,//
            mPassIcon,//
            mStarIcon,//
            mSexIcon,//
            mCityIcon,//
            mMaleIcon,//
            mFemaleIcon;
    private TextView mNameTitle,//
            mPhoneTitle,//
            mPassTitle,//
            mStarTitle,//
            mSexTitle,//
            mCityTitle,//
            mProvinceText,//
            mCityText;//
    private CommonEditText mName,//
            mPhone,//
            mPass,//
            mStar,//
            mProvince,//
            mCity;
    private View[] mSeperators;

    private RadioGroup mSexGroup;
    private RadioButton mMale;
    private RadioButton mFemale;

    private BottomButtonTextView mNext;

    private TableLayout mCenterLayout;

    public RegisterView(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onLanguageChanged();
        onThemeChanged();
        initData();
    }

    @SuppressLint("NewApi")
    private void initComponents(Context context) {
        mPhoto = new ImageView(context);
        mUploadPhotoText = new TextView(context);
        mNameIcon = new ImageView(context);
        mPhoneIcon = new ImageView(context);
        mPassIcon = new ImageView(context);
        mStarIcon = new ImageView(context);
        mSexIcon = new ImageView(context);
        mCityIcon = new ImageView(context);
        mNameTitle = new TextView(context);
        mPhoneTitle = new TextView(context);
        mPassTitle = new TextView(context);
        mStarTitle = new TextView(context);
        mSexTitle = new TextView(context);
        mCityTitle = new TextView(context);
        mMaleIcon = new ImageView(context);
        mFemaleIcon = new ImageView(context);

        mName = new CommonEditText(context, false, true);
        mPhone = new CommonEditText(context, false, true);
        mPass = new CommonEditText(context, false, true);
        mStar = new CommonEditText(context, false, true);
        mProvince = new CommonEditText(context, true, false);
        mCity = new CommonEditText(context, true, false);

        mSexGroup = new RadioGroup(context);
        mMale = new RadioButton(context);
        mFemale = new RadioButton(context);
        mNext = new BottomButtonTextView(context);

        mSeperators = new View[]{new View(context),//
                new View(context),//
                new View(context),//
                new View(context),//
                new View(context),//
                new View(context) //
        };

        mProvinceText = new TextView(context);
        mCityText = new TextView(context);

        mCenterLayout = new TableLayout(context);
        mCenterLayout.setOrientation(VERTICAL);
        mNameRow = new LinearLayout(context);
        mNameRow.setGravity(Gravity.CENTER_VERTICAL);
        mNameRow.addView(mNameIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mNameRow.addView(mNameTitle);
        LU.setMargin(mNameTitle, 25, 0, 40, 0);
        mNameRow.addView(mName, LP.L0W1);
        mCenterLayout.addView(mNameRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mNameRow, 15, 0, 15, 0);
        mCenterLayout.addView(mSeperators[0], LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));

        mPhoneRow = new LinearLayout(context);
        mPhoneRow.setGravity(Gravity.CENTER_VERTICAL);
        mPhoneRow.addView(mPhoneIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mPhoneRow.addView(mPhoneTitle);
        LU.setMargin(mPhoneTitle, 25, 0, 40, 0);
        mPhoneRow.addView(mPhone, LP.L0W1);
        mCenterLayout.addView(mPhoneRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mPhoneRow, 15, 0, 15, 0);
        mCenterLayout.addView(mSeperators[1], LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));

        mPassRow = new LinearLayout(context);
        mPassRow.setGravity(Gravity.CENTER_VERTICAL);
        mPassRow.addView(mPassIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mPassRow.addView(mPassTitle);
        LU.setMargin(mPassTitle, 25, 0, 40, 0);
        mPassRow.addView(mPass, LP.L0W1);
        // mCenterLayout.addView(mPassRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mPassRow, 15, 0, 15, 0);
        //mCenterLayout.addView(mSeperators[2], LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));

        mStarRow = new LinearLayout(context);
        mStarRow.setGravity(Gravity.CENTER_VERTICAL);
        mStarRow.addView(mStarIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mStarRow.addView(mStarTitle);
        LU.setMargin(mStarTitle, 25, 0, 40, 0);
        mStarRow.addView(mStar, LP.L0W1);
        mCenterLayout.addView(mStarRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mStarRow, 15, 0, 15, 0);
        mCenterLayout.addView(mSeperators[3], LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));

        mSexRow = new LinearLayout(context);
        mSexRow.setGravity(Gravity.CENTER_VERTICAL);
        mSexRow.addView(mSexIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mSexRow.addView(mSexTitle);
        LU.setMargin(mSexTitle, 25, 0, 40, 0);
        mSexRow.addView(mSexGroup, LP.L0W1);
        mSexGroup.setOrientation(HORIZONTAL);
        mSexGroup.addView(mMale);
        mSexGroup.addView(mFemale);
        mMale.setChecked(true);
        LU.setMargin(mFemale, 20, 0, 0, 0);

        mCenterLayout.addView(mSexRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mSexRow, 15, 0, 15, 0);
        mCenterLayout.addView(mSeperators[4], LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, 2));

        mCityRow = new LinearLayout(context);
        mCityRow.setGravity(Gravity.CENTER_VERTICAL);
        mCityRow.addView(mCityIcon, LP.lp(40, LinearLayout.LayoutParams.WRAP_CONTENT));
        mCityRow.addView(mCityTitle);
        LU.setMargin(mCityTitle, 25, 0, 40, 0);
        LinearLayout cityContainer = new LinearLayout(context);
        cityContainer.addView(mProvince, LP.L0W1);
        cityContainer.addView(mProvinceText);
        LU.setMargin(mProvinceText, 5, 0, 5, 0);
        cityContainer.addView(mCity, LP.L0W1);
        cityContainer.addView(mCityText);
        LU.setMargin(mCityText, 5, 0, 5, 0);
        mProvinceText.setGravity(Gravity.CENTER);
        mCityText.setGravity(Gravity.CENTER);
        mCityRow.addView(cityContainer, LP.L0W1);

        mCenterLayout.addView(mCityRow, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
        LU.setPadding(mCityRow, 15, 0, 15, 0);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        addView(mPhoto);
        LU.setMargin(mPhoto, 0, 45, 0, 31);
        mUploadPhotoText.setGravity(Gravity.CENTER);
        addView(mUploadPhotoText);
        addView(mCenterLayout, LP.lp(DimensionUtil.w(650), LinearLayout.LayoutParams.WRAP_CONTENT));
        LU.setMargin(mCenterLayout, 0, 70, 0, 84);
        addView(mNext);
    }

    private void initData() {
        mPhone.mText.setInputType(InputType.TYPE_CLASS_PHONE);
        mPhone.setText(Singleton.of(Self.class).getUser().getPhone());
    }

    private LinearLayout mNameRow, mPhoneRow, mPassRow, mStarRow, mCityRow, mSexRow;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "RegisterView";

    private void doRegister() {
        // register
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
        }
        mProgressDialog.setMessage("正在注册中...");
        mProgressDialog.show();
        RegisterUser.start(mPhotoBmp, mPhone.getText(),//
                mPass.getText(), //
                mName.getText(),// fanGroupName,//
                mStar.getText(),//
                mSexGroup.getCheckedRadioButtonId() == 0 ? Sex.Male : Sex.Female, //
                "001", //
                mCity.getText(),//
                "remark",
                new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        mProgressDialog.dismiss();
                        Toast.makeText(ContextManager.context(), "注册成功", Toast.LENGTH_LONG).show();
                        JSONObject jsonUser = json.getJSONObject("userInfo");
                        JSONObject jsonMobUser = json.getJSONObject("easemob_user");
                        String phone = json.getString("phone");
                        final Self self = Singleton.of(Self.class);
                        if (!TextUtils.isEmpty(phone)) {
                            self.getUser().setPhone(phone);
                        }
                        self.setUser(User.fromJson(jsonUser));
                        MobUser mobUser = MobUser.fromJson(jsonMobUser);
                        self.getUser().setMobUser(mobUser);
                        self.getUser().getPhoto().setBmp(mPhotoBmp);
                        HXUtil.registerAndLogin(mobUser.getUuid(), mobUser.getUuid(), new Callback<Boolean>() {
                            @Override
                            public void run(Boolean param) {
                                Logger.e(TAG, "login hx " + param);
                            }
                        });


                        Intent intent = new Intent(ContextManager.activity(), MainTabActivity.class);
                        ContextManager.activity().startActivity(intent);
                        ContextManager.activity().finish();
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        mProgressDialog.dismiss();
                        Toast.makeText(ContextManager.context(), "注册失败", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void setupListeners() {
        mNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = mName.getText();
                final String name = (text == null ? "" : text.toString());
                if (name.length() > 6) {
                    Toast.makeText(getContext(), "用户名过长,仅限6位", Toast.LENGTH_LONG).show();
                }
                doRegister();
            }
        });
        mSexGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v("xxx", "onCheckChanged:" + checkedId);
            }
        });
        mPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.equals(mPhoto)) {
                    Intent intent = new Intent(ContextManager.activity(), ImageSelectActivity.class);
                    intent.putExtra("is_single_select", true);
                    intent.putExtra("is_wh_equal", true);
                    intent.putExtra("need_crop", true);
                    ContextManager.activity().startActivityForResult(intent, 100);
                }
            }
        });
        mProvince.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                TextList view = new TextList(getContext());
                view.setDataSource(new DataSource() {
                    @Override
                    public int count() {
                        return Singleton.of(DistrictData.class).mProvinces.size();
                    }

                    @Override
                    public String at(int position) {
                        return Singleton.of(DistrictData.class).mProvinces.get(position).mName;
                    }
                });
                view.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Singleton.of(DistrictData.class).setCurProvince(position);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(view);
                popupWindow.setWidth(DimensionUtil.w(300));
                popupWindow.setHeight(DimensionUtil.h(500));
                popupWindow.showAsDropDown(mProvince);
            }
        });
        mCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String province = Singleton.of(DistrictData.class).mCurProvince;
                if (TextHelper.isEmptyOrSpaces(province)) {
                    Toast.makeText(getContext(), "请先选择省份", Toast.LENGTH_LONG).show();
                    return;
                }
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                TextList view = new TextList(getContext());
                view.setDataSource(new DataSource() {
                    @Override
                    public int count() {
                        return Singleton.of(DistrictData.class).curCity().size();
                    }

                    @Override
                    public String at(int position) {
                        return Singleton.of(DistrictData.class).curCity().get(position);
                    }
                });
                view.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Singleton.of(DistrictData.class).setCurCity(position);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(view);
                popupWindow.setWidth(DimensionUtil.w(300));
                popupWindow.setHeight(DimensionUtil.h(500));
                popupWindow.showAsDropDown(mCity);
            }
        });
        Singleton.of(DistrictData.class).addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                mProvince.setText(Singleton.of(DistrictData.class).mCurProvince);
                mCity.setText(Singleton.of(DistrictData.class).mCurCity);
            }
        });
        //
        // mStar.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // final ConfigurablePopupWindow popupWindow = new
        // ConfigurablePopupWindow();
        // TextList view = new TextList(getContext());
        // view.setDataSource(new DataSource() {
        // @Override
        // public int count() {
        // return STARS.length;
        // }
        //
        // @Override
        // public String at(int position) {
        // return STARS[position];
        // }
        // });
        // view.setOnItemClickListener(new OnItemClickListener() {
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view, int
        // position, long id) {
        // mStar.setText(STARS[position]);
        // popupWindow.dismiss();
        // }
        // });
        // popupWindow.setContentView(view);
        // popupWindow.setWidth(DimensionUtil.w(300));
        // popupWindow.setHeight(DimensionUtil.h(400));
        // popupWindow.showAsDropDown(mStar);
        // }
        // });

    }

    private final String[] STARS = new String[]{"鹿晗",//
            "吴亦凡",//
            "李易峰",//
            "韩寒",//
            "王菲",//
    };
    private Bitmap mPhotoBmp;

    public void notifyPhotoData(Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        bmp = ShapeImageView.makeDst(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()), ShapeImageView.TYPE_CIRCLE);
        mPhoto.setImageBitmap(bmp);
        mPhotoBmp = bmp;
    }

    @Override
    public void onLanguageChanged() {
        mNameTitle.setText("粉团名:");
        mPhoneTitle.setText("手机号:");
        mPassTitle.setText("密密码:");
        mStarTitle.setText("巨巨星:");
        mSexTitle.setText("性性别:");
        mCityTitle.setText("城城市:");
        mNext.setText("下一步");
        mMale.setText("男");
        mFemale.setText("女");
        mUploadPhotoText.setText("上传照片,做个有颜值的粉丝");
        mProvinceText.setText("省");
        mCityText.setText("市");

        mName.mText.setHint("请用偶像中文名加数字组合,限6位");
        // mStar.mText.setHint("请选择喜欢的明星");
        mProvince.mText.setHint("请选择省份");
        mCity.mText.setHint("请选择城市");

        fixText(mPassTitle);
        fixText(mStarTitle);
        fixText(mSexTitle);
        fixText(mCityTitle);
    }

    private void fixText(TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(textView.getText());

        builder.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onThemeChanged() {
        Theme theme = Singleton.of(Theme.class);
        TU.setTextColor(ColorId.main_text_inverse_color, mNameTitle, mPhoneTitle, mPassTitle, mStarTitle, mSexTitle, mCityTitle, mMale, mFemale, mUploadPhotoText, mProvinceText, mCityText);
        TU.setTextSize(32, mNameTitle, mPhoneTitle, mPassTitle, mStarTitle, mSexTitle, mCityTitle, mMale, mFemale, mUploadPhotoText, mProvinceText, mCityText);
        TU.setImageBitmap(BitmapId.register_default_photo, mPhoto);
        TU.setImageBitmap(BitmapId.register_fentuan_name, mNameIcon);
        TU.setImageBitmap(BitmapId.login_phone_num, mPhoneIcon);
        TU.setImageBitmap(BitmapId.login_lock, mPassIcon);
        TU.setImageBitmap(BitmapId.register_star, mStarIcon);
        TU.setImageBitmap(BitmapId.register_sex, mSexIcon);
        TU.setImageBitmap(BitmapId.register_city, mCityIcon);
        TU.setImageBitmap(BitmapId.register_male, mMaleIcon);
        TU.setImageBitmap(BitmapId.register_female, mFemaleIcon);
        setBackgroundResource(R.drawable.logo_bg);

        mFemale.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(theme.bitmap(BitmapId.register_female)), null, theme.drawable(DrawableId.register_sex_radio), null);
        mMale.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(theme.bitmap(BitmapId.register_male)), null, theme.drawable(DrawableId.register_sex_radio), null);
        mMale.setCompoundDrawablePadding(DimensionUtil.w(10));
        mFemale.setCompoundDrawablePadding(DimensionUtil.w(10));
        mMale.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFemale.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

        for (View view : mSeperators) {
            view.setBackgroundResource(R.drawable.register_divider);
        }
        mCenterLayout.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.register_login_bg));
    }
}
