package com.msjf.fentuan.welcome;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;

public class WelcomeActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new WelcomeView(this));

	}

}
