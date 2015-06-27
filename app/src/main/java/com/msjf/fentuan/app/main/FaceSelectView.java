package com.msjf.fentuan.app.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.owo.app.theme.BitmapId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;

public class FaceSelectView extends GridView {

	private FaceAdapter mAdapter;

	public FaceSelectView(Context context) {
		super(context);

		mAdapter = new FaceAdapter();
		setAdapter(mAdapter);
		setNumColumns(8);
	}

	private class FaceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 80;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		private  Bitmap mBmp;
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(mBmp == null)
			{
				mBmp = Singleton.of(Theme.class).bitmap(BitmapId.welcome_logo);
			}
			ImageView view = null;
			if (convertView == null) {
				view = new ImageView(getContext());
			} else {
				view = (ImageView) convertView;
			}
			view.setImageBitmap(mBmp);
			return view;
		}
	}

}
