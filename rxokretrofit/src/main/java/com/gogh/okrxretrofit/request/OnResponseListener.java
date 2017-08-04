package com.gogh.okrxretrofit.request;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 3/29/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/29/2017 do fisrt create. </li>
 */

public interface OnResponseListener<T> {

    /**
     * 数据请求成功且解析完成
     */
    void onCompleted();

    /**
     * 请求接口异常或解析异常
     * @param e
     */
    void onError(Throwable e);

    /**
     * 数据解析完成
     * @param t
     */
    void onNext(T t);

}
