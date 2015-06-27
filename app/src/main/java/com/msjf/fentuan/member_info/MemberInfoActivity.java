package com.msjf.fentuan.member_info;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;

public class MemberInfoActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		String userId = getIntent().getStringExtra("userId");
		MemberInfoPage page = new MemberInfoPage(this);
		page.setUserId(userId);
		setContentView(page);
	}

}
