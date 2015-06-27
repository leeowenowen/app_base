package com.msjf.fentuan.me.message;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.easemob.chat.EMChat;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.owo.app.base.ConfigurableActivity;

public class MessageActivity extends ConfigurableActivity {

    private MessagePage mPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPage = new MessagePage(this);
		setContentView(mPage);

        // 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        EMChat.getInstance().setAppInited();
	}


	@Override
	protected void onResume() {
		super.onResume();
		// unregister this event listener when this activity enters the background
		DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
		sdkHelper.pushActivity(this);
        if(mPage != null)
        {
            mPage.onResume();
        }
	}

	@Override
	protected void onStop() {
		super.onStop();

		DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
		sdkHelper.popActivity(this);

        if(mPage != null)
        {
            mPage.onStop();
        }
	}

}
