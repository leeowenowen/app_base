package com.msjf.fentuan.member_info;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

public class ShenFenView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private TextView mTitle;
	private ItemText mZhuGuan;
	private ItemText mShouXiXinWenGuan;
	private ItemText mShouXiXingGuaZhuChiRen;
	private TextView mAttention;

	public ShenFenView(Context context) {
		super(context);
		initComponents(context);
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mTitle = new TextView(context);
		mZhuGuan = new ItemText(context);
		mShouXiXinWenGuan = new ItemText(context);
		mShouXiXingGuaZhuChiRen = new ItemText(context);
		mAttention = new TextView(context);

		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		addView(mTitle);
		LU.setMargin(mTitle, 0, 30, 0, 70);
		addView(mZhuGuan);
		addView(mShouXiXinWenGuan);
		LU.setMargin(mShouXiXinWenGuan, 0, 30, 0, 30);
		addView(mShouXiXingGuaZhuChiRen);
		addView(mAttention);
		LU.setMargin(mAttention, 0, 30, 0, 30);
		setPadding(DimensionUtil.w(30), 0, DimensionUtil.w(30), 0);
	}

	@Override
	public void onLanguageChanged() {
		mTitle.setText("粉丝身份权利义务说明");
		mTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		mZhuGuan.setText("粉丝主管:", " 已成功申请成为一线明星俱乐部会员,并累计消费该偶像相关点映等明星产品大于300元,同时"
				+ "给三位粉友发布最新星闻不少于3条.主管有资格享受粉团每月最基本粉丝专场权益1场/月");
		mShouXiXinWenGuan.setText("粉团首席星闻官:", " 已成功申请成为一线明星俱乐部会员,并且累计消费该偶像相关点映等明星产品大于"
				+ "2000元,并且给三位粉友发布最新星闻不少于两条:.有资格享受明星任何福利1份/月,有资格删除所有虚假星闻");
		mShouXiXingGuaZhuChiRen.setText("粉团首席星话主持人:", " 已成功申请成为一线明星俱乐部会员,并在粉丝平台累计消费该偶像相关电影"
				+ "等明星产品总计大于100元, 并给3位粉友发布最新星闻不少于1条,并且至少三次受邀主持星话.有资格享受粉团补贴" + "1次/月");
		mAttention.setText("注意:每单次明星粉团上述三个职位分别只有1个名额,每两周更换一次");
	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.highlight_color, mTitle);
		TU.setTextColor(ColorId.main_text_color, mZhuGuan, mShouXiXinWenGuan,
				mShouXiXingGuaZhuChiRen, mAttention);
		TU.setTextSize(32, mTitle);
		TU.setTextSize(27, mZhuGuan, mShouXiXinWenGuan, mShouXiXingGuaZhuChiRen, mAttention);
	}

	private class ItemText extends TextView {
		public ItemText(Context context) {
			super(context);
		}

		public void setText(String title, String content) {
			String value = title + content;
			SpannableStringBuilder builder = new SpannableStringBuilder();
			builder.append(value);
			builder.setSpan(
					new ForegroundColorSpan(Singleton.of(Theme.class)
							.color(ColorId.highlight_color)), 0, title.length(),
					Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			setText(builder);
		}

	}
}
