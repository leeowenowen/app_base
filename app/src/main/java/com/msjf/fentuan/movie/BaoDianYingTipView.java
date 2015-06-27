package com.msjf.fentuan.movie;

import java.util.ArrayList;

import android.content.Context;

import com.owo.base.common.Tuple.Tuple3;

public class BaoDianYingTipView extends BaoXViewBase {

	public BaoDianYingTipView(Context context) {
		super(context);
	}

	@Override
	protected void initComponents(Context context) {
		super.initComponents(context);
		
		setTitle("报点映");

		ArrayList<Tuple3<String, String, Boolean>> items = new ArrayList<Tuple3<String, String, Boolean>>();
		items.add(new Tuple3<String, String, Boolean>("影院", "北京唐阁影院亦庄店",false));
		items.add(new Tuple3<String, String, Boolean>("点映日期", "2015-5-20",false));
		items.add(new Tuple3<String, String, Boolean>("时间段(场次)", "上午11:30",false));
		items.add(new Tuple3<String, String, Boolean>("可能到场嘉宾", "冯绍峰,窦要",false));

		addGroup("影票信息", items);

		ArrayList<Tuple3<String, String, Boolean>> items2 = new ArrayList<Tuple3<String, String, Boolean>>();
		items2.add(new Tuple3<String, String, Boolean>("单次费用", "38元/人",false));
		items2.add(new Tuple3<String, String, Boolean>("人数", "1",true));
		items2.add(new Tuple3<String, String, Boolean>("总费用", "38元",false));

		addGroup("支付信息", items2);
	}

}
