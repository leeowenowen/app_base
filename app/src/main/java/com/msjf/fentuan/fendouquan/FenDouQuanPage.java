package com.msjf.fentuan.fendouquan;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.msjf.fentuan.gateway_service.fendouquan.CircleClient;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.ContextManager;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FenDouQuanPage extends BasePage {

    private PullToRefreshListView mPullRefreshListView;
    private ListView mActualListView;
    private LinearLayout mDouYiDuanLayout;
    private ImageView mDouYiDuanIcon;
    private TextView mDouYiDuanText;
    private ItemAdapter mItemAdapter;
    private RelativeLayout mContentViewLayout;
    private ImageView mShenBao;

    private int mCurCount = 0;
    private boolean mHasMore = true;
    private int mItemOnePage = 20;
    private int mFinishedPage = 0;
    private int mNextPage = 1;

    public FenDouQuanPage(Context context) {
        super(context, false);
        Singleton.of(FenDouQuanCtrl.class).startCheck();
    }

    @Override
    protected void initComponents(Context context) {
        super.initComponents(context);

        mItemAdapter = new ItemAdapter();
        mPullRefreshListView = new PullToRefreshListView(context);

        // DouYiDuan
        mDouYiDuanIcon = new ImageView(context);
        mDouYiDuanText = new TextView(context);
        mDouYiDuanLayout = new LinearLayout(context);
        mDouYiDuanLayout.setGravity(Gravity.CENTER_VERTICAL);
        mDouYiDuanLayout.setOrientation(VERTICAL);
        mDouYiDuanLayout.addView(mDouYiDuanIcon, LP.lp(DimensionUtil.w(50), DimensionUtil.w(50)));
        mDouYiDuanLayout.addView(mDouYiDuanText);
        LU.setMargin(mDouYiDuanIcon, 0, 0, 0, 5);

        mContentViewLayout = new RelativeLayout(context);
        mShenBao = new ImageView(context);
        mContentViewLayout.addView(mPullRefreshListView, LP.LMM());
        mContentViewLayout.addView(mShenBao);
        RelativeLayout.LayoutParams miBaoLp = (RelativeLayout.LayoutParams) mShenBao.getLayoutParams();
        miBaoLp.width = DimensionUtil.w(120);
        miBaoLp.height = DimensionUtil.w(120);
        miBaoLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        miBaoLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        LU.setMargin(mShenBao, 0, 0, 20, 115);

        setTitleRightExtension(mDouYiDuanLayout);
        setContentView(mContentViewLayout);
        mTitle.setVisibility(INVISIBLE);
        mBack.setVisibility(INVISIBLE);

        mActualListView = mPullRefreshListView.getRefreshableView();
        mActualListView.setDivider(new ColorDrawable(Singleton.of(Theme.class).color(ColorId.view_divider)));
        mActualListView.setCacheColorHint(0);
        mActualListView.setDividerHeight(2);
        mActualListView.setAdapter(mItemAdapter);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void pullDown()
    {
        String label = DateUtils.formatDateTime(ContextManager.appContext(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

        // Do work to refresh the list here.
        final FenDouQuanData data = Singleton.of(FenDouQuanData.class);
        CircleClient.getCircleList(1, FenDouQuanData.NUM_PER_PAGE, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                ArrayList<CircleInfo> infos = new ArrayList<CircleInfo>();
                com.alibaba.fastjson.JSONArray cicleList = jsonObject.getJSONArray("circleInfoList");
                for (int i = 0; i < cicleList.size(); ++i) {
                    JSONObject circleJson = cicleList.getJSONObject(i);
                    CircleInfo info = CircleInfo.fromJson(circleJson);
                    infos.add(info);
                    Logger.v("FenDouQuanLog", "getCircleItem[userId:" + info.getUserId() + "]");
                    if (i == 0) {
                        Logger.v("TestUserId", "first:" + info.getUserId());
                    }
                }
                data.addAll(infos, true, true);
                data.notifyDataSetChanged();
                // Call onRefreshComplete when the list has been refreshed.
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onFailure(JSONObject jsonObject) {
                // Call onRefreshComplete when the list has been refreshed.
                mPullRefreshListView.onRefreshComplete();
            }
        });
    }
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mDouYiDuanLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextManager.activity(), DouYiDuanActivity.class);
                intent.putExtra("type", PageType.DouYiQuan.name());
                ContextManager.activity().startActivityForResult(intent, 200);
            }
        });
        mShenBao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContextManager.activity(), DouYiDuanActivity.class);
                intent.putExtra("type", PageType.MiBao.name());
                ContextManager.activity().startActivityForResult(intent, 200);
            }
        });

        Singleton.of(FenDouQuanData.class).addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                mItemAdapter.notifyDataSetChanged();
            }
        });


        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullDown();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //request circle items
                final FenDouQuanData data = Singleton.of(FenDouQuanData.class);
                int curPageNum = data.size() / FenDouQuanData.NUM_PER_PAGE;

               // LoadingCtrl.start("正在加载粉逗圈信息...");
                CircleClient.getCircleList(curPageNum + 1, FenDouQuanData.NUM_PER_PAGE, new JsonResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        com.alibaba.fastjson.JSONArray cicleList = jsonObject.getJSONArray("circleInfoList");
                        for (int i = 0; i < cicleList.size(); ++i) {
                            JSONObject circleJson = cicleList.getJSONObject(i);
                            CircleInfo info = CircleInfo.fromJson(circleJson);
                            data.add(info, false);
                            Logger.v("FenDouQuanLog", "getCircleItem[userId:" + info.getUserId() + "]");
                            if (i == 0) {
                                Logger.v("TestUserId", "first:" + info.getUserId());
                            }
                        }
                        data.notifyDataSetChanged();
                        // Call onRefreshComplete when the list has been refreshed.
                        mPullRefreshListView.onRefreshComplete();
                        //LoadingCtrl.stop();
                    }

                    @Override
                    public void onFailure(JSONObject jsonObject) {
                        // Call onRefreshComplete when the list has been refreshed.
                        mPullRefreshListView.onRefreshComplete();
                        //LoadingCtrl.stop();
                    }
                });
            }
        });
    }

    public void onResume()
    {
        pullDown();
    }

    void onSendFinished() {
        pullDown();
//        LoadingCtrl.start("正在加载粉逗圈信息...");
//        final FenDouQuanData data = Singleton.of(FenDouQuanData.class);
//        CircleClient.getCircleList(1, FenDouQuanData.NUM_PER_PAGE, new JsonResponseHandler() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                ArrayList<CircleInfo> infos = new ArrayList<CircleInfo>();
//                com.alibaba.fastjson.JSONArray cicleList = jsonObject.getJSONArray("circleInfoList");
//                for (int i = 0; i < cicleList.size(); ++i) {
//                    JSONObject circleJson = cicleList.getJSONObject(i);
//                    CircleInfo info = CircleInfo.fromJson(circleJson);
//                    infos.add(info);
//                    Logger.v("FenDouQuanLog", "getCircleItem[userId:" + info.getUserId() + "]");
//                    if (i == 0) {
//                        Logger.v("FenDouQuanLog", "first:" + info.getUserId());
//                    }
//                }
//                data.addAll(infos, true, true);
//                data.notifyDataSetChanged();
//                LoadingCtrl.stop();
//            }
//
//            @Override
//            public void onFailure(JSONObject jsonObject) {
//                LoadingCtrl.stop();
//            }
//        });
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();

        setTitle("粉逗圈");
        mDouYiDuanText.setText("逗一段");
    }

    @Override
    public void onThemeChanged() {
        super.onThemeChanged();
        Theme theme = Singleton.of(Theme.class);
        TU.setTextColor(ColorId.highlight_color, mDouYiDuanText);
        TU.setTextSize(15, mDouYiDuanText);
        TU.setImageBitmap(BitmapId.fendouquan_douyiduan, mDouYiDuanIcon);
        TU.setImageBitmap(BitmapId.fendouquan_shenbao, mShenBao);
    }

    private class ItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int count = Singleton.of(FenDouQuanData.class).size();
            return count;
        }

        @Override
        public Object getItem(int position) {
            return Singleton.of(FenDouQuanData.class).get(position);
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
            CircleInfo info = (CircleInfo) getItem(position);
            Logger.v("FenDouQuanLog", "getView[position:" + position + "][id:" + info.getUserId() + "]");
            view.updateData(info);
            return view;
        }

    }

}
