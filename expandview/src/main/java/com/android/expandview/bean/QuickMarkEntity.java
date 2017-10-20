package com.android.expandview.bean;

/**
 * Created by wangbei on 2016/3/18.
 */
public class QuickMarkEntity {
    public String msgType; //消息类型。
    private String channelId;  //百度推送参数之一
    private String baiduUserId;  //百度推送参数之一
    public String openId; //用户openId
    public String userId;//用户名
    private String action;  //动作
    private String contentId;  //惟一确定一个片源
    public String userPhone; //手机号
    private String password; //密码
    private Integer proPkgId; //产品包ID
    private String result; //客户端返回的操作结果
    private String verifCode; //验证码
    private Integer userStatus;//0 未登录 1.正式用户 2.游客
    private String imageURL;//生成的二维码地址
    private String headImgUrl;//微信头像
    private String nickName;//微信昵称
    private String mac;
    private String sn;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getProPkgId() {
        return proPkgId;
    }

    public void setProPkgId(Integer proPkgId) {
        this.proPkgId = proPkgId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "QuickMarkEntity{" +
                "msgType=" + msgType +
                ", channelId='" + channelId + '\'' +
                ", baiduUserId='" + baiduUserId + '\'' +
                ", openId='" + openId + '\'' +
                ", userId='" + userId + '\'' +
                ", action='" + action + '\'' +
                ", contentId='" + contentId + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", password='" + password + '\'' +
                ", proPkgId=" + proPkgId +
                ", result='" + result + '\'' +
                ", verifCode='" + verifCode + '\'' +
                ", userStatus=" + userStatus +
                ", imageURL='" + imageURL + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mac='" + mac + '\'' +
                ", sn='" + sn + '\'' +
                '}';
    }
}
