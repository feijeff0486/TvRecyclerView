package com.android.expandview.app;

import android.content.Context;
import android.view.View;

import com.android.expandview.adapter.DynamicAdapter;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 我的 导航模块下提供的列表</p>
 * <p> Created by <b>高晓峰</b> on 10/16/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/16/2017 do fisrt create. </li>
 */

public interface ViewManager {

    /**
     * 获取历史记录列表
     *
     * @param context
     * @author 高晓峰
     * @date 10/16/2017
     * @ChangeLog: <li> 高晓峰 on 10/16/2017 </li>
     */
    View getHistoryView(Context context);

    /**
     * 获取用户中心View
     *
     * @param context
     * @param userName    用户名
     * @param phoneNumber 手机号
     * @ChangeLog: <li> 高晓峰 on 10/16/2017 </li>
     * @author 高晓峰
     * @date 10/16/2017
     */
    View getMemberCenterView(Context context, String userName, String phoneNumber) throws IllegalArgumentException;

    /**
     * 获取系统应用的列表
     *
     * @param context
     * @author 高晓峰
     * @date 10/16/2017
     * @ChangeLog: <li> 高晓峰 on 10/16/2017 </li>
     */
    View getSystemAppView(Context context);

    /**
     * 获取自定义积木列表
     *
     * @param context
     * @param dynamicAdapter 列表适配器
     * @ChangeLog: <li> 高晓峰 on 10/16/2017 </li>
     * @author 高晓峰
     * @date 10/16/2017
     */
    View getDynamicView(Context context, DynamicAdapter dynamicAdapter) throws IllegalStateException;
}
