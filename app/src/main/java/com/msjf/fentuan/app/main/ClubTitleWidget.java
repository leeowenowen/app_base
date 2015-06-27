package com.msjf.fentuan.app.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupClient;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupData;
import com.msjf.fentuan.gateway_service.fans_group.FansGroupItem;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.me.MeActivity;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.DrawableId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.utils.LP;

import java.util.Observable;
import java.util.Observer;

public class ClubTitleWidget extends LinearLayout implements ThemeObserver, LanguageObserver {
    private static final String TAG = "ClueTitleWidget";
    private ImageView mAdd;
    private TextView mTitleLeftText;
    private TextView mTitleRightText;
    private ImageView mDownView;
    private ImageView mAccountInfo;
    private LinearLayout mTitle;

    public ClubTitleWidget(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onLanguageChanged();
        onThemeChanged();
    }

    private void initComponents(Context context) {
        mAdd = new ImageView(context);
        mTitleLeftText = new TextView(context);
        mTitleRightText = new TextView(context);
        mDownView = new ImageView(context);

        mTitle = new LinearLayout(context);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.addView(mTitleLeftText);
        mTitle.addView(mTitleRightText);
        mTitle.addView(mDownView);

        mAccountInfo = new ImageView(context);

        int padding = DimensionUtil.w(20);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER_VERTICAL);
        addView(mAdd);
        addView(mTitle, LP.L0W1);
        addView(mAccountInfo);
        setBackgroundColor(Color.YELLOW);
    }

    private void setupListeners() {
        mAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                final StarListView listView = new StarListView(getContext());
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // add star
                        final FansGroupItem item = FansGroupData.instance().getData().get(position);
                        FansGroupClient.focusFanGroup(item.getTid(), new JsonResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                Logger.v(TAG, "focusFanGroup Success !" + jsonObject.toJSONString());
                                Toast.makeText(getContext(), "关注明星成功!", Toast.LENGTH_LONG).show();
                                item.setFocusFlag(true);
                                FansGroupData.instance().notifyDataSetChanged();
                                popupWindow.dismiss();
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                Logger.v(TAG, "focusFanGroup failed !" + jsonObject.toJSONString());
                                Toast.makeText(getContext(), "关注明星成功!", Toast.LENGTH_LONG).show();
                                item.setFocusFlag(true);
                                FansGroupData.instance().notifyDataSetChanged();
                                popupWindow.dismiss();
//                                Toast.makeText(getContext(), "关注明星失败!", Toast.LENGTH_LONG).show();
//                                popupWindow.dismiss();
                            }
                        });
                    }
                });
                popupWindow.setContentView(listView);
                popupWindow.setWidth(DimensionUtil.w(270));
                popupWindow.setHeight(DimensionUtil.h(620));
                popupWindow.showAsDropDown(mAdd);
            }
        });
        mAccountInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), MeActivity.class);
                ContextManager.activity().startActivity(intent);
            }
        });

        mDownView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final ConfigurablePopupWindow popupWindow = new ConfigurablePopupWindow();
                final FocusedStarListView listView = new FocusedStarListView(getContext());
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                        FansGroupData.instance().setCurrentFanGroupId(listView.getItem(position).getTid());
                        FansGroupData.instance().notifyDataSetChanged();
                    }
                });
                popupWindow.setWidth(DimensionUtil.w(210));
                popupWindow.setHeight(DimensionUtil.h(220));
                popupWindow.setContentView(listView);
                popupWindow.showAsDropDown(mDownView);
            }
        });

        FansGroupData.instance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                String id = FansGroupData.instance().getCurrentFanGroupId();
                String name = null;
                if (TextUtils.isEmpty(id)) {
//                    if (FansGroupData.instance().getData().size() > 0) {
//                        name = FansGroupData.instance().getData().get(0).getStarName();
//                    }
                    name = "";
                } else {
                    name = FansGroupData.instance().getCurrentFansGroupItem().getStarName();
                }
                mTitleRightText.setText(name);
            }
        });
    }

    @Override
    public void onLanguageChanged() {
        mTitleLeftText.setText("粉团");
        //mTitleRightText.setText("冯绍峰");
    }

    @Override
    public void onThemeChanged() {
        mTitleLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(32));
        mTitleRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtil.w(27));
        Theme theme = Singleton.of(Theme.class);

        TU.setImageDrawable(DrawableId.main_account, mAccountInfo);
        TU.setImageDrawable(DrawableId.main_add, mAdd);
        TU.setImageDrawable(DrawableId.main_down_menu, mDownView);

        mTitleLeftText.setTextColor(theme.color(ColorId.highlight_color));
        mTitleRightText.setTextColor(theme.color(ColorId.highlight_color));

        setBackgroundColor(theme.color(ColorId.base_page_title_bg));
    }

}
