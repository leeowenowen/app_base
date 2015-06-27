package com.msjf.fentuan.movie;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.ui.common.DividerView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.common.Tuple.Tuple3;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class BaoXViewBase extends LinearLayout implements ThemeObserver, LanguageObserver {

	private LinearLayout mTitleLayout;
	private TextView mTitle;
	private ImageView mClose;
	private ImageView mOk;
	private LinearLayout mGroups;
	protected EditText mNumer;

	private Client mClient;

	public static interface Client {
		void onClose();

		void onOk();
	}

	public void setClient(Client client) {
		mClient = client;
	}

	public void setTitle(String title) {
		mTitle.setText(title);
	}

	public BaoXViewBase(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onThemeChanged();
		onLanguageChanged();
	}

	protected void initComponents(Context context) {
		mTitle = new TextView(context);
		mTitle.setGravity(Gravity.CENTER);
		mClose = new ImageView(context);
		mOk = new ImageView(context);
		mGroups = new LinearLayout(context);
		mGroups.setOrientation(VERTICAL);

		mTitleLayout = new LinearLayout(context);
		mTitleLayout.addView(mClose);
		mTitleLayout.addView(mTitle, LP.L0W1);
		mTitleLayout.addView(mOk);

		setOrientation(VERTICAL);
		addView(mTitleLayout);
		addView(mGroups);
		LU.setPadding(mTitleLayout, 20, 20, 20, 20);

	}

	protected GroupView addGroup(String title, List<Tuple3<String, String, Boolean>> items) {
		GroupView groupView = new GroupView(getContext());
		groupView.setTitle(title);
		for (int i = 0; i < items.size(); ++i) {
			Tuple3<String, String, Boolean> item = items.get(i);
			groupView.addItem(item.item1, item.item2, i != items.size() - 1, item.item3);
		}
		mGroups.addView(groupView);
		return groupView;
	}

	protected void setupListeners() {
		mOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClient.onClose();
			}
		});
		mClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClient.onOk();
			}
		});
	}

	@Override
	public void onLanguageChanged() {

	}

	@Override
	public void onThemeChanged() {
		TU.setTextColor(ColorId.highlight_color, mTitle);
		TU.setTextSize(27, mTitle);
		TU.setImageBitmap(BitmapId.movie_ok, mOk);
		TU.setImageBitmap(BitmapId.movie_cancel, mClose);
		TU.setBold(mTitle);
	}

	protected class ItemView extends LinearLayout implements ThemeObserver {
		private TextView mItemTitle;
		private TextView mContent;

		public ItemView(Context context, String title, String content, boolean isEdit) {
			super(context);
			mItemTitle = new TextView(context);
			mContent = isEdit ? new EditText(context) : new TextView(context);
			if (isEdit) {
				mNumer = (EditText) mContent;
				mNumer.setGravity(Gravity.CENTER_VERTICAL);
				TU.setTextSize(28, mNumer);
				mNumer.setBackgroundColor(Color.TRANSPARENT);
				mNumer.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			mItemTitle.setText(title);
			mContent.setText(content);

			setGravity(Gravity.CENTER_VERTICAL);
			addView(mItemTitle);
			addView(mContent);
			onThemeChanged();
		}

		public void setContent(String content) {
			mContent.setText(content);
		}

		@Override
		public void onThemeChanged() {
			TU.setTextSize(32, mItemTitle, mContent);
			TU.setTextColor(ColorId.main_text_color, mItemTitle);
			TU.setTextColor(ColorId.highlight_color, mContent);

		}

	}

	protected class GroupView extends LinearLayout implements ThemeObserver {
		private TextView mGroupTitle;
		private LinearLayout mItems;
		private FrameLayout mExtension;

		public GroupView(Context context) {
			super(context);
			mGroupTitle = new TextView(context);
			mItems = new LinearLayout(context);
			mItems.setOrientation(VERTICAL);
			mExtension = new FrameLayout(context);

			setOrientation(VERTICAL);
			addView(new DividerView(context));
			addView(mGroupTitle);
			addView(new DividerView(context));
			addView(mItems);
			addView(mExtension);
			LU.setPadding(mGroupTitle, 20, 0, 20, 0);
			LU.setPadding(mItems, 20, 0, 20, 0);
			LU.setPadding(mExtension, 20, 0, 20, 0);
			onThemeChanged();
		}

		public View getItem(int index) {
			return mItems.getChildAt(index);
		}

		public void setExtension(View v) {
			mExtension.addView(v);
		}

		public void addItem(String title, String content, boolean addBottomLine, boolean isEdit) {
			mItems.addView(new ItemView(getContext(), title, content, isEdit),
					LP.lp(LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(70)));
			if (addBottomLine) {
				mItems.addView(new DividerView(getContext()));
			}
		}

		public void setTitle(String title) {
			mGroupTitle.setText(title);
		}

		@Override
		public void onThemeChanged() {
			Theme theme = Singleton.of(Theme.class);
			mGroupTitle.setBackgroundColor(theme.color(ColorId.cinema_group_title_bg));
			TU.setTextSize(28, mGroupTitle);
			TU.setTextColor(ColorId.main_text_color, mGroupTitle);
		}

	}
}
