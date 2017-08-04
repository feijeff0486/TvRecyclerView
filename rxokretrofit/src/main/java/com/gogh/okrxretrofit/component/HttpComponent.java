package com.gogh.okrxretrofit.component;

import com.gogh.okrxretrofit.HttpClient;
import com.gogh.okrxretrofit.http.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 3/28/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 3/28/2017 do fisrt create. </li>
 */
@Singleton
@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(HttpClient httpClient);
}
