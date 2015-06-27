package com.msjf.fentuan.gateway_service.fendouquan;

/**
 * 粉逗圈赞实体类
 * @ClassName: CirclePraise   
 * @Description: TODO  
 * @author: 唐建飞  
 * @date:2015年5月23日 下午4:55:18
 */
public class CirclePraise {
    /**
     * 主键id
     */
    private String tid;

    /**
     * 粉逗圈消息id
     */
    private String circleId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 有效标识(0:有效,1:无效)
     */
    private Integer validFlag;

    /**
     * 创建时间
     */
    private Long createTime;

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
     * 获取 用户id
     *
     * @return 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置  用户id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 有效标识(0:有效,1:无效)
     *
     * @return 
     */
    public Integer getValidFlag() {
        return validFlag;
    }

    /**
     * 设置  有效标识(0:有效,1:无效)
     *
     * @param validFlag
     */
    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
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
}