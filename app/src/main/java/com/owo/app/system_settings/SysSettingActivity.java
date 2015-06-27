package com.owo.app.system_settings;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.system_settings.ui.SystemSettingWidget;

public class SysSettingActivity extends ConfigurableActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(new SystemSettingWidget(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	};
}
