package com.msjf.fentuan.fendouquan;

import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class FenDouQuanData extends Observable {
    private static final long serialVersionUID = 1L;
    private Map<String, CircleInfo> mIndexs = new HashMap<>();
    private List<CircleInfo> mDataItems = new ArrayList<>();
    public static int NUM_PER_PAGE = 20;

    private FenDouQuanData() {
    }


    public void add(CircleInfo item) {
        add(mDataItems.size(), item, true);
    }




    public void add(int index, CircleInfo item) {
        add(index, item, true);
    }

    public void notifyDataSetChanged() {
        setChanged();
        notifyObservers();
    }
    public void addAll(List<CircleInfo> infos, boolean fromTop, boolean notify)
    {
        ArrayList<CircleInfo> dest = new ArrayList<>();
        for(CircleInfo info : infos)
        {
            if(!mIndexs.containsKey(info.getTid()))
            {
                dest.add(info);
            }
        }
        for(int i = 0; i < dest.size(); ++i)
        {
            add(i, dest.get(i), false);
        }

        if (notify) {
            setChanged();
            notifyObservers();
        }
    }
    public void add(CircleInfo item, boolean notify)
    {
        add(mDataItems.size(), item, notify);
    }

    public void add(int index, CircleInfo item, boolean notify) {
        String tid = item.getTid();
        if(mIndexs.containsKey(tid))
        {
            return;
        }
        mIndexs.put(item.getTid(), item);
        mDataItems.add(index, item);
        if (notify) {
            setChanged();
            notifyObservers();
        }
    }

    public List<CircleInfo> getData() {
        return mDataItems;
    }

    public int size() {
        return mDataItems.size();
    }

    public CircleInfo get(int index) {
        return mDataItems.get(index);
    }
}
