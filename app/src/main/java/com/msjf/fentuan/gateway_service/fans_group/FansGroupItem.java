package com.msjf.fentuan.gateway_service.fans_group;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wangli on 15-6-14.
 */
public class FansGroupItem {
    private String startId;
    private String title;
    private String starName;
    private boolean deleteFlag;
    private String userId;
    private String remark;
    private String tid;
    private long createTime;
    private String fgSummary;
    private boolean focusFlag;
    private int focusCount;

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFgSummary() {
        return fgSummary;
    }

    public void setFgSummary(String fgSummary) {
        this.fgSummary = fgSummary;
    }

    public boolean isFocusFlag() {
        return focusFlag;
    }

    public void setFocusFlag(boolean focusFlag) {
        this.focusFlag = focusFlag;
    }

    public int getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(int focusCount) {
        this.focusCount = focusCount;
    }

    public static FansGroupItem fromJson(JSONObject json)
    {
        FansGroupItem item = new FansGroupItem();
        item.setStartId(json.getString("starId"));
        item.setTitle(json.getString("title"));
        item.setStarName(json.getString("starName"));
        item.setDeleteFlag(json.getBoolean("deleteFlag"));
        item.setUserId(json.getString("userId"));
        item.setRemark(json.getString("remark"));
        item.setTid(json.getString("tid"));
        item.setCreateTime(json.getLongValue("createTime"));
        item.setFgSummary(json.getString("fgSummary"));
        item.setFocusFlag(json.getIntValue("focusFlag") == 0);
        item.setFocusCount(json.getIntValue("focusCount"));
        return item;
    }
}
