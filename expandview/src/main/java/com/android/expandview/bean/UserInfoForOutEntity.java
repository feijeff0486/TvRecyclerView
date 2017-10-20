/**
 * @Title: UserInfoForOutEntity.java
 * @Package com.hiveview.cloudscreen.user.entity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoman
 * @date 2016-2-1 下午3:55:36
 * @version V1.0
 */
package com.android.expandview.bean;

/**
 * @author wangbei
 * @ClassName: UserInfoForOutEntity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-3-10 下午3:55:36
 */
public class UserInfoForOutEntity {
    private String loginState;
    private String id;
    private String userAccount;
    private String accessToken;
    private String refreshToken;
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String isVip;
    private String hasFreePackage;
    private String expiredDate;
    private String userPhone;
    // vip用户获取时间
    private String vipUpdateTime;
    private String vipCreateTime;
    //生成绑定二维码所需的 此参数从 推送apk获取
    private String channelId;
    //生成绑定二维码所需的 此参数从 推送apk获取
    private String baiduUserId;

    private String hasOpenId;

    public String getHasOpenId() {
        return hasOpenId;
    }

    public void setHasOpenId(String hasOpenId) {
        this.hasOpenId = hasOpenId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBaiduUserId() {
        return baiduUserId;
    }

    public void setBaiduUserId(String baiduUserId) {
        this.baiduUserId = baiduUserId;
    }

    public String getVipUpdateTime() {
        return vipUpdateTime;
    }

    public void setVipUpdateTime(String vipUpdateTime) {
        this.vipUpdateTime = vipUpdateTime;
    }

    public String getVipCreateTime() {
        return vipCreateTime;
    }

    public void setVipCreateTime(String vipCreateTime) {
        this.vipCreateTime = vipCreateTime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getHasFreePackage() {
        return hasFreePackage;
    }

    public void setHasFreePackage(String hasFreePackage) {
        this.hasFreePackage = hasFreePackage;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public String toString() {
        return "UserInfoForOutEntity{" +
                "loginState='" + loginState + '\'' +
                ", id='" + id + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", isVip='" + isVip + '\'' +
                ", hasFreePackage='" + hasFreePackage + '\'' +
                ", expiredDate='" + expiredDate + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", vipUpdateTime='" + vipUpdateTime + '\'' +
                ", vipCreateTime='" + vipCreateTime + '\'' +
                ", channelId='" + channelId + '\'' +
                ", baiduUserId='" + baiduUserId + '\'' +
                ", hasOpenId='" + hasOpenId + '\'' +
                '}';
    }
}
