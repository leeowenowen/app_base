package com.msjf.fentuan.movie.cinema;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.msjf.fentuan.ui.common.ContentInverseTextView;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.UIUtil;
import com.owo.ui.ListViewBase;

public class CinemaGroupView extends LinearLayout implements ThemeObserver, LanguageObserver {

	private ImageView mTitleIcon;
	private ContentInverseTextView mTitle;
	private LinearLayout mTitleLayout;
	private ListViewBase mCinemaList;
	private CinemaListAdapter mAdapter;

	public CinemaGroupView(Context context) {
		super(context);
		initComponents(context);
		setupListeners();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mTitleIcon = new ImageView(context);
		mTitle = new ContentInverseTextView(context);
		mTitleLayout = new LinearLayout(context);
		mTitleLayout.addView(mTitleIcon);
		mTitleLayout.addView(mTitle, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.h(50)));
		mTitleLayout.setGravity(Gravity.CENTER_VERTICAL);
		mTitle.setPadding(DimensionUtil.w(19), 0, 0, 0);

		mCinemaList = new ListViewBase(context);
		mAdapter = new CinemaListAdapter();
		mCinemaList.setAdapter(mAdapter);

		setOrientation(VERTICAL);
		addView(mTitleLayout);
		addView(mCinemaList);
	}

	private void setupListeners() {
		mCinemaList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CinemaDataItem item = (CinemaDataItem) mAdapter.getItem(position);
			}
		});
	}

	public void setData(Bitmap icon, String groupName, List<CinemaDataItem> data) {
		mTitle.setText(groupName);
		mTitleIcon.setImageBitmap(icon);
		mAdapter.updateData(data);
		UIUtil.setListViewHeightBasedOnChildren(mCinemaList);
	}

	@Override
	public void onLanguageChanged() {

	}

	@Override
	public void onThemeChanged() {
		Theme theme = Singleton.of(Theme.class);
		setBackgroundColor(theme.color(ColorId.main_bg));
		mTitleLayout.setBackgroundColor(theme.color(ColorId.cinema_group_title_bg));
		mTitle.setTextColor(theme.color(ColorId.main_text_color));
	}

	private class CinemaListAdapter extends BaseAdapter {

		private List<CinemaDataItem> mData;

		public void updateData(List<CinemaDataItem> data) {
			mData = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CinemaItemView view = null;
			if (convertView == null) {
				view = new CinemaItemView(getContext());
			} else {
				view = (CinemaItemView) convertView;
			}
			view.setData(mData.get(position));
			return view;
		}
	}

}
