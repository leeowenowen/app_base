package com.msjf.fentuan.me.message;

import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.owo.base.pattern.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;


public class MessageData extends Observable {
    private static final long serialVersionUID = 1L;
    private Map<String, MessageDataItem> mIndexs = new HashMap<>();
    private List<MessageDataItem> mDataItems = new ArrayList<>();
    public static int NUM_PER_PAGE = 50;

    private MessageData() {
    }

    public void clear() {
        mIndexs.clear();
        mDataItems.clear();
    }


    public void add(MessageDataItem item) {
        add(mDataItems.size(), item, true);
    }


    public void add(int index, MessageDataItem item) {
        add(index, item, true);
    }

    public void notifyDataSetChanged() {
        setChanged();
        notifyObservers();
    }

    public void addAll(List<MessageDataItem> infos, boolean fromTop, boolean notify) {
        ArrayList<MessageDataItem> dest = new ArrayList<>();
        for (MessageDataItem info : infos) {
            if (!mIndexs.containsKey(info.getId())) {
                dest.add(info);
            }
        }
        for (int i = 0; i < dest.size(); ++i) {
            add(i, dest.get(i), false);
        }

        if (notify) {
            setChanged();
            notifyObservers();
        }
    }

    public void add(MessageDataItem item, boolean notify) {
        add(mDataItems.size(), item, notify);
    }

    public void add(int index, MessageDataItem item, boolean notify) {
        String tid = item.getId();
        if (mIndexs.containsKey(tid)) {
            return;
        }
        mIndexs.put(item.getId(), item);
        mDataItems.add(index, item);
        if (notify) {
            setChanged();
            notifyObservers();
        }
    }

    public List<MessageDataItem> getData() {
        return mDataItems;
    }

    public int size() {
        return mDataItems.size();
    }

    public MessageDataItem get(int index) {
        return mDataItems.get(index);
    }
}
