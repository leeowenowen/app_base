package com.msjf.fentuan.me;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class SuggestionPage extends BasePage {

	private EditText mEditText;
	private BottomButtonTextView mOK;

	public SuggestionPage(Context context) {
		super(context, true);
		onLanguageChangedImpl();
		onThemeChangedImpl();
	}

	@Override
	protected void initComponents(Context context) {
		super.initComponents(context);
		mEditText = new EditText(context);
		mOK = new BottomButtonTextView(context);

		LinearLayout contentView = new LinearLayout(context);
		contentView.setGravity(Gravity.CENTER_HORIZONTAL);
		contentView.setOrientation(VERTICAL);
		contentView.addView(mEditText,
				LP.lp(DimensionUtil.w(680), LinearLayout.LayoutParams.WRAP_CONTENT));
		contentView.addView(mOK);
		mEditText.setLineSpacing(DimensionUtil.h(20), 1);
		LU.setPadding(mEditText, 40, 40, 40, 40);
		setContentView(contentView);
	}

	@Override
	public void onLanguageChanged() {
		super.onLanguageChanged();
		onLanguageChangedImpl();
	}

	private void onLanguageChangedImpl() {
		setTitle("意见反馈");
		mOK.setText("下一步");
		mEditText.setHint("请详细描述您遇到的问题，或想要提的建议，并留下你的QQ或者邮箱，方便我们联"
				+ "系你．如果您想申请明星粉团团长，打算专区粉团特殊劳资，同时也想参与讨论，请加入我们的QQ群" + "371316793");
	}

	private void onThemeChangedImpl() {
		TU.setBGColor(ColorId.main_bg, mEditText);
		TU.setTextSize(27, mEditText);
		TU.setTextColor(ColorId.gray_text_color, mEditText);
		mEditText.setHintTextColor(Singleton.of(Theme.class).color(ColorId.gray_text_color));
	}

	@Override
	public void onThemeChanged() {
		super.onThemeChanged();
		onThemeChangedImpl();
	}

}
