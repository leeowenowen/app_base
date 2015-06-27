package com.msjf.fentuan.register;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;

public class PhotoUploadListView extends ListViewBase {
	private DataAdapter mAdapter = new DataAdapter();

	public PhotoUploadListView(Context context) {
		super(context);

		setAdapter(mAdapter);
	}

	private class DataAdapter extends BaseAdapter {

		private final String[] mStars = new String[] { "拍照", "选择相册" };

		@Override
		public int getCount() {
			return mStars.length;
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
			TextView view = null;
			if (convertView == null) {
				view = new TextView(getContext());
				AbsListView.LayoutParams param = new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, DimensionUtil.h(100));
				view.setLayoutParams(param);
			} else {
				view = (TextView) convertView;
			}
			view.setGravity(Gravity.CENTER);
			TU.setTextColor(ColorId.main_text_color, view);
			TU.setTextSize(32, view);
			view.setText(mStars[position]);
			return view;
		}
	}
}
