package com.android.expandview.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 7/19/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 7/19/2017 do fisrt create. </li>
 */

public class AppInfoEntity implements Serializable {

    private static final long serialVersionUID = -7950746480150900983L;

    private int versionCode;

    private String appName;

    /**
     * action or package name
     */
    private String startOrder;

    private String onlineIcon;

    private String versionName;

    private String packageName;

    private boolean isSystemApp;

    private Drawable iconDrawable;

    public AppInfoEntity() {
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(String startOrder) {
        this.startOrder = startOrder;
    }

    public String getOnlineIcon() {
        return onlineIcon;
    }

    public void setOnlineIcon(String onlineIcon) {
        this.onlineIcon = onlineIcon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfoEntity{" +
                "versionCode=" + versionCode +
                ", appName='" + appName + '\'' +
                ", startOrder='" + startOrder + '\'' +
                ", onlineIcon='" + onlineIcon + '\'' +
                ", versionName='" + versionName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", isSystemApp=" + isSystemApp +
                ", iconDrawable=" + iconDrawable +
                '}';
    }

}
