package com.gogh.okrxretrofit.conf;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 设置超时时间，单位为秒</p>
 * <p> Created by <b>高晓峰</b> on 8/3/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/3/2017 do fisrt create. </li>
 */

public class TimeOut {

    /**
     * unit  second
     */
    private long connectTimeout = Config.CONNECT_TIMEOUT;
    private long writeTimeout = Config.WRITE_TIMEOUT;
    private long readTimeout = Config.READ_TIMEOUT;

    public TimeOut() {
    }

    public TimeOut(long connectTimeout, long writeTimeout, long readTimeout) {
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
        if (writeTimeout > 0) {
            this.writeTimeout = writeTimeout;
        }
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        if (writeTimeout > 0) {
            this.writeTimeout = writeTimeout;
        }
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    @Override
    public String toString() {
        return "TimeOut{" +
                "connectTimeout=" + connectTimeout +
                ", writeTimeout=" + writeTimeout +
                ", readTimeout=" + readTimeout +
                '}';
    }

}
