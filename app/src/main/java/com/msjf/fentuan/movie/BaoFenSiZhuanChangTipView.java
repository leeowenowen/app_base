package com.msjf.fentuan.movie;

import java.util.ArrayList;

import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.base.common.Tuple.Tuple3;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaoFenSiZhuanChangTipView extends BaoXViewBase{

	public BaoFenSiZhuanChangTipView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void initComponents(Context context) {
		super.initComponents(context);
		
		setTitle("报粉丝专场");

		ArrayList<Tuple3<String, String, Boolean>> items = new ArrayList<Tuple3<String, String, Boolean>>();
		items.add(new Tuple3<String, String, Boolean>("影院", "北京唐阁影院亦庄店",false));
		items.add(new Tuple3<String, String, Boolean>("点映日期", "2015-5-20",false));
		items.add(new Tuple3<String, String, Boolean>("时间段(场次)", "上午11:30",false));

		GroupView groupView = addGroup("影票信息", items);
		TextView extensionTitle = new TextView(context);
		extensionTitle.setText("专场套餐:");
		TextView extensionContent = new TextView(context);
		extensionContent.setText("电影票一张\n爆米花一桶\n饮料一杯\n狼图腾海报一张");
		LinearLayout extensionLayout = new LinearLayout(context);
		extensionLayout.addView(extensionTitle);
		extensionLayout.addView(extensionContent);
		groupView.setExtension(extensionLayout);
		TU.setTextColor(ColorId.main_text_color, extensionContent, extensionTitle);

		ArrayList<Tuple3<String, String, Boolean>> items2 = new ArrayList<Tuple3<String, String, Boolean>>();
		items2.add(new Tuple3<String, String, Boolean>("单次费用", "38元/人",false));
		items2.add(new Tuple3<String, String, Boolean>("人数", "1",true));
		items2.add(new Tuple3<String, String, Boolean>("总费用", "38元",false));

		addGroup("支付信息", items2);
	}
}
