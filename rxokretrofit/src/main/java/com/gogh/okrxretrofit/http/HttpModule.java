package com.gogh.okrxretrofit.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.okrxretrofit.conf.Config;
import com.gogh.okrxretrofit.conf.Property;
import com.gogh.okrxretrofit.conf.TimeOut;
import com.gogh.okrxretrofit.module.Module;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 相关网络请求组件</p>
 * <p> Created by <b>高晓峰</b> on 3/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/28/2017 do fisrt create. </li>
 */
@dagger.Module
public class HttpModule implements Module {

    /**
     * domain end with "/"
     */
    private String baseUrl;

    private TimeOut timeOut;
    /**
     * data parser factory
     */
    private Converter.Factory convertFactory;

    /**
     * http header
     */
    private List<Property> properties;

    /**
     * provide Retrofit
     *
     * @param builder
     */
    public HttpModule(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.timeOut = builder.timeOut;
        this.properties = builder.properties;
        this.convertFactory = builder.convertFactory;
    }

    /**
     * 设置域名或IP+端口
     *
     * @return
     */
    @Provides
    @Singleton
    @Override
    public String provideBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置解析器
     *
     * @return
     */
    @Provides
    @Singleton
    @Override
    public Converter.Factory provideFactory() {
        return convertFactory;
    }

    /**
     * provide OkHttpClient
     *
     * @return custom OkHttpClient
     */
    @Provides
    @Singleton
    @Override
    public OkHttpClient provideOkHttpClient() {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Config.ACCEPT, Config.ACCEPT_JSON));
        propertyList.add(new Property(Config.USER_AGENT, Config.USER_AGENT_VALUE));
        if (properties != null && properties.size() > 0) {
            propertyList.addAll(properties);
        }
        OkHttpClient client = new OKClient.Builder()
                .addHeaders(propertyList)
                .setTimeOut(timeOut).build();
        return client;
    }

    /**
     * provide Retrofit
     *
     * @param okhttpClient   custom OkHttpClient
     * @param baseUrl
     * @param convertFactory
     * @return
     */
    @Provides
    @Singleton
    @Override
    public Retrofit provideRetrofit(OkHttpClient okhttpClient, @NonNull String baseUrl, @NonNull Converter.Factory convertFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(convertFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okhttpClient)
                .build();
        return retrofit;
    }

    public static class Builder {

        private String baseUrl;

        private TimeOut timeOut;

        private List<Property> properties;

        private Converter.Factory convertFactory;

        public Builder() {

        }

        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder timeOut(@NonNull TimeOut timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder headers(@Nullable List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Builder factory(@Nullable Converter.Factory convertFactory) {
            this.convertFactory = convertFactory;
            return this;
        }

        public HttpModule build() {
            return new HttpModule(this);
        }

    }

}
