package com.msjf.fentuan.gateway_service;

import com.alibaba.fastjson.JSONObject;
import com.owo.base.pattern.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

/**
 * Created by wangli on 15-6-14.
 */
public class ShuoBaData extends Observable {
    private ShuoBaData() {
    }

    public static ShuoBaData instance() {
        return Singleton.of(ShuoBaData.class);
    }

    public void notifyDataSetChanged() {
        setChanged();
        notifyObservers();
    }

    public static class ShuoBaItem {
        public String userId;
        public String content;
        public double longitude;
        public double latitude;
        public double distance;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static ShuoBaItem fromJson(JSONObject json) {
            ShuoBaItem item = new ShuoBaItem();
            item.setUserId(json.getString("userId"));
            item.setContent(json.getString("content"));
            item.setLongitude(json.getDoubleValue("longitude"));
            item.setLatitude(json.getDouble("latitude"));
            item.setDistance(json.getDouble("distance"));
            return item;
        }
    }

    private Map<String, ShuoBaItem> data = new HashMap<String, ShuoBaItem>();

    public Collection<ShuoBaItem> getData() {
        return data.values();
    }

    public void add(ShuoBaItem item) {
        add(item, false);
    }

    public void add(ShuoBaItem item, boolean notify) {
        data.put(item.getUserId(), item);
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
    }
}
