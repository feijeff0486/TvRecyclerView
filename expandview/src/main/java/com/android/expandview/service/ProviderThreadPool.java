package com.android.expandview.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/16/2017 do fisrt create. </li>
 */

public class ProviderThreadPool {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ProviderThreadPool #" + mCount.getAndIncrement());
        }
    };

    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        THREAD_POOL_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    public static ProviderThreadPool get() {
        return SingleHolder.HOLDER;
    }

    public void execute(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public void shutDown(boolean isNow) {
        if (isNow) {
            THREAD_POOL_EXECUTOR.shutdownNow();
        } else {
            THREAD_POOL_EXECUTOR.shutdown();
        }
    }

    private static final class SingleHolder {
        private static final ProviderThreadPool HOLDER = new ProviderThreadPool();
    }
}
