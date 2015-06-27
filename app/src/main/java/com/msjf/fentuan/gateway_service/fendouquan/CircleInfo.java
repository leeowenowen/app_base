package com.msjf.fentuan.gateway_service.fendouquan;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.app.common.Downloadable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 粉逗圈消息实体类
 *
 * @ClassName: CircleInfo
 * @Description: TODO
 * @author: 唐建飞
 * @date:2015年5月23日 下午4:54:42
 */
public class CircleInfo {

    public static class Photo extends Downloadable {
        private Bitmap mBmp;

        public void setBmp(Bitmap bmp) {
            mBmp = bmp;
        }

        public Bitmap getBmp() {
            return mBmp;
        }
    }

    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap bmp) {
        photo.setBmp(bmp);
    }

    /**
     * ***********查询用字段(非表自身字段) begin*************
     */
    //用户昵称
    private String userName;

    //用户头像key
    private String userAvatar;

    //粉逗圈图片集合
    private List<CircleImage> circleImageList;

    //粉逗圈回复集合
    private List<CircleReply> circleReplyList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public List<CircleImage> getCircleImageList() {
        return circleImageList;
    }

    public void setCircleImageList(List<CircleImage> circleImageList) {
        this.circleImageList = circleImageList;
    }

    public List<CircleReply> getCircleReplyList() {
        return circleReplyList;
    }

    public void setCircleReplyList(List<CircleReply> circleReplyList) {
        this.circleReplyList = circleReplyList;
    }

    /**************查询用字段(非表自身字段) end**************/

    /**
     * 主键id
     */
    private String tid;

    /**
     * 发布人id
     */
    private String userId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 地图坐标X轴
     */
    private BigDecimal mapX;

    /**
     * 地图坐标Y轴
     */
    private BigDecimal mapY;

    /**
     * 浏览次数
     */
    private Integer browseCount;

    /**
     * 点赞次数
     */
    private Integer praiseCount;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 删除标识(0:未删除.1:已删除)
     */
    private Integer deleteFlag;

    /**
     * 回复次数
     */
    private Integer replyCount;

    /**
     * 匿名爆料标识(0:是,1:否)
     */
    private Integer anonymousFlag;

    /**
     * 获取 主键id
     *
     * @return
     */
    public String getTid() {
        return tid;
    }

    /**
     * 设置  主键id
     *
     * @param tid
     */
    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    /**
     * 获取 发布人id
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置  发布人id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 消息内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置  消息内容
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取 地图坐标X轴
     *
     * @return
     */
    public BigDecimal getMapX() {
        return mapX;
    }

    /**
     * 设置  地图坐标X轴
     *
     * @param mapX
     */
    public void setMapX(BigDecimal mapX) {
        this.mapX = mapX;
    }

    /**
     * 获取 地图坐标Y轴
     *
     * @return
     */
    public BigDecimal getMapY() {
        return mapY;
    }

    /**
     * 设置  地图坐标Y轴
     *
     * @param mapY
     */
    public void setMapY(BigDecimal mapY) {
        this.mapY = mapY;
    }

    /**
     * 获取 浏览次数
     *
     * @return
     */
    public Integer getBrowseCount() {
        return browseCount;
    }

    /**
     * 设置  浏览次数
     *
     * @param browseCount
     */
    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    /**
     * 获取 点赞次数
     *
     * @return
     */
    public Integer getPraiseCount() {
        return praiseCount;
    }

    /**
     * 设置  点赞次数
     *
     * @param praiseCount
     */
    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    /**
     * 获取 创建时间
     *
     * @return
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置  创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 删除标识(0:未删除.1:已删除)
     *
     * @return
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置  删除标识(0:未删除.1:已删除)
     *
     * @param deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取 回复次数
     *
     * @return
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * 设置  回复次数
     *
     * @param replyCount
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * 获取 匿名爆料标识(0:是,1:否)
     *
     * @return
     */
    public Integer getAnonymousFlag() {
        return anonymousFlag;
    }

    public boolean isMeBrowsed;
    public boolean isMeZaned;

    /**
     * 设置  匿名爆料标识(0:是,1:否)
     *
     * @param anonymousFlag
     */
    public void setAnonymousFlag(Integer anonymousFlag) {
        this.anonymousFlag = anonymousFlag;
    }

    public static CircleInfo fromJson(JSONObject json) {
        CircleInfo info = new CircleInfo();
        info.setUserAvatar(json.getString("userAvatar"));
        info.setReplyCount(json.getIntValue("replyCount"));
        info.setDeleteFlag(json.getIntValue("deleteFlag"));
        info.setUserId(json.getString("userId"));
        info.setMapX(json.getBigDecimal("mapX"));
        info.setMapY(json.getBigDecimal("mapY"));
        com.alibaba.fastjson.JSONArray images = json.getJSONArray("circleImageList");
        List<CircleImage> circleImageList = new ArrayList<>();
        for (int i = 0; i < images.size(); ++i) {
            JSONObject obj = images.getJSONObject(i);
            CircleImage image = CircleImage.fromJson(obj);
            circleImageList.add(image);
        }
        info.setCircleImageList(circleImageList);

        com.alibaba.fastjson.JSONArray replies = json.getJSONArray("circleReplyList");
        List<CircleReply> circleReplyList = new ArrayList<>();
        for (int i = 0; i < replies.size(); ++i) {
            JSONObject obj = replies.getJSONObject(i);
            CircleReply reply = CircleReply.fromJson(obj);
            circleReplyList.add(reply);
        }
        info.setCircleReplyList(circleReplyList);

        info.setBrowseCount(json.getIntValue("browseCount"));
        info.setContent(json.getString("content"));
        info.setTid(json.getString("tid"));
        info.setCreateTime(json.getLongValue("createTime"));
        info.setAnonymousFlag(json.getIntValue("anonymousFlag"));
        info.setUserName(json.getString("userName"));
        info.setPraiseCount(json.getIntValue("praiseCount"));


        return info;
    }
}