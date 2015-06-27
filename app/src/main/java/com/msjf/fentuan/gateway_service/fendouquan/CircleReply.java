package com.msjf.fentuan.gateway_service.fendouquan;

import com.alibaba.fastjson.JSONObject;

/**
 * 粉逗圈回复实体类
 * @ClassName: CircleReply   
 * @Description: TODO  
 * @author: 唐建飞  
 * @date:2015年5月23日 下午4:53:05
 */
public class CircleReply {
    /**
     * 主键id
     */
    private String tid;

    /**
     * 粉逗圈消息id
     */
    private String circleId;

    /**
     * 回复人id
     */
    private String userId;

    /**
     * 回复id(回复粉逗圈回复的id)
     */
    private String replyId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 删除标识(0:未删除.1:已删除)
     */
    private Integer deleteFlag;

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
     * 获取 粉逗圈消息id
     *
     * @return 
     */
    public String getCircleId() {
        return circleId;
    }

    /**
     * 设置  粉逗圈消息id
     *
     * @param circleId
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    /**
     * 获取 回复人id
     *
     * @return 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置  回复人id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 回复id(回复粉逗圈回复的id)
     *
     * @return 
     */
    public String getReplyId() {
        return replyId;
    }

    /**
     * 设置  回复id(回复粉逗圈回复的id)
     *
     * @param replyId
     */
    public void setReplyId(String replyId) {
        this.replyId = replyId == null ? null : replyId.trim();
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

    public static CircleReply fromJson(JSONObject json)
    {
        CircleReply reply = new CircleReply();
        reply.setReplyId(json.getString("replyId"));
        reply.setDeleteFlag(json.getIntValue("deleteFlag"));
        reply.setUserId(json.getString("userId"));
        reply.setCircleId(json.getString("circleId"));
        reply.setTid(json.getString("tid"));
        reply.setCreateTime(json.getLongValue("createTime"));
        reply.setContent(json.getString("content"));
        return reply;
    }
}