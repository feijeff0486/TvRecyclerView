package com.android.expandview.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: recyclerview 的适配器基类</p>
 * <p> Created by <b>高晓峰</b> on 6/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 6/12/2017 do fisrt create. </li>
 */

public abstract class BaseApdater<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 获取item的layout id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 绑定数据到列表
     *
     * @param dataList 列表数据
     * @author 高晓峰
     * @date 7/17/2017
     * @ChangeLog: <li> 高晓峰 on 7/17/2017 </li>
     */
    public abstract void setData(List<T> dataList);

    /**
     * 刷新列表
     *
     * @param dataList  新的数据
     * @author 高晓峰
     * @date 7/17/2017
     * @ChangeLog: <li> 高晓峰 on 7/17/2017 </li>
     */
    public abstract void refresh(List<T> dataList);

    /**
     * 加载更多
     *
     * @param dataList 水平列表数据
     * @author 高晓峰
     * @date 7/17/2017
     * @ChangeLog: <li> 高晓峰 on 7/17/2017 </li>
     */
    public abstract void loadmore(List<T> dataList);

    /**
     * 设置当前列表的容器
     * @author 高晓峰
     * @date 10/19/2017
     * @param containerView 容器
     * @ChangeLog:
     * <li> 高晓峰 on 10/19/2017 </li>
     */
    public abstract void setContainerView(View containerView);

}
