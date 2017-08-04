package com.gogh.okrxretrofit.module;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 相关网络请求实组件</p>
 * <p> Created by <b>高晓峰</b> on 3/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/28/2017 do fisrt create. </li>
 */
public interface Module {

    /**
     * 设置域名或IP+端口
     *
     * @return
     */
    String provideBaseUrl();

    /**
     * 提供OkHttpClient
     *
     * @return 自定义OkHttpClient
     */
    OkHttpClient provideOkHttpClient();

    /**
     * 设置解析器
     *
     * @return
     */
    Converter.Factory provideFactory();

    /**
     * 提供Retrofit
     *
     * @param okhttpClient   自定义OkHttpClient
     * @param baseUrl
     * @param convertFactory
     * @return
     */
    Retrofit provideRetrofit(OkHttpClient okhttpClient, @NonNull String baseUrl, @NonNull Converter.Factory convertFactory);

}
