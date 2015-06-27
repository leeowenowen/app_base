package com.msjf.fentuan.share;

import android.R.dimen;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;

public class ShareView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private GridView mShareGridView;
	private BottomButtonTextView mCancel;
	private ShareAdapter mAdapter = new ShareAdapter();

	public ShareView(Context context) {
		super(context);

		mShareGridView = new GridView(context);
		mCancel = new BottomButtonTextView(context);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(VERTICAL);
		addView(mShareGridView);
		addView(mCancel);
		onLanguageChanged();
		onThemeChanged();

		mShareGridView.setAdapter(mAdapter);
		mShareGridView.setNumColumns(3);
		LinearLayout.LayoutParams cancelParam = (LinearLayout.LayoutParams)mCancel.getLayoutParams();
		cancelParam.width = DimensionUtil.w(500);
		
		LU.setMargin(mCancel, 20, 30, 20, 30);
		LU.setPadding(this, 35, 30, 35, 0);
	}

	@Override
	public void onLanguageChanged() {
		mCancel.setText("取消");
	}

	@Override
	public void onThemeChanged() {

	}
	
	public void setCancelCallback(final Runnable r)
	{
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				r.run();
			}
		});
	}

	private class ShareItemView extends LinearLayout implements ThemeObserver, LanguageObserver {
		private ImageView mImage;
		private TextView mText;
		private ShareDataItem mDataItem;

		public ShareItemView(Context context) {
			super(context);
			mImage = new ImageView(context);
			mText = new TextView(context);
			setGravity(Gravity.CENTER_HORIZONTAL);
			setOrientation(VERTICAL);
			addView(mImage);
			addView(mText);
			onLanguageChanged();
			onThemeChanged();
			mText.setGravity(Gravity.CENTER);
			LU.setMargin(mText, 0, 11, 0, 0);
			LU.setPadding(this, 0, 30, 0, 0);
		}

		@Override
		public void onLanguageChanged() {

		}

		@Override
		public void onThemeChanged() {
			TU.setTextColor(ColorId.main_text_color, mText);
			TU.setTextSize(24, mText);
		}

		public void updateData(ShareDataItem dataItem) {
			mDataItem = dataItem;
			mText.setText(dataItem.mText);
			TU.setImageBitmap(dataItem.mBmpId, mImage);
		}
	}

	private class ShareAdapter extends BaseAdapter {
		private ShareDataItem[] mShareDataItems = new ShareDataItem[] {
				new ShareDataItem("微信朋友圈", BitmapId.hongbao_share_weixinquan,
						ShareType.WeiXinPengyouquan),//
				new ShareDataItem("微信好友", BitmapId.hongbao_share_weixin, ShareType.WeiXinHaoYou),//
				new ShareDataItem("短信", BitmapId.hongbao_share_sms, ShareType.SMS),//
				new ShareDataItem("qq好友", BitmapId.hongbao_share_qq, ShareType.QQ),//
				new ShareDataItem("明星贴吧", BitmapId.hongbao_share_mingxingtieba,
						ShareType.MingXingTieBa),//
				new ShareDataItem("微博好友", BitmapId.hongbao_share_weibo, ShareType.WeiBo),//
		};

		@Override
		public int getCount() {
			return mShareDataItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ShareItemView view = null;
			if (convertView == null) {
				view = new ShareItemView(getContext());
			} else {
				view = (ShareItemView) convertView;
			}
			ShareDataItem item = mShareDataItems[position];
			view.updateData(item);
			return view;
		}
	}

}
