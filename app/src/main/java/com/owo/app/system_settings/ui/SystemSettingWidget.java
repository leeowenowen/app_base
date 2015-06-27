package com.owo.app.system_settings.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurablePopupWindow;

public class SystemSettingWidget extends LinearLayout implements LanguageObserver, ThemeObserver {
	private TextView mChangeLanguageTextView;

	public SystemSettingWidget(Context context) {
		super(context);

		initComponents(context);
		setupListener();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mChangeLanguageTextView = new TextView(context);
		mChangeLanguageTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

		setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.rowHeight());
		addView(mChangeLanguageTextView, lParams);
	}

	private void setupListener() {
		mChangeLanguageTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
				popupWindow.setOutsideTouchable(false);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 100, 0, 0)));
				ListView listView = new ListView(getContext());
				String supportedLanguage = Singleton.of(SystemSettingsData.class).get(
						SystemSettingKeys.SupportedLanguage);
				final String[] supportedLanguages = supportedLanguage.split("##");
				SingleSelectListAdapter adapter = new SingleSelectListAdapter(supportedLanguages);
				listView.setAdapter(adapter);
				listView.setDivider(null);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						String selectLanguages = supportedLanguages[position];
						Singleton.of(SystemSettingsData.class).set(SystemSettingKeys.Language,
								selectLanguages);
						popupWindow.dismiss();
					}
				});
				popupWindow.setContentView(listView);
				popupWindow.setWidth(DimensionUtil.w(300));
				popupWindow.setHeight(DimensionUtil.h(500));
				popupWindow.showAsDropDown(mChangeLanguageTextView);
			}
		});
	}

	@Override
	public void onLanguageChanged() {
		mChangeLanguageTextView.setText(Singleton.of(Language.class).get(
				LanguageResourceKeys.ChangeLanguage));
	}

	@Override
	public void onThemeChanged() {
	}

	private class SingleSelectListAdapter extends BaseAdapter {
		private String[] mTextStrings;

		SingleSelectListAdapter(String[] textStrings) {
			mTextStrings = textStrings;
		}

		@Override
		public int getCount() {
			return mTextStrings.length;
		}

		@Override
		public Object getItem(int position) {
			return mTextStrings[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ItemView view = null;
			if (convertView != null) {
				view = (ItemView) convertView;
			} else {
				view = new ItemView(getContext());
			}
			view.updateData(mTextStrings[position]);
			return view;
		}

		private class ItemView extends LinearLayout implements ThemeObserver, LanguageObserver {
			private TextView mLanguage;
			private ImageView mSelectedMark;

			public ItemView(Context context) {
				super(context);

				mLanguage = new TextView(context);
				mSelectedMark = new ImageView(context);
				mLanguage.setGravity(Gravity.CENTER);

				LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				lParams.gravity = Gravity.LEFT;
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				rParams.gravity = Gravity.RIGHT;

				addView(mLanguage, lParams);
				addView(mSelectedMark, rParams);
				setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
						DimensionUtil.rowHeight()));
			}

			public void updateData(String language) {
				String curLanguage = Singleton.of(SystemSettingsData.class).get(
						SystemSettingKeys.Language);
				mSelectedMark.setVisibility(language.equals(curLanguage) ? VISIBLE : INVISIBLE);
				mLanguage.setText(Singleton.of(Language.class).get(
						Language.toLanguageResourceKey(language)));
				onThemeChanged();
			}

			@Override
			public void onThemeChanged() {
			}

			@Override
			public void onLanguageChanged() {
			}
		}
	}
}
