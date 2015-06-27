package com.msjf.fentuan.app.main;

import com.owo.app.theme.BitmapId;
import com.owo.base.pattern.Singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangli on 15-6-24.
 */
public class StarData {

    public static StarData instance() {
        return Singleton.of(StarData.class);
    }

    private Map<String, BitmapId> mStars = new HashMap<String, BitmapId>();

    private StarData() {
        add("邓紫棋", BitmapId.star_dengziqi);
        add("宋智孝", BitmapId.start_songzhixiao);
        add("吴亦凡", BitmapId.star_wuyifan);
        add("朴信惠", BitmapId.star_puxinhui);
        add("周杰伦", BitmapId.star_zhoujielun);
        add("李敏镐", BitmapId.star_liminhao);
        add("李准基", BitmapId.star_lizhunji);
        add("鹿晗", BitmapId.star_luhan);
        add("张根硕", BitmapId.star_zhanggenshuo);
        add("朴有天", BitmapId.star_puyoutian);
        add("李钟硕", BitmapId.star_lizhongshuo);
        add("bigbang", BitmapId.star_bigbang);
        add("张艺兴", BitmapId.star_zhangyixing);
        add("宋承宪", BitmapId.star_songchegnxian);
        add("宋慧乔", BitmapId.star_songhuiqiao);
        add("李易峰", BitmapId.star_liyifeng);
    }

    private void add(String name, BitmapId id) {
        mStars.put(name, id);
    }

    public BitmapId id(String name) {
        return mStars.get(name);
    }
}
