package com.android.expandview.app;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 数据加载管理器，包含初始化加载、加载更多、刷新和间接加载</p>
 * <p> Created by <b>高晓峰</b> on 6/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 6/16/2017 do fisrt create. </li>
 */

public interface LoadManager<T> {

    /**
     * 初始化加载
     */
    int LOAD_INIT = 0x110120;

    /**
     * 加载更多
     */
    int LOAD_MORE = 0x110121;

    /**
     * 刷新
     */
    int LOAD_REFRESH = 0x110122;

    /**
     * 直接使用外部提供的数据
     *
     * @param t 数据源
     */
    void next(T t);

    /**
     * 初始化加载
     */
    void load();

    /**
     * 加载更多
     */
    void loadMore();

    /**
     * 刷新
     */
    void refresh();

}
