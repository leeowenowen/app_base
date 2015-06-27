package com.msjf.fentuan.user;

import java.math.BigDecimal;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.app.common.Downloadable;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;

public class User extends Downloadable {
    public String id;
    private String phone;
    private String userName;//昵称(粉团名)
    private String starName;
    private Sex sex;
    private String cityId;
    private String cityName;
    private long createTime;
    private long updateTime;
    public String avatar;
    private RegisterSource registerSource;
    private BigDecimal mapX;
    private BigDecimal mapY;
    private long coordinatesUpdateTime;
    private String remark;
    //
    private String userCode;//账号(粉团号)
    private long lastLoginTime;//最后登录时间
    private CircleInfo.Photo photo = new CircleInfo.Photo();

    public MobUser getMobUser() {
        return mobUser;
    }

    public void setMobUser(MobUser mobUser) {
        this.mobUser = mobUser;
    }

    private MobUser mobUser;

    public String getShenFenString() {
        switch (userLevel) {
            case 0:
                return "普通粉丝";
            case 1:
                return "粉丝主管";
            case 2:
                return "粉团首席星闻官";
            case 3:
                return "粉团首席星话主持人";
            default:
                break;

        }
        return "";
    }

    public String getConsumeLevelString() {
        int consume = consumeAmount.intValue();
        if(consume > 10000.0)
        {
            return "土豪粉";
        }
        else if(consume > 300 && consume <= 10000)
        {
            return "尊贵粉";
        }
        else if ( consume > 200 && consume <= 300)
        {
            return "普通粉";
        }
        else if( consume > 100 && consume <= 200)
        {
            return "屌丝粉";
        }
        return "没粉";
    }

    /**
     * 用户余额
     */
    private BigDecimal balance;

    /**
     * 用户消费金额
     */
    private BigDecimal consumeAmount;


    /**
     * 用户等级(0 普通粉丝 1 粉丝主管 2 粉团首席星闻官 3 粉团首席星话主持人)
     */
    private Integer userLevel;

    /**
     * 用户类型(0:普通用户,1:系统用户)
     */
    private Integer userType;

    /**
     * 粉友数
     */
    private Integer fanFriendsCount;

    /**
     * 关注用户数
     */
    private Integer focusUserCount;

    /**
     * 关注粉团数
     */
    private Integer focusFanGroupCount;

    /**
     * 删除标识(0:未删除.1:已删除)
     */
    private Integer deleteFlag;

    /**
     * 注册系统(0:Android,1:IOS)
     */
    private Integer registerSystem;

    //头像Url
    private String avatarUrl;

    public String getAvatarThumbnail() {
        return avatarThumbnail;
    }

    public void setAvatarThumbnail(String avatarThumbnail) {
        this.avatarThumbnail = avatarThumbnail;
    }

    //用户头次是哪个缩略图
    private String avatarThumbnail;


    //关注用户id(判断该用户是否已关注该用户) (使用/v1/user/get_user_info时，可传递focusId(当前登录人id)，来查看当前登陆人是否关注此用户)
    private String focusId;


    //关注标识(0:已关注,1:未关注)
    private Integer focusFlag;

    //
    public CircleInfo.Photo getPhoto() {
        return photo;
    }


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getFanFriendsCount() {
        return fanFriendsCount;
    }

    public void setFanFriendsCount(Integer fanFriendsCount) {
        this.fanFriendsCount = fanFriendsCount;
    }

    public Integer getFocusUserCount() {
        return focusUserCount;
    }

    public void setFocusUserCount(Integer focusUserCount) {
        this.focusUserCount = focusUserCount;
    }

    public Integer getFocusFanGroupCount() {
        return focusFanGroupCount  == null ? 0 : focusFanGroupCount;
    }

    public void setFocusFanGroupCount(Integer focusFanGroupCount) {
        this.focusFanGroupCount = focusFanGroupCount;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getRegisterSystem() {
        return registerSystem;
    }

    public void setRegisterSystem(Integer registerSystem) {
        this.registerSystem = registerSystem;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    //0已关注
    public boolean  isFocused() {
        return focusFlag == 0;
    }

    public void setFocusFlag(Integer focusFlag) {
        this.focusFlag = focusFlag;
    }

    public static void parserJson(User user, JSONObject json) {
        user.setId(json.getString("tid"));
        user.setPhone(json.getString("phone"));
        user.setUserName(json.getString("userName"));
        user.setUserCode(json.getString("userCode"));
        user.setStarName(json.getString("starName"));
        user.setSex(json.getIntValue("sex") == 0 ? Sex.Male : Sex.Female);
        user.setCityId(json.getString("cityId"));
        user.setCityName(json.getString("cityName"));
        user.setCreateTime(json.getLongValue("createTime"));
        user.setUpdateTime(json.getLongValue("updateTime"));
        user.setLastLoginTime(json.getLongValue("lastLoginTime"));
        String avatar = json.getString("avatar");
        user.setAvatar(avatar);
        user.setRegisterSource(RegisterSource.fromIndex(json.getIntValue("registerSource")));
        user.setMapX(new BigDecimal(json.getDoubleValue("mapX")));
        user.setMapY(new BigDecimal(json.getDoubleValue("mapY")));
        user.setCoordinatesUpdateTime(json.getLongValue("coordinatesUpdateTime"));
        user.setRemark(json.getString("remark"));
        user.setBalance(new BigDecimal(json.getDoubleValue("balance")));
        user.setConsumeAmount(new BigDecimal(json.getDoubleValue("consumeAmount")));
        user.setUserLevel(json.getIntValue("userLevel"));
        user.setFanFriendsCount(json.getIntValue("fanFriendsCount"));
        user.setFocusUserCount(json.getIntValue("focusUserCount"));
        user.setDeleteFlag(json.getIntValue("deleteFlag"));
        user.setRegisterSystem(json.getIntValue("registerSystem"));
        user.setAvatarUrl(json.getString("avatarUrl"));
        user.setFocusId(json.getString("focusId"));
        user.setFocusFlag(json.getIntValue("focusFlag"));
        user.setAvatarThumbnail(json.getString("avatarThumbnail"));
        Log.v("ParseUser", "[id:" + user.getId() + "][focused:" + user.focusFlag + "][name:" + user.userName + "]");

    }

    public static User fromJson(JSONObject json) {
        User user = new User();
        parserJson(user, json);
        user.setDownloadState(DownloadState.Downloaded);
        return user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public RegisterSource getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(RegisterSource registerSource) {
        this.registerSource = registerSource;
    }

    public BigDecimal getMapX() {
        return mapX;
    }

    public void setMapX(BigDecimal mapX) {
        this.mapX = mapX;
    }

    public BigDecimal getMapY() {
        return mapY;
    }

    public void setMapY(BigDecimal mapY) {
        this.mapY = mapY;
    }

    public long getCoordinatesUpdateTime() {
        return coordinatesUpdateTime;
    }

    public void setCoordinatesUpdateTime(long coordinatesUpdateTime) {
        this.coordinatesUpdateTime = coordinatesUpdateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
