package com.msjf.fentuan.member_info;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class SendNewsView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private TextView mTitleLeft;
	private TextView mContentLeft;
	private EditText mTitleRight;
	private EditText mContentRight;
	private ImageView mSend;
	private View mDividerHorizental, mDividerVertical;

	public SendNewsView(Context context) {
		super(context);

		mTitleLeft = new TextView(context);
		mContentLeft = new TextView(context);
		mTitleRight = new EditText(getContext());
		mContentRight = new EditText(getContext());
		mSend = new ImageView(context);
		mDividerHorizental = new View(context);
		mDividerVertical = new View(context);

		LinearLayout title = new LinearLayout(context);
		title.setGravity(Gravity.BOTTOM);
		title.addView(mTitleLeft);
		title.addView(mTitleRight);

		LinearLayout content = new LinearLayout(context);
		content.addView(mContentLeft);
		content.addView(mContentRight);

		LinearLayout left = new LinearLayout(context);
		left.setOrientation(VERTICAL);
		left.addView(title, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(105)));
		left.addView(mDividerVertical,LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(1)));
		left.addView(content, LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(235)));

		setGravity(Gravity.CENTER_VERTICAL);
		addView(left, LP.lp(DimensionUtil.w(525), LinearLayout.LayoutParams.MATCH_PARENT));
		addView(mDividerHorizental, LP.lp(DimensionUtil.w(1), LinearLayout.LayoutParams.MATCH_PARENT));
		addView(mSend);
		
		LU.setMargin(title, 22, 0, 0, 15);
		LU.setMargin(content, 22, 18, 0, 0);
		LU.setMargin(mSend, 15, 0, 15, 0);
		onLanguageChanged();
		onThemeChanged();
		setupListener();
		mTitleRight.requestFocus();
	}

	public static interface Delegate
	{
		void onSend(String title, String content);
	}
    private Delegate mDelegate;
    public void setDelegate(Delegate delegate)
    {
        mDelegate = delegate;
    }

	private void setupListener()
	{
		mSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
                if(TextUtils.isEmpty(mTitleRight.getText()) || TextUtils.isEmpty(mContentRight.getText()))
                {
                    Toast.makeText(getContext(), "请输入标题或者内容!", Toast.LENGTH_LONG).show();
                    return;
                }
                mDelegate.onSend(mTitleRight.getText().toString(), mContentRight.getText().toString());
			}
		});
	}

	@Override
	public void onLanguageChanged() {
		mTitleLeft.setText("星闻标题:");
		mContentLeft.setText("星闻内容:");
		mContentRight.setHint("描述明星现场,100字以内.");
		
	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.main_text_color, mTitleLeft, mTitleRight, mContentLeft, mContentRight);
		Theme theme = Singleton.of(Theme.class);
		mContentRight.setHintTextColor(theme.color(ColorId.gray_text_color));
		TU.setTextSize(27, mTitleLeft, mTitleRight, mContentLeft, mContentRight);
		TU.setImageBitmap(BitmapId.main_chat_send, mSend);
		TU.setBGColor(ColorId.main_text_color, mDividerHorizental, mDividerVertical);
	}

}
