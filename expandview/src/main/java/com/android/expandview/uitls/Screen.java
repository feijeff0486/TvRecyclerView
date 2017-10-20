package com.android.expandview.uitls;

import android.content.Context;
import android.util.DisplayMetrics;

import com.gogh.okrxretrofit.util.Logger;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 屏幕尺寸</p>
 * <p> Created by <b>高晓峰</b> on 7/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 7/27/2017 do fisrt create. </li>
 */

public class Screen {

    private static final String TAG = "Screen";

    private static int HEIGHT;
    private static int WIDTH;
    private static float DENSITY;
    private static float SCALE_DENSITY;

    public static Screen get() {
        return SingleHolder.HOLDER;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static float getDensity() {
        return DENSITY;
    }

    public static float getScaleDensity() {
        return SCALE_DENSITY;
    }

    public void init(Context context) {
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        // 屏幕宽度
        WIDTH = mDisplayMetrics.widthPixels;
        // 屏幕高度
        HEIGHT = mDisplayMetrics.heightPixels;
        // 像素密度
        DENSITY = mDisplayMetrics.density;
        // 缩放密度
        SCALE_DENSITY = mDisplayMetrics.scaledDensity;

        Logger.d(TAG, "Screen size : widthPixels = " + WIDTH
                + ", heightPixels = " + HEIGHT
                + ", density = " + DENSITY
                + ", scaledensity = " + SCALE_DENSITY);
    }

    private static final class SingleHolder {
        private static final Screen HOLDER = new Screen();
    }
}
