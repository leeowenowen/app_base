package com.msjf.fentuan.movie.seat;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.movie.BasePage;
import com.owo.app.base.ConfigurableActivity;
import com.owo.base.pattern.Singleton;

public class SeatActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		BasePage page = new BasePage(this, false);
		page.setTitle("<<狼图腾>>");
		SeatView view = new SeatView(this);
		view.setData(Singleton.of(SeatData.class));
		page.setContentView(view);
		setContentView(page);
	}

}
