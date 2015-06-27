package com.msjf.fentuan.movie.order;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.movie.BasePage;
import com.owo.app.base.ConfigurableActivity;
import com.owo.base.pattern.Singleton;

public class OrderActivity extends ConfigurableActivity {
	private void makeOrderData() {
		OrderData orderData = Singleton.of(OrderData.class);
		orderData.mMovieName = "狼图腾";
		orderData.mCinemaName = "北京唐阁影城亦庄店";
		orderData.mDataTime = "2015-03-14 周六21:10";
		orderData.mSeatInfo = "２号厅２排２座";
		orderData.mTotalPrice = 57;
		orderData.mPriceDescription = "已含服务费：２元／张";
		orderData.mPhoneNum = "13439042557";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		makeOrderData();
		BasePage page = new BasePage(this, false);
		page.setTitle("订单信息");
		OrderView view = new OrderView(this);
		view.setData(Singleton.of(OrderData.class));
		page.setContentView(view);
		setContentView(page);
	}

}
