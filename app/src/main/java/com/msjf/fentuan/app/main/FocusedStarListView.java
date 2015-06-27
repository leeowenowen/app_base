package com.msjf.fentuan.app.main;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.nostra13.universalimageloader.utils.L;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FocusedStarListView extends ListViewBase {

    private DataAdapter mAdapter = new DataAdapter();

    public FocusedStarListView(Context context) {
        super(context);

        setAdapter(mAdapter);
        mAdapter.updateData();
        FansGroupData.instance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {

                mAdapter.updateData();
            }
        });
    }

    public FansGroupItem getItem(int index) {
        return (FansGroupItem) mAdapter.getItem(index);
    }

    private class DataAdapter extends BaseAdapter {

        private List<FansGroupItem> mData;

        public void updateData() {
            mData = FansGroupData.instance().getFocusedData();
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
            ItemView view = null;
            if (convertView == null) {
                view = new ItemView(getContext());
            } else {
                view = (ItemView) convertView;
            }
            view.update(mData.get(position));
            return view;
        }
    }

    private class ItemView extends LinearLayout implements ThemeObserver {
        private TextView mStar;
        private ImageView mPhoto;

        public ItemView(Context context) {
            super(context);
            mStar = new TextView(context);
            mPhoto = new ImageView(context);
            mStar.setGravity(Gravity.CENTER);

            setGravity(Gravity.CENTER_VERTICAL);
            LU.setPadding(this, 0, 0, 26, 0);
            addView(mStar, LP.L0M1);
            addView(mPhoto, LP.lp(DimensionUtil.w(50), DimensionUtil.w(50)));
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, DimensionUtil.h(69));
            setLayoutParams(param);
            onThemeChanged();
        }

        public void update(FansGroupItem item) {
            TU.setImageBitmap(StarData.instance().id(item.getStarName()), mPhoto);
            mStar.setText(item.getStarName());
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mStar);
            TU.setTextSize(27, mStar);
            //TU.setBGColor(ColorId.main_bg, this);
            mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.star_photo));
        }
    }

}
