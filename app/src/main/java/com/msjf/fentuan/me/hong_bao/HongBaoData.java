package com.msjf.fentuan.me.hong_bao;

import com.alibaba.fastjson.JSONObject;
import com.owo.base.pattern.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by wangli on 15-6-17.
 */

public class HongBaoData extends Observable {

    public static class HongBaoDataItem
    {
        private String tid;
        private String amount;
        private long timestamp;
        private String id;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public static HongBaoDataItem fromJson(JSONObject json)
        {
            HongBaoDataItem item = new HongBaoDataItem();
            item.setId(json.getString("redEnvelopeId"));
            item.setAmount(json.getString("money"));
            item.setTimestamp(json.getLongValue("getTime"));
            return item;
        }
    }

    private HongBaoData() {
    }

    public static HongBaoData instance() {
        return Singleton.of(HongBaoData.class);
    }

    public void notifyDataSetChanged() {
        setChanged();
        notifyObservers();
    }

    private Map<String, HongBaoDataItem> data = new HashMap<String, HongBaoDataItem>();

    public List<HongBaoDataItem> getData() {
        ArrayList<HongBaoDataItem> items = new ArrayList<HongBaoDataItem>();
        for (HongBaoDataItem item : data.values()) {
            items.add(item);
        }
        return items;
    }

    public void add(HongBaoDataItem item) {
        add(item, false);
    }

    public void add(HongBaoDataItem item, boolean notify) {
        data.put(item.getId(), item);
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
    }
}