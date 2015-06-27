package com.msjf.fentuan.me.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.owo.app.base.ConfigurableActivity;

public class OneOneChatActivity extends ConfigurableActivity {
    OneOneChatPage mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userId = getIntent().getStringExtra("userId");
        mPage = new OneOneChatPage(this, userId);
        setContentView(mPage);
    }

    protected void onResume() {
        Log.i("OneOneChatActivity", "onResume");
        super.onResume();

        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
        sdkHelper.pushActivity(this);
        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(mListener, new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventReadAck});
    }

    @Override
    protected void onPause() {
        Log.i("OneOneChatActivity", "onPause");

        // unregister this event listener when this activity enters the background
        EMChatManager.getInstance().unregisterEventListener(mListener);

        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();

        // 把此activity 从foreground activity 列表里移除
        sdkHelper.popActivity(this);

        super.onStop();
    }

    private String userId;

    private EMEventListener mListener = new EMEventListener() {
        @Override
        public void onEvent(EMNotifierEvent event) {
            switch (event.getEvent()) {
                case EventNewMessage: //普通消息
                {
                    EMMessage message = (EMMessage) event.getData();
                    //提示新消息
                    HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
                    if (userId != null && userId.equals(message.getFrom())) {
                        mPage.refreshUI();
                    }
                    break;
                }

                case EventOfflineMessage: {
                    mPage.refreshUI();
                    break;
                }

                default:
                    break;
            }
        }
    };


}
