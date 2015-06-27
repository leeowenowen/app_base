package com.msjf.fentuan.hongbao;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;

public class HongBaoActivity extends ConfigurableActivity {
	private HongBaoPage mHongBaoPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Double amount = getIntent().getDoubleExtra("amount", 0.0);
		mHongBaoPage = new HongBaoPage(this, amount);
		setContentView(mHongBaoPage);
	}

}
