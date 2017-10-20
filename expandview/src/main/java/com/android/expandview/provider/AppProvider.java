package com.android.expandview.provider;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.expandview.bean.AppStoreEntity;
import com.android.expandview.provider.biz.AppHistory;
import com.android.expandview.service.ProviderThreadPool;
import com.gogh.okrxretrofit.request.OnResponseListener;
import com.gogh.okrxretrofit.util.Logger;

import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/16/2017 do fisrt create. </li>
 */

public class AppProvider implements IProvider {

    private static final String TAG = "AppProvider";

    private OnResponseListener onResponseListener;

    private Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<AppStoreEntity> appStoreEntities = (List<AppStoreEntity>) msg.obj;
                if (appStoreEntities != null && appStoreEntities.size() > 0) {
                    onResponseListener.onNext(appStoreEntities);
                    onResponseListener.onCompleted();
                } else {
                    onResponseListener.onError(new Throwable("Not found apps history."));
                }
            }
        }
    };

    public static AppProvider get() {
        return SingleHolder.HOLDER;
    }

    @Override
    public void getDatas(final Context context, final OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
        ProviderThreadPool.get().execute(new Runnable() {
            @Override
            public void run() {
                List<AppStoreEntity> appStoreEntities = AppHistory.getAppHistoryList(context, "0", String.valueOf(System.currentTimeMillis()));
                Logger.d(TAG, "appStoreEntities : " + appStoreEntities.toString());
                if (onResponseListener != null) {
                    Message message = mUiHandler.obtainMessage(1, appStoreEntities);
                    mUiHandler.sendMessage(message);
                } else {
                    throw new NullPointerException("Please set your OnResponseListener for callback.");
                }
            }
        });
    }

    private static final class SingleHolder {
        private static final AppProvider HOLDER = new AppProvider();
    }
}
