package com.gogh.okrxretrofit.conf;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 目前作为http请求的header属性封装使用</p>
 * <p> Created by <b>高晓峰</b> on 03/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 03/28/2017 do fisrt create. </li>
 */
public class Property implements Serializable {

    private static final long serialVersionUID = 113232502559493323L;

    private String key;
    private String value;

    public Property() {
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "Property{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
