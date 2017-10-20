package com.android.expandview.bean;

import android.support.annotation.NonNull;

import com.android.expandview.provider.biz.DateFomator;

/**
 * @author xieyi
 * @ClassName AppStoreEntity
 * @Description 记录应用游戏市场的实体
 * @date 2014-9-24 下午4:42:13
 */
public class AppStoreEntity implements Comparable<AppStoreEntity> {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String appName;// 应用名称
    private String iconUri;// 应用图标的uri
    private int iconId = -1;// 如果是外接存储应用，那么此项有值
    private String packageName;// 包名
    private Long launchTime;// 应用启动时间
    private String formatTime;// 按yyyy-MM-dd格式化的时间

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Long launchTime) {
        this.launchTime = launchTime;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(long formatTime) {
        this.formatTime = DateFomator.getCurrentDate(formatTime);
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    @Override
    public String toString() {
        return "AppStoreEntity{" +
                "appName='" + appName + '\'' +
                ", iconUri='" + iconUri + '\'' +
                ", iconId=" + iconId +
                ", packageName='" + packageName + '\'' +
                ", launchTime=" + launchTime +
                ", formatTime='" + formatTime + '\'' +
                '}';
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
    public int compareTo(@NonNull AppStoreEntity o) {
        return o.getLaunchTime().compareTo(this.getLaunchTime());
    }
}
