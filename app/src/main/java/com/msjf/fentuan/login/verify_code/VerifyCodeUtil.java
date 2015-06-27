package com.msjf.fentuan.login.verify_code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

import com.msjf.fentuan.app.com.msjf.fentuan.app.setting.SystemSettings;
import com.msjf.fentuan.app.main.MainTabActivity;
import com.msjf.fentuan.gateway_service.user.CheckValidateCode;
import com.msjf.fentuan.gateway_service.user.SendValidateCode;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.login.verify_code.smssdk.SMSReceiver;
import com.msjf.fentuan.register.RegisterActivity;
import com.msjf.fentuan.user.Self;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;

public class VerifyCodeUtil {
    private static final String TAG = "VerifyCodeUtil";
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "6b601873dac0";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "436e9e08cdababda55d55bbd6e5b6395";

    private boolean mInited;

    private void ensureInited() {
        if (!mInited) {
            init();
            mInited = true;
        }
    }

    private VerifyCodeUtil() {

    }

    private String mPhoneNum;
    private String mCountryCode;
    private String mOriginalText;
    private TextView mCountDownTextView;
    private TextView mVerifyCodeTextView;

    // TODO: remember some information and auto complete it
    private void clearOnPassVerify(boolean cleanVerifyCode) {
        BaseHandler.removeCallbacks(mTimerRunnable);
        if (mCountDownTextView != null) {
            mCountDownTextView.setEnabled(true);
            mCountDownTextView.setText(mOriginalText);
        }
        if (cleanVerifyCode) {
            mCountDownTextView = null;

            if (mVerifyCodeTextView != null) {
                mVerifyCodeTextView.setText(null);
            }
            mVerifyCodeTextView = null;
            mPhoneNum = null;
            mCountryCode = null;
            mOriginalText = null;
        }
    }

    public void startVerify(String phone, TextView countDownTextView, TextView verifyCodeTextView) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(ContextManager.activity(), "请输入您的手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        //ensureInited();
        mCountDownTextView = countDownTextView;
        mVerifyCodeTextView = verifyCodeTextView;
        mOriginalText = mCountDownTextView.getText().toString();
        mPhoneNum = phone.trim().replaceAll("\\s*", "");
//        String[] country = getCurrentCountry();
//        if (country != null) {
//            mCountryCode = country[1];
//        }

        // SMSSDK.getSupportedCountries();
        SendValidateCode.start(mPhoneNum, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject jsonObject) {
                mVerifyCodeTextView.setText("1234");
                clearOnPassVerify(false);
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.e(TAG, "SendValidateCode Failed!");
                Toast.makeText(mCountDownTextView.getContext(), "发送验证码失败!", Toast.LENGTH_LONG).show();
                clearOnPassVerify(false);
            }
        });
        SystemSettings.setPhone(mPhoneNum);
        countDown();
    }

    public void login() {
//        if (mVerifyCodeTextView == null || TextHelper.isEmptyOrSpaces(mVerifyCodeTextView.getText().toString().trim())) {
//            Toast.makeText(ContextManager.context(), "请输入验证码!", Toast.LENGTH_LONG).show();
//            return;
//        }
        SystemSettings.setPhone(mPhoneNum);
        //*******************************For test****************************
        String verifyCode = "1234";
        //*******************************For test****************************

        CheckValidateCode.start(mPhoneNum, verifyCode, new JsonResponseHandler() {
            @Override
            public void onSuccess(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "登录成功！");
                Self self = Singleton.of(Self.class);
                Class<?> destActivityClass = RegisterActivity.class;
                if (self.isRegistered()) {
                    destActivityClass = MainTabActivity.class;
                }
                Intent intent = new Intent(ContextManager.activity(), destActivityClass);
                ContextManager.activity().startActivity(intent);
                ContextManager.activity().finish();
            }

            @Override
            public void onFailure(com.alibaba.fastjson.JSONObject jsonObject) {
                Logger.v(TAG, "登录失败:");
                Toast.makeText(ContextManager.context(), "登陆失败!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContextManager.activity(), MainTabActivity.class);
                ContextManager.activity().startActivity(intent);
                ContextManager.activity().finish();
            }
        });
        //SMSSDK.submitVerificationCode(mCountryCode, mPhoneNum, mVerifyCodeTextView.getText().toString());
    }

    /**
     * 检查电话号码
     */
    private void checkPhoneNum() {
        String rule = countryRules.get(mCountryCode);
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(mPhoneNum);
        if (!m.matches()) {
            Toast.makeText(ContextManager.activity(), "您输入的手机号格式有误!", Toast.LENGTH_SHORT).show();
            return;
        }
        SMSSDK.getVerificationCode(mCountryCode, mPhoneNum, osmHandler);
    }

    private static final int RETRY_INTERVAL = 60;
    private int mTime = RETRY_INTERVAL;

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @SuppressWarnings("unchecked")
        @Override
        public boolean handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                /** 提交验证码 */
                String toast = "";
                if (result == SMSSDK.RESULT_COMPLETE) {
                    toast = "验证码验证成功!";
                    Intent intent = new Intent(ContextManager.activity(), RegisterActivity.class);
                    ContextManager.activity().startActivity(intent);
                    ContextManager.activity().finish();
                } else {
                    toast = "验证码验证失败!";
                }
                Toast.makeText(ContextManager.context(), toast, Toast.LENGTH_LONG).show();
                clearOnPassVerify(true);
                uninit();

            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                /** 获取验证码成功后的执行动作 */
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // count down
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    // 根据服务器返回的网络错误，给toast提示
                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            Toast.makeText(ContextManager.activity(), des, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ContextManager.activity(), "网络错误!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 请求支持国家列表
                    onCountryListGot((ArrayList<HashMap<String, Object>>) data);
                } else {
                    // 忽略国家列表,直接获取验证码
                    SMSSDK.getVerificationCode(mCountryCode, mPhoneNum, osmHandler);
                }
            }
            return false;
        }
    });

    // onCreate()
    public void init() {
        // 初始化短信SDK
        SMSSDK.initSDK(ContextManager.context(), APPKEY, APPSECRET);

        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        mActivity = ContextManager.activity();
        mActivity.registerReceiver(mSMSReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    private Activity mActivity;

    // onDestroy
    public void uninit() {
        SMSSDK.unregisterAllEventHandler();
        mActivity.unregisterReceiver(mSMSReceiver);
        mInited = false;
    }

    // 国家号码规则
    private HashMap<String, String> countryRules;

    private void onCountryListGot(ArrayList<HashMap<String, Object>> countries) {
        // 解析国家列表
        for (HashMap<String, Object> country : countries) {
            String code = (String) country.get("zone");
            String rule = (String) country.get("rule");
            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
                continue;
            }

            if (countryRules == null) {
                countryRules = new HashMap<String, String>();
            }
            countryRules.put(code, rule);
        }
        // 检查手机号码
        checkPhoneNum();
    }

    private static String getMCC() {
        TelephonyManager tm = (TelephonyManager) ContextManager.activity().getSystemService(Context.TELEPHONY_SERVICE);
        // 返回当前手机注册的网络运营商所在国家的MCC+MNC. 如果没注册到网络就为空.
        String networkOperator = tm.getNetworkOperator();

        // 返回SIM卡运营商所在国家的MCC+MNC. 5位或6位. 如果没有SIM卡返回空
        String simOperator = tm.getSimOperator();

        String mcc = null;
        if (!TextUtils.isEmpty(networkOperator) && networkOperator.length() >= 5) {
            mcc = networkOperator.substring(0, 3);
        }

        if (TextUtils.isEmpty(mcc)) {
            if (!TextUtils.isEmpty(simOperator) && simOperator.length() >= 5) {
                mcc = simOperator.substring(0, 3);
            }
        }
        return mcc;
    }

    private OnSendMessageHandler osmHandler;
    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";

    private String[] getCurrentCountry() {
        String mcc = getMCC();
        String[] country = null;
        if (!TextUtils.isEmpty(mcc)) {
            try {
                country = SMSSDK.getCountryByMCC(mcc);
            } catch (Exception e) {
            }

        }

        if (country == null) {
            Log.w("SMSSDK", "no country found by MCC: " + mcc);
            country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        }
        return country;
    }

    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mTime--;
            if (mTime == 0) {
                mTime = RETRY_INTERVAL;
                mCountDownTextView.setText(mOriginalText);
                mCountDownTextView.setEnabled(true);
            } else {
                mCountDownTextView.setText("倒计时:" + mTime + "秒");
                mCountDownTextView.setEnabled(false);
                BaseHandler.postDelayed(this, 1000);
            }
        }
    };

    /**
     * 倒数计时
     */
    private void countDown() {
        mTime = RETRY_INTERVAL;
        BaseHandler.post(mTimerRunnable);
    }

    private SMSReceiver mSMSReceiver = new SMSReceiver(new SMSSDK.VerifyCodeReadListener() {
        @Override
        public void onReadVerifyCode(final String verifyCode) {
            BaseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mVerifyCodeTextView != null) {
                        mVerifyCodeTextView.setText(verifyCode);
                    }
                }
            });
        }
    });
}
