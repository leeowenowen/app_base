package com.msjf.fentuan.gateway_service.fans_group;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.owo.base.pattern.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by wangli on 15-6-14.
 */
public class FansGroupData extends Observable {

    private FansGroupData() {
    }

    public List<FansGroupItem> getFocusedData()
    {
        List<FansGroupItem> focused = new ArrayList<FansGroupItem>();
        for(FansGroupItem item : FansGroupData.instance().getData())
        {
            if(item.isFocusFlag())
            {
                focused.add(item);
            }
        }
//        if(focused.size() == 0)
//        {
//            focused.add(FansGroupData.instance().getData().get(0));
//        }

        return focused;
    }

    public static FansGroupData instance() {
        return Singleton.of(FansGroupData.class);
    }

    public void notifyDataSetChanged() {
        setChanged();
        notifyObservers();
    }

    private Map<String, FansGroupItem> data = new HashMap<String, FansGroupItem>();

    public List<FansGroupItem> getData() {
        ArrayList<FansGroupItem> items = new ArrayList<FansGroupItem>();
        for (FansGroupItem item : data.values()) {
            items.add(item);
        }
        return items;
    }

    public void add(FansGroupItem item) {
        add(item, false);
    }

    public void add(FansGroupItem item, boolean notify) {
        data.put(item.getTid(), item);
        if (notify) {
            notifyDataSetChanged();
        }
        if(TextUtils.isEmpty(currentFanGroupId))
        {
            currentFanGroupId = item.getTid();
        }
    }

    public void clear() {
        data.clear();
    }

    public String getCurrentFanGroupId() {
        return currentFanGroupId;
    }

    public FansGroupItem getCurrentFansGroupItem() {
        if (currentFanGroupId == null) {
            return null;
        }
        return data.get(currentFanGroupId);
    }

    public void setCurrentFanGroupId(String currentFanGroupId) {
        this.currentFanGroupId = currentFanGroupId;
    }

    private String currentFanGroupId;

}
