package com.android.expandview;

import android.content.Context;

import com.android.expandview.provider.biz.UserState;
import com.android.expandview.uitls.Screen;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 我的 页面的功能入口，需要通过 {@link #init(Context)} 初始化</p>
 * <p> Created by <b>高晓峰</b> on 10/19/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/19/2017 do fisrt create. </li>
 */

public class MineTab {

    public static MineTab get() {
        return SingleHolder.HOLDER;
    }

    private static final class SingleHolder {
        private static final MineTab HOLDER = new MineTab();
    }

    /**
     * 用于初始化 我的 页面的相关信息
     * @author 高晓峰
     * @date 10/19/2017
     * @param context
     * @ChangeLog:
     * <li> 高晓峰 on 10/19/2017 </li>
     */
    public void init(Context context){
        Screen.get().init(context);
        UserState.getInstance().notifyReFreshUserState(context);
    }
}
