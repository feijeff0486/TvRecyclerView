package com.gogh.okrxretrofit.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.okrxretrofit.conf.Property;
import com.gogh.okrxretrofit.conf.TimeOut;
import com.gogh.okrxretrofit.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 本地自定义httpclient，可以通过内部类Builder，添加不同的header值</p>
 * <p> Created by <b>高晓峰</b> on 103/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 03/28/2017 do fisrt create. </li>
 */
class OKClient {

    private static final String TAG = "OKClient";

    @Nullable
    private List<Property> properties;

    @Nullable
    private TimeOut timeOut;

    private OKClient() {
    }

    protected static OKClient get() {
        return SingleHolder.CLIENT;
    }

    @NonNull
    protected okhttp3.OkHttpClient build(@Nullable Builder builder) {
        if (null != builder) {
            if(builder.timeOut == null){
                timeOut = new TimeOut();
            } else {
                this.timeOut = builder.timeOut;
            }

            this.properties = builder.properties;
        }

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.debug(TAG, "OkHttp message : " + message);
            }
        });
        loggingInterceptor.setLevel(level);

        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

        httpClient
                .addInterceptor(chain -> {

                    Request original = chain.request();
                    Request.Builder config = original.newBuilder();

                    if (null != properties) {
                        for (Property property : properties) {
                            config.addHeader(property.getKey(), property.getValue());
                        }
                    }

                    Request request = config.method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);
                    return response;
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(timeOut.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(timeOut.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(timeOut.getReadTimeout(), TimeUnit.SECONDS);

        return httpClient.build();
    }

    private static final class SingleHolder {
        private static final OKClient CLIENT = new OKClient();
    }

    /**
     * 内部类，用于给http请求添加header属性
     */
    static class Builder {

        @Nullable
        private List<Property> properties = new ArrayList<>();

        @Nullable
        private TimeOut timeOut;

        Builder() {
        }

        @NonNull
        Builder addHeaders(@Nullable List<Property> propertyList) {
            if (null != propertyList && propertyList.size() > 0) {
                properties.addAll(propertyList);
            }
            return this;
        }

        @NonNull
        Builder addHeader(@Nullable Property property) {
            if (null != property) {
                properties.add(property);
            }
            return this;
        }

        @NonNull
        Builder setTimeOut(@Nullable TimeOut timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        @NonNull
        okhttp3.OkHttpClient build() {
            return OKClient.get().build(this);
        }
    }

}
