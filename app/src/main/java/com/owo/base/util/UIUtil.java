package com.owo.base.util;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.msjf.fentuan.log.Logger;

import java.lang.reflect.Field;

public class UIUtil {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
            return;
        }

        int totalHeight = 0;
        int min = listAdapter.getCount();

        for (int i = 0; i < min; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * ((min == 0) ? 0 : (min - 1))) + 10;
        listView.setLayoutParams(params);
    }

    @SuppressLint("NewApi")
    public static void setGridViewHeightBasedOnChildren(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
            return;
        }
        int col = listView.getNumColumns();
        int count = listAdapter.getCount();

        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        int totalHeight = listItem.getMeasuredHeight() * ((count % col == 0) ? (count / col) : count / col + 1);
        totalHeight += listView.getVerticalSpacing() * (count / col + 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        if(totalHeight == 0)
        {
            Logger.v("UIUtil", "gridview height 0");
        }
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView listView, int itemHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 0;
            listView.setLayoutParams(params);
            return;
        }
        int col = listView.getNumColumns();
        int count = listAdapter.getCount();

        int totalHeight = itemHeight * ((count % col == 0) ? (count / col) : count / col + 1);
        totalHeight += 5 * (count / col + 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        if(totalHeight == 0)
        {
            Logger.v("UIUtil", "gridview height 0");
        }
        listView.setLayoutParams(params);
    }

}
