package com.android.expandview.provider;

import android.content.Context;

import com.gogh.okrxretrofit.request.OnResponseListener;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/16/2017 do fisrt create. </li>
 */

interface IProvider {

    void getDatas(Context context, OnResponseListener onResponseListener);

}
