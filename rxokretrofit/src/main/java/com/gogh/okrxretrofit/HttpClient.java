package com.gogh.okrxretrofit;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 用于提供接口服务</p>
 * <p> Created by <b>高晓峰</b> on 3/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/28/2017 do fisrt create. </li>
 */
public class HttpClient {

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Retrofit retrofit;

    private HttpClient() {
    }

    @NonNull
    public static HttpClient newInstance() {
        return SingleHolder.TASK;
    }

    public <T> T getRequestApi(Class<T> service) {
        return retrofit.create(service);
    }

    private static final class SingleHolder {
        private static final HttpClient TASK = new HttpClient();
    }

}
