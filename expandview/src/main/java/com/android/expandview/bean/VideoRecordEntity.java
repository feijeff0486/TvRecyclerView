package com.android.expandview.bean;

import android.support.annotation.NonNull;

import com.android.expandview.provider.biz.DateFomator;

public class VideoRecordEntity implements Comparable<VideoRecordEntity> {

    private static final long serialVersionUID = 1L;

    private int source;// 标识来源 0:点播 1:极清 2:教育
    private String albumId;// 极清的专辑Id
    private String picUrl;// 图片路径
    private String movieName;// 影片名称
    private Long recordTime;// 播放时的时间
    private String formatTime;// 按yyyy-MM-dd格式化的时间
    private Integer videoset_type;// 专辑频道（电影、电视剧）
    private String vrsAlbumId;
    private String vrsTvId;
    private Integer cpId;
    private int videoset_id;
    private String currentEpisode;

    public String getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(String currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public int getVideoset_id() {
        return videoset_id;
    }

    public void setVideoset_id(int videoset_id) {
        this.videoset_id = videoset_id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = Long.valueOf(recordTime);
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = DateFomator.getCurrentDate(Long
                .valueOf(formatTime));
    }

    public void setFormatTime(long formatTime) {
        this.formatTime = DateFomator.getCurrentDate(formatTime);
    }

    public Integer getVideoset_type() {
        return videoset_type;
    }

    public void setVideoset_type(Integer videoset_type) {
        this.videoset_type = videoset_type;
    }

    public String getVrsAlbumId() {
        return vrsAlbumId;
    }

    public void setVrsAlbumId(String vrsAlbumId) {
        this.vrsAlbumId = vrsAlbumId;
    }

    public String getVrsTvId() {
        return vrsTvId;
    }

    public void setVrsTvId(String vrsTvId) {
        this.vrsTvId = vrsTvId;
    }

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId = cpId;
    }

    @Override
    public String toString() {
        return "VideoRecordEntity [source=" + source + ", albumId=" + albumId + ", picUrl=" + picUrl + ", movieName=" + movieName + ", recordTime="
                + recordTime + ", formatTime=" + formatTime + ", videoset_type=" + videoset_type + ", vrsAlbumId=" + vrsAlbumId + ", vrsTvId="
                + vrsTvId + ", cpId=" + cpId + "]";
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NonNull VideoRecordEntity o) {
        return o.getRecordTime().compareTo(this.getRecordTime());
    }
}
