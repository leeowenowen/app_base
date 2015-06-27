package com.msjf.fentuan.me;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.owo.app.common.DLog;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;
import com.zxing.encoding.EncodingHandler;

public class MyBarcodeInfoView extends LinearLayout implements ThemeObserver, LanguageObserver {
	private static final String TAG = "MyBarcodeInfoView";
	private ImageView mPhoto;
	private TextView mName;
	private ImageView mSex;
	private TextView mFansGroup;
	private ImageView mBarcode;
	private TextView mDescription;
	private User mUser;


	public MyBarcodeInfoView(Context context, User user) {
		super(context);
        mUser = user;
		initComponents(context);
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mPhoto = new ImageView(context);
		mName = new TextView(context);
		mSex = new ImageView(context);
		mFansGroup = new TextView(context);
		mBarcode = new ImageView(context);
		mDescription = new TextView(context);

		LinearLayout nameSex = new LinearLayout(context);
		nameSex.setGravity(Gravity.CENTER);
		nameSex.addView(mName);
		nameSex.addView(mSex);
		LU.setMargin(mSex, 22, 0, 0, 0);

		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
		addView(mPhoto, LP.lp(DimensionUtil.w(180), DimensionUtil.w(180)));
		LU.setMargin(mPhoto, 0, 20, 0, 20);
		addView(nameSex);
		mFansGroup.setGravity(Gravity.CENTER);
		addView(mFansGroup);
		LU.setMargin(mFansGroup, 0, 20, 0, 20);
		addView(mBarcode, LP.lp(DimensionUtil.w(215), DimensionUtil.w(215)));
		addView(mDescription);
		mDescription.setGravity(Gravity.CENTER);
		LU.setMargin(mDescription, 0, 20, 0, 20);
	}

	@Override
	public void onThemeChanged() {

		Theme theme = Singleton.of(Theme.class);
		mPhoto.setImageBitmap(mUser.getPhoto().getBmp());
		Bitmap qrCodeBitmap;
		try {
			qrCodeBitmap = EncodingHandler.createQRCode(mUser.getUserName(), 215);
			mBarcode.setImageBitmap(qrCodeBitmap);
		} catch (WriterException e) {
			DLog.e(TAG, "生成二维码失败", e);
			e.printStackTrace();
		}
		mSex.setImageBitmap(theme.bitmap(BitmapId.me_femail));
		TU.setTextColor(ColorId.main_text_color, mName, mFansGroup, mDescription);
		TU.setTextSize(32, mName);
		TU.setTextSize(27, mFansGroup, mDescription);
	}

	@Override
	public void onLanguageChanged() {
		mName.setText(mUser.getUserName());
		mFansGroup.setText("中国" + mUser.getCityName() + "-" + mUser.getStarName() + "粉团");
		mDescription.setText("扫描上边的二维码团,加我粉团");
        TU.setBGColor(ColorId.gray_text_color, this);
	}

}
