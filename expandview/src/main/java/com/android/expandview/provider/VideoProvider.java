package com.android.expandview.provider;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.expandview.bean.VideoRecordEntity;
import com.android.expandview.provider.biz.IQiYiHistory;
import com.android.expandview.provider.biz.PengYunHistory;
import com.android.expandview.service.ProviderThreadPool;
import com.gogh.okrxretrofit.request.OnResponseListener;
import com.gogh.okrxretrofit.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/16/2017 do fisrt create. </li>
 */

public class VideoProvider implements IProvider {

    private static final String TAG = "VideoProvider";

    private OnResponseListener onResponseListener;

    private Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<VideoRecordEntity> videoRecordEntities = (List<VideoRecordEntity>) msg.obj;
                if (videoRecordEntities != null && videoRecordEntities.size() > 0) {
                    onResponseListener.onNext(videoRecordEntities);
                    onResponseListener.onCompleted();
                } else {
                    onResponseListener.onError(new Throwable("Not found iqiyi videovs history."));
                }
            }
        }
    };

    public static VideoProvider get() {
        return SingleHolder.HOLDER;
    }

    @Override
    public void getDatas(final Context context, final OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
        ProviderThreadPool.get().execute(new Runnable() {
            @Override
            public void run() {
                List<VideoRecordEntity> videoRecordEntities = new ArrayList<>();
                List<VideoRecordEntity> qiyiVideo = IQiYiHistory.getHistoryList(context, "0", String.valueOf(System.currentTimeMillis()));
                List<VideoRecordEntity> pyVideo = PengYunHistory.getHistoryList(context, "0", String.valueOf(System.currentTimeMillis()));
                Logger.d(TAG, "qiyiVideo " + qiyiVideo.toString());
                Logger.d(TAG, "pyVideo " + pyVideo.toString());
                if (qiyiVideo != null && qiyiVideo.size() > 0) {
                    videoRecordEntities.addAll(qiyiVideo);
                }
                if (pyVideo != null && pyVideo.size() > 0) {
                    videoRecordEntities.addAll(pyVideo);
                }
                Collections.sort(videoRecordEntities);
                if (onResponseListener != null) {
                    Message message = mUiHandler.obtainMessage(1, videoRecordEntities);
                    mUiHandler.sendMessage(message);
                } else {
                    throw new NullPointerException("Please set your OnResponseListener for callback.");
                }
            }
        });
    }

    private static final class SingleHolder {
        private static final VideoProvider HOLDER = new VideoProvider();
    }
}
