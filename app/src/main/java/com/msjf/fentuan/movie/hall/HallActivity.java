package com.msjf.fentuan.movie.hall;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.movie.BasePage;
import com.owo.app.base.ConfigurableActivity;
import com.owo.base.pattern.Singleton;

public class HallActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		BasePage page = new BasePage(this, false);
		page.setTitle("<<狼图腾>>");
		HallView view = new HallView(this);
		view.setData("今天03-09", Singleton.of(HallData.class));
		page.setContentView(view);
		setContentView(page);
	}

}
