package com.msjf.fentuan.movie.cinema;

import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.owo.app.theme.BitmapId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;

public class CinemaView extends ScrollView {
	private LinearLayout mContainer;

	public CinemaView(Context context) {
		super(context);

		mContainer = new LinearLayout(context);
		mContainer.setOrientation(LinearLayout.VERTICAL);
		addView(mContainer);
	}

	private Bitmap getGroupIcon(String groupName) {
		BitmapId id = null;
		if (groupName.contains("常去")) {
			id = BitmapId.cinema_favorite;
		} else if (groupName.contains("附近")) {
			id = BitmapId.cinema_nearby;
		}
		if (id == null) {
			return null;
		}
		return Singleton.of(Theme.class).bitmap(id);
	}

	public void updateData(CinemaData data) {
		mContainer.removeAllViews();
		for (Entry<String, List<CinemaDataItem>> entry : data.entrySet()) {
			String groupName = entry.getKey();
			List<CinemaDataItem> groupItems = entry.getValue();
			CinemaGroupView groupView = new CinemaGroupView(getContext());
			groupView.setData(getGroupIcon(groupName), groupName, groupItems);
			mContainer.addView(groupView);
		}
	}

}
