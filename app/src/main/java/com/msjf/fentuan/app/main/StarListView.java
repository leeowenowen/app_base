package com.msjf.fentuan.app.main;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.MultiFormatReader;
import com.msjf.fentuan.R;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Observable;
import java.util.Observer;

public class StarListView extends ListViewBase {

    private DataAdapter mAdapter = new DataAdapter();

    public StarListView(Context context) {
        super(context);

        setAdapter(mAdapter);
        mAdapter.updateData(FansGroupData.instance().getData());
        FansGroupData.instance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                mAdapter.updateData(FansGroupData.instance().getData());
            }
        });
    }

    private class DataAdapter extends BaseAdapter {

        private List<FansGroupItem> mData;


        public void updateData(List<FansGroupItem> data) {
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
            ItemView view = null;
            if (convertView == null) {
                view = new ItemView(getContext());
            } else {
                view = (ItemView) convertView;
            }
            view.update((FansGroupItem) getItem(position), position);
            return view;
        }
    }

    private class ItemView extends LinearLayout implements ThemeObserver {
        private TextView mIndex;
        private ImageView mPhoto;
        private TextView mName;
        private ImageView mFocused;
        private LinearLayout mRed;

        public ItemView(Context context) {
            super(context);
            mIndex = new TextView(context);
            mPhoto = new ImageView(context);
            mFocused = new ImageView(context);
            mName = new TextView(context);
            mName.setGravity(Gravity.CENTER_HORIZONTAL);
            mRed = new LinearLayout(context);
            mRed.setOrientation(VERTICAL);
            mRed.setGravity(Gravity.CENTER_HORIZONTAL);
            LU.setPadding(mRed, 10, 15, 10, 10);
            mRed.addView(mFocused);
            mRed.addView(mName);
            LU.setMargin(mName, 0, 10, 0, 0);

            setGravity(Gravity.CENTER_VERTICAL);
            LU.setPadding(this, 15, 10, 15, 10);
            addView(mIndex);
            addView(mPhoto, LP.lp(DimensionUtil.w(100), DimensionUtil.w(100)));
            LU.setMargin(mPhoto, 20, 0, 20, 0);
            addView(mRed,LP.LWW());
            onThemeChanged();
        }

        public void update(FansGroupItem item, int index) {
            mIndex.setText("" + index);
            mName.setText(item.getStarName());
            mFocused.setSelected(item.isFocusFlag());
            TU.setImageBitmap(StarData.instance().id(item.getStarName()), mPhoto);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onThemeChanged() {
            TU.setTextSize(27, mIndex, mName);
            TU.setTextColor(ColorId.main_text_color, mIndex);
            TU.setTextColor(ColorId.main_text_inverse_color, mName);
            //mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.star_photo));
            mName.setEms(1);
            TU.setBGColor(ColorId.highlight_color2, mRed);
            TU.setImageDrawable(DrawableId.main_star_focus, mFocused);

        }
    }

}
