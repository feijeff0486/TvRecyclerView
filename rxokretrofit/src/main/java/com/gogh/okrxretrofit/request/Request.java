package com.gogh.okrxretrofit.request;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/3/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/3/2017 do fisrt create. </li>
 */

public class Request<T> {

    public static Request get() {
        return SingleHolder.HOLDER;
    }

    public void request(Observable<T> observable, OnResponseListener<T> onResponseListener) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        if (onResponseListener != null) {
                            onResponseListener.onNext(t);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onResponseListener != null) {
                            onResponseListener.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (onResponseListener != null) {
                            onResponseListener.onCompleted();
                        }
                    }
                });
    }

    private static final class SingleHolder {
        private static final Request HOLDER = new Request();
    }
}
