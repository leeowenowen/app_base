package com.msjf.fentuan.me.hong_bao;

import android.os.Bundle;

import com.owo.app.base.ConfigurableActivity;

public class MyHongBaoActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHongBaoPage page = new MyHongBaoPage(this);
		setContentView(page);
	}

}
