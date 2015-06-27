package com.msjf.fentuan.app.main;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;
import com.owo.ui.view.ShapeImageView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class StarListView_bkup extends ListViewBase {

    private DataAdapter mAdapter = new DataAdapter();

    public StarListView_bkup(Context context) {
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
            view.update((FansGroupItem)getItem(position), position);
            return view;
        }
    }

    private class ItemView extends LinearLayout implements ThemeObserver {
        private TextView mIndex;
        private ShapeImageView mPhoto;
        private TextView mPlayWithHim;

        public ItemView(Context context) {
            super(context);
            mIndex = new TextView(context);
            mPhoto = new ShapeImageView(context, ShapeImageView.TYPE_CIRCLE);
            mPlayWithHim = new TextView(context);

            setGravity(Gravity.CENTER_VERTICAL);
            LU.setPadding(this, 15, 10, 15, 10);
            addView(mIndex);
            addView(mPhoto, LP.lp(DimensionUtil.w(100), DimensionUtil.w(100)));
            LU.setMargin(mPhoto, 20, 0, 20, 0);
            addView(mPlayWithHim,
                    LP.lp(DimensionUtil.w(42), LayoutParams.WRAP_CONTENT));
            onThemeChanged();
        }

        public void update(FansGroupItem item, int index) {
            mIndex.setText("" + index);
            mPlayWithHim.setText(item.getStarName());
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onThemeChanged() {
            TU.setTextSize(27, mIndex, mPlayWithHim);
            TU.setTextColor(ColorId.main_text_color, mIndex);
            TU.setTextColor(ColorId.main_text_inverse_color, mPlayWithHim);
            mPhoto.setImageBitmap(Singleton.of(Theme.class).bitmap(BitmapId.star_photo));
            mPlayWithHim.setEms(1);
            mPlayWithHim.setBackgroundDrawable(ThemeUtil
                    .getNinePatchDrawable(R.drawable.main_play_with_him));
            LU.setPadding(mPlayWithHim, 10, 30, 10, 10);
        }
    }

}
