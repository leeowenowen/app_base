package com.msjf.fentuan.app.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import com.msjf.fentuan.gateway_service.fendouquan.CircleClient;
import com.owo.app.base.ConfigurableActivity;

public class MainActivity extends ConfigurableActivity {
	private MainFrame mMainFrame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mMainFrame = new MainFrame(this);
		mMainFrame.onCreate(savedInstanceState);

		setContentView(mMainFrame);
	}

	@Override
	protected void onDestroy() {
		mMainFrame.onDestroy();

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMainFrame.onPause();

		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mMainFrame.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMainFrame.onSaveInstanceState(outState);

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mMainFrame.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {
		}
		return super.dispatchKeyEvent(event);

	}
}
