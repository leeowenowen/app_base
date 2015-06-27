package com.msjf.fentuan.me.hong_bao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.gateway_service.HongBaoClient;
import com.msjf.fentuan.me.message.MessageActivity;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.common.BottomButtonTextView;
import com.msjf.fentuan.ui.common.LeftTextRightImageLayout;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.Sex;
import com.msjf.fentuan.user.User;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHongBaoPage extends BasePage {

    private ListView mListView;
    private HongBaoAdapter mAdapter;

    public MyHongBaoPage(Context context) {
        super(context, false);

        HongBaoData.instance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                mAdapter.notifyDataSetChanged();
            }
        });

        HongBaoClient.get_user_red_envelope(1, 500);
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);
        mListView = new ListView(context);
        mAdapter = new HongBaoAdapter();
        mListView.setAdapter(mAdapter);

        setContentView(mListView);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();

    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("我的红包");
    }


    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        User user = Singleton.of(Self.class).getUser();
        Theme theme = Singleton.of(Theme.class);
    }
    private static   long ONE_DAY =  24 * 60 * 60 * 1000;
    private class HongBaoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return HongBaoData.instance().getData().size();
        }


        private boolean showTitle(int i)
        {
            if (i == 0)
            {
                return true;
            }
            long timestamp = ((HongBaoData.HongBaoDataItem) getItem(i)).getTimestamp();
            long preTimestamp = ((HongBaoData.HongBaoDataItem) getItem(i-1)).getTimestamp();
            return timestamp - preTimestamp > ONE_DAY;
        }

        @Override
        public Object getItem(int i) {
            return HongBaoData.instance().getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HongBaoItemView itemView = null;
            if(view == null)
            {
                itemView = new HongBaoItemView(getContext());
            }
            else
            {
                itemView = (HongBaoItemView)view;
            }

            itemView.updateData(((HongBaoData.HongBaoDataItem)getItem(i)), showTitle(i));

            return itemView;
        }
    }

    private class DateItemView extends TextView {

        public DateItemView(Context context) {
            super(context);
            TU.setTextColor(ColorId.main_text_color, this);
            TU.setTextSize(25, this);
        }
    }

    private class HongBaoItemView extends LinearLayout implements LanguageObserver, ThemeObserver {
        private TextView mTitle;
        private ImageView mHongBaoIcon;
        private TextView mFrom;
        private TextView mAmount;
        private TextView mTime;

        public HongBaoItemView(Context context) {
            super(context);
            mTitle = new TextView(context);
            mTitle.setGravity(Gravity.CENTER_VERTICAL);

            mHongBaoIcon = new ImageView(context);
            mFrom = new TextView(context);
            mAmount = new TextView(context);
            mTime = new TextView(context);

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(VERTICAL);
            layout.addView(mFrom);
            layout.addView(mAmount);

            LinearLayout bottom = new LinearLayout(context);
            bottom.setGravity(Gravity.CENTER_VERTICAL);
            bottom.addView(mHongBaoIcon);
            bottom.addView(layout,LP.L0W1);
            bottom.addView(mTime);

            LU.setPadding(bottom, 20, 10, 20, 10);
            LU.setPadding(mTitle, 20, 10, 20, 10);
            LU.setMargin(layout, 20, 0, 20, 0);

            setOrientation(LinearLayout.VERTICAL);
            addView(mTitle, LP.lp(ViewGroup.LayoutParams.MATCH_PARENT, DimensionUtil.h(80)));
            addView(bottom);


            onLanguageChanged();
            onThemeChanged();
        }

        @Override
        public void onLanguageChanged() {
            mFrom.setText("你成功获取了来自粉团的红包");
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_color, mFrom, mAmount);
            TU.setTextColor(ColorId.gray_text_color, mTime);
            TU.setImageBitmap(BitmapId.barcode, mHongBaoIcon);
            TU.setTextSize(28, mFrom, mAmount);
            TU.setTextSize(25, mTime);

            TU.setTextColor(ColorId.main_text_color, mTitle);
            TU.setTextSize(25, mTitle);
            TU.setBGColor(ColorId.gray_text_color,mTitle);
        }

        public void updateData(HongBaoData.HongBaoDataItem item, boolean showTitle) {
            mAmount.setText("红包金额:" + item.getAmount() + "元");
            hightlightNum(mAmount);
            SimpleDateFormat fromatter = new SimpleDateFormat("HH:mm:ss");
            mTime.setText(fromatter.format(item.getTimestamp()));
            mTitle.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            SimpleDateFormat fromatter2 = new SimpleDateFormat("yyyy-MM-dd");
            mTitle.setText(fromatter2.format(item.getTimestamp()));
        }
    }

    private void hightlightNum(TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(textView.getText());
        Pattern regexPattern = Pattern.compile("[0-9]+");
        Matcher matcher = regexPattern.matcher(builder.toString());
        while (matcher.find()) {
            builder.setSpan(
                    new ForegroundColorSpan(Singleton.of(Theme.class)
                            .color(ColorId.highlight_color)), matcher.start(), matcher.end(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        textView.setText(builder);
    }

}
