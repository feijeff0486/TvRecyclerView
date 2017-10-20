package com.android.expandview.adapter;

import android.view.View;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 自定义布局的适配器</p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public interface DynamicAdapter<T> {

    /**
     * 获取item的layout
     * @author 高晓峰
     * @date 10/19/2017
     * @ChangeLog:
     * <li> 高晓峰 on 10/19/2017 </li>
     */
    int getLayoutId();

    /**
     * 返回当前的ViewHolder
     * @author 高晓峰
     * @date 10/19/2017
     * @param item
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    T getViewHolder(View item);

    /**
     * 获取列表的数量
     * @author 高晓峰
     * @date 10/19/2017
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    int getItemCount();

    /**
     * 设置当前列表所在 group 根布局，用于焦点的处理
     * @author 高晓峰
     * @date 10/19/2017
     * @param view 容器
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    void onContanier(View view);

    /**
     * 数据绑定
     * @author 高晓峰
     * @date 10/19/2017
     * @param t 数据类型
     * @param position 索引值
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    void onBindView(T t, int position);

    /**
     * 调整view的属性
     * @author 高晓峰
     * @date 10/19/2017
     * @param itemtView item
     * @param position 索引值
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    void resetItemParams(View itemtView, int position);

    /**
     * 当item可见时
     * @author 高晓峰
     * @date 10/19/2017
     * @param t 数据类型
     * @param itemtView item
     * @param position 索引值
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    void onViewAttachedToWindow(T t, View itemtView, int position);

    /**
     * 当item不可见时
     * @author 高晓峰
     * @date 10/19/2017
     * @param t 数据类型
     * @param itemtView item
     * @param position 索引值
     * @ChangeLog:
     * <li> 高晓峰 on  10/19/2017 </li>
     */
    void onViewDetachedFromWindow(T t, View itemtView, int position);

}
