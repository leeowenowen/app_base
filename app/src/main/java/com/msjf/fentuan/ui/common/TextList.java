package com.msjf.fentuan.ui.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.ColorId;
import com.owo.ui.ListViewBase;

public class TextList extends ListViewBase {
	public TextList(Context context) {
		super(context);
		mAdapter = new DistrictAdapter();
		setAdapter(mAdapter);
	}

	private DataSource mDataSource;
	private DistrictAdapter mAdapter;

	public void setDataSource(DataSource dataSource) {
		mDataSource = dataSource;
		mAdapter.notifyDataSetChanged();
	}

	private class DistrictAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mDataSource == null ? 0 : mDataSource.count();
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
			} else {
				view = (TextView) convertView;
			}
			view.setText(mDataSource.at(position));
			TU.setTextColor(ColorId.main_text_color, view);
			TU.setTextSize(27, view);
			return view;
		}
	}

	public static interface DataSource {
		int count();

		String at(int position);
	}

}
