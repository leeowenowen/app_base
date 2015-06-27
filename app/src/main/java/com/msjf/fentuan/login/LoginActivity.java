package com.msjf.fentuan.login;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;

public class LoginActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new VideoBgLoginView(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
