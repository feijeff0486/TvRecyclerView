package com.android.expandview.bean;


import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * @author xieyi
 * @ClassName CollectEntity
 * @Description 收藏实体
 * @date 2014-9-12 上午9:54:40
 */
public class CollectEntity implements Serializable{

    public static final int ACTION_TYPE = 0;// 标识请求极清时的类型
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private int cid;// 专辑频道（电影、电视剧）
    private String name;// 专辑名称
    private int episodeUpdate;// 当前更新到的集数
    private int collectId;// 点播专辑唯一标识
    private int episodeTotal;// 点播专辑总集数
    private int cpId;// 0:自有内容 1:奇艺
    private long collectTime;// 收藏时间
    private String albumId; // 存放极清的专辑ID
    private String cpName;// 存放极清的厅名称
    private int source;// 标识是点播的数据还是极清的 0:点播 1:极清 2:教育
    private int updateType;// 如果是电视剧的话标识更新状态（已更新完、正在更新）
    private String picUrl;// 图片路径
    private String blueRayImg;
    private String cornerPic;

    public String getCornerPic() {
        return cornerPic;
    }

    public void setCornerPic(String cornerPic) {
        this.cornerPic = cornerPic;
    }

    public String getBlueRayImg() {
        return blueRayImg;
    }

    public void setBlueRayImg(String blueRayImg) {
        this.blueRayImg = blueRayImg;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpisodeUpdate() {
        return episodeUpdate;
    }

    public void setEpisodeUpdate(Integer episodeUpdate) {
        this.episodeUpdate = episodeUpdate;
    }

    public String getSimpleVideoset_update() {
        return episodeUpdate == 0 ? null : (String.format("更新至第%s集" + episodeUpdate));
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getEpisodeTotal() {
        return episodeTotal;
    }

    public void setEpisodeTotal(Integer episodeTotal) {
        this.episodeTotal = episodeTotal;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public String getSimpleCollectTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String newTime = format.format(collectTime);
        return "收藏时间 " + newTime;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "CollectEntity{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", episodeUpdate=" + episodeUpdate +
                ", collectId=" + collectId +
                ", episodeTotal=" + episodeTotal +
                ", cpId=" + cpId +
                ", collectTime='" + collectTime + '\'' +
                ", albumId='" + albumId + '\'' +
                ", cpName='" + cpName + '\'' +
                ", source=" + source +
                ", updateType=" + updateType +
                ", picUrl='" + picUrl + '\'' +
                ", blueRayImg='" + blueRayImg + '\'' +
                ", cornerPic='" + cornerPic + '\'' +
                '}';
    }
}
