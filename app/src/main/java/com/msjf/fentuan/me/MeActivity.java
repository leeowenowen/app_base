package com.msjf.fentuan.me;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.owo.app.base.ConfigurableActivity;

public class MeActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MePage page = new MePage(this);
		setContentView(page);
	}

}
