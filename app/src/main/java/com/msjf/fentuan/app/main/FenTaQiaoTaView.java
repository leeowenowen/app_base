package com.msjf.fentuan.app.main;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ListViewBase;

public class FenTaQiaoTaView extends ListViewBase implements ThemeObserver {
    private final User mUser;
    private ItemViewAdapter mAdapter;

    public FenTaQiaoTaView(Context context, User user) {
        super(context);
        mUser = user;
        mAdapter = new ItemViewAdapter();
        setAdapter(mAdapter);
    }

    @Override
    public void onThemeChanged() {
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    private class ItemViewAdapter extends BaseAdapter {

        private final String[] mTexts = new String[]{"粉他", "敲他"};
        private final BitmapId[] mBmps = new BitmapId[]{BitmapId.main_mark_add,
                BitmapId.main_mark_hammer};
        private final BitmapId[] mBmpsSelected = new BitmapId[]{BitmapId.main_mark_has_add,
                BitmapId.main_mark_hammer};

        @Override
        public int getCount() {
            return mTexts.length;
        }

        @Override
        public Object getItem(int position) {
            return mTexts[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemView view = null;
            if (convertView == null) {
                view = new ItemView(parent.getContext());
            } else {
                view = (ItemView) convertView;
            }
            view.mText.setText(mTexts[position]);
            BitmapId[] ids = mUser.isFocused() ? mBmpsSelected : mBmps;
            TU.setImageBitmap(ids[position], view.mIcon);
            return view;
        }
    }

    public static class ItemView extends LinearLayout implements ThemeObserver {

        ImageView mIcon;
        TextView mText;

        public ItemView(Context context) {
            super(context);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    DimensionUtil.h(44));
            setLayoutParams(param);

            mIcon = new ImageView(context);
            mText = new TextView(context);
            mText.setGravity(Gravity.CENTER);

            setGravity(Gravity.CENTER_VERTICAL);
            addView(mIcon);
            addView(mText);
            LU.setPadding(this, 20, 0, 44, 0);
            LU.setMargin(mText, 10, 0, 0, 0);
            onThemeChanged();
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mText);
            TU.setTextSize(32, mText);
            //TU.setBGColor(ColorId.main_bg, this);
        }
    }

}
